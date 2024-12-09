public class Review {
    private int rating;
    private String comment;

    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
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
}
