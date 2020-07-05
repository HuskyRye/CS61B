public class WindowsPosSum {
    public static void windowPosSum(int[] a, int n) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] <= 0)
                continue;
            int sum = 0;
            for (int j = i; j <= n+i; ++j) {
                if (j == a.length) {
                    break;
                }
                sum += a[j];
            }
            a[i] = sum;
        }
    }
    
    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSum(a, n);
        // Should print [4, 8, -3, 13, 9, 4]
        System.out.println(java.util.Arrays.toString(a));
        
        int[] b = {1, -1, -1, 10, 5, -1};
        windowPosSum(b, 2);
        // Should print [-1, -1, -1, 14, 4, -1]
        System.out.println(java.util.Arrays.toString(b));
    }
}