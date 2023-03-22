import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String[] products = {"Хлеб", "Молоко", "Мясо"};
        int[] prices = {50, 90, 350};

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " " + prices[i] + " руб/шт.");
        }

        Scanner scanner = new Scanner(System.in);
        int[] basket = new int[products.length];

        while (true) {
            System.out.println("Введите номер товара и колличество, или введите end");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }


            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Неверный ввод! Попробуйте еще раз.");
                continue;
            }
            try {
                if ((Integer.parseInt(parts[0]) > products.length) | (Integer.parseInt(parts[0]) < 1)) {
                    System.out.println("Вы ввели не существующий товар! Попробуйте еще раз.");
                    continue;
                }

                if (Integer.parseInt(parts[1]) < 0) {
                    System.out.println("Вы ввели отрицательное колличество товаров! Попробуйте еще раз.");
                    continue;
                }
                basket[(Integer.parseInt(parts[0]) - 1)] += Integer.parseInt(parts[1]);
            } catch (NumberFormatException exception) {
                System.out.println("Введены некорректные данные! Попробуйте еще раз");
            }
        }

        int sum = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < basket.length; i++) {
            if (basket[i] != 0) {
                System.out.println(products[i] + " " + basket[i] + " шт. " + prices[i] + " руб/шт " +
                        (prices[i] * basket[i]) + " руб в сумме");
                sum += prices[i] * basket[i];
            }
        }
        System.out.println("Итого " + sum + " руб");

    }
}