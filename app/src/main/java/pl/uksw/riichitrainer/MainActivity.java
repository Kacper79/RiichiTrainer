package pl.uksw.riichitrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.uksw.riichitrainer.data.SettingsStorage;

public class MainActivity extends Activity {
    private TextView welcomeText;
    private SettingsStorage settingsStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsStorage = new SettingsStorage(this);
        welcomeText = findViewById(R.id.welcomeText);

        Button startButton = findViewById(R.id.startButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        Button statsButton = findViewById(R.id.statsButton);
        Button resourcesButton = findViewById(R.id.resourcesButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuizModeActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
            }
        });

        resourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResourcesActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        welcomeText.setText("Welcome, " + settingsStorage.getNickname());
    }
}
