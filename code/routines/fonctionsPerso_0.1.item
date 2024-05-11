package routines;

public class fonctionsPerso {
	// Fonction pour récupérer le numéro du mois à partir de l'abréviation
		public static String getMonthNumber(String monthAbbreviation) {
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
		
		// Fonction pour récupérer le nom du jour de la semaine à partir de l'abréviation
		public static String getFullWeekName(String abb) {
	        switch (abb.toLowerCase()) {
	            case "mon":
	                return "Monday";
	            case "tue":
	                return "Tuesday";
	            case "wed":
	                return "Wednesday";
	            case "thu":
	                return "Thursday";
	            case "fri":
	                return "Friday";
	            case "sat":
	                return "Saturday";
	            case "sun":
	                return "Sunday";
	            default:
	                return "Invalid abbreviation";
	        }
	    }
		
		// Fonction pour récupérer le nom du mois de l'abréviation
		public static String getFullMonthName(String abb) {
	        switch (abb.toLowerCase()) {
	            case "jan":
	                return "January";
	            case "feb":
	                return "February";
	            case "mar":
	                return "March";
	            case "apr":
	                return "April";
	            case "may":
	                return "May";
	            case "jun":
	                return "June";
	            case "jul":
	                return "July";
	            case "aug":
	                return "August";
	            case "sep":
	                return "September";
	            case "oct":
	                return "October";
	            case "nov":
	                return "November";
	            case "dec":
	                return "December";
	            default:
	                return "Invalid abbreviation";
	        }
	    }
		
		public static String getQuarterName(String monthNumber) {
	        switch ((Integer.parseInt(monthNumber)- 1) / 3) {
	            case 0:
	                return "Q1";
	            case 1:
	                return "Q2";
	            case 2:
	                return "Q3";
	            case 3:
	                return "Q4";
	            default:
	                return "Invalid month number";
	        }
	    }
	
		public static Integer getDecadeAtOrderTime(String orderDate, Integer birthYear) {
			if (birthYear != null) {
				int orderYear = Integer.parseInt(orderDate.split(" ")[5]);
				int delta = (orderYear - birthYear);
				return delta - delta % 10;
			} else {
				return null;
			}
		}
	
}
