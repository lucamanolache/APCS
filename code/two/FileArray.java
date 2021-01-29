package two;

import java.io.*;

public class FileArray<T extends Serializable> {

    private final String filename;

    private Serializable[][] holding;

    private FileOutputStream file;
    private ObjectOutputStream out;

    public FileArray(int rows, int cols, String filename) {
        this.filename = filename;
        this.holding = new Serializable[rows][cols];

        try {
            file = new FileOutputStream(filename);
            out = new ObjectOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (new File(filename).exists()) {
            FileInputStream file;
            ObjectInputStream in;

            try {
                file = new FileInputStream(filename);
                in = new ObjectInputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // make sure dimensions match
            boolean good = true;

            try {
                if (in.readInt() != rows || in.readInt() != cols) {
                    good = false;
                }
            } catch (IOException e) {
                good = false;
            }

            // load it into an array
            if (good) {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        try {
                            holding[i][j] = (Serializable) in.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            try {
                out.writeInt(rows);
                out.writeInt(cols);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void set(int row, int col, T object) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(object); // TODO: get it to write at the correct location
        out.flush();
    }

    public T get(int row, int col) {
        try {
            FileInputStream file = new FileInputStream(filename);
            System.out.println(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            int rows = in.readInt(), cols = in.readInt();
            if (rows < row || cols < col) {
                throw new ArrayIndexOutOfBoundsException(String.format(
                        "Tried accessing row %d and col %d when the size is %d rows by %d cols", row, col, rows, cols));
            }
            in.skip(row * cols + col - 1);
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
