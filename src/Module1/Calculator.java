package Module1;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Calculator {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("""
        Добро пожаловать в калькулятор!
        C - сбросить предыдущие вычисления
        s - выключить калькулятор""");

    while (true) {
      try {
        System.out.println("Введите первое число");
        int firstNumber = scanner.nextInt();
        boolean isSavedResult = true;

        while (true) {
          System.out.println("Введите оператор или команду: ");
          char operation = scanner.next().charAt(0);

          switch (operation) {
            case 's':
              return;
            case 'C':
              isSavedResult = false;
              firstNumber = 0;
              break;
          }

          if (!isSavedResult) {
            System.out.println("Память сброшена");
            break;
          }
          
          if (!List.of('+', '-', '*', '/').contains(operation)) {
            System.err.println("Неизвестный оператор");
            break;
          }

          System.out.println("Введите второе число: ");
          int secondNumber = scanner.nextInt();
          firstNumber = calculateExpression(firstNumber, secondNumber, operation);
          System.out.println(firstNumber);
        }
      } catch (InputMismatchException e) {
        System.err.println("Неизвестное число");
        return;
      } catch (ArithmeticException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  private static int calculateExpression(int firstNumber, int secondNumber, char operation) {
    try {
      return switch (operation) {
        case '+' -> firstNumber + secondNumber;
        case '-' -> firstNumber - secondNumber;
        case '*' -> firstNumber * secondNumber;
        case '/' -> firstNumber / secondNumber;
        default -> throw new ArithmeticException();
      };
    } catch (ArithmeticException e) {
      throw new ArithmeticException("Ошибка при вычислении");
    }
  }
}
