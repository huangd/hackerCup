package facebook.hackercup.qualification;

import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * User: huangd
 * Date: 1/25/13
 * Time: 8:42 PM
 */
public class BeautifulStrings {

    private Map<Character, Integer> characterMap;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = Files.newReader(new File("src/main/resources/BeautifulStrings.big"), Charset.forName("UTF-8"));
        int lineNumber = Integer.parseInt(bufferedReader.readLine());
        for (int i = 1; i <= lineNumber; ++i) {
            BeautifulStrings beautifulStrings = new BeautifulStrings();
            beautifulStrings.populateMap(bufferedReader.readLine());
            System.out.println("Case #" + i + ": " + beautifulStrings.getMax());
        }
        bufferedReader.close();
    }

    private void populateMap(String input) {
        characterMap = new HashMap<Character, Integer>();
        input = input.toLowerCase();
        for (int i = 0; i < input.length(); i++) {
            char aChar = input.charAt(i);
            if (aChar >= 'a' && aChar <= 'z') {
                updateMap(aChar);
            }
        }
    }

    private void updateMap(char aChar) {
        Integer integer = characterMap.get(aChar);
        if (integer == null) {
            integer = 1;
        } else {
            integer++;
        }
        characterMap.put(aChar, integer);
    }

    private int getMax() {
        Set<Character> keys = characterMap.keySet();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (Character character : keys) {
            arrayList.add(characterMap.get(character));
        }
        Collections.sort(arrayList);

        int max = 0;
        for (int i = arrayList.size() - 1, j = 26; i >= 0; i--, j--) {
            max += arrayList.get(i) * j;
        }
        return max;
    }
}
