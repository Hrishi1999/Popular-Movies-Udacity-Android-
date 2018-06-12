package gridentertainment.net.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieHelper implements Parcelable {

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
    private int id;

    public static final String PATH = "http://image.tmdb.org/t/p/w342";

    protected MovieHelper(Parcel in) {
        movTitle = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        ratings = in.readString();
        release = in.readString();
        id = in.readInt();
        results = in.createTypedArrayList(MovieHelper.CREATOR);
    }

    public static final Creator<MovieHelper> CREATOR = new Creator<MovieHelper>() {
        @Override
        public MovieHelper createFromParcel(Parcel in) {
            return new MovieHelper(in);
        }

        @Override
        public MovieHelper[] newArray(int size) {
            return new MovieHelper[size];
        }
    };

    public MovieHelper() {

    }

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
        this.overview = description;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getRelease() { return release; }

    public void setRelease(String releaseD) {
        this.release = releaseD;
    }

    public List<MovieHelper> getResults() { return results; }

    public int getId() {
        return id;
    }

    public void setId(int id1) {
        this.id = id1;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movTitle);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(ratings);
        parcel.writeString(release);
        parcel.writeInt(id);
        parcel.writeTypedList(results);
    }
}

