package pl.uksw.riichitrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.uksw.riichitrainer.data.QuestionBank;
import pl.uksw.riichitrainer.data.SettingsStorage;
import pl.uksw.riichitrainer.data.StatsStorage;
import pl.uksw.riichitrainer.model.QuizResult;

public class ResultActivity extends Activity {
    private int score;
    private int total;
    private String mode;
    private QuizResult result;
    private SettingsStorage settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        settings = new SettingsStorage(this);
        score = getIntent().getIntExtra("score", 0);
        total = getIntent().getIntExtra("total", 0);
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = QuestionBank.MODE_MIXED;
        result = new QuizResult(score, total, mode);

        if (savedInstanceState == null) {
            new StatsStorage(this).saveResult(score, total);
        }

        TextView resultText = findViewById(R.id.resultText);
        Button shareButton = findViewById(R.id.shareButton);
        Button retryButton = findViewById(R.id.retryButton);
        Button mainButton = findViewById(R.id.mainButton);

        resultText.setText(buildResultText());

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareResult();
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                intent.putExtra("mode", mode);
                startActivity(intent);
                finish();
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private String buildResultText() {
        return settings.getNickname() + "\n"
                + QuestionBank.getModeLabel(mode) + "\n\n"
                + "Score: " + score + "/" + total + "\n"
                + "Percent: " + result.getPercent() + "%\n"
                + "Rank: " + result.getRank();
    }

    private void shareResult() {
        String text = "My Riichi Trainer result: " + score + "/" + total
                + " (" + result.getPercent() + "%). Rank: " + result.getRank() + ".";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sendIntent, "Share result"));
    }
}
