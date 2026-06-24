package pl.uksw.riichitrainer.data;

import android.content.Context;
import android.content.SharedPreferences;

public class StatsStorage {
    private static final String FILE = "stats";
    private static final String KEY_GAMES = "games";
    private static final String KEY_CORRECT = "correct";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_BEST_PERCENT = "best_percent";

    private SharedPreferences preferences;

    public StatsStorage(Context context) {
        preferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public void saveResult(int correct, int total) {
        int oldGames = preferences.getInt(KEY_GAMES, 0);
        int oldCorrect = preferences.getInt(KEY_CORRECT, 0);
        int oldTotal = preferences.getInt(KEY_TOTAL, 0);
        int oldBest = preferences.getInt(KEY_BEST_PERCENT, 0);
        int percent = total == 0 ? 0 : Math.round(correct * 100f / total);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_GAMES, oldGames + 1);
        editor.putInt(KEY_CORRECT, oldCorrect + correct);
        editor.putInt(KEY_TOTAL, oldTotal + total);
        if (percent > oldBest) {
            editor.putInt(KEY_BEST_PERCENT, percent);
        }
        editor.apply();
    }

    public int getGames() { return preferences.getInt(KEY_GAMES, 0); }
    public int getCorrect() { return preferences.getInt(KEY_CORRECT, 0); }
    public int getTotal() { return preferences.getInt(KEY_TOTAL, 0); }
    public int getBestPercent() { return preferences.getInt(KEY_BEST_PERCENT, 0); }

    public int getAveragePercent() {
        int total = getTotal();
        if (total == 0) return 0;
        return Math.round(getCorrect() * 100f / total);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
