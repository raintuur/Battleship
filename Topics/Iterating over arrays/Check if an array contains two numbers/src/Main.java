import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = getNumbersFromInput(scanner);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println(occurInNumbersInSequence(n, m, numbers) || occurInNumbersInSequence(m, n, numbers));
    }

    private static int[] getNumbersFromInput(Scanner scanner) {
        int arraySize = scanner.nextInt();
        int[] numbers = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            numbers[i] = scanner.nextInt();
        }
        return numbers;
    }

    private static boolean occurInNumbersInSequence(int a, int b, int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i - 1] == a && numbers[i] == b) {
                return true;
            }
        }
        return false;
    }
}