package com.bertharand.todoapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bertharand.todoapp.R;
import com.bertharand.todoapp.ui.fragment.DetailFragment;
import com.bertharand.todoapp.ui.fragment.MainFragment;
import com.bertharand.todoapp.utils.AppSettings;

public class MainActivity extends AppCompatActivity implements MainFragment.OnTaskClickListener {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppSettings.isAuthorized(this)) {
            setContentView(R.layout.activity_main);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, new MainFragment())
                    .commit();
        } else {
            startLoginActivity();
        }
    }

    private void startLoginActivity(){
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    @Override
    public final void onClick(long taskId, String taskTitle, String taskDescription) {
        DetailFragment detailFragment = DetailFragment.newInstance(taskId, taskTitle, taskDescription);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.slide_out_right)
                .add(R.id.main_content, detailFragment, DetailFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public final void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG);
        if (fragment instanceof OnBackPressedListener) {
            ((OnBackPressedListener)fragment).onBackPressed();
        }
        super.onBackPressed();
    }

    public interface OnBackPressedListener{
        void onBackPressed();
    }
}
