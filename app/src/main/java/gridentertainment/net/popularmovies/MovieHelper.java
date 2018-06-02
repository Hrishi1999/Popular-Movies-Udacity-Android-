package gridentertainment.net.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieHelper {

    @SerializedName("title")
    private String movTitle;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String ratings;
    @SerializedName("release_date")
    private String release;
    @SerializedName("results")
    private List<MovieHelper> results;


    public static final String PATH = "http://image.tmdb.org/t/p/w342";

    public String getMovTitle() {
        return movTitle;
    }

    public void setMovTitle(String title) {
        this.movTitle = title;
    }

    public String getPosterPath() {
        return PATH + posterPath;
    }

    public void setPosterPath(String poster) {
        this.posterPath = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String description) {
        this.overview = overview;
    }

    public String getRatings() {
        return ratings;
    }

    public String getRelease() { return release; }

    public void setRelease(String releaseD) {
        this.release = releaseD;
    }

    public void getRatings(String rating) {
        this.ratings = rating;
    }

    public List<MovieHelper> getResults() { return results; }


}

