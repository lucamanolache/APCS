package exam;

public class Position {

    public int r, c;

    public Position(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public static Position findPosition(int num, int[][] intArray) {
        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                if (intArray[i][j] == num) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    public static Position[][] getSuccessorArray(int[][] intArray) {
        Position[][] successorArray = new Position[intArray.length][intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            for (int j = 0; j < intArray[i].length; j++) {
                successorArray[i][j] = findPosition(intArray[i][j], intArray);
            }
        }

        return successorArray;
    }
}
