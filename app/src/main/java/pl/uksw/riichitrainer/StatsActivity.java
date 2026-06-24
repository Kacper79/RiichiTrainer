package pl.uksw.riichitrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.uksw.riichitrainer.data.SettingsStorage;
import pl.uksw.riichitrainer.data.StatsStorage;
import pl.uksw.riichitrainer.util.RankScoreHelper;

public class StatsActivity extends Activity {
    private TextView statsText;
    private StatsStorage statsStorage;
    private SettingsStorage settingsStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        statsStorage = new StatsStorage(this);
        settingsStorage = new SettingsStorage(this);
        statsText = findViewById(R.id.statsText);
        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsStorage.clear();
                updateStatsText();
                Toast.makeText(StatsActivity.this, "Stats cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStatsText();
    }

    private void updateStatsText() {
        String text = "Nickname: " + settingsStorage.getNickname() + "\n\n"
                + "Games played: " + statsStorage.getGames() + "\n"
                + "Correct answers: " + statsStorage.getCorrect() + "\n"
                + "Total questions: " + statsStorage.getTotal() + "\n"
                + "Average accuracy: " + statsStorage.getAveragePercent() + "%\n"
                + "Best quiz: " + statsStorage.getBestPercent() + "%\n\n"
                + "Current rank: " + RankScoreHelper.getRank(statsStorage.getCorrect(), statsStorage.getTotal());
        statsText.setText(text);
    }
}
