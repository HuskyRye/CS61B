public class RabinKarpAlgorithm {

    static final int UNIQUECHARS = 128;
    static final int PRIMEBASE = 6113;

    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (input.length() < pattern.length()) {
            return -1;
        }
        String sub = input.substring(0, pattern.length());
        if (sub.equals(pattern)) {
            return 0;
        }
        RollingString rssub = new RollingString(sub, pattern.length());
        RollingString rspattern = new RollingString(pattern, pattern.length());
        for (int i = pattern.length(), j = 1; i < input.length(); ++i, ++j) {
            rssub.addChar(input.charAt(i));
            if (rssub.hashCode() == rspattern.hashCode() && rssub.equals(rspattern)) {
                return j;
            }
        }
        return -1;
    }
}
