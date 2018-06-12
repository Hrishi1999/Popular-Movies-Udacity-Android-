package gridentertainment.net.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TrailerHelper implements Parcelable {

    private static final String URL = "https://www.youtube.com/watch?v=";
    private String name;
    private String key;
    private String site;
    private String thumbnail;
    private List<TrailerHelper> results;

    protected TrailerHelper(Parcel in) {
        name = in.readString();
        key = in.readString();
        site = in.readString();
        thumbnail = in.readString();
        results = in.createTypedArrayList(TrailerHelper.CREATOR);
    }

    public static final Creator<TrailerHelper> CREATOR = new Creator<TrailerHelper>() {
        @Override
        public TrailerHelper createFromParcel(Parcel in) {
            return new TrailerHelper(in);
        }

        @Override
        public TrailerHelper[] newArray(int size) {
            return new TrailerHelper[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name1) {
        this.name = name1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key1) {
        this.name = key1;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site1) {
        this.name = site1;
    }

    public List<TrailerHelper> getResults() { return results; }

    public String getYoutube()
    {
        return URL + getKey();
    }

    public String getYoutubeThumbnail()
    {
        return "https://img.youtube.com/vi/" + getKey() + "/0.jpg";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
        parcel.writeString(site);
        parcel.writeString(thumbnail);
        parcel.writeTypedList(results);
    }
}
