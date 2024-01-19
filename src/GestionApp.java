import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

import com.exercice1.NomExists;
import com.exercice1.Validation;
import com.exercice2.Conn;
import com.exercice3.CRUD;
import com.exercice3.IOT_time_series;

public class GestionApp {  
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);     
        
        Conn app = new Conn();
        app.connect();        
        
            while (true) {
                System.out.println("\nVeuillez selectionner une option :");
                System.out.println("1. Ajouter un nouvel appareil");
                System.out.println("2. Retirer un appareil");
                System.out.println("3. Modifier un appareil");
                System.out.println("4. Afficher tous les appareil");
                System.out.println("5. Afficher temperatures croissantes");
                System.out.println("6. Afficher temperatures decroissantes");
                System.out.println("7. Sortie");
                System.out.println("");
                System.out.print("Saisissez votre choix : ");
                int mainChoix = in.nextInt();
                in.nextLine();

                System.out.println("");

                switch (mainChoix) {
                    case 1:
                        System.out.println("=========================================");
                        System.out.println("Entrez les information pour l'appareil");
                        System.out.println("=========================================");

                        String nom = Validation.nomInput(in);
                        String deviceid = Validation.deviceIDInput(in);
                        String type = Validation.typeInput(in);
                        
                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = dateFormat.format(currentDate);
                        Timestamp date = Timestamp.valueOf(dateString);
                        
                        String status = Validation.statusInput(in);
                        
                        CRUD.insertData(nom, deviceid, type, date, status);
                        
                        break;

                    case 2:
                        System.out.print("Entrez le nom de l'appareil a supprimer : ");

                        String nomToDelete = in.nextLine();
                        if (!NomExists.nomExists(nomToDelete)) {
                            System.out.println("appareil inexistant. La mise a jour a echoue.");
                        } else {
                        CRUD.deleteData(nomToDelete);
                        }
                        break;

                    case 3:
                        System.out.print("Entrez l'ancien nom de l'appareil : ");

                        String oldnom = in.nextLine();

                        if (!NomExists.nomExists(oldnom)) {
                            System.out.println("appareil inexistant. La mise a jour a echoue.");
                        } else {
                            String newNom = Validation.nomInput(in);
                            String newdeviceid = Validation.deviceIDInput(in);
                            String newType = Validation.typeInput(in);
                            String newStatus = Validation.statusInput(in);

                            CRUD.updateData(oldnom, newNom, newdeviceid, newType, newStatus);
                        }
                        break;

                    case 4:
                        CRUD.readData();
                        break;

                    case 5:
                        IOT_time_series.tempDataASC();
                        break;

                    case 6:
                        IOT_time_series.tempDataDESC();
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
        }       
    }
}