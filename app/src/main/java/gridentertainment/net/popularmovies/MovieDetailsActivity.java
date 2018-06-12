package gridentertainment.net.popularmovies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String API = BuildConfig.API_KEY;
    ;
    private Window window;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private VideoAdapter mAdapter;
    private ReviewAdapter mAdapter2;
    private int id;
    private boolean isStarred = false;
    private MovieHelper movie;
    private CardView cardView1;
    private CardView cardView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movie = getIntent().getParcelableExtra("data");
        final String title = movie.getMovTitle();
        final String releaseDate = movie.getRelease();
        String ratings = movie.getRatings();
        String desc = movie.getOverview();
        String image = movie.getPosterPath();
        if(getIntent().getExtras().getInt("fromStarred")==1)
        {
            image = image.substring(30);
        }
        id = movie.getId();

        window = this.getWindow();
        setTitle(title);

        cardView1 = findViewById(R.id.cardView2);
        cardView2 = findViewById(R.id.cardView);

        final TextView titleTv = findViewById(R.id.title_tv);
        final TextView ratingsTv = findViewById(R.id.ratings_tv);
        final TextView releaseTv = findViewById(R.id.release_tv);
        final TextView overviewTv = findViewById(R.id.overview_tv);
        final ImageView img = findViewById(R.id.imageView2);
        final TextView videos_tv = findViewById(R.id.video_tv);
        final TextView reviews_tv = findViewById(R.id.reviews_tv);

        TextView descriptionTv = findViewById(R.id.overview_tv2);
        ScrollView scrollView = findViewById(R.id.scrollViewDetails);
        scrollView.smoothScrollTo(0,0);

        if(!isNetworkAvailable(this))
        {
            cardView1.setVisibility(View.GONE);
            cardView2.setVisibility(View.GONE);
        }

        mRecyclerView = findViewById(R.id.recylerViewDetails);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VideoAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView2 = findViewById(R.id.recyclerViewReviews);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        mAdapter2 = new ReviewAdapter(this);
        mRecyclerView2.setAdapter(mAdapter2);

        LoadVideosReviews();

        titleTv.setText(title);
        ratingsTv.setText(ratings);
        descriptionTv.setText(desc);
        releaseTv.setText(releaseDate);

        Picasso.get()
                .load(image)
                .placeholder(R.color.colorAccent)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        img.setImageBitmap(bitmap);
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @SuppressLint("ResourceType")
                                    @Override
                                    public void onGenerated(@NonNull Palette palette) {
                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                        if (textSwatch == null) {
                                            return;
                                        }
                                        ActionBar actionBar = getSupportActionBar();
                                        assert actionBar != null;
                                        int color = textSwatch.getRgb();
                                        actionBar.setBackgroundDrawable(new ColorDrawable(color));
                                        changeStatusBarColor(color);
                                        ratingsTv.setTextColor(color);
                                        releaseTv.setTextColor(color);
                                        overviewTv.setTextColor(color);
                                        videos_tv.setTextColor(color);
                                        reviews_tv.setTextColor(color);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        MenuItem mItem = menu.findItem(R.id.star);
        if (checkIfMovieIsInDb(movie)) {
            mItem.setIcon(R.drawable.ic_star_black_24dp);
            isStarred = !isStarred;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        isStarred = !isStarred;

        if (id == R.id.star) {

            if (isStarred) {
                starMovie(movie);
                item.setIcon(R.drawable.ic_star_black_24dp);
            }
            if (!isStarred) {
                unstarMovie(movie);
                item.setIcon(R.drawable.ic_star_border_black_24dp);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadVideosReviews() {
        List<TrailerHelper> videos = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            videos.add(new TrailerHelper(Parcel.obtain()));
        }
        mAdapter.setMovieList(videos);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIHelper service =
                retrofit.create(APIHelper.class);
        Call<TrailerHelper> call;
        call = service.getVideos(id, API);

        assert call != null;
        call.enqueue(new Callback<TrailerHelper>() {
            @Override
            public void onResponse(@NonNull Call<TrailerHelper> call, @NonNull Response<TrailerHelper> response) {
                assert response.body() != null;
                List<TrailerHelper> videos = response.body().getResults();
                if(!videos.isEmpty())
                {
                    mAdapter.setMovieList(videos);
                }
                else
                {
                    cardView1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerHelper> call, @NonNull Throwable t) {
                Log.e("", t.toString());
            }
        });

        Call<ReviewsHelper> call2;
        call2 = service.getReviews(id, API);

        assert call2 != null;
        call2.enqueue(new Callback<ReviewsHelper>() {
            @Override
            public void onResponse(@NonNull Call<ReviewsHelper> call2, @NonNull Response<ReviewsHelper> response) {
                assert response.body() != null;
                List<ReviewsHelper> reviews = response.body().getResults();
                if(!reviews.isEmpty())
                {
                    mAdapter2.setMovieList(reviews);
                }
                else
                {
                    cardView2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewsHelper> call2, @NonNull Throwable t) {
                Log.e("", t.toString());
            }
        });

    }

    private void starMovie(MovieHelper movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDBContract.Movie.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(MovieDBContract.Movie.COLUMN_MOVIE_TITLE, movie.getMovTitle());
        contentValues.put(MovieDBContract.Movie.COLUMN_MOVIE_DESCRIPTION, movie.getOverview());
        contentValues.put(MovieDBContract.Movie.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieDBContract.Movie.COLUMN_MOVIE_RELEASE_DATE, movie.getRelease());
        contentValues.put(MovieDBContract.Movie.COLUMN_MOVIE_VOTE_AVERAGE, movie.getRatings());
        getContentResolver().insert(MovieDBContract.Movie.CONTENT_URI, contentValues);
    }

    private void unstarMovie(MovieHelper movie) {
        String selection = MovieDBContract.Movie.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getId())};
        getContentResolver().delete(MovieDBContract.Movie.CONTENT_URI, selection, selectionArgs);
    }

    private void changeStatusBarColor(int color) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }

    private boolean checkIfMovieIsInDb(MovieHelper movie) {
        Cursor cursor = getContentResolver().query(
                MovieDBContract.Movie.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int movieId = cursor.getInt(
                        cursor.getColumnIndex(MovieDBContract.Movie.COLUMN_MOVIE_ID));
                if (movieId == movie.getId()) {
                    return true;
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}