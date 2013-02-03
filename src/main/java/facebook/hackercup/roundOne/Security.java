package facebook.hackercup.roundOne;

import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * User: huangd
 * Date: 2/2/13
 * Time: 10:03 PM
 */
public class Security {

    private char specialChar = '?';
    private boolean isFinished;
    private String k;
    private ArrayList<String> oK1Sections;
    private ArrayList<String> oK2Sections;
    private ArrayList<String> k1Sections;
    private ArrayList<String> k2Sections;
    private ArrayList<Integer> kIndexList;

    public Security(int m, String k1, String k2) {
        oK1Sections = getKSection(m, k1);
        oK2Sections = getKSection(m, k2);
        simplifySections();
    }

    private void simplifySections() {
        kIndexList = new ArrayList<Integer>();
        k1Sections = new ArrayList<String>(oK1Sections);
        k2Sections = new ArrayList<String>(oK2Sections);

        for (int i = 0; i < oK1Sections.size(); ++i) {
            String section = oK1Sections.get(i);
            if (section.contains(specialChar + "")) {
                kIndexList.add(i);
                continue;
            } else {
                if (k2Sections.remove(section)) {
                    k1Sections.remove(section);
                } else {
                    kIndexList.add(i);
                }
            }
        }
    }

    private String rebuildResult() {
        ArrayList<String> sections = getKSection(kIndexList.size(), k);
        for (int i = 0; i < sections.size(); ++i) {
            oK1Sections.set(kIndexList.get(i), sections.get(i));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String section : oK1Sections) {
            stringBuilder.append(section);
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println(new Date(start));
        BufferedReader bufferedReader
                = Files.newReader(new File("src/main/resources/Security.small"), Charset.forName("UTF-8"));
        int lineNumber = Integer.parseInt(bufferedReader.readLine());
        for (int i = 1; i <= lineNumber; ++i) {
            int m = Integer.parseInt(bufferedReader.readLine());
            String k1 = bufferedReader.readLine();
            String k2 = bufferedReader.readLine();

            Security security = new Security(m, k1, k2);
            security.backtrack(new ArrayList<String>(), security.k2Sections);
            String result = "IMPOSSIBLE";
            if (security.isFinished) {
                result = security.rebuildResult();
            }
            System.out.println("Case #" + i + ": " + result);
        }
        bufferedReader.close();
        long end = System.currentTimeMillis();
        System.out.println(new Date(end));
    }

    private ArrayList<String> getKSection(int m, String k) {
        ArrayList<String> kSections = new ArrayList<String>();
        if (m != 0) {
            int I = k.length() / m;
            for (int i = 0; i < k.length(); i = i + I) {
                kSections.add(k.substring(i, i + I));
            }
        }

        return kSections;
    }

    private void backtrack(ArrayList<String> kSections, ArrayList<String> k2Sections) {
        if (isSolution(kSections)) {
            isFinished = true;
            updateK(kSections, k1Sections);
        } else {
            if (isFinished) {
                return;
            } else {
                Collections.sort(k2Sections);
                ArrayList<String> candidates = getCandidates(kSections.size(), k2Sections);
                for (String candidate : candidates) {
                    kSections.add(candidate);
                    k2Sections.remove(candidate);
                    backtrack(kSections, k2Sections);
                    k2Sections.add(candidate);
                    kSections.remove(kSections.size() - 1);
                }
            }
        }
    }

    private ArrayList<String> getCandidates(int k1Index, ArrayList<String> k2Sections) {
        ArrayList<String> candidates = new ArrayList<String>();
        if (k1Index < k1Sections.size()) {
            String k1Section = k1Sections.get(k1Index);
            for (String k2Section : k2Sections) {
                if (isPossible(k1Section, k2Section)) {
                    candidates.add(k2Section);
                }
            }
        }
        return candidates;
    }

    private boolean isPossible(String k1Section, String k2Section) {
        int length = k1Section.length();
        for (int i = 0; i < length; ++i) {
            char k1Char = k1Section.charAt(i);
            char k2Char = k2Section.charAt(i);
            if (k1Char == specialChar || k2Char == specialChar) {
                continue;
            } else if (k1Char == k2Char) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }


    private boolean isSolution(ArrayList<String> kSections) {
        return kSections.size() == k1Sections.size();
    }

    private void updateK(ArrayList<String> kSections, ArrayList<String> k1Sections) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < kSections.size(); ++i) {
            String kSection = kSections.get(i);
            String k1Section = k1Sections.get(i);
            for (int j = 0; j < kSection.length(); ++j) {
                char kChar = kSection.charAt(j);
                char k1Char = k1Section.charAt(j);
                if (kChar == specialChar && k1Char == specialChar) {
                    stringBuilder.append('a');
                } else if (kChar == k1Char) {
                    stringBuilder.append(kChar);
                } else if (kChar == specialChar || k1Char == specialChar) {
                    if (kChar == specialChar) {
                        stringBuilder.append(k1Char);
                    } else {
                        stringBuilder.append(kChar);
                    }
                } else {
                    throw new RuntimeException("Wrong candidates");
                }
            }
        }
        k = stringBuilder.toString();
    }
}
