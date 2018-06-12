package gridentertainment.net.popularmovies;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class ReviewHolder extends RecyclerView.ViewHolder {

    public TextView mContent;
    public TextView mAuthor;
    private boolean isOpened = false;


    public ReviewHolder(View itemView) {
        super(itemView);
        mContent = itemView.findViewById(R.id.textViewRv);
        mAuthor = itemView.findViewById(R.id.titleRv);
    }

    public void bind(final ReviewsHelper review, final ReviewAdapter.OnItemClickListener listener) {
        mContent.setText(review.getContent());
        mAuthor.setText(review.getAuthor());

        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        mAuthor.setTextColor(color);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                 if(isOpened)
                 {
                     mContent.setMaxLines(4);
                 }
                 if(!isOpened)
                 {
                     mContent.setMaxLines(Integer.MAX_VALUE);
                 }
                isOpened = !isOpened;
            }
        });
    }
}
