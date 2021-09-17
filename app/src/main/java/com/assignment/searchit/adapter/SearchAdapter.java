package com.assignment.searchit.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.searchit.R;
import com.assignment.searchit.activity.SearchActivity;
import com.assignment.searchit.model.SearchImageResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    ArrayList<SearchImageResponse.ImageDetails> data;
    Context context;
    OnSearchResultClicked onSearchResultClicked;

    public SearchAdapter(ArrayList<SearchImageResponse.ImageDetails> data, Context context, OnSearchResultClicked onSearchResultClicked) {
        this.data = data;
        this.context = context;
        this.onSearchResultClicked = onSearchResultClicked;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchImageResponse.ImageDetails imageDetails = data.get(position);
        holder.txtLikes.setText(imageDetails.getLikes());
        holder.txtDownloads.setText(imageDetails.getDownloads());
        holder.txtViews.setText(imageDetails.getViews());
        holder.txtUsername.setText(imageDetails.getUser());
        holder.txtBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchResultClicked.onOpenInBrowser(imageDetails.getLargeImageURL());
            }
        });

        try {
            Picasso.get().load(imageDetails.getLargeImageURL())
                    .error(R.drawable.ic_thumbnail)
                    .into(holder.imgImage);
        }catch (Exception e){
            holder.imgImage.setImageDrawable(ContextCompat.getDrawable(
                    context, R.drawable.ic_thumbnail
            ));
        }

        try {
            Picasso.get().load(imageDetails.getUserImageURL())
                    .error(R.drawable.ic_user)
                    .into(holder.imgUserImage);
        } catch (Exception e){
            holder.imgUserImage.setImageDrawable(ContextCompat.getDrawable(
                    context, R.drawable.ic_user
            ));
        }

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchResultClicked.onOpenInFullScreen(imageDetails.getLargeImageURL());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView txtLikes, txtViews, txtDownloads, txtUsername, txtBrowser;
        ImageView imgImage, imgUserImage;
        CardView cvItem;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            cvItem = itemView.findViewById(R.id.cvItem);
            txtLikes = itemView.findViewById(R.id.txtItemLikes);
            txtViews = itemView.findViewById(R.id.txtItemViews);
            txtDownloads = itemView.findViewById(R.id.txtItemDownloads);
            txtUsername = itemView.findViewById(R.id.txtItemUsername);
            txtBrowser = itemView.findViewById(R.id.txtItemBrowser);
            imgImage = itemView.findViewById(R.id.imgItemImage);
            imgUserImage = itemView.findViewById(R.id.imgItemUserImage);
        }
    }

    public interface OnSearchResultClicked{
        void onOpenInBrowser(String url);
        void onOpenInFullScreen(String url);
    }
}
