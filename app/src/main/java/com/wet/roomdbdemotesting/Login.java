package com.wet.roomdbdemotesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText et_user_id,et_password;
    Button btn_login;


    AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_password = findViewById(R.id.et_password);
        et_user_id = findViewById(R.id.et_user_id);
        btn_login = findViewById(R.id.btn_login);

        appDatabase = AppDatabase.getInstance(this);

        btn_login.setOnClickListener(v -> {
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
            {
                login();
            }
        });

    }

    private void login() {

        AppExecutor.getInstance().getDiskIO().execute(()->{
           UserEntity userEntity = appDatabase.userDao().loginUser(et_user_id.getText().toString(),et_password.getText().toString());
           if (userEntity==null)
           {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(Login.this, "Invalid Login!!!", Toast.LENGTH_SHORT).show();
                   }
               });
           }
           else
           {
               String name = userEntity.name;
               startActivity(new Intent(Login.this, Welcome.class)
               .putExtra("name",name));
           }
        });
    }
}