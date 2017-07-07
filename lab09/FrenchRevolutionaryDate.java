public class FrenchRevolutionaryDate extends Date {

    /**
     * In a nonleap year for the French Revolutionary Calendar, the first
     * twelve months have 30 days and month 13 has five days.
     */
    public FrenchRevolutionaryDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    @Override
    public Date nextDate() {
        int newday = this.dayOfMonth() + 1;
        int newmonth = this.month();
        int newyear = this.year();

        if (newday == 31 && newmonth != 13) {
            newmonth += 1;
            newday = 1;
        } else if (newday == 6 && newmonth == 13) {
            newmonth = 1;
            newday = 1;
            newyear += 1;
        }
        return new FrenchRevolutionaryDate(newyear, newmonth, newday);
    }

    @Override
    public int dayOfYear() {
        return (month() - 1) * 30 + dayOfMonth();
    }

}
