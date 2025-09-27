package MaidHouse;

import java.util.Arrays;

public class prime {

    public static void main(String[] args) {
        int n = 20000;
        boolean[] isPrime = isPrime(n); // 调用埃拉托斯特尼筛法
        int count = 0;

        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                System.out.printf("%-6d", i);
                count++;
                if (count % 5 == 0) {
                System.out.println(); // 每行打印 5 个质数
            }
        }
    }
}



    // 使用埃拉托斯特尼筛法判定素数
    private static boolean[] isPrime(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);

        // 0 和 1 不用判断，直接初始化
        if (n >= 0) prime[0] = false;
        if (n >= 1) prime[1] = false;

        // 从 2 开始判断
        for (int i = 2; i * i <= n; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    prime[j] = false;
                }
            }
        }
        return prime;
    }
}
