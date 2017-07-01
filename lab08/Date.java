public class Date {

    /* DAYS_IN_MONTH[month-1] represents the number of days in month during a non-leap year. */
    public static final int[] DAYS_IN_MONTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int myMonth;        // months range from 1 (January) through 12 (December)
    private int myDateInMonth;  // dates-in-month range from 1 through the number of days in the month
    private int myYear;         // years are between 1900 and 2100 (arbitrary decision)

    public Date(int month, int dateInMonth, int year) {
        myMonth = month;
        myDateInMonth = dateInMonth;
        myYear = year;
    }

    // Determine if the date information is internally consistent.
    public void isOK() {
        if (myYear > 2100 || myYear < 1900) {
            throw new IllegalStateException("Wrong year range");
        } else if (myMonth < 0 || myMonth > 12) {
            throw new IllegalStateException("Wrong month range");
        } else if (myMonth == 1 || myMonth == 3 || myMonth == 5 || myMonth == 7 || myMonth == 8 || myMonth == 10 || myMonth == 12) {
            if (myDateInMonth < 0 || myDateInMonth > 31)
                throw new IllegalStateException("Wrong day range");
        } else if (myMonth == 4 || myMonth == 6 || myMonth == 9 || myMonth == 11) {
            if (myDateInMonth < 0 || myDateInMonth > 30)
                throw new IllegalStateException("Wrong day range");
        } else if (myMonth == 2) {
            if (myDateInMonth < 0 || myDateInMonth > 29)
                throw new IllegalStateException("Wrong day range");
        }
        boolean leapDay;
        if (myYear % 4 != 0) {
            leapDay = false;
        } else if (myYear % 100 != 0) {
            leapDay = true;
        } else if (myYear % 400 != 0) {
            leapDay = false;
        } else {
            leapDay = true;
        }
        if (myMonth == 2 && myDateInMonth == 29 && leapDay == false)
            throw new IllegalStateException("Wrong day range");
    }
    // YOUR CODE HERE
}

