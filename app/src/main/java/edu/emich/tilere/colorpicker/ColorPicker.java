package edu.emich.tilere.colorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/***
 * Represents a color picker container.
 *
 * A color picker shows an ellipse overlayed on top of a background.
 * This ellipse moves position based on the user's last X/Y
 * coordinates of their finger.
 */
public class ColorPicker extends View {
    // Canvas used for drawing components to the screen
    private Canvas m_canvas;

    // Whether to draw the bitmap in m_bitmap
    private boolean toDrawBitmap = false;

    // Bitmap to be drawn
    private Bitmap m_bitmap;
    private double m_lastXPos = 0.0;
    private double m_lastYPos = 0.0;

    private final TextPaint m_xPosPaint = new TextPaint();
    private final TextPaint m_yPosPaint = new TextPaint();

    private final Rect m_bitmapContainer = new Rect();

    private PriorityQueue<PaintItem> Paints = new PriorityQueue<>();

    public ColorPicker(Context context) {
        super (context);

        init();
    }
    public ColorPicker(Context context, AttributeSet set) {
        super(context, set);

        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        setupPaint();

        this.setOnTouchListener(this::onTouch);
    }

    private void setupPaint() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Just in case, let's size check
        if (this.getWidth() != 0 || this.getHeight() != 0) {
            if (toDrawBitmap) {
                processBitmap(canvas);
            }

            m_xPosPaint.setAntiAlias(true);
            m_xPosPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
            m_xPosPaint.setColor(0xFF000000);

            m_yPosPaint.setAntiAlias(true);
            m_yPosPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
            m_yPosPaint.setColor(0xFF000000);

            String xPos = "X: " + m_lastXPos;
            String yPos = "Y: " + m_lastYPos;

            int xWidth = (int) m_xPosPaint.measureText(xPos);
            int yWidth = (int) m_yPosPaint.measureText(yPos);

            StaticLayout.Builder builder = StaticLayout.Builder.obtain(xPos, 0, xPos.length(), m_xPosPaint, xWidth);
            StaticLayout xBuilder = builder.build();
            xBuilder.draw(canvas);

            canvas.translate(0, xBuilder.getHeight());

            builder = StaticLayout.Builder.obtain(yPos, 0, yPos.length(), m_yPosPaint, yWidth);
            StaticLayout yBuilder = builder.build();
            yBuilder.draw(canvas);

        }

        this.m_canvas = canvas;
        super.onDraw(canvas);
    }

    /**
     * Handles touch input to the canvas
     * @param v
     * @param event
     * @return
     */
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (x < 0) {
            x = 0;
        }

        if (y < 0) {
            y = 0;
        }

        m_lastXPos = x;
        m_lastYPos = y;

        invalidate();
        return true;
    }

    /**
     * Processes the bitmap currently specified
     * @param canvas Canvas to draw bitmap onto
     */
    private void processBitmap(Canvas canvas) {
        double bitmapRatio = (double)m_bitmap.getWidth() / (double)m_bitmap.getHeight();
        int calculatedOutputHeight = 0;
        int calculatedOutputWidth = 0;

        // Resize to fill
        calculatedOutputWidth = this.getWidth();
        calculatedOutputHeight = (int)(calculatedOutputWidth / bitmapRatio);

        // Image is too tall, resize to max height instead
        if (calculatedOutputHeight > this.getHeight()) {
            calculatedOutputHeight = this.getHeight();
            calculatedOutputWidth = (int)(m_bitmap.getWidth() * bitmapRatio);
        }

        // Only recreate bitmap if necessary
        // This avoids an expensive allocation
        if (calculatedOutputHeight != m_bitmap.getHeight() || calculatedOutputWidth != m_bitmap.getWidth()) {
            m_bitmap = Bitmap.createScaledBitmap(m_bitmap, calculatedOutputWidth, calculatedOutputHeight, false);
        }

        m_bitmapContainer.set(0, 0, calculatedOutputWidth, calculatedOutputHeight);
        canvas.drawBitmap(m_bitmap, null, m_bitmapContainer, null);

    }

    public void setImage(Bitmap image) {
        toDrawBitmap = true;
        m_bitmap = image;

        invalidate();
    }
    
}
