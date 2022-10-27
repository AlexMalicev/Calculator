import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("       ***** Клалькулятор *****");
        System.out.println("Допустим ввод чисел от 1 до 10 и от I до X");
        System.out.println("Выберите математическую операцию: + - * /");
        System.out.println("Введите выражение:");
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println(Calculator.calc(sc.nextLine()));
        } catch (InputException e) {
            System.err.println(e.getMessage());
        }
    }
}
class Calculator {
    public static boolean Operation(char c) {
        return c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/';
    }

    public static boolean ValidNumberpr(int num) {
        return num >= 0 && num <= 10;
    }

    public static boolean ValidNumberOtv(int num) {
        return num >= 0 && num <= 100;
    }

    public static String calc(String in) throws InputException {
        in = in.replaceAll(" ", "");

        if (in.isEmpty()) {
            throw new InputException("Ошибка ввода! Пустая входная строка.");
        }

        ArrayList <String> inputs = new ArrayList();
        for (int i = 0; i < in.length(); ++i) {
            if (Operation(in.charAt(i))) {
                inputs.add(in.substring(0, i));
                inputs.add(in.substring(i, i + 1));
                inputs.add(in.substring(i + 1));
                break;
            }
        }
        if (inputs.isEmpty()) {
            throw new InputException("Ошибка ввода! Не найдена допустимая операция.");
        } else if (inputs.size() == 1) {
            throw new InputException("Ошибка ввода! Отсутствуют значения.");
        } else if (inputs.size() == 2) {
            throw new InputException("Ошибка ввода! Одно из значений отсутствует.");
        }

        int Num1, Num2;
        boolean isRoman = false;

        try {
            Num1 = Integer.parseInt(inputs.get(0));
            try {
                Num2 = Integer.parseInt(inputs.get(2));
            } catch (NumberFormatException e) {
                throw new InputException("Ошибка ввода! Неожиданное значение " + inputs.get(2) + ".");
            }
        } catch (NumberFormatException e) {
            try {
                Num1 = RomanNumber.toRoman(inputs.get(0)).getNumber();
            } catch (IllegalArgumentException e1) {
                throw new InputException("Ошибка ввода! Неожиданное значение " + inputs.get(0) + ".");
            }
            try {
                Num2 = RomanNumber.toRoman(inputs.get(2)).getNumber();
            } catch (IllegalArgumentException e1) {
                throw new InputException("Ошибка ввода! Неожиданное значение " + inputs.get(2) + ".");
            }
            isRoman = true;
        }

        if (!ValidNumberpr(Num1) || !ValidNumberpr(Num2)) {
            throw new InputException("Ошибка ввода! Недопустимое значение " + (ValidNumberpr(Num2) ?
                    (isRoman ? RomanNumber.toRoman(Num1) : Num1) :
                    (isRoman ? RomanNumber.toRoman(Num2) : Num2)
            ) + ".");
        } else {
            int result;
            switch (inputs.get(1).charAt(0)) {
                case '+':
                    result = Num1 + Num2;
                    break;
                case '-':
                    result = Num1 - Num2;
                    break;
                case '*':
                    result = Num1 * Num2;
                    break;
                case '/':
                    result = Num1 / Num2;
                    break;
                default:
                    throw new InputException("Ошибка ввода! Неожиданный оператор " + inputs.get(1) + ".");
            }
            if (ValidNumberOtv(result)) {
                return isRoman ? RomanNumber.toRoman(result).toString() :
                        Integer.toString(result);

            } else {
                throw new InputException("Результат вычисления не поддерживается калькулятором.");
            }
        }
    }
}
class InputException extends Exception {
    public InputException() {
    }

    public InputException(String message) {
        super(message);
    }

    public InputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputException(Throwable cause) {
        super(cause);
    }

    public InputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
enum RomanNumber {
    I(1),II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10);

    private int number;

    RomanNumber(int num){
        this.number = num;
    }
    public int getNumber() {
        return number;
    }
    public static RomanNumber toRoman(int num){
        return values()[num-1];
    }
    public static RomanNumber toRoman(String str){
        return RomanNumber.valueOf(str);
    }
    public boolean moreThen(RomanNumber rhs){
        return this.getNumber() > rhs.getNumber();
    }
    public boolean lessThen(RomanNumber rhs){
        return this.getNumber() < rhs.getNumber();
    }
    public boolean equal(RomanNumber rhs){
        return this.getNumber() == rhs.getNumber();
    }
    public static RomanNumber sum(RomanNumber lhs, RomanNumber rhs){
        return toRoman(lhs.getNumber() + rhs.getNumber());
    }
    public static RomanNumber sub(RomanNumber lhs, RomanNumber rhs){
        return toRoman(lhs.getNumber() - rhs.getNumber());
    }
    public static RomanNumber mult(RomanNumber lhs, RomanNumber rhs){
        return toRoman(lhs.getNumber() * rhs.getNumber());
    }
    public static RomanNumber div(RomanNumber lhs, RomanNumber rhs){
        return toRoman(lhs.getNumber() / rhs.getNumber());
    }
}