package facebook.hackercup.roundOne;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: huangd
 * Date: 2/2/13
 * Time: 1:56 PM
 */
public class CardGameTest {
    @Test
    public void getSum() throws Exception {
        long N = 4;
        long K = 3;
        long[] a = {3, 6, 2, 8};
        CardGame cardGame = new CardGame();
        long sum = cardGame.getSum(N, K, a);
        assertEquals(30, sum);
    }
}
