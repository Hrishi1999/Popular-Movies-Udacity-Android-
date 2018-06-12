package gridentertainment.net.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class StarredAdapter extends RecyclerView.Adapter<StarredAdapter.StarredHolder> {

    private Cursor mCursor;

    @Override
    public StarredHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movieitem, parent, false);
        StarredHolder viewHolder = new StarredHolder(view);
        return viewHolder;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final StarredHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.textView.setText(mCursor.getString(
                mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_TITLE)));
        String posterPath = mCursor.getString(
                mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_POSTER_PATH));
        if(posterPath!=null)
        {
            Picasso.get()
                    .load(posterPath)
                    .placeholder(R.color.colorAccent)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            holder.imageView.setImageBitmap(bitmap);
                            Palette.from(bitmap)
                                    .generate(new Palette.PaletteAsyncListener() {
                                        @Override
                                        public void onGenerated(Palette palette) {
                                            Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                            if (textSwatch == null) {
                                                return;
                                            }
                                            holder.linearLayout.setBackgroundColor(textSwatch.getRgb());
                                            holder.textView.setTextColor(textSwatch.getBodyTextColor());
                                        }
                                    });
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
        else
        {
            holder.imageView.setImageDrawable(new ColorDrawable(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public class StarredHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView textView;
        public LinearLayout linearLayout;


        public StarredHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.title);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(), MovieDetailsActivity.class);
            mCursor.moveToPosition(getAdapterPosition());
            MovieHelper currentMovie = getMovieFromCursor();
            intent.putExtra("data", currentMovie);
            intent.putExtra("fromStarred", 1);
            itemView.getContext().startActivity(intent);
        }

        @NonNull
        private MovieHelper getMovieFromCursor() {
            MovieHelper currentMovie = new MovieHelper();
            currentMovie.setMovTitle(mCursor.getString(
                    mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_TITLE)));
            currentMovie.setId(mCursor.getInt(
                    mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_ID)));
            currentMovie.setOverview(mCursor.getString(
                    mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_DESCRIPTION)));
            currentMovie.setPosterPath(mCursor.getString(
                    mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_POSTER_PATH)));
            currentMovie.setRelease(mCursor.getString(
                    mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_RELEASE_DATE)));
            currentMovie.setRatings(mCursor.getString(
                    mCursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_VOTE_AVERAGE)));
            return currentMovie;
        }
    }
}
