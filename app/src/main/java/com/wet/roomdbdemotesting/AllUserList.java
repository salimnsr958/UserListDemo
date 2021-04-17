package com.wet.roomdbdemotesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllUserList extends AppCompatActivity {

    AppDatabase appDatabase;
    AllUserListAdapter allUserListAdapter;
    RecyclerView recycler_user_list;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_list);


        recycler_user_list = findViewById(R.id.recycler_user_list);
        appDatabase = AppDatabase.getInstance(this);
        loadAllUserList();
    }

    private void loadAllUserList() {

        AppExecutor.getInstance().getDiskIO().execute(()->{

            List<UserEntity> userEntityList = new ArrayList<>();
            List<UserEntity> userEntity = appDatabase.userDao().loadAllList();

            if (userEntity.size()<1)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AllUserList.this, "No User Found!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0 ; i < userEntity.size(); i++)
                        {
                            UserEntity userEntity1 = new UserEntity();

                            userEntity1.setName(userEntity.get(i).name);
                            userEntity1.setUserId(userEntity.get(i).userId);
                            userEntity1.setPassword(userEntity.get(i).password);

                            userEntityList.add(userEntity1);
                        }
                        LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recycler_user_list.setLayoutManager(layoutManager);
                        allUserListAdapter = new AllUserListAdapter(getApplicationContext(), userEntityList);
                        recycler_user_list.setAdapter(allUserListAdapter);
                    }
                });

            }


        });


    }

    public static class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private LayoutInflater inflater;
        List<UserEntity> data = Collections.emptyList();

        // create constructor to innitilize context and data sent from MainActivity
        CustomAdapter(Context context, List<UserEntity> data) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.data = data;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.layout_user_list, parent, false);
            UserListViewHolder holder = new UserListViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


            final UserListViewHolder myHolder = (UserListViewHolder) holder;
            final UserEntity current = data.get(position);

            myHolder.text_user_name.setText(current.getUserId());
            myHolder.text_name.setText(current.getName());
            myHolder.text_password.setText(current.getPassword());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}