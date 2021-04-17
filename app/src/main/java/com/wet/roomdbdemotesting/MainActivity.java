package com.wet.roomdbdemotesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText et_user_id,et_password,et_name;


    Button btn_register,btn_login,btn_see_all_user;

    AppDatabase appDatabase;

    ImageView image_user;
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    String img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_user_id  = findViewById(R.id.et_user_id);
        et_password  = findViewById(R.id.et_password);
        et_name  = findViewById(R.id.et_name);
        btn_register  = findViewById(R.id.btn_register);
        btn_login  = findViewById(R.id.btn_login);
        btn_see_all_user  = findViewById(R.id.btn_see_all_user);
        image_user  = findViewById(R.id.image_user);



        appDatabase = AppDatabase.getInstance(this);



        image_user.setOnClickListener(v -> {

            checkPermission();
            takePhoto();

        });

        btn_register.setOnClickListener(v -> {
            if (et_user_id.getText().toString().isEmpty())
            {
                et_user_id.setError("Enter user ID");
                et_user_id.requestFocus();
            }
            else
            if (et_password.getText().toString().isEmpty())
            {
                et_password.setError("Enter Password");
                et_password.requestFocus();
            }
            else
            if (et_name.getText().toString().isEmpty())
            {
                et_name.setError("Enter Name");
                et_name.requestFocus();
            }
            else
            {
                registerUser();
            }
        });


        btn_login.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Login.class));
        });

        btn_see_all_user.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,AllUserList.class));
        });
    }



    private void registerUser() {

        AppExecutor.getInstance().getDiskIO().execute(()->{
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(et_user_id.getText().toString());
            userEntity.setName(et_name.getText().toString());
            userEntity.setPassword(et_password.getText().toString());
            userEntity.setImage(img1);

            appDatabase.userDao().registerUser(userEntity);


        });

        Toast.makeText(this, "User Registered Successfully!!!", Toast.LENGTH_SHORT).show();

    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)+
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {

                Snackbar.make(MainActivity.this.findViewById(android.R.id.content),
                        "Please Grant Permissions to upload profile photo",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        }).show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            // write your logic code if permission already granted
        }
    }

    private void takePhoto() {

        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Gallery") // folder selection title
                .single()
                .showCamera(true)
                .imageDirectory("Camera")
                .toolbarImageTitle("Tap to select") // image selection title
                .theme(R.style.ef_BaseTheme)
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .start(); // start image picker activity with request code

    }


    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images


            Image image = ImagePicker.getFirstImageOrNull(data);
            if (image!=null)
            {
                img1 = image.getPath();
                image_user.setVisibility(View.VISIBLE);
                Glide.with(this).load(image.getUri()).into(image_user);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }






}