import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            sum += Integer.parseInt(input.substring(i, i + 1));
        }
        System.out.println(sum);
    }
}