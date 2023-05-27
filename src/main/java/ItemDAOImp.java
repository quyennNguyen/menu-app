import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class ItemDAOImp implements ItemDAO {

    private static DecimalFormat numberFormat = new DecimalFormat("0.00");
    private static final double TAX = 0.06;
    private Connection connection;

    public ItemDAOImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addToCart(Item item) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO item (type, name, price, availability)
                    VALUES (?,?,?,?);
                    """);
            statement.setString(1, item.getType());
            statement.setString(2, item.getName());
            statement.setDouble(3, item.getPrice());
            statement.setBoolean(4, item.isAvailability());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeFromCart(int targetId) {
        Item item = getItem(targetId);
        if (item != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("""
                        DELETE FROM item WHERE id=?
                        """);
                statement.setInt(1, targetId);
                statement.executeUpdate();
                statement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Item getItem(int targetId) {
        Item item = null;
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM item WHERE id=?
                    """);
            statement.setInt(1, targetId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String type = result.getString("type");
                String name = result.getString("name");
                double price = result.getDouble("price");
                boolean availability = result.getBoolean("availability");
                item = new Item(id, type, name, price, availability);
            }
            statement.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void viewCart() {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM item
                    """);
            ResultSet result = statement.executeQuery();
            Item item = null;
            while (result.next()) {
                int id = result.getInt("id");
                String type = result.getString("type");
                String name = result.getString("name");
                double price = result.getDouble("price");
                boolean availability = result.getBoolean("availability");
                item = new Item(id, type, name, price, availability);
                System.out.println(item);
            }
            if (item == null) {
                System.out.println("Your cart is empty.");
            }
            statement.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clearCart() {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                        DELETE FROM item
                        """);
            System.out.println("Your cart is now empty.");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkout(double inputTip) {
        double total = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM item
                    """);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                total += result.getDouble("price");
            }
            if (total != 0) {
                System.out.println("""
                        RECEIPT
                        **********
                        THE JAVA CLUB
                        000 Hell Rd,
                        Blackhole VOID 20169
                        **********
                        """);
                viewCart();
                System.out.println("\n**********\n");
                System.out.println("SUBTOTAL: $" + numberFormat.format(total));
                double tax = total * TAX;
                System.out.println("TAX: $" + numberFormat.format(tax));
                double tip = total * inputTip / 100;
                System.out.println("TIP: $" + numberFormat.format(tip));
                total += tax + tip;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("TOTAL: $" + numberFormat.format(total));
    }
}
