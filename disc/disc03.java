public class disc03 {
    public static int[] insert(int[] arr, int item, int position) {
        int[] result = new int[arr.length + 1];
        position = Math.min(position, arr.length);
        System.arraycopy(arr, 0, result, 0, position);
        result[position] = item;
        System.arraycopy(arr, position, result, position + 1, arr.length - position);
        return result;
    }

    public static void reverse(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length / 2; ++i) {
            int temp = arr[i];
            int j = length - 1 - i;
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static int[] replicate(int[] arr) {
        int length = 0;
        for (int x : arr) {
            length += x;
        }
        int[] result = new int[length];
        int k = 0;
        for (int x : arr) {
            for (int counter = 0; counter < x; ++counter) {
                result[k] = x;
                k += 1;
            }
        }
        return result;
    }

    public static void SLListTest() {
        SLList list = new SLList();
        list.addFirst(2);
        list.reverse();
        list.reverseRecursive();
        list.addFirst(6);
        list.addFirst(5);
        // list.insert(10, 1);
        // list.insert(10, 7);
        list.reverse();
        list.reverseRecursive();
    }
    public static void insertTest() {
        int[] x = new int[]{ 5, 9, 14, 15 };
        int[] y = insert(x, 10, 2);
        int[] z = insert(x, 10, 4);
        int[] r = insert(x, 10, 5);
    }
    public static void replicateTest() {
        int[] arr = new int[]{ 3, 2, 1 };
        int[] result = replicate(arr);
    }

    public static void main(String[] args) {
        SLListTest();
        insertTest();
        replicateTest();
    }
}

class SLList {
    private static class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }
    private IntNode first;
    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public void insert(int item, int position) {
        if (first == null || position == 0) {
            addFirst(item);
            return;
        }
        IntNode p = first;
        while (position > 1 && p.next != null) {
            p = p.next;
            position -= 1;
        }
        p.next = new IntNode(item, p.next);
    }

    public void reverse() {
        if (first == null || first.next == null) {
            return;
        }
        IntNode p = first.next;
        first.next = null;
        while (p != null) {
            IntNode temp = p.next;
            p.next = first;
            first = p;
            p = temp;
        }
    }

    private IntNode reverseHelper(IntNode list) {
        if (list == null || list.next == null) {
            return list;
        }
        IntNode end = list.next;
        IntNode reversed = reverseHelper(list.next);
        end.next = list;
        list.next = null;
        return reversed;
    }

    public void reverseRecursive() {
        first = reverseHelper(first);
    }
}
