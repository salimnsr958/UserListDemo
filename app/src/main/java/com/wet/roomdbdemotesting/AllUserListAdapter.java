package com.wet.roomdbdemotesting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllUserListAdapter extends RecyclerView.Adapter<AllUserListAdapter.AllUserListViewHolder> {



    private Context context;
    private LayoutInflater inflater;
    List<UserEntity> data = Collections.emptyList();

    AllUserListAdapter(Context context, List<UserEntity> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @NonNull
    @Override
    public AllUserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.layout_user_list, parent, false);
        return new AllUserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserListViewHolder holder, int position) {

        UserEntity userEntity = data.get(position);

        String uri = userEntity.getImage();


        holder.text_user_name.setText(userEntity.getUserId());
        holder.text_name.setText(userEntity.getName());
        holder.text_password.setText(userEntity.getPassword());
        Glide.with(context).load(uri).into(holder.image_user);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class AllUserListViewHolder extends RecyclerView.ViewHolder {

        public TextView text_user_name,text_name,text_password;

        public ImageView image_user;

        public AllUserListViewHolder(@NonNull View itemView) {
            super(itemView);

            text_user_name = itemView.findViewById(R.id.text_user_name);
            text_name = itemView.findViewById(R.id.text_name);
            text_password = itemView.findViewById(R.id.text_password);
            image_user = itemView.findViewById(R.id.image_user);

        }
    }
}
