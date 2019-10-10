
public class Date {
    private int month;
    private int day;
    private int year;

    private static int count;

    public boolean leapYear() {
        if (year % 4 == 0) {   //possible leap year
            if (year % 400 == 0) return true;
            else if (year % 100 == 0) return false;
            else return true;
        } else return false;
    }

    public int getDaysInMonth() {
        int[] VALID_DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && leapYear())
            return 29;
        else return VALID_DAYS[month];
    }

    /**
     * default constructor
     * sets month to 1, day to 1 and year to 2000
     */
    public Date() {
        setDate(1, 1, 2000);
        count++;
    }

    /**
     * overloaded constructor
     *
     * @param mm   initial value for month
     * @param dd   initial value for day
     * @param yyyy initial value for year
     *             <p>
     *             passes parameters to setDate method
     */
    public Date(int mm, int dd, int yyyy) {
        setDate(mm, dd, yyyy);
        count++;
    }

    public Date(int mm, int yyyy) {
        setDate(mm, 1, yyyy);
    }

    public Date(Date oldDate) {   // copy constructor
        setDate(oldDate.getMonth(), oldDate.getDay(), oldDate.getYear());
    }


    /* accessor methods */
    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    /** mutator method */
    /**
     * setDate
     *
     * @param mm   new value for month
     * @param dd   new value for day
     * @param yyyy new value for year
     *             passes parameters to setMonth, setDay, and setYear
     */
    public void setDate(int mm, int dd, int yyyy) {
        setYear(yyyy);
        setMonth(mm);
        setDay(dd);
    }

    /** helper methods */
    /**
     * setDay
     *
     * @param dd new value for day
     *           if dd is legal day for current month, sets day to dd
     *           otherwise, sets day to 1
     */
    private void setDay(int dd) {

        day = (dd >= 1 && dd <= getDaysInMonth() ? dd : 1);
    }

    /**
     * setMonth
     *
     * @param mm new value for month
     *           if mm is between 1 and 12, sets month to mm
     *           otherwise, sets month to 1
     */
    private void setMonth(int mm) {
        month = (mm >= 1 && mm <= 12 ? mm : 1);
    }

    /**
     * setYear
     *
     * @param yyyy new value for year
     *             sets year to yyyy
     */
    private void setYear(int yyyy) {
        year = yyyy;
    }

    /**
     * toString
     *
     * @return String
     * returns date in mm/dd/yyyy format
     */
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    public int daysLeftInMonth() {
        return getDaysInMonth() - day;
    }

    public void increment() {
        if (month == 12 && day == getDaysInMonth()) {
            setDate(1, 1, year + 1);
        } else if (day == getDaysInMonth()) {
            setDate(month + 1, 1, year);
        } else {
            setDate(month, day + 1, year);
        }
    }

    /**
     * equals
     *
     * @param d Date object to compare to this
     * @return true if d is equal to this
     * false, otherwise
     */
    public boolean equals(Date d) {
        boolean returnValue;
        if (this.month == d.month
                && this.day == d.day
                && this.year == d.year)
            returnValue = true;
        else
            returnValue = false;
        return returnValue;
    }
}