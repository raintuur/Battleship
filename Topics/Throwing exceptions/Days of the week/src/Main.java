import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

public class Main {

    public static String getDayOfWeekName(int number) {
        if (number < 1 || number > 7) {
            throw new IllegalArgumentException();
        }
        return DayOfWeek.of(number).getDisplayName(TextStyle.SHORT, Locale.US);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}