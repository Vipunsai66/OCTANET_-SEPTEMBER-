import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}

class Receipt {
    private ArrayList<Item> items;
    private double taxRate;
    private double discountRate;

    public Receipt(double taxRate, double discountRate) {
        this.items = new ArrayList<>();
        this.taxRate = taxRate;
        this.discountRate = discountRate;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public double calculateSubtotal() {
        return items.stream().mapToDouble(Item::getTotalPrice).sum();
    }

    public double calculateTax() {
        return calculateSubtotal() * taxRate;
    }

    public double calculateDiscount() {
        return calculateSubtotal() * discountRate;
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateTax() - calculateDiscount();
    }

    public void generateReceipt() {
        System.out.println("Receipt:");
        System.out.println("Item Name\tPrice\tQuantity\tTotal");
        for (Item item : items) {
            System.out.printf("%s\t%.2f\t%d\t\t%.2f\n", item.getName(), item.getPrice(), item.getQuantity(), item.getTotalPrice());
        }
        System.out.printf("Subtotal:\t\t\t\t%.2f\n", calculateSubtotal());
        System.out.printf("Tax:\t\t\t\t\t%.2f\n", calculateTax());
        System.out.printf("Discount:\t\t\t\t-%.2f\n", calculateDiscount());
        System.out.printf("Total:\t\t\t\t\t%.2f\n", calculateTotal());
    }

    public void saveReceiptToFile(String filename) {
        try (FileWriter fileWriter = new FileWriter(filename); PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println("Receipt:");
            printWriter.println("Item Name\tPrice\tQuantity\tTotal");
            for (Item item : items) {
                printWriter.printf("%s\t%.2f\t%d\t\t%.2f\n", item.getName(), item.getPrice(), item.getQuantity(), item.getTotalPrice());
            }
            printWriter.printf("Subtotal:\t\t\t\t%.2f\n", calculateSubtotal());
            printWriter.printf("Tax:\t\t\t\t\t%.2f\n", calculateTax());
            printWriter.printf("Discount:\t\t\t\t-%.2f\n", calculateDiscount());
            printWriter.printf("Total:\t\t\t\t\t%.2f\n", calculateTotal());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

public class ReceiptGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Receipt receipt = new Receipt(0.07, 0.05);  // 7% tax rate, 5% discount rate

        while (true) {
            System.out.println("Enter item name (or type 'done' to finish):");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) {
                break;
            }

            System.out.println("Enter price of " + name + ":");
            double price = scanner.nextDouble();

            System.out.println("Enter quantity of " + name + ":");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // consume newline

            receipt.addItem(new Item(name, price, quantity));
        }

        receipt.generateReceipt();

        System.out.println("Do you want to save the receipt to a file? (yes/no)");
        String save = scanner.nextLine();
        if (save.equalsIgnoreCase("yes")) {
            System.out.println("Enter filename:");
            String filename = scanner.nextLine();
            receipt.saveReceiptToFile(filename);
        }

        scanner.close();
    }
}
