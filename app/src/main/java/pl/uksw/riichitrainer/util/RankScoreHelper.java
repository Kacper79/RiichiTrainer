package pl.uksw.riichitrainer.util;

public class RankScoreHelper {
    public static String getRank(int correct, int total) {
        if (total <= 0) return "No rank";

        int percent = correct * 100 / total;

        if (percent >= 90) return "Riichi Master";
        else if (percent >= 80) return "Expert";
        else if (percent >= 65) return "Advanced";
        else if (percent >= 50) return "Adept";
        else if (percent >= 40) return "Novice";
        return "Total Beginner";
    }
}
