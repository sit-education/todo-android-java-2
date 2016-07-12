package com.bertharand.todoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.utils.AppSettings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppSettings.isAuthorized(this)) {
            setContentView(R.layout.activity_main);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, new MainFragment())
                    .commit();
        } else {
            Intent i = new Intent(this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            overridePendingTransition(0, 0);
        }
    }

}
