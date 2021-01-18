import java.util.ArrayList;
import java.util.List;

public class QuickReference {
    public static void main(String[] args) {
        String str = new String("string");

        System.out.printf("The length of this string is %d\n", str.length());
        System.out.printf("The substring from 1 to 3 is %s\n", str.substring(1, 3));
        System.out.printf("The substring from 1 is %s\n", str.substring(1));
        System.out.printf("The location of \"ing\" in %s str is %d\n", str, str.indexOf("ing"));
        System.out.printf("str == \"string\" is %b but str.equals(\"string\") is %b!\n", str == "string", str.equals("string"));
        System.out.printf("str is %s %s \"other string\"\n",
                (str.compareTo("other string") < 0 ? "less than" : (str.compareTo("other string") == 0 ? "equal to" : "greater than")), str);

        Integer i = new Integer(10);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.printf("Integer i is %d\n", i.intValue());

        Double d = new Double(10);
        System.out.printf("Double d is %.1f\n", d);

        System.out.printf("|-10| is %d\n", Math.abs(-10));
        System.out.printf("|-10.5| is %.1f\n", Math.abs(-10.5));
        System.out.printf("2 ^ 32 is %.0f\n", Math.pow(2, 32));
        System.out.printf("Square root of 4 is %.0f\n", Math.sqrt(4));
        System.out.printf("This number is random %.4f\n", Math.random());

        List<Integer> list = new ArrayList<>();
        System.out.printf("The size of this list is %d\n", list.size());
        list.add(0, 10);
        list.add(20);
        list.set(1, 5);
        list.remove(0);
        System.out.printf("The 1st elements of the list is now %d\n", list.get(0));

        System.out.printf("list.toString() gives %s", list.toString());
        System.out.printf("list.equals(d) <- the double created earlier gives %b", list.equals(d));
    }
}
