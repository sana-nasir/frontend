package org.weibeld.example.tabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.weibeld.example.R;

public class LoginActivity extends AppCompatActivity {


    public Button loginBtn;

    public void  initLogin() {
        loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loggedin = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(loggedin);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLogin();
    }

}
