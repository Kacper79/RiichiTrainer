package pl.uksw.riichitrainer.model;

public class QuizResult {
    private int score;
    private int total;
    private String mode;

    public QuizResult(int score, int total, String mode) {
        this.score = score;
        this.total = total;
        this.mode = mode;
    }

    public int getScore() { return score; }
    public int getTotal() { return total; }
    public String getMode() { return mode; }

    public int getPercent() {
        if (total == 0) return 0;
        return Math.round((score * 100f) / total);
    }

    public String getRank() {
        int percent = getPercent();
        if (percent >= 90) return "Tenpai Master";
        if (percent >= 75) return "Strong Intermediate";
        if (percent >= 55) return "Careful Beginner";
        return "Needs More Practice";
    }
}
