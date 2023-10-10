package edu.emich.tilere.colorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    // X position of the color picker
    private double pickerX = 0.0;

    // Y position of the color picker
    private double pickerY = 0.0;

    // Whether or not to show the color picker
    private boolean isPickerVisible = false;

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
        canvas.drawPaint(m_drawPaint);
        this.m_canvas = canvas;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
