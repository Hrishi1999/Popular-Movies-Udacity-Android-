package gridentertainment.net.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

class MovieViewHolder extends RecyclerView.ViewHolder
{
    private static final String TAG = "MovieHolder" ;
    public ImageView imageView;
    public TextView textView;
    public LinearLayout linearLayout;

    @SuppressLint("ResourceAsColor")
    public MovieViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.title);
        linearLayout = itemView.findViewById(R.id.linearLayout);
    }

    @SuppressLint("ResourceAsColor")
    public void bind(final MovieHelper movie, final MoviesAdapter.OnItemClickListener listener) {
        textView.setText(movie.getMovTitle());
        if(movie.getPosterPath()!=null)
        {
            Picasso.get()
                    .load(movie.getPosterPath())
                    .placeholder(R.color.colorAccent)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            imageView.setImageBitmap(bitmap);
                            Palette.from(bitmap)
                                    .generate(new Palette.PaletteAsyncListener() {
                                        @Override
                                        public void onGenerated(Palette palette) {
                                            Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                            if (textSwatch == null) {
                                                return;
                                            }
                                            linearLayout.setBackgroundColor(textSwatch.getRgb());
                                            textView.setTextColor(textSwatch.getBodyTextColor());
                                        }
                                    });
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Log.i(TAG,"Error getting image");
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
        else
        {
            imageView.setImageDrawable(new ColorDrawable(R.color.colorPrimary));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetails.class);
                intent.putExtra("data",movie);
                v.getContext().startActivity(intent);
            }
        });
    }

}
