package two;

import java.io.*;

public class FileMatrix<T extends Serializable> {

    private final String filename;
    private int rows, cols;

    private Serializable[][] holding;

    public FileMatrix(int rows, int cols, String filename) {
        this.filename = filename;
        this.holding = new Serializable[rows][cols];
        this.rows = rows;
        this.cols = cols;

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
                int t1 = in.readInt(), t2 = in.readInt();
                if (t1 != rows || t2 != cols) {
                    good = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
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
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::write));
    }

    public void set(int row, int col, T object) {
        holding[row][col] = object;
    }

    public T get(int row, int col) {
        return (T) holding[row][col];
    }

    private void write() {
        FileOutputStream file;
        ObjectOutputStream out;
        try {
            file = new FileOutputStream(filename);
            out = new ObjectOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            out.writeInt(rows);
            out.writeInt(cols);
            for (Serializable[] arr : holding) {
                for (Serializable t : arr) {
                    out.writeObject(t);
                }
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
