package ru.netology;

import java.io.*;

public class Basket {
    protected int[] prices;
    protected String[] products;
    protected int[] basket;

    public Basket(int[] prices, String[] products, File file) {
        this.prices = prices;
        this.products = products;
        this.basket = new int[prices.length];
        if (file.exists()) {
            String[] text = loadFromTxtFile(file).split(" ");
            int y = 0;
            for (int i = 0; i < text.length - 1; i++) {
                if (i % 2 != 0) {
                    this.basket[y] = Integer.parseInt(text[i]);
                    y++;
                }
            }
            printCart();
        }
    }

    public int getLength() {
        return this.basket.length;
    }

    public void showProducts() {
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < this.products.length; i++) {
            System.out.println(this.products[i] + " " + this.prices[i] + " руб/шт.");
        }
    }

    public void addToCart(int productNum, int amount) {
        this.basket[productNum] += amount;
    }

    public void saveTxt(File file) throws Exception {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < this.basket.length; i++) {
            text.append(i).append(" ").append(basket[i]).append(" ");
        }
        PrintWriter out = new PrintWriter(file);
        out.println(text);
        out.close();
        file.createNewFile();
    }

    static String loadFromTxtFile(File textFile) {
        StringBuilder text = new StringBuilder();
        try (Reader reader = new FileReader(textFile)) {
            int t;
            while ((t = reader.read()) != -1) {
                text.append((char) t);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return text.toString();
    }

    public void printCart() {
        int sum = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < basket.length; i++) {
            if (basket[i] != 0) {
                System.out.println(products[i] + " " + basket[i] + " шт. " + prices[i] + " руб/шт " +
                        (prices[i] * basket[i]) + " руб. в сумме");
                sum += prices[i] * basket[i];
            }
        }
        System.out.println("Итого: " + sum + " руб.");
    }
}
