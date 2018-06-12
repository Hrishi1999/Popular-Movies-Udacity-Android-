package gridentertainment.net.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import gridentertainment.net.popularmovies.R;

public class VideoHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;
    public LinearLayout linearLayout;
    private final Context context;

    public VideoHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageViewVid);
        textView = itemView.findViewById(R.id.vidName);
        context = itemView.getContext();
    }

    @SuppressLint("ResourceAsColor")
    public void bind(final TrailerHelper video, final VideoAdapter.OnItemClickListener listener) {
        textView.setText(video.getName());
        if(video.getYoutubeThumbnail()!=null)
        {
            Picasso.get()
                    .load(video.getYoutubeThumbnail())
                    .placeholder(R.color.colorAccent)
                    .into(imageView);
        }
        else
        {
            imageView.setImageDrawable(new ColorDrawable(R.color.colorPrimary));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video.getYoutube())));
            }
        });
    }

}
