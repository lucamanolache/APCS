package exam2018;

interface StringChecker {
    boolean isValid(String str);
}

public class CodeWordChecker implements StringChecker {

    private int minLength, maxLength;
    private String notInclude;

    public CodeWordChecker(int minLength, int maxLength, String notInclude) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.notInclude = notInclude;
    }

    public CodeWordChecker(String notInclude) {
        this(6, 20, notInclude);
    }

    @Override
    public boolean isValid(String str) {
        return str.length() <= maxLength && str.length() >= minLength && !str.contains(notInclude);
    }
}
