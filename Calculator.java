import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String result = CalculatorApp.calc(scanner.nextLine());
        System.out.println(result);
    }
}
final class CalculatorApp {
    private static HashMap<String, Integer> roman = new HashMap<>(
            Map.ofEntries(
                    Map.entry("I", 1), Map.entry("II", 2),
                    Map.entry("III", 3), Map.entry("IV", 4),
                    Map.entry("V", 5), Map.entry("VI", 6),
                    Map.entry("VII", 7), Map.entry("VIII", 8),
                    Map.entry("IX", 9), Map.entry("X", 10)
            )
    );

    public static String calc(String input) throws Exception {
        var sub = input.split(" ");
        if(sub.length != 3)
            throw new Exception("Не коректный ввод");

        String result;
        if(isCorrectArab(sub[0]) && isCorrectArab(sub[2]) && isCorrectOperator(sub[1])) {
            result = String.valueOf(execute(Integer.parseInt(sub[0]), Integer.parseInt(sub[2]), sub[1]));
        } else if(isCorrectRoman(sub[0]) && isCorrectRoman(sub[2]) && isCorrectOperator(sub[1])) {
            int temp = execute(getArabFromRoman(sub[0]), getArabFromRoman(sub[2]), sub[1]);
            if(temp <= 0)
                throw new Exception("Не коректный ввод");

            result = getRomanFromArab(String.valueOf(temp));
        } else {
            throw new Exception("Не коректный ввод");
        }
        return result;
    }
    private static int execute(int a, int b, String operator) {
        int result = 0;

        switch(operator) {
            case "+": result = a + b;
                break;
            case "-": result = a - b;
                break;
            case "*": result = a * b;
                break;
            case "/": result = a / b;
                break;
        }

        return result;
    }
    private static int getArabFromRoman(String str) {
        return roman.get(str);
    }
    private static String getRomanFromArab(String str) {
        String[] num1 = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] num2 = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

        String result = "";
        int number = Integer.parseInt(str);
        int firstDigit = number / 10;
        int secondDigit = number % 10;

        if(number < 10) {
            result = num1[Integer.parseInt(str) - 1];
        } else if (secondDigit == 0) {
            result = num2[firstDigit - 1];
        } else {
            result = num2[firstDigit - 1].concat(num1[secondDigit - 1]);
        }

        return result;
    }
    private static boolean isCorrectArab(String str) {
        return !isCorrectRoman(str) && Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= 10;
    }
    private static boolean isCorrectOperator(String str) {
        return str.length() == 1 && "+-*/".contains(str);
    }
    private static boolean isCorrectRoman(String str) {
        return roman.getOrDefault(str, null) != null;
    }
}