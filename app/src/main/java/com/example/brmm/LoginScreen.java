package com.example.brmm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        final EditText ULID = (EditText)findViewById(R.id.ULID_Textbox);
        final EditText password = (EditText)findViewById(R.id.Password_Textbox);
        Button OK = (Button)findViewById(R.id.Login_OK_Button);
        DatabaseConnection connection = new DatabaseConnection();
        Thread thread = new Thread(connection);
        thread.start();

        OK.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String ULID_retrieved = ULID.getText().toString();
                        String password_retrieved = password.getText().toString();
                        //Authenticate(ULID_retrieved,password_retrieved);
                    }
                }
        );
    }



}