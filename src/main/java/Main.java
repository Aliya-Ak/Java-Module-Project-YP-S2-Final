import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int numberOfGuests = getNumberOfGuests(scanner);
        if (numberOfGuests == 1) {
            System.out.println("Сам ел, сам плати.");
            return;
        }

        BillSplitter billSplitter = new BillSplitter();
        addItems(scanner, billSplitter);

        System.out.println("__________________");
        billSplitter.printItemList();
        System.out.println("__________________");
        System.out.println("ИТОГО:" + billSplitter.totalPrice);
        billSplitter.getDevidedPrice(numberOfGuests);

    }

    private static int getNumberOfGuests(Scanner scanner) {
        int numberOfGuests;

        while (true) {
            System.out.print("На скольких человек необходимо разделить счёт? ");
            if (scanner.hasNextInt()) {
                numberOfGuests = scanner.nextInt();
                if (numberOfGuests < 1) {
                    /*Количество человек меньше 1. Это некорректное значение для подсчёта.
                    Если значение некорректное, то показать ошибку и заново попросить пользователя ввести корректное количество гостей.
                    Запрашивать количество гостей необходимо до тех пор, пока не будет введено корректное значение > 1.
                     */
                    System.out.println(">> Призраки не платят. Попробуй еще раз.");
                } else {
                    //Если пользователь ввёл корректное значение > 1, то программа продолжает выполнение.
                    //
                    System.out.println("Давай считать!");
                    break;
                }
            } else{
                System.out.println(">> Нужно ввести цифру (1,2,3...). Попробуй еще раз.");
                scanner.next();
            }
        }
        return numberOfGuests;
    }

    private static void addItems(Scanner scanner, BillSplitter billSplitter) {
        while (true) {
            System.out.print("Введите Название товара (или 'Завершить' для завершения): ");
            String itemName = scanner.next();

            if (itemName.equalsIgnoreCase("Завершить")) {
                break;
            }
            double itemPrice = getItemPrice(scanner);
            billSplitter.addItem(itemName, itemPrice);
        }
    }

    private static double getItemPrice(Scanner scanner) {
        double itemPrice;
        while (true) {
            System.out.print("Введите Цену товара : ");
            if (scanner.hasNextDouble()) {
                itemPrice = scanner.nextDouble();
                if (itemPrice < 0) {
                    System.out.println(">> Цена не может быть отрицательной. Попробуй еще раз.");
                } else {
                    scanner.nextLine();
                    break;
                }
            } else {
                System.out.println(">> Нужно ввести число, например 10.45 или 11.40. Попробуй еще раз.");
                scanner.next();
            }
        }
        return itemPrice;
    }
}

class BillSplitter {
    ArrayList<Item> ItemList = new ArrayList<>();
    double totalPrice = 0.0;

    public void addItem(String itemName, double itemPrice) {
        Item newItem = new Item(itemName, itemPrice);
        ItemList.add(newItem);
        System.out.println("Добавлен товар: '" + itemName + "' (" + itemPrice + " р.)");
        totalPrice = totalPrice + itemPrice;
    }
    public void printItemList() {
        System.out.println("Добавленные товары:");
        for (Item item : ItemList) {
           System.out.println(item.getName()+" - "+item.getPrice()+" р.");
        }
    }
    public void getDevidedPrice(int numberOfGuests) {
        if (numberOfGuests <= 0) {
            System.out.println("Ошибка!");
        }

        double pricePerPerson = totalPrice / numberOfGuests;
        long rubles = (long) Math.floor(pricePerPerson);

        String rubleEnding;
        if (rubles % 10 == 1 && rubles % 100 != 11) {
            rubleEnding = "рубль";
        } else if (rubles % 10 >= 2 && rubles % 10 <= 4 && (rubles % 100 < 10 || rubles % 100 >= 20)) {
            rubleEnding = "рубля";
        } else {
            rubleEnding = "рублей";
        }
        System.out.println(String.format("%.2f", pricePerPerson) + " " + rubleEnding+" с человека.");
    }
}

class Item {
    private final String name;
    private final double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

}

