package exam2018;

import java.util.ArrayList;

class WordPair {

    private String first;
    private String second;

    public WordPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }
}

public class WordPairList {

    private ArrayList<WordPair> allPairs;

    public WordPairList(String[] words) {
        allPairs = new ArrayList<>(words.length * words.length / 2);
        // set an initial capacity in order to have less copying arrays when the list needs to grow to make it faster
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) { // j = i + 1 to insure it is not adding a pair with itself
                allPairs.add(new WordPair(words[i], words[j]));
            }
        }
    }

    public int numMatches() {
        int matches = 0;
        for (WordPair pair : allPairs) {
            // using .equals because Java has a very weird implementation of String. Instead of Strings being stored
            // as a char[] or char * like normal, it stores them in the String Constant Pool.
            if (pair.getFirst().equals(pair.getSecond())) {
                matches++;
            }
        }
        return matches;
    }
}
