public class LogicalOperator {

    private static String regex = "([~]*[a-z]{1}){1}((\\s)*(=>|<=|<=*>|[|,&]){1}(\\s)*([~]*[a-z]{1}){1})*";

    private static String regex2 = "(=>|<=|<=*>|[|,&]){1}";
    public static void main(String[] args) {
        String testCase1 = "a =>b";
        String testCase2 = "a<=a";
        String testCase3 = "~b<==>~~~a";
        String testCase4 = "a<=>b | a";

        System.out.println(legal(testCase1));
        System.out.println(legal(testCase2));
        System.out.println(legal(testCase3));
        System.out.println(legal(testCase4));
    }

    public static boolean legal(String str) {
        return str.matches(regex);
    }
}
