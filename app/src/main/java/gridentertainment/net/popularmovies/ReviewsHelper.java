package gridentertainment.net.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReviewsHelper implements Parcelable {

    private String author;
    private String content;
    private List<ReviewsHelper> results;


    protected ReviewsHelper(Parcel in) {
        author = in.readString();
        content = in.readString();
        results = in.createTypedArrayList(ReviewsHelper.CREATOR);
    }

    public static final Creator<ReviewsHelper> CREATOR = new Creator<ReviewsHelper>() {
        @Override
        public ReviewsHelper createFromParcel(Parcel in) {
            return new ReviewsHelper(in);
        }

        @Override
        public ReviewsHelper[] newArray(int size) {
            return new ReviewsHelper[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeTypedList(results);
    }
}
