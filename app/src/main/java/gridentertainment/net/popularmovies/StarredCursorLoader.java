package gridentertainment.net.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

public class StarredCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private StarredAdapter starredAdapter;

    public StarredCursorLoader(Context context, StarredAdapter starredAdapter) {
        this.context = context;
        this.starredAdapter = starredAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case 11:
                String[] projection = {
                        MovieDBContract.Movie.COLUMN_MOVIE_TITLE,
                        MovieDBContract.Movie.COLUMN_MOVIE_ID,
                        MovieDBContract.Movie.COLUMN_MOVIE_DESCRIPTION,
                        MovieDBContract.Movie.COLUMN_MOVIE_POSTER_PATH,
                        MovieDBContract.Movie.COLUMN_MOVIE_RELEASE_DATE,
                        MovieDBContract.Movie.COLUMN_MOVIE_VOTE_AVERAGE
                };
                return new CursorLoader(context,
                        MovieDBContract.Movie.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader failed: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        starredAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        starredAdapter.swapCursor(null);
    }
}