package pl.uksw.riichitrainer.model;

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
