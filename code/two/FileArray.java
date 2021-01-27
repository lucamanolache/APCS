package two;

import java.io.*;

public class FileArray<T extends Serializable> {

    private final String filename;

    public FileArray(String filename) {
        this.filename = filename;
    }

    public void set(int row, int col) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);


    }

    public T get(int row, int col) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);

        int rows = in.readInt(), cols = in.readInt();
        if (rows < row || cols < col) {
            throw new ArrayIndexOutOfBoundsException(String.format(
                    "Tried accessing row %d and col %d when the size is %d rows by %d cols", row, col, rows, cols));
        }
        in.skip(row * cols + col - 1);
        return (T) in.readObject();
    }
}
