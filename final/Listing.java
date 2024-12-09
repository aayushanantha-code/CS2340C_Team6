public class Listing {
    private String name;
    private String description;
    private double price;
    private String location;
    private String category;
    private String status;

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
    
    /**
     * Gets the name of the listing
     * @return the name of the listing
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description of the listing
     * @return the description of the listing
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the price of the listing
     * @return the price of the listing
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Gets the location of the listing
     * @return the location of the listing
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the category of the listing
     * @return the category of the listing
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Gets the status of the listing
     * @return the status of the listing
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the name of the listing
     * @param name the name of the listing
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the listing
     * @param description the description of the listing
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the price of the listing
     * @param price the price of the listing
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the location of the listing
     * @param location the location of the listing
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the category of the listing
     * @param category the category of the listing
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the status of the listing
     * @param status the status of the listing
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
