// проверка
import java.util.Locale;
import java.util.Scanner;

/**
 * - Метод умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b. Данные передаются в одну строку (смотрите пример)! Решения, в которых каждое число и арифмитеческая операция передаются с новой строки считаются неверными.
   - Метод умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.
   - Метод должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.
   - Метод умеет работать только с целыми числами.
   - Метод умеет работать только с арабскими или римскими цифрами одновременно, при вводе пользователем строки вроде 3 + II метод должен выбросить исключение и прекратить свою работу.
   - При вводе римских чисел, ответ должен быть выримскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.
   - При вводе пользователем неподходящих чисел метдод выбрасывает исключение и завершает свою работу.
   - При вводе пользователем строки, не соответствующей одной из вышеопведен исанных арифметических операций, метод выбрасывает исключение и завершает свою работу
   - Результатом операции деления является целое число, остаток отбрасывается.
   - Результатом работы метода с арабскими числами могут быть отрицательные числа и ноль. Результатом работы метода с римскими числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение
 */


public class Calculator {
    public static void main(String[] args) throws Exception {
        CalculatorHelper calculatorHelper = new CalculatorHelper();
        System.out.println("Введите выражение:");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String input = myObj.nextLine();  // Read user input
        System.out.println(calculatorHelper.calc(input));
    }
}

class CalculatorHelper {
    public String calc(String input) throws Exception {
        String[] splitText = input.split(" ");
        Boolean rome = false;
        int letter1, letter2;

        int countLetters = 0;

        for (int i = 0; i < 3; i+=2) {
            try {
                Integer.parseInt(splitText[i]);
            } catch (NumberFormatException e) {
                rome = true;
                countLetters++;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("Input expression must be: <digit> <math operator> <digit>");
            }
        }

        if (countLetters == 1) {
            throw new Exception("letter format do not consist");
        }

        letter1 = getLetter(splitText[0]);
        if (letter1 <= 0)
            throw new Exception("Arab letter should be between 0 and 10");
        letter2 = getLetter(splitText[2]);
        if (letter2 <= 0)
            throw new Exception("Arab letter should be between 0 and 10");
        int result = getResult(letter1, letter2, splitText[1]);
        String output;

        if (rome) {
            if (result < 0) {
                throw new Exception("Rome letter result should be between 0 and 10");
            } else output = getRomeNumber(result);
        } else {

            /* if (result >= 0) {
                throw new Exception("Arab letter should be < 0");
            } else  */output = String.valueOf(result);

        }
        return output;
    }

    public Integer getLetter(String letter) throws Exception {
        int integer = 0;

        try {
            integer = Integer.parseInt(letter);
        } catch (Exception e) {
            switch (letter.toLowerCase(Locale.ROOT)) {
                case "i":
                    integer = 1;
                    break;
                case "ii":
                    integer = 2;
                    break;
                case "iii":
                    integer = 3;
                    break;
                case "iv":
                    integer = 4;
                    break;
                case "v":
                    integer = 5;
                    break;
                case "vi":
                    integer = 6;
                    break;
                case "vii":
                    integer = 7;
                    break;
                case "viii":
                    integer = 8;
                    break;
                case "ix":
                    integer = 9;
                    break;
                case "x":
                    integer = 10;
                    break;
                default:
                    throw new Exception("Rome letter > 10");
            }
        }
        return integer;
    }

    public String getRomeNumber(int num) throws Exception {
        String romeNum = null;
        if (num < 1)
            throw new Exception("Rome answer must be > 0");
        switch (num) {
            case 1:
                romeNum = "I";
                break;
            case 2:
                romeNum = "II";
                break;
            case 3:
                romeNum = "III";
                break;
            case 4:
                romeNum = "IV";
                break;
            case 5:
                romeNum = "V";
                break;
            case 6:
                romeNum = "VI";
                break;
            case 7:
                romeNum = "VII";
                break;
            case 8:
                romeNum = "VIII";
                break;
            case 9:
                romeNum = "IX";
                break;
            case 10:
                romeNum = "X";
                break;
            default:
                romeNum = Roman2Arabic.toRoman(num);
        }
        return romeNum;
    }

    public Integer getResult(int letter1, int letter2, String s) throws Exception {
        int result = 0;
        switch (s) {
            case "/":
                result = letter1 / letter2;
                break;
            case "+":
                result = letter1 + letter2;
                break;
            case "-":
                result = letter1 - letter2;
                break;
            case "*":
                result = letter1 * letter2;
                break;
            default:
                throw new Exception("Wrong operation format");
        }

        return result;
    }
}


class Roman2Arabic {
    private static int[] intervals={0, 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static String[] numerals={"", "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    
    /* public static void main(String args[]) {
        int i=1974;
        String roman=toRoman(i);
        int arabic = toArabic(roman);
        System.out.println("Initial="+i+", roman="+roman+", arabic="+arabic);
    } */
    
     /**
     * Dichotomic search in sorted array of intervals
     * @param number
     * @return floor index closest to number
     */
    private final static int findFloor(final int number, final int firstIndex, final int lastIndex) {
        if(firstIndex==lastIndex)
            return firstIndex;
        if(intervals[firstIndex]==number)
            return firstIndex;
        if(intervals[lastIndex]==number)
            return lastIndex;
        final int median=(lastIndex+firstIndex)/2;
        if(median==firstIndex)
            return firstIndex;
        if(number == intervals[median])
            return median;
        if(number > intervals[median])
            return findFloor(number, median, lastIndex);
        else
            return findFloor(number, firstIndex, median);

    }

    public final static String toRoman(final int number) {
        int floorIndex=findFloor(number, 0, intervals.length-1);
        if(number==intervals[floorIndex])
            return numerals[floorIndex];
        return numerals[floorIndex]+toRoman(number-intervals[floorIndex]);
    }
    
    public static int toArabic(String roman) {
        int result = 0;
        for (int i = intervals.length-1; i >= 0; i-- ) {
            while (roman.indexOf(numerals[i]) == 0 && numerals[i].length() > 0) {
                result += intervals[i];
                roman = roman.substring(numerals[i].length());
            }
        }
        return result;
    }
}
