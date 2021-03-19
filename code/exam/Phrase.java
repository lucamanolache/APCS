package exam;

public class Phrase {

    private String currentPhrase;

    public Phrase(String p) {
        currentPhrase = p;
    }

    public int findNthOccurrence(String str, int n) {
        return -1; // implemented by the APCS people
    }

    public void replaceNthOccurrence(String str, int n, String repl) {
        int nthOccurrence = findNthOccurrence(str, n);
        if (nthOccurrence == -1) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(currentPhrase.substring(0, nthOccurrence));
        builder.append(repl);
        builder.append(currentPhrase.substring(nthOccurrence + repl.length()));

        currentPhrase = builder.toString();
        // Strings are immutable and java internally creates them using a String Builder so this should be the fastest
        // way.
    }

    public int findLastOccurrence(String str) {
        // brute force solution cus I don't have time to implement Boyer Moore pattern detection :(
        int index = -1;

        for (int i = 0; i < currentPhrase.length() - str.length(); i++) {   // O ( S * P ) S = currentPhrase.length and
            boolean matches = true;                                         //             P = str.length
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(i + j) != currentPhrase.charAt(i + j)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                index = i;
            }
        }

        return index;
//        return currentPhrase.lastIndexOf(str); one line answer can't be used :(
    }
}
