package gridentertainment.net.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

class MovieDBContract {

    private MovieDBContract() {
    }

    public static final String AUTHORITY = "gridentertainment.net.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class Movie implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_DESCRIPTION = "movieDescription";
        public static final String COLUMN_MOVIE_POSTER_PATH = "moviePosterPath";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movieVoteAverage";

        public static final String CREATE_TABLE_MOVIES = "CREATE TABLE " +
                Movie.TABLE_NAME + "(" +
                Movie._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Movie.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                Movie.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                Movie.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL," +
                Movie.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL," +
                Movie.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                Movie.COLUMN_MOVIE_VOTE_AVERAGE + " LONG NOT NULL" +
                ");";
    }
}