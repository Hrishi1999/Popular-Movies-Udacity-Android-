package gridentertainment.net.popularmovies;

import java.util.List;

public class ReviewsHelper {

    private String author;
    private String content;
    private List<ReviewsHelper> results;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String name1) {
        this.author = name1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String name1) {
        this.author = content;
    }

    public List<ReviewsHelper> getResults() { return results; }

}
