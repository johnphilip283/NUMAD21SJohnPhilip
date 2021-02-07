package edu.neu.madcourse.numad21s_johnphilip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
    }

    public void onClickPrevious(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
