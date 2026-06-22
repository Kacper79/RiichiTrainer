package pl.uksw.riichitrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pl.uksw.riichitrainer.data.QuestionBank;

public class QuizModeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_mode);

        Button discardButton = findViewById(R.id.discardButton);
        Button yakuButton = findViewById(R.id.yakuButton);
        Button readingButton = findViewById(R.id.readingButton);
        Button mixedButton = findViewById(R.id.mixedButton);

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuiz(QuestionBank.MODE_DISCARD);
            }
        });

        yakuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuiz(QuestionBank.MODE_YAKU);
            }
        });

        readingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuiz(QuestionBank.MODE_READING);
            }
        });

        mixedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuiz(QuestionBank.MODE_MIXED);
            }
        });
    }

    private void openQuiz(String mode) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }
}
