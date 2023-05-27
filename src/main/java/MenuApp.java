import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuApp {
    private static Scanner scan = new Scanner(System.in);
    private static Connection connection = DBConnect.getConnection();
    private static ItemDAO itemDAO = new ItemDAOImp(connection);

    public static void main(String[] args) {
        System.out.println("~ WELCOME TO THE JAVA CLUB ~");
        int option = 0;
        do {
            System.out.println("""
                                        
                    ~ MENU ~
                    - Press (1) to add an item to your cart
                    - Press (2) to remove an item from your cart
                    - Press (3) to view your cart
                    - Press (4) to clear your cart
                    - Press (5) to checkout
                    - Press (6) to exit
                    """);
            try {
                option = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                scan.nextLine();
                continue;
            }
            switch (option) {
                case 1 -> addItem();
                case 2 -> removeItem();
                case 3 -> viewCart();
                case 4 -> clearCart();
                case 5 -> checkout();
                case 6 -> System.out.println("""
                        ~ Thank you for visiting ~
                        ~ See you again soon ~""");
                default -> System.out.println("Option is not available. Please try again!");
            }
        } while (option != 6);
        scan.close();
        DBConnect.closeConnection(connection);
    }

    private static void addItem() {
        System.out.println("Please Choose from Options below:");
        String type = "";
        String name = "";
        double price = 0;
        boolean availability = false;
        int optionForType = 0;
        do {
            System.out.println("""
                    - Press (1) for ENTREES
                    - Press (2) for SIDES
                    - Press (3) for DESSERTS
                    - Press (4) for BEVERAGES""");
            try {
                optionForType = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                scan.nextLine();
                continue;
            }
            switch (optionForType) {
                case 1 -> {
                    type = "Entrees";
                    int optionForEntrees = 0;
                    do {
                        System.out.println("""
                                ENTREES:
                                - Press (1) for BURGER
                                - Press (2) for PIZZA
                                - Press (3) for CHICKEN WINGS""");
                        try {
                            optionForEntrees = scan.nextInt();
                            scan.nextLine();
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                            scan.nextLine();
                            continue;
                        }
                        switch (optionForEntrees) {
                            case 1 -> name = "Burger";
                            case 2 -> name = "Pizza";
                            case 3 -> name = "Chicken Wings";
                            default -> System.out.println("Option is not available. Please try again!");
                        }
                    } while (optionForEntrees != 1 && optionForEntrees != 2 && optionForEntrees != 3);
                    price = 9.99;
                    availability = true;
                }
                case 2 -> {
                    type = "Sides";
                    int optionForSide = 0;
                    do {
                        System.out.println("""
                                SIDES:
                                - Press (1) for FRIES
                                - Press (2) for SALAD
                                - Press (3) for GRILLED CORN""");
                        try {
                            optionForSide = scan.nextInt();
                            scan.nextLine();
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                            scan.nextLine();
                            continue;
                        }
                        switch (optionForSide) {
                            case 1 -> name = "Fries";
                            case 2 -> name = "Salad";
                            case 3 -> name = "Grilled Corn";
                            default -> System.out.println("Option is not available. Please try again!");
                        }
                    } while (optionForSide != 1 && optionForSide != 2 && optionForSide != 3);
                    price = 3.99;
                    availability = true;
                }
                case 3 -> {
                    type = "Dessert";
                    int optionForDessert = 0;
                    do {
                        System.out.println("""
                                DESSERTS:
                                - Press (1) for ICE CREAM
                                - Press (2) for APPLE PIES
                                - Press (3) for CHOCO LAVE CAKE""");
                        try {
                            optionForDessert = scan.nextInt();
                            scan.nextLine();
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                            scan.nextLine();
                            continue;
                        }
                        switch (optionForDessert) {
                            case 1 -> name = "Ice Cream";
                            case 2 -> name = "Apple Pies";
                            case 3 -> name = "Choco Lava Cake";
                            default -> System.out.println("Option is not available. Please try again!");
                        }
                    } while (optionForDessert != 1 && optionForDessert != 2 && optionForDessert != 3);
                    price = 3.99;
                    availability = true;
                }
                case 4 -> {
                    type = "Beverages";
                    int optionForDrink = 0;
                    do {
                        System.out.println("""
                                BEVERAGES:
                                - Press (1) for WATER
                                - Press (2) for JUICES
                                - Press (3) for SODA""");
                        try {
                            optionForDrink = scan.nextInt();
                            scan.nextLine();
                        } catch (InputMismatchException e) {
                            e.printStackTrace();
                            scan.nextLine();
                            continue;
                        }
                        switch (optionForDrink) {
                            case 1 -> name = "Water";
                            case 2 -> name = "Juices";
                            case 3 -> name = "Soda";
                            default -> System.out.println("Option is not available. Please try again!");
                        }
                    } while (optionForDrink != 1 && optionForDrink != 2 && optionForDrink != 3);
                    price = 1.99;
                    availability = true;
                }
                default -> System.out.println("Option is not available. Please try again!");
            }
        } while (optionForType != 1 && optionForType != 2 && optionForType != 3 && optionForType != 4);
        if (itemDAO.addToCart(new Item(type, name, price, availability))) {
            System.out.println("You have successfully added an item.");
        } else {
            System.out.println("Something is wrong. Please try again!");
        }
    }

    private static void removeItem() {
        int id = 0;
        do {
            System.out.print("Please enter ID of the item: ");
            try {
                id = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                scan.nextLine();
                continue;
            }
        } while (id == 0);
        if (itemDAO.removeFromCart(id)) {
            System.out.println("You have successfully removed an item.");
        } else {
            System.out.println("Invalid ID or Something is wrong. Please try again!");
        }
    }

    private static void viewCart() {
        itemDAO.viewCart();
    }

    private static void clearCart() {
        itemDAO.clearCart();
    }

    private static void checkout() {
        double tip = 0;
        int option = 0;
        do {
            System.out.println("""
                Suggested Gratuity:
                - Press (1) for 10% tip
                - Press (2) for 15% tip
                - Press (3) for 20% tip
                - Press (4) for none""");
            try {
                option = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                e.printStackTrace();
                scan.nextLine();
                continue;
            }
            switch (option) {
                case 1 -> tip = 10;
                case 2 -> tip = 15;
                case 3 -> tip = 20;
                case 4 -> tip = 0;
                default -> System.out.println("Option is not available. Please try again!");
            }
        } while (option != 1 && option != 2 && option != 3 && option != 4);
        itemDAO.checkout(tip);
    }

}
