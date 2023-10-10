package edu.emich.tilere.colorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
        super.onDraw(canvas);
        m_bitmapContainer.left = 0;
        m_bitmapContainer.top = 0;
        m_bitmapContainer.right = this.getWidth();
        m_bitmapContainer.bottom = this.getHeight();

        if (toDrawBitmap) {
            m_canvas.drawBitmap(m_bitmap, m_bitmapContainer, (Rect) null, null);
        }
        this.m_canvas = canvas;
    }

    public void setImage(Bitmap image) {
        toDrawBitmap = true;
        m_bitmap = image;

        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
