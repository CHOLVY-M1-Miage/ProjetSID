package routines;

public class utilisateurFonctions {
  
 // Fonction pour récupérer le numéro du mois à partir de l'abréviation
    public String getMonthNumber(String monthAbbreviation) {
        // Tableau associatif des abréviations de mois avec leurs numéros correspondants
        String[] monthsAbbreviations = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int monthNumber = 0;
        for (int i = 0; i < monthsAbbreviations.length; i++) {
            if (monthsAbbreviations[i].equals(monthAbbreviation)) {
                monthNumber = i + 1; // Ajoute 1 car les indices commencent à 0
                break;
            }
        }
        return Integer.toString(monthNumber);
    }
}
