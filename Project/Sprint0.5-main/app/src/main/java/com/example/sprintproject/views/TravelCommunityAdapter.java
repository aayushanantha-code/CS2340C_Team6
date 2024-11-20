package com.example.sprintproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelCommunityPost;

import java.util.List;

public class TravelCommunityAdapter extends
        RecyclerView.Adapter<TravelCommunityAdapter.postHolder> {

    private Context context;
    private List<TravelCommunityPost> postList;
    private postClickListener clickListener;

    public TravelCommunityAdapter(Context context, List<TravelCommunityPost> postList,
                                  postClickListener clickListener) {
        this.context = context;
        this.postList = postList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public postHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.travel_list_item, parent, false);
        return new postHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postHolder holder, int position) {
        TravelCommunityPost post = postList.get(position);
        holder.title.setText(post.getUser());
        String destinationAndDuration = post.getDestination().getName()
                + " for " + post.getDestination().getDuration() + " days";
        holder.destinationAndDuration.setText(destinationAndDuration);

        // Set click listener on each item (acting as button)
        holder.itemView.setOnClickListener(v -> clickListener.onPostClick(post));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }




    public static class postHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView destinationAndDuration;

        public postHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_user);
            destinationAndDuration = itemView.findViewById(R.id.post_destinationAndDuration);
        }
    }



    // Interface for item click listener
    public interface postClickListener {
        void onPostClick(TravelCommunityPost post);
    }
}
