package ssd;

public class AnodeSSD extends SSD {

    @Override
    public void setSegments(float[] segments) {
        if (segments.length != 7) {
            throw new IllegalArgumentException("Length of segment array needs to be 7");
        }
        for (int i = 0; i < segments.length; i++) {
            this.segments[i] = segments.length < 0.5;
        }
    }
}
