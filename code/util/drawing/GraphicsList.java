package util.drawing;

import java.util.AbstractList;
import java.util.ArrayList;

public class GraphicsList extends AbstractList<Integer> {

    private Window window;
    // I just need to override a few methods, however extending an ArrayList<Integer> would not work because
    // sublist is private not protected so I can not change that. This effects the drawing of some of my algorithms
    // because sublist would give an arraylist without the overridden set method.
    private ArrayList<Integer> list = new ArrayList<>();
    public boolean display;
    int gets = 0;
    int sets = 0;

    public GraphicsList(Window window) {
        this.window = window;
        this.display = false;
    }

    @Override
    public Integer get(int index) {
//        if (display) {
//            window.drawArray(this, index);
//        }
        if (display)
            gets++;
        return list.get(index);
    }

    @Override
    public Integer set(int index, Integer element) {
        Integer out = list.set(index, element);
        if (display) {
            window.drawArray(this, index);
            sets++;
        }
        return out;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean add(Integer integer) {
        return list.add(integer);
    }

    public void displayOutput() {
        System.out.println("Gets: " + gets);
        System.out.println("Sets: " + sets);
    }
}
