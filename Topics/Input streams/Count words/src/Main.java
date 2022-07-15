import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine().trim();
        reader.close();

        // alternative to "split("\\s+")" regex solution
        int count = input.length() == 0 ? 0 : 1;
        for (int i = 1; i < input.length() - 1; i++) {
            if (input.charAt(i - 1) != ' ' && input.charAt(i) == ' ') {
                count++;
            }
        }
        System.out.println(count);
    }
}