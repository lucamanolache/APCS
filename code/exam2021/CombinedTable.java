package exam2021;

class SingleTable {

    public int getNumSeats() {
        return -1;
    }

    public int getHeight() {
        return -1;
    }

    public double getViewQuality() {
        return -1;
    }

    public void setViewQuality() {

    }
}

public class CombinedTable {

    private final SingleTable t1;
    private final SingleTable t2;

    public CombinedTable(SingleTable t1, SingleTable t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public boolean canSeat(int people) {
        return people <= t1.getNumSeats() + t2.getNumSeats() - 2;
    }

    public double getDesirability() {
        return t1.getHeight() == t2.getHeight() ?
                (t1.getViewQuality() + t2.getViewQuality()) / 2 :
                (t1.getViewQuality() + t2.getViewQuality()) / 2 - 10;
    }
}
