/** Class that determines whether or not a year is a leap year.
 *  @author Xiang Gu
 */
public class LeapYear {

    /** Calls isLeapYear to print correct statement.
     *  @param  year to be analyzed
     */
    private static void checkLeapYear(int year) {
        if (isLeapYear(year)) {
            System.out.printf("%d is a leap year.\n", year);
        } else {
            System.out.printf("%d is not a leap year.\n", year);
        }
    }

    /** Method that determines whether or not an input year is leap year
     *	@source 61B, UCBerkeley
     */
    public static boolean isLeapYear(int year) {
    	if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /** Must be provided an integer as a command line argument ARGS. */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter command line arguments.");
            System.out.println("e.g. java Year 2000");
        }
        for (int i = 0; i < args.length; i++) {
            try {
                int year = Integer.parseInt(args[i]);
                checkLeapYear(year);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
    }
}

