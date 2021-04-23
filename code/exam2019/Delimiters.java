package exam2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Delimiters {
    /**
     * The open and close delimiters.
     */
    private String openDel;
    private String closeDel;

    /**
     * Constructs a Delimiters object where open is the open delimiter and close is the
     * close delimiter.
     * Precondition: open and close are non-empty strings.
     */
    public Delimiters(String open, String close) {
        openDel = open;
        closeDel = close;
    }

    /**
     * Returns an ArrayList of delimiters from the array tokens, as described in part (a).
     */
    public ArrayList<String> getDelimitersList(String[] tokens) {
        // would be nice if they gave a StringTokenizer instead of an array :(

//        ArrayList<String> ret = new ArrayList<>();
//        for (String token : tokens) {
//            if (token.equals(openDel) || token.equals(closeDel)) {
//                ret.add(token);
//            }
//        }
//        return ret;

        // cleaner and maybe faster way to do this would be to use a stream;
        return Arrays.stream(tokens).filter(s -> s.equals(openDel) || s.equals(closeDel)).collect(Collectors.toCollection(ArrayList::new));
        // this was partly from my IDE, which knew that Collectors.toCollection(ArrayList::new) was a thing, my original code looked like this
        // return new ArrayList<>(Arrays.stream(tokens).filter(s -> s.equals(openDel) || s.equals(closeDel)).collect(Collectors.toList()));
    }

    /**
     * Returns true if the delimiters are balanced and false otherwise, as described in part (b).
     * Precondition: delimiters contains only valid open and close delimiters.
     */
    public boolean isBalanced(ArrayList<String> delimiters) {
//        int open = 0;
//        for (String token : delimiters) {
//            if (token.equals(openDel)) {
//                open++;
//            }
//            if (token.equals(closeDel)) {
//                open--;
//            }
//        }
//        return open == 0;

        // again like in the fist problem, streams are far cooler
        return delimiters.stream().filter(s -> s.equals(openDel)).count() == delimiters.stream().filter(s -> s.equals(closeDel)).count();
    }
}