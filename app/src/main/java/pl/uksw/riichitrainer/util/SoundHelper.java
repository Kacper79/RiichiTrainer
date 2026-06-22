package pl.uksw.riichitrainer.util;

import android.content.Context;
import android.media.MediaPlayer;

import pl.uksw.riichitrainer.R;

public class SoundHelper {
    private Context context;
    private MediaPlayer backgroundPlayer;

    public SoundHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public void playCorrect(boolean enabled) {
        playEffect(enabled, R.raw.correct);
    }

    public void playWrong(boolean enabled) {
        playEffect(enabled, R.raw.wrong);
    }

    public void playTick(boolean enabled) {
        playEffect(enabled, R.raw.tick);
    }

    private void playEffect(boolean enabled, int resId) {
        if (!enabled) return;
        final MediaPlayer player = MediaPlayer.create(context, resId);
        if (player == null) return;
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        player.start();
    }

    public void startBackgroundMusic(boolean enabled) {
        if (!enabled) return;
        if (backgroundPlayer != null) return;
        backgroundPlayer = MediaPlayer.create(context, R.raw.bgm);
        if (backgroundPlayer != null) {
            backgroundPlayer.setLooping(true);
            backgroundPlayer.start();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundPlayer != null) {
            if (backgroundPlayer.isPlaying()) {
                backgroundPlayer.stop();
            }
            backgroundPlayer.release();
            backgroundPlayer = null;
        }
    }
}
