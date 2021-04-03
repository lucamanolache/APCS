package exam;

interface StudyPractice {
    String getProblem();
    void nextProblem();
}

public class MultPractice implements StudyPractice {

    private final int firstInt;
    private int secondInt;

    public MultPractice(int firstInt, int secondInt) {
        this.firstInt = firstInt;
        this.secondInt = secondInt;
    }

    public String getProblem() {
        return String.format("%d TIMES %d", firstInt, secondInt);
    }

    public void nextProblem() {
        secondInt++;
    }
}
