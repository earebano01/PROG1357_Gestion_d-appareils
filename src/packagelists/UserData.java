package packagelists;
public class UserData {
   private ArrayList<String> pseudo;
   private ArrayList<String> motdepasse;
   private LinkedList<String> fname;
   private LinkedList<String> lname;
   private LinkedList<String> position;
   private LinkedList<String> email;
   private LinkedList<String> status;

   public UserData() {
    this.pseudo = new ArrayList<>();
    this.motdepasse = new ArrayList<>();
    this.fname = new LinkedList<>();
    this.lname = new LinkedList<>();
    this.position = new LinkedList<>();
    this.email = new LinkedList<>();
    this.status = new LinkedList<>();
   }

   public void addUser (String pseudo, String motdepasse, String fname, String lname, String position, String email, String status){
    this.pseudo.add(pseudo);
    this.motdepasse.add(motdepasse);
    this.fname.add(fname);
    this.lname.add(lname);
    this.position.add(position);
    this.email.add(email);
    this.status.add(status);
   }

   public void removeUser (int index){
    this.pseudo.remove(index);
    this.motdepasse.remove(index);
    this.fname.remove(index);
    this.lname.remove(index);
    this.position.remove(index);
    this.email.remove(index);
    this.status.remove(index);
   }

   public void displayUser(){
    System.out.println("Informations sur l'utilisateur");
    System.out.println("");
    for (int i = 0; i < pseudo.size(); i++) {
        System.out.println("Utilisateur :" + pseudo.get(i));
        System.out.println("Prenom : " + fname.get(i));
        System.out.println("Nom : " + lname.get(i));
        System.out.println("Position : " + position.get(i));
        System.out.println("Courriel : " + email.get(i));
        System.out.println("Status : " + status.get(i));
        System.out.println("");
        }
   }

public int getSize() {
        return pseudo.size();
    }
}