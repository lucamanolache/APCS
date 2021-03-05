package util.drawing;

import java.util.ArrayList;

public class GraphicsList extends ArrayList<Integer> {

    private Window window;
    public boolean display;

    public GraphicsList(Window window) {
        this.window = window;
        this.display = false;
    }

    @Override
    public Integer set(int index, Integer element) {
        Integer out = super.set(index, element);
        if (display) {
            window.drawArray(this);
        }
        return out;
    }
}
