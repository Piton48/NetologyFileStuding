import java.io.*;

public class Basket {
    protected int[] prices;
    protected String[] products;
    protected int[] basket;

    public Basket(int[] prices, String[] products, File file) throws IOException, ClassNotFoundException {
        this.prices = prices;
        this.products = products;
        this.basket = new int[prices.length];
        if (file.exists()) {
            loadFromBinFile(file);
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

    public void saveBin(File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(this.basket);
        }
    }

    public void loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)){
            this.basket = (int[]) ois.readObject();
        }
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
