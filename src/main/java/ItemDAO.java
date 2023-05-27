public interface ItemDAO {
    boolean addToCart(Item item);
    boolean removeFromCart(int id);
    Item getItem(int id);
    void viewCart();
    void clearCart();
    void checkout(double tip);
}
