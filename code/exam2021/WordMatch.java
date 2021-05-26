package exam2021;

public class WordMatch {

    private String secret;

    public WordMatch(String word) {
        this.secret = word;
    }

    public int scoreGuess(String guess) {
        int count = 0;
        for (int i = 0; i < secret.length() - guess.length(); i++) {
            if (secret.indexOf(guess, i) != -1) {
                count++;
            }
        }
        return count * guess.length() * guess.length();
    }

    public String findBetterGuess(String guess1, String guess2) {
        return scoreGuess(guess1) >= scoreGuess(guess2) ? guess1 : guess2;
    }
}
