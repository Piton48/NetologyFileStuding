package ru.netology;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;


public class Basket {
    protected static int[] prices;
    protected static String[] products;
    protected static int[] basket;
    protected Config config = new Config(new File("shop.xml"));

    public Basket(int[] prices, String[] products) throws IOException, ParserConfigurationException, SAXException {
        Basket.prices = prices;
        Basket.products = products;
        basket = new int[prices.length];
    }

    public Basket(int[] prices, String[] products, File file) throws IOException, ParserConfigurationException, SAXException {
        Basket.prices = prices;
        Basket.products = products;
        if (config.readConfig("load", "enabled").equals("true")) {
            if (config.readConfig("load", "format").equals("text")) loadFromTxtFile(file);
            if (config.readConfig("load", "format").equals("json")) ;
        }
    }

    public int getLength() {
        return basket.length;
    }

    public void showProducts() {
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " " + prices[i] + " руб/шт.");
        }
    }

    public void addToCart(int productNum, int amount) {
        basket[productNum] += amount;
    }

    public void saveTxt(File file) throws Exception {
        if (file.getName().endsWith(".txt")) {
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < basket.length; i++) {
                text.append(i).append(" ").append(basket[i]).append(" ");
            }
            PrintWriter out = new PrintWriter(file);
            out.println(text);
            out.close();
            file.createNewFile();
        }
    }

    void loadFromTxtFile(File textFile) {
        if (textFile.exists()) {
            StringBuilder text = new StringBuilder();
            try (Reader reader = new FileReader(textFile)) {
                int t;
                while ((t = reader.read()) != -1) {
                    text.append((char) t);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            String[] line = text.toString().split(" ");
            int y = 0;
            for (int i = 0; i < line.length - 1; i++) {
                if (i % 2 != 0) {
                    basket[y] = Integer.parseInt(line[i]);
                    y++;
                }
            }
            printCart();
        }
    }

    public static void printCart() {
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

    public void saveJson(File file) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < basket.length; i++) {
            jsonObject.put(products[i], basket[i]);
        }
        try (FileWriter writer = new
                FileWriter(file)) {
            writer.write(jsonObject.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromJsonFile(File file) {
        if (file.exists()) {
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(file));
                JSONObject jsonObject = (JSONObject) obj;
                for (int i = 0; i < basket.length; i++) {
                    String value = jsonObject.get(products[i]).toString();
                    basket[i] = Integer.parseInt(value);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        printCart();
    }

}
