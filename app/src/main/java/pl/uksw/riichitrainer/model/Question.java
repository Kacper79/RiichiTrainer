package pl.uksw.riichitrainer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Question {
    private String id;
    private String mode;
    private String title;
    private int difficultyLevel;
    private String situation;
    private String hand;
    private String river;
    private String dora;
    private String[] answers;
    private int correctIndex;
    private String explanation;
    private String yakuHint;

    public Question(String id, String mode, String title, int difficultyLevel,
                    String situation, String hand, String river, String dora,
                    String[] answers, int correctIndex, String explanation, String yakuHint) {
        this.id = id;
        this.mode = mode;
        this.title = title;
        this.difficultyLevel = difficultyLevel;
        this.situation = situation;
        this.hand = hand;
        this.river = river;
        this.dora = dora;
        this.answers = answers;
        this.correctIndex = correctIndex;
        this.explanation = explanation;
        this.yakuHint = yakuHint;
    }

    public void shuffleAnswers(long seed, float shuffleChance){
        if (answers == null || answers.length <= 1 || correctIndex != 0 || shuffleChance <= 0.001) {
            return;
        }
        if (shuffleChance > 1.0f) {
            shuffleChance = 1.0f;
        }

        Random random = new Random(seed + id.hashCode()); //id must be different so the mixing differs between questions not only between runs

        float roll = random.nextInt(100) / 100.0f;
        if (roll >= shuffleChance) {
            return; //do nothing, the dice decided :)
        }

        //begin shuffling
        String tmpCorrCopy = answers[correctIndex];
        List<String> newAns = Arrays.asList(answers);
        Collections.shuffle(newAns, random);

        for(int i = 0; i < newAns.size(); i++){
            answers[i] = newAns.get(i);
            if(answers[i] == tmpCorrCopy){
                correctIndex = i;
            }
        }
    }

    public String getId() { return id; }
    public String getMode() { return mode; }
    public String getTitle() { return title; }
    public int getDifficultyLevel() { return difficultyLevel; }
    public String getSituation() { return situation; }
    public String getHand() { return hand; }
    public String getRiver() { return river; }
    public String getDora() { return dora; }
    public String[] getAnswers() { return answers; }
    public int getCorrectIndex() { return correctIndex; }
    public String getExplanation() { return explanation; }
    public String getYakuHint() { return yakuHint; }
}
