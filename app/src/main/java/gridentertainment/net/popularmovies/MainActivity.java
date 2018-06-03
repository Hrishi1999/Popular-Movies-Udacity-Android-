package gridentertainment.net.popularmovies;

import android.os.Parcel;
import android.support.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerViewMain);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        loadFeed(1);
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
                            break;
                        case R.id.item2:
                            loadFeed(2);
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
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIHelper service =
                retrofit.create(APIHelper.class);
        Call<MovieHelper> call = null;

        switch (itm)
        {
            case 1:
                call = service.getPopularMovies(API);
                setTitle("Popular Movies");
                break;
            case 2:
                call = service.getTopRatedMovies(API);
                setTitle("Top Rated Movies");
                break;
        }

        assert call != null;
        call.enqueue(new Callback<MovieHelper>() {
            @Override
            public void onResponse(@NonNull Call<MovieHelper> call, @NonNull Response<MovieHelper> response) {
                assert response.body() != null;
                List<MovieHelper> movies = response.body().getResults();
                mAdapter.setMovieList(movies);
            }

            @Override
            public void onFailure(@NonNull Call<MovieHelper> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}

