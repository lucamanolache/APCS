package ssd;

/**
 *
 */
public abstract class SSD {

    protected boolean[] segments;

    /**
     * Set the SSD to a known value.
     * @param value A value that is known to be displayed.
     * @return true if the value is possible and false if not possible. If true the SSD will be set to that.
     */
    public boolean set(int value) {
        switch (value) {
            case 1:
                this.segments = new boolean[]{false, true, true, false, false, false, false};
                return true;
            case 2:
                this.segments = new boolean[]{true, true, false, true, true, false, true};
                return true;
            case 3:
                this.segments = new boolean[]{true, true, true, true, false, false, true};
                return true;
            default:
                return false;
        }
    }

    /**
     * Set segments to high/low voltage given an array of booleans.
     * @param segments The segment values.
     */
    public abstract void setSegments(float[] segments);

    /**
     * Get an array that states which segments are on/off.
     * @return which segments are on/off.
     */
    public boolean[] getSegments() {
        return segments;
    };
}
