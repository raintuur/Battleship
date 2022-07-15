import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();
            try {
                int number = Integer.parseInt(input);
                if (number == 0) {
                    break;
                }
                System.out.println(number * 10);
            } catch (Exception e) {
                System.out.println("Invalid user input: " + input);
            }
        }
    }
}