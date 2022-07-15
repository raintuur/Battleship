import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long m = scanner.nextLong();
        long previousFactorial = 1;
        long factorial = 2;
        long factorialSum = 1;


        while (factorialSum <= m) {
            previousFactorial = factorial;
            factorialSum *= factorial;
            factorial++;
        }

        System.out.println(previousFactorial);

    }
}