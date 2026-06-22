package pl.uksw.riichitrainer.timer;

import android.os.AsyncTask;

public class QuizTimerTask extends AsyncTask<Void, Integer, Boolean> {
    public interface TimerListener {
        void onTimerTick(int millisLeft, float progress);
        void onTimerFinished();
    }

    private int totalMillis;
    private int startMillis;
    private TimerListener listener;
    private boolean running = true;

    public QuizTimerTask(int totalMillis, int startMillis, TimerListener listener) {
        this.totalMillis = totalMillis;
        this.startMillis = startMillis;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int left = startMillis;
        while (left >= 0 && running && !isCancelled()) {
            publishProgress(left);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                return false;
            }
            left -= 250;
        }
        return running && !isCancelled();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int left = values[0];
        float progress = totalMillis <= 0 ? 0f : left / (float) totalMillis;
        if (listener != null) {
            listener.onTimerTick(left, progress);
        }
    }

    @Override
    protected void onPostExecute(Boolean finished) {
        if (finished != null && finished.booleanValue() && listener != null) {
            listener.onTimerFinished();
        }
    }

    public void stopTimer() {
        running = false;
        cancel(true);
        listener = null;
    }
}
