public class GregorianDate extends Date {

    public static int[] monthLengths = {31, 28, 31, 30, 31, 30, 31,
            31, 30, 31, 30, 31};

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    @Override
    public Date nextDate() {
        int newday = this.dayOfMonth()+1;
        int newmonth=this.month();
        int newyear=this.year();
        if (newday>monthLengths[month()-1]) {
            newmonth+=1;
            newday=1;
        }
        if (newmonth>12){
            newyear+=1;
            newmonth=1;
        }
        return new GregorianDate(newyear,newmonth,newday);
    }


    @Override
    public int dayOfYear() {
        int rtnValue = 0;
        for (int m = 0; m < month() - 1; m++) {
            rtnValue += monthLengths[m];
        }
        return rtnValue + dayOfMonth();
    }

}
