package packagelists;
public class App {
    public static void main(String[] args) throws Exception {
        Exemple ex = new Exemple();

        ex.Add("CCNB");
        ex.Add(10);
        ex.Add("CCNB");
        ex.Add(11);
        ex.Add("CCNB");
        ex.Add(19);
        do{
            try {
                //demandeChiffre();
                // break;
                ex.remove1(0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
