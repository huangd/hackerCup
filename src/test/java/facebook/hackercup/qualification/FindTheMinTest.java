package facebook.hackercup.qualification;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: huangd
 * Date: 1/25/13
 * Time: 11:05 PM
 */
public class FindTheMinTest {
    @Test
    public void testGetN() throws Exception {
        FindTheMin findTheMin = new FindTheMin();
        int[] m = new int[7];
        m[0] = 0;
        m[1] = 2;
        m[2] = 3;
        int last = findTheMin.getN(m, 0, 3);
        assertEquals(1, last);

        m = new int[7];
        m[0] = 3;
        m[1] = 4;
        m[2] = 5;
        last = findTheMin.getN(m, 0, 3);
        assertEquals(0, last);
    }
}
