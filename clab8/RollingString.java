import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{
    private Deque<Character> string;
    private int hashCode;
    private int offset;

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        string = new ArrayDeque<>(length);
        for (char c : s.toCharArray()) {
            hashCode = hashCode * UNIQUECHARS;
            hashCode = Math.floorMod(hashCode, PRIMEBASE);
            hashCode = hashCode + c;
            hashCode = Math.floorMod(hashCode, PRIMEBASE);
            string.addLast(c);
        }
        offset = 1;
        for (int i = 0; i < length - 1; ++i) {
            offset = offset * UNIQUECHARS;
            offset = Math.floorMod(offset, PRIMEBASE);
        }
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        hashCode = hashCode - string.removeFirst() * offset;
        hashCode = hashCode * UNIQUECHARS + c;
        hashCode = Math.floorMod(hashCode, PRIMEBASE);
        string.addLast(c);
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (char c : string) {
            strb.append(c);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return string.size();
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        RollingString other = (RollingString) o;
        return string.equals(other.string);
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        return Math.floorMod(hashCode, PRIMEBASE);
    }
}
