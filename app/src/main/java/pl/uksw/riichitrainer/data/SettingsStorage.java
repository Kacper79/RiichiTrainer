package pl.uksw.riichitrainer.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsStorage {
    private static final String FILE = "settings";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_SECONDS = "seconds";
    private static final String KEY_TIMER = "timer";
    private static final String KEY_SOUND = "sound";
    private static final String KEY_MUSIC = "music";

    private SharedPreferences preferences;

    public SettingsStorage(Context context) {
        preferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public String getNickname() {
        return preferences.getString(KEY_NICKNAME, "Player");
    }

    public int getDifficultyIndex() {
        return preferences.getInt(KEY_DIFFICULTY, 1);
    }

    public int getDifficultyLevel() {
        return getDifficultyIndex() + 1;
    }

    public int getQuestionSeconds() {
        return preferences.getInt(KEY_SECONDS, 20);
    }

    public boolean isTimerEnabled() {
        return preferences.getBoolean(KEY_TIMER, true);
    }

    public boolean isSoundEnabled() {
        return preferences.getBoolean(KEY_SOUND, true);
    }

    public boolean isMusicEnabled() {
        return preferences.getBoolean(KEY_MUSIC, false);
    }

    public void save(String nickname, int difficultyIndex, int seconds,
                     boolean timerEnabled, boolean soundEnabled, boolean musicEnabled) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NICKNAME, nickname);
        editor.putInt(KEY_DIFFICULTY, difficultyIndex);
        editor.putInt(KEY_SECONDS, seconds);
        editor.putBoolean(KEY_TIMER, timerEnabled);
        editor.putBoolean(KEY_SOUND, soundEnabled);
        editor.putBoolean(KEY_MUSIC, musicEnabled);
        editor.apply();
    }
}
