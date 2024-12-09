public class Cart {
    public List items;

    /**
     * Constructor for Cart
     */
    public Cart() {
        items = new ArrayList<Item>();
    }

    /**
     * Adds an item to the cart
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the cart
     * @param item
     * @return The item removed
     */
    public Item removeItem(Item item) {
        items.remove(item);
        return item;
    }
}
