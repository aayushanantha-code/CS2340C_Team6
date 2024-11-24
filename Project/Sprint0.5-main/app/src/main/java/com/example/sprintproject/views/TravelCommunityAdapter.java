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
        RecyclerView.Adapter<TravelCommunityAdapter.PostHolder> {

    private Context context;
    private List<TravelCommunityPost> postList;
    private PostClickListener clickListener;

    /**
     * Constructor for the TravelCommunityAdapterAdapter class
     * @param context The context of the activity
     * @param postList The list of travel posts to display
     * @param clickListener The listener wheneveer user clicks
     */

    public TravelCommunityAdapter(Context context, List<TravelCommunityPost> postList,
                                  PostClickListener clickListener) {
        this.context = context;
        this.postList = postList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.travel_list_item, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
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




    /**
     * Inner class to hold the views for each item in the list
     */
    public static class PostHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView destinationAndDuration;

        /**
         * Constructor for the postHolder class
         * @param itemView The view for the item
         */
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_user);
            destinationAndDuration = itemView.findViewById(R.id.post_destinationAndDuration);
        }
    }



    /**
     * Interface for the click listener
     */
    public interface PostClickListener {
        /**
         * Method to handle the click event
         * @param post The post that was clicked
         */
        void onPostClick(TravelCommunityPost post);
    }
}
