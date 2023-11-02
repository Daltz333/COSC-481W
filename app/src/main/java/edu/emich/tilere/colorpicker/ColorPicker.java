package edu.emich.tilere.colorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

/***
 * Represents a color picker container.
 *
 * A color picker shows an ellipse overlayed on top of a background.
 * This ellipse moves position based on the user's last X/Y
 * coordinates of their finger.
 */
public class ColorPicker extends View {
    // Container used by canvas to draw elements to the screen
    private Paint m_drawPaint;

    // Canvas used for drawing components to the screen
    private Canvas m_canvas;

    // Whether to draw the bitmap in m_bitmap
    private boolean toDrawBitmap = false;

    // Bitmap to be drawn
    private Bitmap m_bitmap;

    private final Rect m_bitmapContainer = new Rect();

    public ColorPicker(Context context) {
        super (context);

        setupPaint();
    }
    public ColorPicker(Context context, AttributeSet set) {
        super(context, set);

        setupPaint();
    }

    private void setupPaint() {
        m_drawPaint = new Paint();
        m_drawPaint.setColor(Color.BLACK);
        m_drawPaint.setAntiAlias(true);
        m_drawPaint.setStrokeWidth(5);

        m_drawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        m_drawPaint.setStrokeJoin(Paint.Join.ROUND);
        m_drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Just in case, let's size check
        if (this.getWidth() != 0 || this.getHeight() != 0) {

            if (toDrawBitmap) {
                processBitmap(canvas);
            }
        }

        this.m_canvas = canvas;
        super.onDraw(canvas);
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
