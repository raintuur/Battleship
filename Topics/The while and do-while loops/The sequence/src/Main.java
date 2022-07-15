import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfElements = scanner.nextInt();
        int maxDivisibleByFour = 0;
        while (numberOfElements > 0) {
            int input = scanner.nextInt();
            if (input % 4 == 0 && input > maxDivisibleByFour) {
                maxDivisibleByFour = input;
            }
            numberOfElements--;
        }
        System.out.println(maxDivisibleByFour);
    }
}