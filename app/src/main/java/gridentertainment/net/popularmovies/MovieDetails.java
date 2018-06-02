package gridentertainment.net.popularmovies;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MovieDetails extends AppCompatActivity {

    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String ratings = getIntent().getStringExtra("ratings");
        String desc = getIntent().getStringExtra("description");
        final String releaseDate = getIntent().getStringExtra("release");

        window = this.getWindow();
        setTitle(title);

        final TextView titleTv = findViewById(R.id.title_tv);
        final TextView ratingsTv = findViewById(R.id.ratings_tv);
        final TextView releaseTv = findViewById(R.id.release_tv);
        final TextView overviewTv = findViewById(R.id.overview_tv);
        final ImageView img = findViewById(R.id.imageView2);
        TextView descriptionTv = findViewById(R.id.overview_tv2);

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
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                        if (textSwatch == null) { return; }
                                        ActionBar actionBar = getSupportActionBar();
                                        assert actionBar != null;
                                        int color = textSwatch.getRgb();
                                        actionBar.setBackgroundDrawable(new ColorDrawable(color));
                                        changeStatusBarColor(color);
                                        ratingsTv.setTextColor(color);
                                        releaseTv.setTextColor(color);
                                        overviewTv.setTextColor(color);
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

    private void changeStatusBarColor(int color)
    {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }
}
