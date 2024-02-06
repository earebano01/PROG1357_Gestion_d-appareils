package com.exercice1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    Scanner in = new Scanner(System.in);

    /**
     * Cette méthode est utilisée pour valider le nom d'utilisateur (pseudo).
     * L'utilisateur est invité à saisir un nom d'utilisateur, et la méthode vérifie
     * s'il est conforme aux critères définis, c'est-à-dire s'il ne contient que des
     * lettres et des chiffres.
     *
     * @param in Le scanner pour lire l'entrée de l'utilisateur.
     * @return Le nom d'utilisateur validé.
     */
    public static String nomInput(Scanner in) {
        String nom = "";
        boolean validNom = false;
        while (!validNom) {
            System.out.print("\n(Assurez-vous qu'il contienne uniquement des lettres)");
            System.out.print("\nNom d'appareil : ");
            nom = in.nextLine();
            if (isValidNom(nom)) {
                validNom = true;
            } else {
                System.out.println("Vous avez entré un nom d'appareil non-valide!\n");
            }
        }
        return nom;
    }

    /**
     * Cette méthode est utilisée pour valider le nom d'appareil (nom).
     * Elle vérifie si le nom d'appareil contient uniquement des lettres et des
     * chiffres.
     *
     * @param nom Le nom d'appareil à valider.
     * @return true si le nom d'appareil est valide (lettres
     *         seulement), sinon false.
     */
    public static boolean isValidNom(String nom) {
        // Le modèle regex permettant uniquement des lettres et des chiffres
        String regex = "^[a-zA-Z ]+$";

        // Compile le modèle regex en un pattern
        Pattern pattern = Pattern.compile(regex);

        // Crée un matcher pour le nom d'utilisateur
        Matcher matcher = pattern.matcher(nom);

        // Vérifie si le nom d'utilisateur correspond au modèle regex
        return matcher.matches();
    }
    
    public static String deviceIDInput(Scanner in) {
        String deviceID = "";
        boolean ValidDeviceID = false;
        while (!ValidDeviceID) {
            System.out.print("\n(Assurez-vous qu'il contienne uniquement des lettres et chiffres)");
            System.out.print("\nDevice ID d'appareil : ");
            deviceID = in.nextLine();
            if (isValidDeviceID(deviceID)) {
                ValidDeviceID = true;
            } else {
                System.out.println("Vous avez entré un Device ID d'appareil non-valide!\n");
            }
        }
        return deviceID;
    }

    /**
     * Cette méthode est utilisée pour valider le deviceID d'appareil (deviceID).
     * Elle vérifie si le deviceID d'appareil contient uniquement des lettres et des
     * chiffres.
     *
     * @param deviceID Le deviceID d'appareil à valider.
     * @return true si le deviceID d'appareil est valide (lettres
     *         seulement), sinon false.
     */
    public static boolean isValidDeviceID(String deviceID) {
        // Le modèle regex permettant uniquement des lettres et des chiffres
        String regex = "^[a-zA-Z0-9]+$";

        // Compile le modèle regex en un pattern
        Pattern pattern = Pattern.compile(regex);

        // Crée un matcher pour le deviceID d'utilisateur
        Matcher matcher = pattern.matcher(deviceID);

        // Vérifie si le deviceID d'utilisateur correspond au modèle regex
        return matcher.matches();
    }

    public static String typeInput(Scanner in) {
        String type = "";
        boolean validType = false;
        while (!validType) {
            System.out.print("\n(Assurez-vous qu'il contienne uniquement des lettres et chiffres)");
            System.out.print("\nType d'appareil : ");
            type = in.nextLine();
            if (isValidType(type)) {
                validType = true;
            } else {
                System.out.println("Vous avez entré un type d'appareil non-valide!\n");
            }
        }
        return type;
    }

    /**
     * Cette méthode est utilisée pour valider le type d'appareil (type).
     * Elle vérifie si le type d'appareil contient uniquement des lettres et des
     * chiffres.
     *
     * @param type Le type d'appareil à valider.
     * @return true si le type d'appareil est valide (lettres
     *         seulement), sinon false.
     */
    public static boolean isValidType(String type) {
        // Le modèle regex permettant uniquement des lettres et des chiffres
        String regex = "^[a-zA-Z0-9' ]+$";

        // Compile le modèle regex en un pattern
        Pattern pattern = Pattern.compile(regex);

        // Crée un matcher pour le type d'utilisateur
        Matcher matcher = pattern.matcher(type);

        // Vérifie si le type d'utilisateur correspond au modèle regex
        return matcher.matches();
    }
    
    public static String typeMesureInput(Scanner in) {
        String type = "";
        boolean validTypeMesure = false;
        while (!validTypeMesure) {
            System.out.print("\n(Assurez-vous qu'il contienne uniquement des lettres et chiffres)");
            System.out.print("\nType de Mesure : ");
            type = in.nextLine();
            if (isValidTypeMesure(type)) {
                validTypeMesure = true;
            } else {
                System.out.println("Vous avez entré un type de mesure non-valide!\n");
            }
        }
        return type;
    }

    /**
     * Cette méthode est utilisée pour valider le type d'appareil (type).
     * Elle vérifie si le type d'appareil contient uniquement des lettres et des
     * chiffres.
     *
     * @param type Le type d'appareil à valider.
     * @return true si le type d'appareil est valide (lettres
     *         seulement), sinon false.
     */
    public static boolean isValidTypeMesure(String type) {
        // Le modèle regex permettant uniquement des lettres et des chiffres
        String regex = "^[a-zA-Z0-9 ]+$";

        // Compile le modèle regex en un pattern
        Pattern pattern = Pattern.compile(regex);

        // Crée un matcher pour le type d'utilisateur
        Matcher matcher = pattern.matcher(type);

        // Vérifie si le type d'utilisateur correspond au modèle regex
        return matcher.matches();
    }

    public static String typeActionInput(Scanner in) {
        String type = "";
        boolean validtypeAction = false;
        while (!validtypeAction) {
            System.out.print("\n(Assurez-vous qu'il contienne uniquement des lettres et chiffres)");
            System.out.print("\nType d'Action' : ");
            type = in.nextLine();
            if (isValidTypeAction(type)) {
                validtypeAction = true;
            } else {
                System.out.println("Vous avez entré un type d'action non-valide!\n");
            }
        }
        return type;
    }

    /**
     * Cette méthode est utilisée pour valider le type d'appareil (type).
     * Elle vérifie si le type d'appareil contient uniquement des lettres et des
     * chiffres.
     *
     * @param type Le type d'appareil à valider.
     * @return true si le type d'appareil est valide (lettres
     *         seulement), sinon false.
     */
    public static boolean isValidTypeAction(String type) {
        // Le modèle regex permettant uniquement des lettres et des chiffres
        String regex = "^[a-zA-Z0-9 ]+$";

        // Compile le modèle regex en un pattern
        Pattern pattern = Pattern.compile(regex);

        // Crée un matcher pour le type d'utilisateur
        Matcher matcher = pattern.matcher(type);

        // Vérifie si le type d'utilisateur correspond au modèle regex
        return matcher.matches();
    }

    public static String actuation_statusInput(Scanner in) {
        String type = "";
        boolean validactuation_status = false;
        while (!validactuation_status) {
            System.out.print("\n(Assurez-vous qu'il contienne uniquement des lettres et chiffres)");
            System.out.print("\nType d'Action' : ");
            type = in.nextLine();
            if (isValidactuation_status(type)) {
                validactuation_status = true;
            } else {
                System.out.println("Vous avez entré un type d'action non-valide!\n");
            }
        }
        return type;
    }

    /**
     * Cette méthode est utilisée pour valider le type d'appareil (type).
     * Elle vérifie si le type d'appareil contient uniquement des lettres et des
     * chiffres.
     *
     * @param type Le type d'appareil à valider.
     * @return true si le type d'appareil est valide (lettres
     *         seulement), sinon false.
     */
    public static boolean isValidactuation_status(String type) {
        // Le modèle regex permettant uniquement des lettres et des chiffres
        String regex = "^[a-zA-Z0-9 ]+$";

        // Compile le modèle regex en un pattern
        Pattern pattern = Pattern.compile(regex);

        // Crée un matcher pour le type d'utilisateur
        Matcher matcher = pattern.matcher(type);

        // Vérifie si le type d'utilisateur correspond au modèle regex
        return matcher.matches();
    }

    /**
     * Cette méthode est utilisée pour valider le statut (Actif/Inactif).
     * L'utilisateur est invité à saisir un statut, et la méthode vérifie si il est
     * conforme aux critères définis, c'est-à-dire s'il est égal à "Actif" ou
     * "Inactif" (insensible à la casse).
     *
     * @param in Le scanner pour lire l'entrée de l'utilisateur.
     * @return Le statut validé.
     */
    public static String statusInput(Scanner in) {
        String status = "";
        boolean validStatus = false;
        while (!validStatus) {
            System.out.print("\nStatus (Actif/Inactif): ");
            status = in.nextLine();
            if (isValidStatus(status)) {
                validStatus = true;
            } else {
                System.out.println("Status doit être soit Actif ou Inactif!\n");
            }
        }
        return status;
    }

    /**
     * Cette méthode est utilisée pour valider le statut, qui doit être soit "Actif"
     * ou "Inactif".
     *
     * @param status Le statut à valider.
     * @return true si le statut est "Actif" ou "Inactif", sinon false.
     */
    public static boolean isValidStatus(String status) {
        // Vérifie si le statut est égal à "Actif" ou "Inactif" (non sensible à la
        // casse)
        return status.equalsIgnoreCase("Actif") || status.equalsIgnoreCase("Inactif");
    }

}
