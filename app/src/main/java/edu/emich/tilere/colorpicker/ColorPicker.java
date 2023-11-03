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
    // Whether to draw the bitmap in m_bitmap
    private boolean toDrawBitmap = false;

    // Bitmap to be drawn
    private Bitmap m_bitmap;
    private double m_lastXPos = 0.0;
    private double m_lastYPos = 0.0;

    private final int m_crosshairWidth = 50;
    private final int m_crosshairLineThickness = 10;

    private final TextPaint m_xPosPaint = new TextPaint();
    private final TextPaint m_yPosPaint = new TextPaint();

    private final Paint m_crossHairPaint = new Paint();
    private final Paint m_topLinePaint = new Paint();
    private final Paint m_leftLinePaint = new Paint();
    private final Paint m_rightLinePaint = new Paint();
    private final Paint m_bottomLinePaint = new Paint();

    private final Rect m_bitmapContainer = new Rect();

    private final Canvas m_canvasBuffer = new Canvas();

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
            // DRAW BITMAP
            if (toDrawBitmap) {
                processBitmap(canvas);
            }

            // DRAW DEBUG TEXT
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

            // DRAW SQUARE CROSSHAIRS
            m_crossHairPaint.setColor(Color.RED);
            m_crossHairPaint.setStrokeWidth(10);
            m_crossHairPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect((int)m_lastXPos, (int)m_lastYPos, (int)m_lastXPos + m_crosshairWidth, (int)m_lastYPos + m_crosshairWidth, m_crossHairPaint);

            // DRAW TOP LINE
            m_topLinePaint.setColor(Color.RED);
            m_topLinePaint.setStrokeWidth(m_crosshairLineThickness);
            canvas.drawLine((int)(m_lastXPos + (m_crosshairWidth / 2.0)), 0, (int)(m_lastXPos + (m_crosshairWidth / 2.0)), (int)m_lastYPos, m_topLinePaint);

            // DRAW TOP LINE
            m_leftLinePaint.setColor(Color.RED);
            m_leftLinePaint.setStrokeWidth(m_crosshairLineThickness);
            canvas.drawLine(0, (int)(m_lastYPos + (m_crosshairWidth / 2.0)), (int)m_lastXPos, (int)(m_lastYPos + (m_crosshairWidth / 2.0)), m_leftLinePaint);


        }

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
        } else if (x > getWidth()) {
            x = getWidth();
        }

        if (y < 0) {
            y = 0;
        } else if (y > getHeight()) {
            y = getHeight();
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
