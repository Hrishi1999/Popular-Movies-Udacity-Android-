package gridentertainment.net.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {

    private List<ReviewsHelper> mReviewList;
    private ReviewAdapter.OnItemClickListener listener;
    private LayoutInflater mInflater;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(ReviewsHelper item);
    }

    public ReviewAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mReviewList = new ArrayList<>();
        this.listener = listener;

    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.reviewitem, parent, false);
        ReviewHolder viewHolder = new ReviewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewHolder holder, int position)
    {
        holder.bind(mReviewList.get(position), listener);
    }

    @Override
    public int getItemCount()
    {
        return (mReviewList == null) ? 0 : mReviewList.size();
    }

    public void setReviewsList(List<ReviewsHelper> reviewList)
    {
        this.mReviewList.clear();
        this.mReviewList.addAll(reviewList);
        notifyDataSetChanged();
    }

    public ArrayList getReviewsList(){
        return new ArrayList<ReviewsHelper>(mReviewList);
    }
}
