package gridentertainment.net.popularmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String API = BuildConfig.API_KEY;
    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private int lastMenu = 1;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerViewMain);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        if (savedInstanceState != null){
            int value = savedInstanceState.getInt("menu");
            switch (value)
            {
                case 1:
                    setTitle(R.string.pop_movies_title);
                    break;
                case 2:
                    setTitle(R.string.top_movies_title);
                    break;
                case 3:
                    setTitle(R.string.star_movies_title);
                    break;
            }
            mAdapter.setMovieList(savedInstanceState.<MovieHelper>getParcelableArrayList("movies"));

        }
        else
        {
            if(isNetworkAvailable(this))
            {
                loadFeed(1);
            }
            else
            {
                loadStarredMovies();
                Snackbar snackbar = Snackbar
                        .make(mRecyclerView, getString(R.string.internet_unavailable), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("menu", lastMenu);
        savedInstanceState.putParcelableArrayList("movies", mAdapter.getMovieList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        View menuItemView = findViewById(R.id.mybutton);
        if (id == R.id.mybutton) {
            PopupMenu popup=
                    new PopupMenu(this, menuItemView);
            popup.inflate(R.menu.context_men);
            popup.show();

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item2) {

                    switch (item2.getItemId())
                    {
                        case R.id.item1:
                            loadFeed(1);
                            lastMenu = 1;
                            break;
                        case R.id.item2:
                            loadFeed(2);
                            lastMenu = 2;
                            break;
                        case R.id.item3:
                            loadStarredMovies();
                            lastMenu = 3;
                            break;
                    }
                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFeed(int itm)
    {
        List<MovieHelper> movies = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            movies.add(new MovieHelper(Parcel.obtain()));
        }
        mAdapter.setMovieList(movies);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIHelper service =
                retrofit.create(APIHelper.class);
        Call<MovieHelper> call = null;

        switch (itm)
        {
            case 1:
                call = service.getPopularMovies(API);
                setTitle(R.string.pop_movies_title);
                break;
            case 2:
                call = service.getTopRatedMovies(API);
                setTitle(R.string.top_movies_title);
                break;
        }

        assert call != null;
        call.enqueue(new Callback<MovieHelper>() {
            @Override
            public void onResponse(@NonNull Call<MovieHelper> call, @NonNull Response<MovieHelper> response) {
                assert response.body() != null;
                List<MovieHelper> movies = response.body().getResults();
                mAdapter.setMovieList(movies);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieHelper> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void loadStarredMovies()
    {
        StarredAdapter starredAdapter = new StarredAdapter();
        getLoaderManager().initLoader(
                11, null, new StarredCursorLoader(this, starredAdapter));
        mRecyclerView.setAdapter(starredAdapter);
        setTitle(R.string.star_movies_title);
    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}

