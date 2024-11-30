package com.example.pexels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<Photo> photoList;
    private Context context;

    // Constructor to initialize photo list and context
    public PhotoAdapter(List<Photo> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        // Get the photo at the current position
        Photo photo = photoList.get(position);

        // Use Glide to load the image into the ImageView
        Glide.with(context).load(photo.getImageUrl()).into(holder.imageView);

        // Set the photo title
        holder.title.setText(photo.getTitle());
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the photo list
        return photoList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;

        // ViewHolder constructor
        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
