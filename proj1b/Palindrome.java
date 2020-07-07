public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> deque = new ArrayDeque<>();
        // LinkedListDeque<Character> deque = new LinkedListDeque<>();
        // LinkedListDequeJosh<Character> deque = new LinkedListDequeJosh<>();
        for (int i = 0; i < word.length(); ++i) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    private boolean palindromeHelper(Deque<Character> word) {
        if (word.size() <= 1) {
            return true;
        }
        return (word.removeFirst() == word.removeLast() && palindromeHelper(word));
    }

    public boolean isPalindrome(String word) {
        return palindromeHelper(wordToDeque(word));
    }

    private boolean palindromeHelper(Deque<Character> word, CharacterComparator cc) {
        if (word.size() <= 1) {
            return true;
        }
        return (cc.equalChars(word.removeFirst(), word.removeLast()) && palindromeHelper(word, cc));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return palindromeHelper(wordToDeque(word), cc);
    }
}
