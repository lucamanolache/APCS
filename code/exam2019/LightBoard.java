package exam2019;

public class LightBoard {

    /**
     * The lights on the board, where true represents on and false represents off.
     */
    private boolean[][] lights;

    /**
     * Constructs a LightBoard object having numRows rows and numCols columns.
     * Precondition: numRows > 0, numCols > 0
     * Postcondition: each light has a 40% probability of being set to on.
     */
    public LightBoard(int numRows, int numCols) {
        lights = new boolean[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // Math.random() gives something from 0.0 to 1.0 with uniform distribution. therefore, it has a
                // 40% probability to be set on (true) if it is less than 0.4
                lights[i][j] = Math.random() <= 0.40;
            }
        }
    }

    /**
     * Evaluates a light in row index row and column index col and returns a status
     * as described in part (b).
     * Precondition: row and col are valid indexes in lights.
     */
    public boolean evaluateLight(int row, int col) {
        var status = lights[row][col];
        int on = 0;
        for (int i = 0; i < lights[row].length; i++) {
            if (lights[row][i]) on++;
        }
        if (status) {
            // light is on, therefore return false if the lights that are on in the column are even
            if (on % 2 == 0) {
                return false;
            }
        } else {
            if (on % 3 == 0) {
                return true;
            }
        }
        return status;
    }
}