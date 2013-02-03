package facebook.hackercup.roundOne;

import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * User: huangd
 * Date: 2/2/13
 * Time: 10:07 AM
 */
public class CardGame {

    private long modulo = 1000000007;
    private BigInteger kFactorial;
    private HashMap<Long, BigInteger> nKMap;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println(new Date(start));
        BufferedReader bufferedReader
                = Files.newReader(new File("src/main/resources/CardGame.big"), Charset.forName("UTF-8"));
        int lineNumber = Integer.parseInt(bufferedReader.readLine());
        for (int i = 1; i <= lineNumber; ++i) {
            String lineOne = bufferedReader.readLine();
            String[] NK = lineOne.split(" ");
            long N = Long.parseLong(NK[0]);
            long K = Long.parseLong(NK[1]);
            String lineTwo = bufferedReader.readLine();
            String[] aArray = lineTwo.split(" ");
            long[] a = new long[aArray.length];
            for (int index = 0; index < a.length; ++index) {
                a[index] = Long.parseLong(aArray[index]);
            }
            CardGame cardGame = new CardGame();
            long sum = cardGame.getSum(N, K, a);
            System.out.println("Case #" + i + ": " + sum);
        }
        bufferedReader.close();
        long end = System.currentTimeMillis();
        System.out.println(new Date(end));
    }

    public long getSum(long N, long K, long[] a) {
        init(N - 1, K - 1);
        Arrays.sort(a);
        long sum = 0;
        for (int i = 0; i < a.length && N - 1 - i >= K - 1; ++i) {
            long subsetNumber = getSubsetNumber(N - 1 - i, K - 1);
            sum += subsetNumber * a[a.length - 1 - i];
            sum = sum % modulo;
        }
        return sum % modulo;
    }

    private long getSubsetNumber(long totalElement, long pickedElement) {
        if (pickedElement < 0) {
            throw new IllegalArgumentException("pickedElement must be non-negative");
        } else if (pickedElement > totalElement) {
            throw new IllegalArgumentException("pickedElement must be less than totalElement");
        } else {
            BigInteger subSetNumber = nKMap.get(totalElement);
            subSetNumber = subSetNumber.divide(kFactorial);
            return subSetNumber.mod(new BigInteger(modulo + "")).longValue();
        }
    }

    private void init(long N, long K) {
        kFactorial = BigInteger.ONE;
        for (int i = 1; i <= K; ++i) {
            kFactorial = kFactorial.multiply(new BigInteger(i + ""));
        }
        nKMap = new HashMap<Long, BigInteger>();
        for (long n = K; n <= N; ++n) {
            if (n == K) {
                BigInteger bigNK = BigInteger.ONE;
                for (long k = 0; k < K; ++k) {
                    BigInteger big = new BigInteger(n - k + "");
                    bigNK = bigNK.multiply(big);
                }
                nKMap.put(n, bigNK);
            } else {
                BigInteger bigNK = nKMap.get(n - 1)
                        .multiply(new BigInteger(n + ""))
                        .divide(new BigInteger(n - K + ""));
                nKMap.put(n, bigNK);
            }
        }
    }
}
