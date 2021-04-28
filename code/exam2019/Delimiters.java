package exam2019;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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

        // cleaner and maybe faster way to do this would be to use a stream; made it run in parallel cus why not.
        // because this is running in parallel it should be a lot faster than the method used above to do this.
        // It is also safe to do this since every step only concerns 1 element and order does not matter.
        return Arrays.stream(tokens).parallel().filter(s -> s.equals(openDel) || s.equals(closeDel)).collect(Collectors.toCollection(ArrayList::new));
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
//            if (open < 0) {
//                return false;
//            }
//        }
//        return open == 0;

        // kinda complicated so i hope it works. essentially turns it into a list of 1's and -1's and then adds up them,
        // if at any point when it is adding them up, the sum is less than 0, that means that there are more closed
        // therefore we must return false. I can not use a parallel stream here because for reduce, it requires the result
        // of the previous element.
        return delimiters.stream().map(s -> s.equals(openDel) ? 1 : -1).reduce(0, (c, e) -> c + e < 0 || e == Integer.MAX_VALUE ? Integer.MAX_VALUE : c + e) == 0;
    }
}