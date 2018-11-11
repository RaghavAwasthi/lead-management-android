package com.community.jboss.leadmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.community.jboss.leadmanagement.main.MainActivity;

public class LaunchActivity extends AppCompatActivity {
    boolean wasRunBefore;
int REQUEST_CODE_INTRO =258;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_name), Context.MODE_PRIVATE);
        wasRunBefore = preferences.getBoolean(getString(R.string.was_run_before_key), false);

        //Show info slider on first run
        if(wasRunBefore) {
            final Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            final Intent intent = new Intent(this, SliderActivity.class);
            startActivityForResult(intent,REQUEST_CODE_INTRO);

        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INTRO) {
            if (resultCode == RESULT_OK) {
                SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_name), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean(getString(R.string.was_run_before_key), true);
                editor.apply();
                final Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                // Cancelled the intro finish this activity too.
                finish();
            }
        }
    }
}
