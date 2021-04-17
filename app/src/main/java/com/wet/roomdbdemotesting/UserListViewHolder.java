package com.wet.roomdbdemotesting;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserListViewHolder extends RecyclerView.ViewHolder {

    public TextView text_user_name,text_name,text_password;


    public UserListViewHolder(@NonNull View itemView) {
        super(itemView);

        text_user_name = itemView.findViewById(R.id.text_user_name);
        text_name = itemView.findViewById(R.id.text_name);
        text_password = itemView.findViewById(R.id.text_password);

    }
}
