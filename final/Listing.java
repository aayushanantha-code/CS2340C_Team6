public class Listing {
    public String name;
    public String description;
    public double price;
    public String location;
    public String category;
    public String status;

    public Listing(String name, String description, double price, String location, String category) {
        createListing(name, description, price, location, category, "Unsold");
    }

    public void createListing(String name, String description, double price, String location, String category, String status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.location = location;
        this.category = category;
        this.status = status;
    }

}
