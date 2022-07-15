import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int currentMaxNumber = 0;
        int i = 0;

        do {
            i = scanner.nextInt();
            if (i > currentMaxNumber) {
                currentMaxNumber = i;
            }
        } while (i != 0);
        System.out.println(currentMaxNumber);
    }
}