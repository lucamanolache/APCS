package exam2019;

public class APCalendar {
    /**
     * Returns true if year is a leap year and false otherwise.
     */
    private static boolean isLeapYear(int year) {
        return false;
    }

    /**
     * Returns the number of leap years between year1 and year2, inclusive.
     * Precondition: 0 <= year1 <= year2
     */
    public static int numberOfLeapYears(int year1, int year2) {
        var ans = 0;
        // just a simple loop, probably a faster way to solve this using mod, however that's kinda risky cus i can always
        // get the calculations wrong (also, iirc 1900 was not a leap year but 2000 was cus every 100 years its not a
        // leap year and every 1000 years that rule is broken. idk leap years are weird)
        for (int i = year1; i <= year2; i++) {
            if (isLeapYear(i)) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * Returns the value representing the day of the week for the first day of year,
     * where 0 denotes Sunday, 1 denotes Monday, ..., and 6 denotes Saturday.
     */
    private static int firstDayOfYear(int year) {
        return -1;
    }

    /**
     * Returns n, where month, day, and year specify the nth day of the year.
     * Returns 1 for January 1 (month = 1, day = 1) of any year.
     * Precondition: The date represented by month, day, year is a valid date.
     */
    private static int dayOfYear(int month, int day, int year) {
        return -1;
    }

    /**
     * Returns the value representing the day of the week for the given date
     * (month, day, year), where 0 denotes Sunday, 1 denotes Monday, ...,
     * and 6 denotes Saturday.
     * Precondition: The date represented by month, day, year is a valid date.
     */
    public static int dayOfWeek(int month, int day, int year) {
        int dayOfYear = dayOfYear(month, day, year);
        int firstDay = firstDayOfYear(year);

        // day of year % 7 will return the day of the week assuming the first day is sunday,
        // adding what the first day is will get the correct answer I think.
        return (dayOfYear + firstDay) % 7;
    }
}