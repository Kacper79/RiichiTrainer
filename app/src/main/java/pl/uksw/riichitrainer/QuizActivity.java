package pl.uksw.riichitrainer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.uksw.riichitrainer.data.QuestionBank;
import pl.uksw.riichitrainer.data.SettingsStorage;
import pl.uksw.riichitrainer.model.Question;
import pl.uksw.riichitrainer.timer.QuizTimerTask;
import pl.uksw.riichitrainer.ui.TileStripView;
import pl.uksw.riichitrainer.ui.TimerPieView;
import pl.uksw.riichitrainer.util.SoundHelper;

public class QuizActivity extends Activity implements QuizTimerTask.TimerListener {
    private static final String STATE_INDEX = "index";
    private static final String STATE_SCORE = "score";
    private static final String STATE_ANSWERED = "answered";
    private static final String STATE_SELECTED = "selected";
    private static final String STATE_MILLIS_LEFT = "millis_left";
    private static final String STATE_QUESTION_SEED = "question_seed";
    private static final int ANSWER_SHUFFLE_CHANCE_PERCENT = 70;
    private TextView progressText;
    private TextView modeText;
    private TextView situationText;
    private TextView doraText;
    private TextView riverText;
    private TextView feedbackText;
    private TimerPieView timerView;
    private TileStripView handView;
    private Button[] answerButtons;
    private Button nextButton;
    private ArrayList<Question> questions;
    private String mode;
    private int currentIndex = 0;
    private int score = 0;
    private boolean answered = false;
    private int selectedAnswer = -1;
    private int millisLeft;
    private int totalMillis;
    private long questionSeed;

    private boolean timerEnabled;
    private boolean soundEnabled;
    private boolean musicEnabled;
    private QuizTimerTask timerTask;
    private SoundHelper soundHelper;
    private SettingsStorage settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        settings = new SettingsStorage(this);
        soundHelper = new SoundHelper(this);
        timerEnabled = settings.isTimerEnabled();
        soundEnabled = settings.isSoundEnabled();
        musicEnabled = settings.isMusicEnabled();
        totalMillis = settings.getQuestionSeconds() * 1000;
        millisLeft = totalMillis;

        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = QuestionBank.MODE_MIXED;

        if (savedInstanceState != null) {
            questionSeed = savedInstanceState.getLong(STATE_QUESTION_SEED, System.currentTimeMillis());
        } else {
            questionSeed = System.currentTimeMillis();
        }

        questions = QuestionBank.getQuestions(mode, settings.getDifficultyLevel(), questionSeed);

