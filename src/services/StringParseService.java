package services;

/**
 * Service class used to parse strings
 * Created by Team USA on 12/1/14.
 */
public class StringParseService
{
    /**
     * Checks whether a number is a long or not
     * @param number The number to check if it's a long or not
     * @return true if the string passed in is a long, false otherwise.
     */
    public static boolean isLong(String number)
    {
        try
        {
            Long.parseLong(number);
            return true;
        }

        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Checks whether a number is a double or not
     * @param number The number to check if it's a double or not
     * @return true if the string passed in is a double, false otherwise.
     */
    public static boolean isDouble(String number) {
        try
        {
            Double.parseDouble(number);
            return true;
        }

        catch(Exception e)
        {
            return false;
        }
    }
    

    /**
     * Parses a string and converts it into a long valued number.
     *
     * Returns:
     *      A Long object containing the value of the string.
     *                          or
     *      null if the string couldn't be parsed.
     */
    public static Long parseLong(String number)
    {
        if(number == null || number.isEmpty()) {
            return null;
        }

        try {
            return Long.parseLong(number);
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * Parses a string and converts it into a double valued number.
     *
     * Returns:
     *      A Double object containing the value of the string.
     *                          or
     *      null if the string couldn't be parsed.
     */
    public static Double parseDouble(String number)
    {
        if(number == null || number.isEmpty()) {
            return null;
        }

        try  {
            return Double.parseDouble(number);
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * Parses a string and converts it into a int valued number.
     *
     * Returns:
     *      A Integer object containing the value of the string.
     *                          or
     *      null if the string couldn't be parsed.
     */
    public static Integer parseInteger(String number)
    {
        if(number == null || number.isEmpty()) {
            return null;
        }

        try  {
            return Integer.parseInt(number);
        }
        catch(Exception e) {
            return null;
        }
    }
}
