package facebook.hackercup.qualification;

import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * User: huangd
 * Date: 1/25/13
 * Time: 10:42 PM
 */
public class FindTheMin {

    private HashMap<Integer, Integer> hashMap;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader
                = Files.newReader(new File("src/main/resources/FindTheMin.big"), Charset.forName("UTF-8"));
        int lineNumber = Integer.parseInt(bufferedReader.readLine());
        for (int i = 1; i <= lineNumber; ++i) {
            String lineOne = bufferedReader.readLine();
            String[] nk = lineOne.split(" ");
            int n = Integer.parseInt(nk[0]);
            int k = Integer.parseInt(nk[1]);

            String lineTwo = bufferedReader.readLine();
            String[] abcr = lineTwo.split(" ");
            int a = Integer.parseInt(abcr[0]);
            long b = Long.parseLong(abcr[1]);
            long c = Long.parseLong(abcr[2]);
            long r = Long.parseLong(abcr[3]);

            FindTheMin findTheMin = new FindTheMin();
            int[] m = findTheMin.getMArray(a, b, c, r, k);
            findTheMin.init(m, k);
            for (int l = 0; l <= k; l++) {
                int next = findTheMin.getN(m, l, k);
                m[l + k] = next;
            }

            n = n % (k + 1);
            System.out.println("Case #" + i + ": " + m[k + n]);

        }
        bufferedReader.close();
    }

    public void init(int[] m, int length) {
        hashMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < length; ++i) {
            Integer count = hashMap.get(m[i]);
            if (count == null) {
                hashMap.put(m[i], 1);
            } else {
                hashMap.put(m[i], count + 1);
            }

        }
    }

    public int getN(int[] m, int from, int length) {
        if (from > 0) {
            Integer count = hashMap.get(m[from - 1]);
            if (count == 1) {
                hashMap.remove(m[from - 1]);
            } else {
                hashMap.put(m[from - 1], count - 1);
            }

            count = hashMap.get(m[from - 1 + length]);
            if (count == null) {
                hashMap.put(m[from - 1 + length], 1);
            } else {
                hashMap.put(m[from - 1 + length], count + 1);
            }
        }

        for (int i = 0; i <= 1000000000; ++i) {
            if (!hashMap.keySet().contains(i)) {
                return i;
            }
        }
        throw new RuntimeException();
    }

    public int[] getMArray(int a, long b, long c, long r, int k) {
        int[] m = new int[k * 2 + 1];

        for (int i = 0; i < k; ++i) {
            if (i == 0) {
                m[i] = a;
            } else {
                long element = (b * m[i - 1] + c) % r;
                m[i] = (int) element;
            }
        }
        return m;
    }
}
