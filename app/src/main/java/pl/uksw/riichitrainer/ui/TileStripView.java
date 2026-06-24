package pl.uksw.riichitrainer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TileStripView extends View {
    private String hand = "";
    private Paint tilePaint;
    private Paint borderPaint;
    private Paint textPaint;

    public TileStripView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        tilePaint = new Paint();
        tilePaint.setAntiAlias(true);
        tilePaint.setStyle(Paint.Style.FILL);
        tilePaint.setColor(Color.WHITE);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2f);
        borderPaint.setColor(Color.rgb(69, 90, 100));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(24f);
    }

    public void setHand(String hand) {
        this.hand = hand == null ? "" : hand;
        invalidate();
    }

    private String getDisplayTile(String tile){
        if(tile.endsWith("*")) return tile.substring(0, tile.length() - 1);
        return tile;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String[] tiles = hand.trim().length() == 0 ? new String[0] : hand.trim().split("\\s+");
        if (tiles.length == 0) return;

        float viewWidth = getWidth();
        float viewHeight = getHeight();
        float gap = 5f;
        float tileWidth = Math.min(48f, (viewWidth - gap * (tiles.length - 1)) / tiles.length);
        float tileHeight = Math.min(viewHeight - 8f, tileWidth * 1.35f);
        float startX = Math.max(4f, (viewWidth - (tileWidth * tiles.length + gap * (tiles.length - 1))) / 2f);
        float top = (viewHeight - tileHeight) / 2f;

        for (int i = 0; i < tiles.length; i++) {
            float left = startX + i * (tileWidth + gap);
            RectF rect = new RectF(left, top, left + tileWidth, top + tileHeight);
            canvas.drawRoundRect(rect, 6f, 6f, tilePaint);
            canvas.drawRoundRect(rect, 6f, 6f, borderPaint);

            String label = tiles[i];
            textPaint.setColor(getSuitColor(label));
            textPaint.setTextSize(tileWidth < 34f ? 16f : 22f);
            canvas.drawText(getDisplayTile(label), rect.centerX(), rect.centerY() - ((textPaint.descent() + textPaint.ascent()) / 2f), textPaint);
        }
    }

    private int getSuitColor(String tile) {
        if(tile.endsWith("*")) return Color.rgb(198, 40, 40);

        else if (tile.endsWith("m")) return Color.rgb(183, 28, 28);   // manzu
        else if (tile.endsWith("p")) return Color.rgb(21, 101, 192);  // pinzu
        else if (tile.endsWith("s")) return Color.rgb(46, 125, 50);   // souzu

        else if (tile.equals("R")) return Color.rgb(198, 40, 40);     // red dragon
        else if (tile.equals("G")) return Color.rgb(46, 125, 50);     // green dragon
        else if (tile.equals("Wh")) return Color.rgb(69, 90, 100);    // white dragon

        return Color.rgb(38, 50, 56);                            // winds
    }
}
