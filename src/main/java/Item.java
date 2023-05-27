public class Item {
    private int id;
    private String type;//entree, dessert, beverage
    private String name;//burger, pizza, pasta, ice cream, pies, soda, water, juices
    private double price;
    private boolean availability;

    public Item(int id, String type, String name, double price, boolean availability) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public Item(String type, String name, double price, boolean availability) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }
}
