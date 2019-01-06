package bedsole.katrina.androidcodingchallenge;

/**
 * Model for Harry Potter Merchandise.
 */
public class HPMerch {
    String title;
    String imageURL;
    String author;

    public HPMerch(String title, String imageURL, String author) {
        this.title = title;
        this.imageURL = imageURL;
        this.author = author;
    }

    public String toString() {
        return "title= " + title + ", author= " + author + ", imageURL = " + imageURL;
    }
}
