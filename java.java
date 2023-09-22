import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Toy {
    class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight; // Вес в процентах от 100

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
}

public class ToyStore {
    private List<Toy> toys = new ArrayList<>();

    public void addToy(int id, String name, int quantity, double weight) {
        Toy toy = new Toy(id, name, quantity, weight);
        toys.add(toy);
    }

    public void updateToyWeight(int id, double newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.setWeight(newWeight);
                return;
            }
        }
        System.out.println("Игрушка с указанным ID не найдена.");
    }

    public void playToyLottery() {
        List<Toy> prizeToys = new ArrayList<>();
        double totalWeight = 0;

        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        if (totalWeight == 0) {
            System.out.println("Нет игрушек для розыгрыша.");
            return;
        }

        Random random = new Random();
        double randomValue = random.nextDouble() * totalWeight;

        for (Toy toy : toys) {
            randomValue -= toy.getWeight();
            if (randomValue <= 0) {
                prizeToys.add(toy);
                toy.setQuantity(toy.getQuantity() - 1);
                break;
            }
        }

        if (!prizeToys.isEmpty()) {
            Toy prizeToy = prizeToys.get(0);
            System.out.println("Поздравляем! Вы выиграли: " + prizeToy.getName());
            savePrizeToyToFile(prizeToy);
        }
    }

    private void savePrizeToyToFile(Toy prizeToy) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("prize_toys.txt", true))) {
            writer.println(prizeToy.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        // Добавляем игрушки
        toyStore.addToy(1, "Мяч", 10, 30);
        toyStore.addToy(2, "Кукла", 5, 20);
        toyStore.addToy(3, "Машинка", 8, 25);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false; // Добавляем условие завершения цикла
        @SuppressWarnings("InfiniteLoopStatement")
        while (!exit) {
            System.out.println("1. Добавить игрушку");
            System.out.println("2. Изменить вес игрушки");
            System.out.println("3. Розыгрыш игрушки");
            System.out.println("4. Выйти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("ID игрушки: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Чтобы съесть перевод строки
                    System.out.print("Название игрушки: ");
                    String name = scanner.nextLine();
                    System.out.print("Количество игрушек: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Вес игрушки (в % от 100): ");
                    double weight = scanner.nextDouble();

                    toyStore.addToy(id, name, quantity, weight);
                    break;
                case 2:
                    System.out.print("ID игрушки: ");
                    int toyId = scanner.nextInt();
                    System.out.print("Новый вес игрушки (в % от 100): ");
                    double newWeight = scanner.nextDouble();

                    toyStore.updateToyWeight(toyId, newWeight);
                    break;
                case 3:
                    toyStore.playToyLottery();
                    break;
                case 4:
                    exit = true; // Условие завершения цикла
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте еще раз.");
            }
        }
    }
}