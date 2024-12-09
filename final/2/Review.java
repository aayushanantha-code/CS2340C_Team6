public class Review {
    private int rating;
    private String comment;
    private User user;

    public Review(int rating, String comment, User user) {
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }

    /**
     * Gets the rating of the review
     * @return the rating of the review
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Gets the comment of the review
     * @return the comment of the review
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Sets the rating of the review
     * @param rating the rating of the review
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Sets the comment of the review
     * @param comment the comment of the review
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
