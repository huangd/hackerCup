package facebook.hackercup.qualification;

import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * User: huangd
 * Date: 1/25/13
 * Time: 9:58 PM
 */
public class BalancedSmileys {

    Parentheses parentheses;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader
                = Files.newReader(new File("src/main/resources/BalancedSmileys.big"), Charset.forName("UTF-8"));
        int lineNumber = Integer.parseInt(bufferedReader.readLine());
        for (int i = 1; i <= lineNumber; ++i) {
            BalancedSmileys balancedSmileys = new BalancedSmileys();
            boolean isValid = balancedSmileys.isValid(bufferedReader.readLine());
            if(isValid){
                System.out.println("Case #" + i + ": YES");
            }else{
                System.out.println("Case #" + i + ": NO");
            }

        }
        bufferedReader.close();
    }

    private boolean isValid(String input){
        parentheses = new Parentheses();
        char preC = ' ';
        for(int i=0; i<input.length(); ++i){
            char c = input.charAt(i);
            if(isValidChar(c)){
                if(c == '('){
                    if(preC == ':'){
                        parentheses.mightAddOne();
                    }else{
                        parentheses.addOne();
                    }
                }else if(c == ')'){
                    if(preC == ':'){
                        parentheses.mightRemoveOne();
                    }else{
                        parentheses.removeOne();
                    }
                    if(!parentheses.isValid()){
                        return false;
                    }
                }
                preC = c;
            }else{
                return false;
            }
        }
        return parentheses.isEmpty();
    }

    private boolean isValidChar(char c){
        if( c>='a'&&c<='z' || c==' ' || c==':' || c=='(' || c==')'){
            return true;
        }else{
            return false;
        }
    }

    class Parentheses {
        int min = 0;
        int max = 0;

        void addOne(){
            min++;
            max++;
        }

        void mightAddOne(){
            max++;
        }

        void removeOne(){
            min--;
            max--;
        }

        void mightRemoveOne(){
            min--;
        }

        boolean isValid(){
            if(max < 0){
                return false;
            }else{
                return true;
            }
        }

        boolean isEmpty(){
            if(min<=0){
                return true;
            }else{
                return false;
            }
        }
    }
}
