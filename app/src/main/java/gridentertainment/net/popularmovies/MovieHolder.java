package gridentertainment.net.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class MovieViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView;
    public TextView textView;
    public LinearLayout linearLayout;

    public MovieViewHolder(View itemView)
    {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.title);
        linearLayout = itemView.findViewById(R.id.linearLayout);
    }
}
