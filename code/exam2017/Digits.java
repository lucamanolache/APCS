package exam;

import java.util.ArrayList;

public class Digits {
    private ArrayList<Integer> digitList;

    public Digits(int num) {
        var stringNum = String.valueOf(num);
        digitList = new ArrayList<>();
        for (int i = 0; i < stringNum.length(); i++) {
            digitList.add(Integer.valueOf(stringNum.substring(i, i + 1)));
        }
    }

    public boolean isStrictlyIncreasing() {
        for (int i = 1; i < digitList.size(); i++) {
            if (digitList.get(i - 1) >= digitList.get(i)) {
                return false;
            }
        }
        return true;
    }
}
