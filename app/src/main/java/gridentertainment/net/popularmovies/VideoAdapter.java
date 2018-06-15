package gridentertainment.net.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder>{

    private static final String TAG = "MovieAdapter";
    private List<TrailerHelper> mVideoList;
    private VideoAdapter.OnItemClickListener listener;
    private LayoutInflater mInflater;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(TrailerHelper item);
    }

    public VideoAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mVideoList = new ArrayList<>();
        this.listener = listener;

    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.videoitem, parent, false);
        VideoHolder viewHolder = new VideoHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position)
    {
        holder.bind(mVideoList.get(position), listener);
    }

    @Override
    public int getItemCount()
    {
        return (mVideoList == null) ? 0 : mVideoList.size();
    }

    public void setVideoList(List<TrailerHelper> videoList)
    {
        this.mVideoList.clear();
        this.mVideoList.addAll(videoList);
        notifyDataSetChanged();
    }

    public ArrayList getVideoList(){
        return new ArrayList<TrailerHelper>(mVideoList);
    }
}

