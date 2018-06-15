package gridentertainment.net.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder>
{

    private List<MovieHelper> mMovieList;
    private OnItemClickListener listener;
    private LayoutInflater mInflater;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(MovieHelper item);
    }

    public MoviesAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.movieitem, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position)
    {
        holder.bind(mMovieList.get(position), listener);
    }

    @Override
    public int getItemCount()
    {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(List<MovieHelper> movieList)
    {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public ArrayList getMovieList(){
        return new ArrayList<MovieHelper>(mMovieList);
    }
}