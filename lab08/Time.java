public class Time {

    private int myHours;
    private int myMinutes;
    
    public Time (String s) {
        if (s==null){
            throw new IllegalArgumentException("Null is illegal");
        }else {
            int colonPos = s.indexOf(":");
            myHours = Integer.parseInt(s.substring(0, colonPos));
            myMinutes = Integer.parseInt(s.substring(colonPos + 1));
            if (myHours < 0 || myHours > 23) {
                throw new IllegalArgumentException("Hours should between 0 and 23");
            } else if (myMinutes < 0 || myMinutes > 23) {
                throw new IllegalArgumentException("Minutes should between 0 and 23");
            }
        }
    }
    
    public Time (int hours, int minutes) {
        myHours = hours;
        myMinutes = minutes;
    }

    public boolean equals (Object obj) {
        Time t = (Time) obj;
        return myHours == t.myHours && myMinutes == t.myMinutes;
    }

    public String toString() {
        if (myMinutes < 10) {
            return myHours + ":0" + myMinutes;
        } else {
            return myHours + ":" + myMinutes;
        }
    }
}
