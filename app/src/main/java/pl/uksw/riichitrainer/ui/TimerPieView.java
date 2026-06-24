package pl.uksw.riichitrainer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TimerPieView extends View {
    private Paint paint;
    private Paint textPaint;
    private float progress = 1f;
    private int seconds = 0;
    private boolean enabled = true;

    public TimerPieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(28f);
    }

    public void setTimerEnabled(boolean enabled) {
        this.enabled = enabled;
        invalidate(); //use this function to rerender timer
    }

    public void setProgress(float progress, int seconds) {
        if (progress < 0f) progress = 0f;
        if (progress > 1f) progress = 1f;
        this.progress = progress;
        this.seconds = seconds;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        int pad = 6;

        float left = (width - size) / 2f + pad;
        float top = (height - size) / 2f + pad;
        float right = left + size - 2 * pad;
        float bottom = top + size - 2 * pad;
        RectF oval = new RectF(left, top, right, bottom);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(120, 144, 156));
        canvas.drawOval(oval, paint);

        if (enabled) {
            if (progress > 0.35f) {
                paint.setColor(Color.rgb(46, 125, 50));
            } else {
                paint.setColor(Color.rgb(198, 40, 40));
            }
            canvas.drawArc(oval, -90, 360f * progress, true, paint);

            canvas.drawText(String.valueOf(seconds), width / 2f,
                    height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f), textPaint);
        } else {
            canvas.drawText("∞", width / 2f,
                    height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f), textPaint);
        }
    }
}
