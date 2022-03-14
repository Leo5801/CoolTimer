package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);

        seekBar.setMax(600);
        seekBar.setProgress(60);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minutes = i/60;
                int seconds = i - (i * 60);

                String minutesString = "";
                String secondsString = "";

                if (i < 10){
                    minutesString = "0" + i;
                }else{
                    minutesString = String.valueOf(i);
                }

                if (seconds < 10) {
                    secondsString = "0" + i;
                }else {
                    secondsString = String.valueOf(seconds);
                }
                textView.setText(minutesString + ":" + secondsString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}