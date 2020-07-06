public class disc01 {
    public static int fib(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public static int fib2(int n, int k, int f0, int f1) {
        if (n == k) {
            return f0;
        } else {
            return fib2(n, k + 1, f1, f0 + f1);
        }
    }

    public static int fib3(int n) {
        int prev = 0, curr = 1;
        for (int k = 0; k < n; ++k) {
            curr = prev + curr;
            prev = curr - prev; // prev, curr = curr, prev + curr
        }
        return prev;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i) {
            System.out.print(fib3(i) + " ");
        }
    }
}