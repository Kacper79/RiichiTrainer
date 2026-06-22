package pl.uksw.riichitrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pl.uksw.riichitrainer.data.SettingsStorage;

public class SettingsActivity extends Activity {
    private SettingsStorage storage;
    private EditText nicknameEdit;
    private EditText timeEdit;
    private Spinner difficultySpinner;
    private CheckBox timerCheck;
    private CheckBox soundCheck;
    private CheckBox musicCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storage = new SettingsStorage(this);
        nicknameEdit = findViewById(R.id.nicknameEdit);
        timeEdit = findViewById(R.id.timeEdit);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        timerCheck = findViewById(R.id.timerCheck);
        soundCheck = findViewById(R.id.soundCheck);
        musicCheck = findViewById(R.id.musicCheck);
        Button saveButton = findViewById(R.id.saveButton);

        loadSettings();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void loadSettings() {
        nicknameEdit.setText(storage.getNickname());
        timeEdit.setText(String.valueOf(storage.getQuestionSeconds()));
        difficultySpinner.setSelection(storage.getDifficultyIndex());
        timerCheck.setChecked(storage.isTimerEnabled());
        soundCheck.setChecked(storage.isSoundEnabled());
        musicCheck.setChecked(storage.isMusicEnabled());
    }

    private void saveSettings() {
        String nickname = nicknameEdit.getText().toString().trim();
        if (nickname.length() == 0) {
            nickname = "Player";
        }

        int seconds = 20;
        try {
            seconds = Integer.parseInt(timeEdit.getText().toString().trim());
        } catch (NumberFormatException e) {
            seconds = 20;
        }

        if (seconds < 5) seconds = 5;
        if (seconds > 90) seconds = 90;

        storage.save(
                nickname,
                difficultySpinner.getSelectedItemPosition(),
                seconds,
                timerCheck.isChecked(),
                soundCheck.isChecked(),
                musicCheck.isChecked()
        );

        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
