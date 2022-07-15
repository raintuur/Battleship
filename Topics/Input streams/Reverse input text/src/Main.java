import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer stringBuffer = new StringBuffer();
        String input = reader.readLine();
        stringBuffer.append(input);
        System.out.println(stringBuffer.reverse());
    }
}