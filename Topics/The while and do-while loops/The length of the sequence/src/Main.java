import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;
        int sequenceCount = 0;

        do {
            input = scanner.nextInt();
            sequenceCount++;
        } while (input != 0);

        System.out.println(--sequenceCount);
    }
}