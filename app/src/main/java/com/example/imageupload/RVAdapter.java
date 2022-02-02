package com.example.imageupload;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {

    ArrayList<ModelClass> objectModelClassList;
    public RVAdapter(ArrayList<ModelClass> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }



    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolderClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_raw, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {
        ModelClass objectModelClass=objectModelClassList.get(position);
        holder.imageNameTV.setText(objectModelClass.getImageName());

        holder.objectImageView.setImageBitmap(objectModelClass.getImage());

    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder{
        TextView imageNameTV;
        ImageView objectImageView;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            imageNameTV=itemView.findViewById(R.id.sr_imageDetailsTV);
            objectImageView=itemView.findViewById(R.id.sr_imageTV);

        }
    }
}
