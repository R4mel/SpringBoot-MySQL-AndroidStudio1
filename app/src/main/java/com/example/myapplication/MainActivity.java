package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.User;
import com.example.myapplication.retrofit.RetrofitService;
import com.example.myapplication.retrofit.UserAPI;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents() {
        TextInputEditText inputEditID = this.findViewById(R.id.form_textFieldID);
        TextInputEditText inputEditPassword = this.findViewById(R.id.form_textFieldPassword);
        TextInputEditText inputEditName = this.findViewById(R.id.form_textFieldName);
        TextInputEditText inputEditAge = this.findViewById(R.id.form_textFieldAge);

        MaterialButton buttonSave = this.findViewById(R.id.form_buttonSave);


        RetrofitService retrofitService = new RetrofitService();
        UserAPI userAPI = retrofitService.getRetrofit().create(UserAPI.class);

        buttonSave.setOnClickListener(view -> {
            String id = String.valueOf(inputEditID.getText());
            String password = String.valueOf(inputEditPassword.getText());
            String name = String.valueOf(inputEditName.getText());
            int age = Integer.parseInt(String.valueOf(inputEditAge.getText())); // int라서 다르게 받았음


            User user = new User();
            user.setId(id);
            user.setPassword(password);
            user.setName(name);
            user.setAge(age);

            userAPI.save(user)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) { // 저장이 되었다면
                            Toast.makeText(MainActivity.this, "Save Success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) { // 저장이 실패했다면
                            Toast.makeText(MainActivity.this, "Save failed", Toast.LENGTH_LONG).show();
                            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });


        });
    }
}