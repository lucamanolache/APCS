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
    public abstract boolean set(int value);

    /**
     * Set segments to high/low voltage given an array of booleans.
     * @param segments The segment values.
     */
    public abstract void setSegments(int[] segments);

    /**
     * Get an array that states which segments are on/off.
     * @return
     */
    public boolean[] getSegments() {
        return segments;
    };
}
