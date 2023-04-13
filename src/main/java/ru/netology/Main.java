package ru.netology;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException
            , ParserConfigurationException, SAXException {

        Config config = new Config(new File("shop.xml"));
        String[] products = {"Хлеб", "Молоко", "Мясо"};
        int[] prices = {50, 90, 350};
        File logFile = new File(config.readConfig("log", "fileName"));
        Basket basket = new Basket(prices, products
                , new File(config.readConfig("load", "fileName")));
        ClientLog log = new ClientLog();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            basket.showProducts();
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
                if ((Integer.parseInt(parts[0]) > basket.getLength())
                        | (Integer.parseInt(parts[0]) < 1)) {
                    System.out.println("Вы ввели не существующий товар! Попробуйте еще раз.");
                    continue;
                }

                if (Integer.parseInt(parts[1]) < 0) {
                    System.out.println("Вы ввели отрицательное колличество товаров! Попробуйте еще раз.");
                    continue;
                }

                basket.addToCart(Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1]));

                if (config.readConfig("save", "enabled").equals("true")) {
                    if (config.readConfig("save", "format").equals("json")) {
                        basket.saveJson(new File(config.readConfig("save", "fileName")));
                    }
                    if (config.readConfig("save", "format").equals("text")) {
                        basket.saveTxt(new File(config.readConfig("save", "fileName")));
                    }
                }

                log.log(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));


            } catch (NumberFormatException exception) {
                System.out.println("Введены некорректные данные! Попробуйте еще раз");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            basket.printCart();
        }

        if (config.readConfig("log", "enabled").equals("true"))
            log.exportAsCSV(logFile);
    }

}
