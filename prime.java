package MaidHouse;

public class prime {

    public static void main(String[] args){
            int count = 0; // 个数计数器

            for (int i = 2; i <= 20000; i++) {
                if (isPrime(i)) {
                    System.out.printf("%-6d", i);
                    count++;
                    if (count % 5 == 0) {
                        System.out.println(); // 每五个进行一次换行
                    }
                }
        }
    }


    // 使用素数平方根定理判定素数
    private static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        int sqrt = (int) Math.sqrt(n);
        for (int i = 3; i <= sqrt; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