        bindViews();

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(STATE_INDEX, 0);
            score = savedInstanceState.getInt(STATE_SCORE, 0);
            answered = savedInstanceState.getBoolean(STATE_ANSWERED, false);
            selectedAnswer = savedInstanceState.getInt(STATE_SELECTED, -1);
            millisLeft = savedInstanceState.getInt(STATE_MILLIS_LEFT, totalMillis);
        }

        showQuestion();
    }

    private void bindViews() {
        progressText = findViewById(R.id.progressText);
        modeText = findViewById(R.id.modeText);
        situationText = findViewById(R.id.situationText);
        doraText = findViewById(R.id.doraText);
        riverText = findViewById(R.id.riverText);
        feedbackText = findViewById(R.id.feedbackText);
        timerView = findViewById(R.id.timerView);
        handView = findViewById(R.id.handView);
        nextButton = findViewById(R.id.nextButton);

        answerButtons = new Button[4];
        answerButtons[0] = findViewById(R.id.answer0Button);
        answerButtons[1] = findViewById(R.id.answer1Button);
        answerButtons[2] = findViewById(R.id.answer2Button);
        answerButtons[3] = findViewById(R.id.answer3Button);

        for (int i = 0; i < answerButtons.length; i++) {
            final int index = i;
            answerButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAnswerSelected(index);
                }
            });
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextQuestion();
            }
        });
    }

    private int getColorValue(int colorId) {
        return getResources().getColor(colorId);
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            openResultScreen();
            return;
        }

        Question q = questions.get(currentIndex);
        q.shuffleAnswers(questionSeed, ANSWER_SHUFFLE_CHANCE_PERCENT);

        progressText.setText("Question " + (currentIndex + 1) + "/" + questions.size() + "   Score: " + score);
        modeText.setText(QuestionBank.getModeLabel(q.getMode())); // + " | " + q.getTitle() - hide that so the answer to the question is not that obvious - needed only for debug
        situationText.setText(q.getSituation());
        doraText.setText(q.getDora());
        riverText.setText(q.getRiver());
        handView.setHand(q.getHand());

        String[] answers = q.getAnswers();
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(answers[i]);
            answerButtons[i].setEnabled(!answered);

            answerButtons[i].setTextColor(getColorValue(R.color.answer_text_dark));
            answerButtons[i].setBackgroundColor(getColorValue(R.color.answer_normal));

            answerButtons[i].clearAnimation();
            answerButtons[i].setScaleX(1f);
            answerButtons[i].setScaleY(1f);
        }

        feedbackText.setVisibility(answered ? View.VISIBLE : View.GONE);
        nextButton.setVisibility(answered ? View.VISIBLE : View.GONE);

        updateTimerViewFromState();

        if (answered) {
            showAnsweredState(selectedAnswer);
        } else {
            feedbackText.setText("");
        }
    }

    private void onAnswerSelected(int index) {
        if (answered) return;
        selectedAnswer = index;
        answered = true;
        stopTimer();
        updateTimerViewFromState();

        Question q = questions.get(currentIndex);
        boolean correct = index == q.getCorrectIndex();
        if (correct) {
            score++;
            soundHelper.playCorrect(soundEnabled);
        } else {
            soundHelper.playWrong(soundEnabled);
            vibrateShortly();
        }

        showAnsweredState(index);
    }

    private void showAnsweredState(int selectedIndex) {
        Question q = questions.get(currentIndex);
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setEnabled(false);
        }

        int correctIndex = q.getCorrectIndex();
        markButton(correctIndex, R.color.answer_correct, R.color.answer_text_light);

        if (selectedIndex >= 0 && selectedIndex != correctIndex) {
            markButton(selectedIndex, R.color.answer_wrong, R.color.answer_text_light);
        }

        if (selectedIndex >= 0) {
            answerButtons[selectedIndex].animate()
                    .scaleX(1.07f)
                    .scaleY(1.07f)
                    .setDuration(130)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < answerButtons.length; i++) {
                                answerButtons[i].animate().scaleX(1f).scaleY(1f).setDuration(130).start();
                            }
                        }
                    })
                    .start();
        }

        String prefix = selectedIndex == correctIndex ? "Correct. " : "Not quite. ";
        feedbackText.setText(prefix + q.getExplanation() + "\n\nHint: " + q.getYakuHint());
        feedbackText.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        progressText.setText("Question " + (currentIndex + 1) + "/" + questions.size() + "   Score: " + score);
    }

    private void markButton(int index, int backgroundColorId, int textColorId) {
        answerButtons[index].setBackgroundColor(getColorValue(backgroundColorId));
        answerButtons[index].setTextColor(getColorValue(textColorId));
    }

    private void updateTimerViewFromState() {
        timerView.setTimerEnabled(timerEnabled);

        if (!timerEnabled) {
            timerView.setProgress(1f, 0);
            return;
        }

        float progress = totalMillis <= 0 ? 0f : millisLeft / (float) totalMillis;
        int secondsLeft = (int) Math.ceil(millisLeft / 1000.0);

        timerView.setProgress(progress, secondsLeft);
    }

    private void goToNextQuestion() {
        currentIndex++;
        answered = false;
        selectedAnswer = -1;
        millisLeft = totalMillis;

        if (currentIndex >= questions.size()) {
            openResultScreen();
        } else {
            showQuestion();
            startTimerIfNeeded();
        }
    }

    private void openResultScreen() {
        stopTimer();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questions.size());
        intent.putExtra("mode", mode);
        startActivity(intent);
        finish();
    }

    private void startTimerIfNeeded() {
        stopTimer();
        if (!timerEnabled || answered) return;
        timerTask = new QuizTimerTask(totalMillis, millisLeft, this);
        timerTask.execute();
    }

    private void stopTimer() {
        if (timerTask != null) {
            timerTask.stopTimer();
            timerTask = null;
        }
    }

    @Override
    public void onTimerTick(int millisLeft, float progress) {
        this.millisLeft = millisLeft;
        int secondsLeft = (int) Math.ceil(millisLeft / 1000.0);
        timerView.setProgress(progress, secondsLeft);
        if (secondsLeft <= 3 && secondsLeft > 0 && millisLeft % 1000 == 0) {
            soundHelper.playTick(soundEnabled);
        }
    }

    @Override
    public void onTimerFinished() {
        if (!answered) {
            millisLeft = 0;
            selectedAnswer = -1;
            answered = true;
            soundHelper.playWrong(soundEnabled);
            vibrateShortly();
            showAnsweredState(-1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundHelper.startBackgroundMusic(musicEnabled);
        startTimerIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        soundHelper.stopBackgroundMusic();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_INDEX, currentIndex);
        outState.putInt(STATE_SCORE, score);
        outState.putBoolean(STATE_ANSWERED, answered);
        outState.putInt(STATE_SELECTED, selectedAnswer);
        outState.putInt(STATE_MILLIS_LEFT, millisLeft);
        outState.putLong(STATE_QUESTION_SEED, questionSeed);
    }

    private void vibrateShortly() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(120);
    }
}
