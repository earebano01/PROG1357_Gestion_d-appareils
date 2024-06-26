import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.exercice1.NomExists;
import com.exercice1.Validation;
import com.exercice2.Conn;
import com.exercice3.CRUD;
import com.exercice4.ObjetConnecte;
import com.exercice4.Simulator;
import com.exercice4.Capteur;
import com.exercice4.Actuateur;
import com.exercice5.DataApiHandler;
import com.google.gson.JsonObject;


public class GestionApp {
    public static void main(String args[]) {
        try (Conn conn = new Conn()) {
        Stack<List<JsonObject>> dataReceived = new Stack<>();
        DataApiHandler dataApiHandler = new DataApiHandler(dataReceived);
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", 8080);
        HttpServer server = HttpServer.create(address, 0);
        server.createContext("/api/data", dataApiHandler);
        server.start();
        System.out.println("Server started at " + address);

        Scanner in = new Scanner(System.in);
        Stack<ObjetConnecte> dataStack = new Stack<>();
        Queue<ObjetConnecte> dataQueue = new LinkedList<>();
        Simulator sim = new Simulator();

            while (true) {
                try {
                    System.out.println("\nVeuillez selectionner une option :");
                    System.out.println("1. Voir les données disponibles");
                    System.out.println("2. Ajouter un nouvel appareil");
                    System.out.println("3. Verification des donnees et sauvegarde");
                    System.out.println("4. Retirer un appareil");
                    System.out.println("5. Modifier un appareil");
                    System.out.println("6. Afficher tous les appareil");
                    System.out.println("7. Sortie");
                    System.out.println("");
                    System.out.print("Saisissez votre choix : ");
                    int mainChoix = in.nextInt();
                    in.nextLine();

                    System.out.println("");

                    switch (mainChoix) {
                        case 1:
                        System.out.println(dataApiHandler.viewAvailableData());
                        break;

                        case 2:
                            System.out.println("=========================================");
                            System.out.println("Entrez les informations pour l'appareil");
                            System.out.println("=========================================");

                            String nom = Validation.nomInput(in);
                            String type = Validation.typeInput(in);
                            LocalDate date = LocalDate.now();
                            LocalTime time = LocalTime.now();
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                            String formattedDate = date.format(dateFormatter);
                            String formattedTime = time.format(timeFormatter);
                            String status = Validation.statusInput(in);

                            String jsonDataFromApi = dataApiHandler.viewAvailableData();
                    
                            if (jsonDataFromApi.isEmpty() || jsonDataFromApi.equals("Aucune donnee disponible. Veuillez reessayer apres avoir recu des donnees.")) {
                                System.out.println("Les donnees JSON sont vides ou null. Veuillez reessayer.");
                                return;
                            }

                            System.out.println("\nVoulez-vous ajouter un capteur (1) ou un actionneur (2) ?");
                            int appareilType = in.nextInt();
                            in.nextLine();
                            String typeMesure = "";
                            String typeAction = "";    
                            String deviceid = "";
                            Double temperature = 0.0;
                            Double humidity = 0.0;
                            int sound = 0;
                            Double distance = 0.0;
                            int photoresistor = 0;
                            String value = "";

                            if (appareilType == 1) {
                                System.out.println("\nMenu Type de Mesure :");
                                System.out.println("1. Temperature");
                                System.out.println("2. Humidite");
                                System.out.println("3. Temperature & Humidite");
                                System.out.println("4. Son");
                                System.out.println("5. Distance");
                                System.out.println("6. Lumiere");
                                System.out.print("\nEntrez votre choix entre 1 a 6 : ");
                            
                                int mesureChoice = in.nextInt();
                                in.nextLine();
                            
                            
                            if (!jsonDataFromApi.isEmpty()) {
                                switch (mesureChoice) {
                                    case 1:
                                        typeMesure = "Temperature";
                                        JsonObject temperatureData = dataApiHandler.exTempData(jsonDataFromApi);
                                        if (temperatureData != null) {
                                            temperature = temperatureData.get("temperature").getAsDouble();
                                            deviceid = temperatureData.get("deviceid").getAsString();
                                        }
                                        break;
                                    case 2:
                                        typeMesure = "Humidite";
                                        JsonObject humidityData = dataApiHandler.extHumData(jsonDataFromApi);
                                        if (humidityData != null) {
                                            humidity = humidityData.get("humidity").getAsDouble();
                                            deviceid = humidityData.get("deviceid").getAsString();
                                        }
                                        break;
                                    case 3:
                                        typeMesure = "Temperature & Humidite";
                                        JsonObject temperatureHumidityData = dataApiHandler.extTempHumData(jsonDataFromApi);
                                        if (temperatureHumidityData != null) {
                                            temperature = temperatureHumidityData.get("temperature").getAsDouble();
                                            humidity = temperatureHumidityData.get("humidity").getAsDouble();
                                            deviceid = temperatureHumidityData.get("deviceid").getAsString();
                                        }
                                        break;
                                    case 4:
                                        typeMesure = "Son";
                                        JsonObject soundData = dataApiHandler.exSonData(jsonDataFromApi);
                                        if (soundData != null) {
                                            sound = soundData.get("sound").getAsInt();
                                            deviceid = soundData.get("deviceid").getAsString();
                                        }
                                        break;

                                    case 5:
                                        typeMesure = "Distance";
                                        break;

                                    case 6:
                                        typeMesure = "Lumiere";
                                        JsonObject lightData = dataApiHandler.exPhotoData(jsonDataFromApi);
                                        if (lightData != null) {
                                            photoresistor = lightData.get("photoresistor").getAsInt();
                                            deviceid = lightData.get("deviceid").getAsString();
                                        }
                                        break;
                                    default:
                                        System.out.println("Choix non valide. Veuillez choisir entre 1 a 6.");
                                        return;
                                }
                            }
                                System.out.println("\n=========================================");
                                System.out.println("Informations sur le capteur insere");
                                System.out.println("=========================================");
                    
                                ObjetConnecte capteur = new Capteur(nom, deviceid, type, typeMesure, temperature, humidity, sound, distance, photoresistor, formattedDate, formattedTime, status, value);
                                capteur.capteurInfo();
                                ((Capteur) capteur).mesurer(sim);
                                dataStack.push(capteur);

                            } else if (appareilType == 2) {
                                System.out.println("\nMenu Type d'Action :");
                                System.out.println("1. Allumer / Eteindre");
                                System.out.println("2. Ouvrir / Fermer");
                                System.out.println("3. Augmenter / Diminuer");
                                System.out.println("4. Activer / Desactiver");
                                System.out.println("5. Tourner / Incliner");
                                System.out.println("6. Positionner / Reinitialiser");
                                System.out.print("\nEntrez votre choix entre 1 et 6 : ");
                                
                                int actionChoice = in.nextInt();
                                in.nextLine();
                                                         
                                switch (actionChoice) {
                                    case 1:
                                        typeAction = "Allumer / Eteindre";
                                        JsonObject ledStatus = dataApiHandler.exLedStatus(jsonDataFromApi);
                                        if (ledStatus != null) {
                                            value = ledStatus.get("led").getAsString();
                                        }
                                        break;
                                    case 2:
                                        typeAction = "Ouvrir / Fermer";
                                        JsonObject relayStatus = dataApiHandler.exRelayStatus(jsonDataFromApi);
                                        if (relayStatus != null) {
                                            value = relayStatus.get("relay").getAsString();
                                        }
                                        break;
                                    case 3:
                                        typeAction = "Augmenter / Diminuer";
                                        break;
                                    case 4:
                                        typeAction = "Activer / Desactiver";
                                        break;
                                    case 5:
                                        typeAction = "Tourner / Incliner";
                                        break;
                                    case 6:
                                        typeAction = "Positionner / Reinitialiser";
                                        break;
                                    default:
                                        System.out.println("Choix non valide. Veuillez choisir entre 1 et 6.");
                                        return;
                                }

                                System.out.println("=========================================");
                                System.out.println("Informations sur l'actionneur insere");
                                System.out.println("=========================================");

                                ObjetConnecte actuateur = new Actuateur(nom, deviceid, type, typeAction, formattedDate, formattedTime, status, value);
                                actuateur.actionneurInfo();
                                dataQueue.offer(actuateur);

                            } else {
                                System.out.println(
                                        "Choix non valide. Veuillez saisir (1) pour capteur ou (2) pour actionneur.");
                            }

                            break;

                        case 3:
                            System.out.println("\n=== Donnees dans le Pile ===");
                            for (ObjetConnecte obj : dataStack) {
                                if (obj instanceof Capteur) {
                                    ((Capteur) obj).capteurAll();
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

                            System.out.print("\nVoulez-vous confirmer l'insertion dans la base de donnees ? (1) Oui ou (2) Non : ");
                            int confirmation = in.nextInt();
                            in.nextLine();

                            if (confirmation == 1) {
                                if (!dataStack.isEmpty() || !dataQueue.isEmpty()) {
                                    while (!dataStack.isEmpty()) {
                                        ObjetConnecte obj = dataStack.pop();
                                        if (obj instanceof Capteur) {
                                            int objetId = CRUD.insertObjetConnecte(obj.nom, obj.deviceID, obj.type, obj.typeMesure, obj.typeAction);
                                            CRUD.insertCapteur(objetId, obj.status, obj.temperature, obj.humidite, obj.son, obj.distance, obj.lumiere, obj.formattedDate, obj.formattedTime);
                                        }
                                    }

                                    while (!dataQueue.isEmpty()) {
                                        ObjetConnecte obj = dataQueue.poll();
                                        if (obj instanceof Actuateur) {
                                            int objetId = CRUD.insertObjetConnecte(obj.nom, obj.deviceID, obj.type, obj.typeMesure, obj.typeAction);
                                            CRUD.insertActionneur(objetId, obj.status, obj.formattedDate, obj.formattedTime);
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

                        case 4:
                            System.out.print("Entrez le nom de l'appareil à supprimer : ");
                            String nomToDelete = in.nextLine();
                            if (!NomExists.nomExists(nomToDelete)) {
                                System.out.println("Appareil inexistant. La suppression a échoué.");
                            } else {
                                CRUD.deleteData(nomToDelete);
                            }
                            
                            break;

                        case 5:
                            System.out.println("\nVoulez-vous modifier un capteur (1) ou un actionneur (2) ?");
                            int majChoix = in.nextInt();
                            in.nextLine();

                            String newNom = "", newdeviceid = "", newType = "", newTypeMesure = "", newTypeAction = "";

                            if (majChoix == 1) {
                                System.out.print("Entrez l'ancien nom du Capteur : ");
                                String oldnom = in.nextLine();

                                if (!NomExists.nomExists(oldnom)) {
                                    System.out.println("Capteur inexistant. La mise a jour a echoue.");
                                } else {
                                    newNom = Validation.nomInput(in);
                                    newdeviceid = Validation.deviceIDInput(in);
                                    newType = Validation.typeInput(in);

                                    System.out.println("\nMenu Type de Mesure :");
                                    System.out.println("1. Temperature");
                                    System.out.println("2. Humidite");
                                    System.out.println("3. Temperature & Humidite");
                                    System.out.println("4. Son");
                                    System.out.println("5. Distance");
                                    System.out.println("6. Lumiere");
                                    System.out.print("\nEntrez votre choix entre 1 a 6 : ");

                                    int mesureChoice = in.nextInt();
                                    in.nextLine();

                                    switch (mesureChoice) {
                                        case 1:
                                            newTypeMesure = "Temperature";
                                            break;
                                        case 2:
                                            newTypeMesure = "Humidite";
                                            break;
                                        case 3:
                                            newTypeMesure = "Temperature & Humidite";
                                            break;
                                        case 4:
                                            newTypeMesure = "Son";
                                            break;
                                        case 5:
                                            newTypeMesure = "Distance";
                                            break;
                                        case 6:
                                            newTypeMesure = "Lumiere";
                                            break;
                                        default:
                                            System.out.println("Choix non valide. Veuillez choisir entre 1 a 6.");
                                            return;
                                    }
                                }
                                CRUD.updateCapteur(oldnom, newNom, newdeviceid, newType, newTypeMesure);

                            } else if (majChoix == 2) {
                                System.out.print("Entrez l'ancien nom de l'actionneur : ");
                                String oldnom = in.nextLine();

                                if (!NomExists.nomExists(oldnom)) {
                                    System.out.println("Actionneur inexistant. La mise a jour a echoue.");
                                } else {
                                    newNom = Validation.nomInput(in);
                                    newdeviceid = Validation.deviceIDInput(in);
                                    newType = Validation.typeInput(in);

                                    System.out.println("\nMenu Type d'Action :");
                                    System.out.println("1. Allumer / Eteindre");
                                    System.out.println("2. Ouvrir / Fermer");
                                    System.out.println("3. Augmenter / Diminuer");
                                    System.out.println("4. Activer / Desactiver");
                                    System.out.println("5. Tourner / Incliner");
                                    System.out.println("6. Positionner / Reinitialiser");
                                    System.out.print("\nEntrez votre choix entre 1 et 6 : ");

                                    int actionChoice = in.nextInt();
                                    in.nextLine();

                                    switch (actionChoice) {
                                        case 1:
                                            newTypeAction = "Allumer / Eteindre";
                                            break;
                                        case 2:
                                            newTypeAction = "Ouvrir / Fermer";
                                            break;
                                        case 3:
                                            newTypeAction = "Augmenter / Diminuer";
                                            break;
                                        case 4:
                                            newTypeAction = "Activer / Desactiver";
                                            break;
                                        case 5:
                                            newTypeAction = "Tourner / Incliner";
                                            break;
                                        case 6:
                                            newTypeAction = "Positionner / Reinitialiser";
                                            break;
                                        default:
                                            System.out.println("Choix non valide. Veuillez choisir entre 1 et 6.");
                                            return;
                                    }

                                    CRUD.updateActuateur(oldnom, newNom, newdeviceid, newType, newTypeAction);
                                }
                            } else {
                                System.out.println(
                                        "Choix non valide. Veuillez choisir 1 (capteur) ou 2 (actionneur).");
                            }
                        break;
                            
                        case 6:
                            System.out.println("\nQuel tableau voulez-vous voir ?");
                            System.out.println("1. Objet Connecte");
                            System.out.println("2. Capteur");
                            System.out.println("3. Actionneur");
                            System.out.print("\nEntrez votre choix entre 1 et 3 : ");
                            int lireChoix = in.nextInt();
                            in.nextLine();
                            
                            switch (lireChoix) {
                                case 1:
                                    CRUD.readOB();
                                    break;
                                case 2:
                                    CRUD.readCA();
                                    break;
                                case 3:
                                    CRUD.readAC();
                                    break;
                                default:
                                    System.out.println("Choix non valide. Veuillez choisir entre 1 et 3.");
                            }
                            break;

                        case 7:
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