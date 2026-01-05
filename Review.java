public class Review {

    private final int reviewId;
    private final int rating; // 1–5
    private final String text;

    public Review(int reviewId, int rating, String text) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1–5");
        this.reviewId = reviewId;
        this.rating = rating;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + "/5 - " + text;
    }
}
