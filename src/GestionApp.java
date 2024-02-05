import java.sql.Timestamp;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.exercice1.NomExists;
import com.exercice1.Validation;
import com.exercice2.Conn;
import com.exercice3.CRUD;
import com.exercice3.IOT_time_series;
import com.exercice4.ObjetConnecte;
import com.exercice4.Simulator;
import com.exercice4.Capteur;
import com.exercice4.Actuateur;

public class GestionApp {
    public static void main(String args[]) {
        try (Conn conn = new Conn()) {

            Scanner in = new Scanner(System.in);
            Stack<ObjetConnecte> dataStack = new Stack<>();
            Queue<ObjetConnecte> dataQueue = new LinkedList<>();
            Simulator sim = new Simulator();

            while (true) {
                try {
                    System.out.println("\nVeuillez selectionner une option :");
                    System.out.println("1. Ajouter un nouvel appareil");
                    System.out.println("2. Verify des donnees et sauvegarde");
                    System.out.println("3. Retirer un appareil");
                    System.out.println("4. Modifier un appareil");
                    System.out.println("5. Afficher tous les appareil");
                    System.out.println("6. Afficher temperatures croissantes");
                    System.out.println("7. Afficher temperatures decroissantes");
                    System.out.println("8. Sortie");
                    System.out.println("");
                    System.out.print("Saisissez votre choix : ");
                    int mainChoix = in.nextInt();
                    in.nextLine();

                    System.out.println("");

                    switch (mainChoix) {
                        case 1:
                            System.out.println("=========================================");
                            System.out.println("Entrez les informations pour l'appareil");
                            System.out.println("=========================================");

                            String nom = Validation.nomInput(in);
                            String deviceid = Validation.deviceIDInput(in);
                            String type = Validation.typeInput(in);
                            Date currentDate = new Date();
                            Timestamp date = new Timestamp(currentDate.getTime());
                            String status = Validation.statusInput(in);

                            System.out.println("\nVoulez-vous ajouter un capteur (1) ou un actionneur (2) ?");
                            int appareilType = in.nextInt();
                            in.nextLine();

                            if (appareilType == 1) {
                                String typeMesure = Validation.typeMesureInput(in);
                                Double reading_value = sim.readTemperature();;

                                System.out.println("=========================================");
                                System.out.println("Informations sur le capteur insere");
                                System.out.println("=========================================");

                                ObjetConnecte capteur = new Capteur(nom, deviceid, type, typeMesure, reading_value, date, status);
                                capteur.capteurInfo();
                                ((Capteur) capteur).mesurer(sim);
                                dataStack.push(capteur);

                            } else if (appareilType == 2) {
                                String typeAction = Validation.typeActionInput(in);
                                String actuation_status = Validation.actuation_statusInput(in);

                                System.out.println("=========================================");
                                System.out.println("Informations sur l'actionneur insere");
                                System.out.println("=========================================");

                                ObjetConnecte actuateur = new Actuateur(nom, deviceid, type, typeAction, date, actuation_status, status);
                                actuateur.actionneurInfo();
                                dataQueue.offer(actuateur);
                                

                            } else {
                                System.out.println(
                                        "Choix non valide. Veuillez saisir (1) pour capteur ou (2) pour actionneur.");
                            }

                            break;

                        case 2:
                            System.out.println("\n=== Donnees dans le Pile ===");
                            for (ObjetConnecte obj : dataStack) {
                                if (obj instanceof Capteur) {
                                    ((Capteur) obj).capteurInfo();
                                    System.out.println("\n");
                                }
                            }

                            System.out.println("\n=== Donnees dans le Queue ===");
                            for (ObjetConnecte obj : dataQueue) {
                                if (obj instanceof Actuateur) {
                                    ((Actuateur) obj).actionneurInfo();
                                    System.out.println("\n");
                                }
                            }

                            System.out.print("\n\nVoulez-vous confirmer l'insertion dans la base de donnees ? (1) Oui ou (2) Non : ");
                            int confirmation = in.nextInt();
                            in.nextLine();

                            if (confirmation == 1) {
                                if (!dataStack.isEmpty() || !dataQueue.isEmpty()) {
                                    while (!dataStack.isEmpty()) {
                                        ObjetConnecte obj = dataStack.pop();
                                        if (obj instanceof Capteur) {
                                            int objetId = CRUD.insertObjetConnecte(obj.nom, obj.deviceID, obj.type, obj.typeMesure, obj.typeAction);
                                            CRUD.insertCapteur(objetId, obj.status, obj.reading_value, obj.timestamp);
                                        }
                                    }

                                    while (!dataQueue.isEmpty()) {
                                        ObjetConnecte obj = dataQueue.poll();
                                        if (obj instanceof Actuateur) {
                                            int objetId = CRUD.insertObjetConnecte(obj.nom, obj.deviceID, obj.type, obj.typeMesure, obj.typeAction);
                                            CRUD.insertActionneur(objetId, obj.status, obj.timestamp);
                                        }
                                    }

                                    dataStack.clear();
                                    dataQueue.clear();

                                    System.out.println("\nDonnees inserees dans la base de donnees avec succes !");
                                } else {
                                    System.out.println("Erreur : Aucune donnee a enregistrer dans la base de donnees.");
                                }
                            } else {
                                System.out.println(
                                        "Insertion annulee. Les donnees ne sont pas inserees dans la base de donnees.");
                            }

                            break;

                        case 3:
                            System.out.print("Entrez le nom de l'appareil a supprimer : ");

                            String nomToDelete = in.nextLine();
                            if (!NomExists.nomExists(nomToDelete)) {
                                System.out.println("Appareil inexistant. La mise a jour a echoue.");
                            } else {
                                CRUD.deleteData(nomToDelete);
                            }
                            break;

                        case 4:
                            System.out.print("Entrez l'ancien nom de l'appareil : ");

                            String oldnom = in.nextLine();

                            if (!NomExists.nomExists(oldnom)) {
                                System.out.println("Appareil inexistant. La mise a jour a echoue.");
                            } else {
                                String newNom = Validation.nomInput(in);
                                String newdeviceid = Validation.deviceIDInput(in);
                                String newType = Validation.typeInput(in);
                                String newtypeMesure = Validation.typeInput(in);
                                String newtypeAction = Validation.typeInput(in);
                                String newStatus = Validation.statusInput(in);

                                CRUD.updateData(oldnom, newNom, newdeviceid, newType, newtypeMesure, newtypeAction,
                                        newStatus);
                            }
                            break;

                        case 5:
                            CRUD.readData();
                            break;

                        case 6:
                            IOT_time_series.tempDataASC();
                            break;

                        case 7:
                            IOT_time_series.tempDataDESC();
                            break;

                        case 8:
                            System.out.println("Fin du programme.");
                            System.out.println("");
                            System.exit(0);
                            in.close();
                            break;

                        default:
                            System.out.println("Choix non valide. Veuillez saisir une option valide.");
                            System.out.println("");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Veuillez saisir un nombre entier.");
                    in.nextLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}