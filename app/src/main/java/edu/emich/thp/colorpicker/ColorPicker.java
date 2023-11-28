package edu.emich.thp.colorpicker;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import edu.emich.thp.GlobalSettings.GlobalSettings;
import edu.emich.thp.utils.AndroidUtils;

/***
 * Represents a color picker container.
 *
 * A color picker shows an ellipse overlayed on top of a background.
 * This ellipse moves position based on the user's last X/Y
 * coordinates of their finger.
 */
public class ColorPicker extends View {
    // Whether to draw the bitmap in m_bitmap
    private static boolean toDrawBitmap = false;

    // Bitmap to be drawn
    private static Bitmap m_bitmap;
    private double m_lastXPos = 0.0;
    private double m_lastYPos = 0.0;

    private final int m_crosshairWidth = 50;
    private final int m_crosshairLineThickness = 10;
    private int m_selectedHexColor = 0;

    private final TextPaint m_xPosPaint = new TextPaint();
    private final TextPaint m_yPosPaint = new TextPaint();
    private final TextPaint m_hexPaint = new TextPaint();
    private final Paint m_crossHairPaint = new Paint();
    private final Paint m_topLinePaint = new Paint();
    private final Paint m_leftLinePaint = new Paint();
    private final Paint m_rightLinePaint = new Paint();
    private final Paint m_bottomLinePaint = new Paint();
    private final Paint m_previewPaint = new Paint();
    private final Rect m_bitmapContainer = new Rect();

    // Magic number because the y coordinate is offset from the top by
    private final int kMagicOffset = 125;

    // This is the bitmap that all objects are drawn upon
    private Bitmap m_globalBitmap;

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
        setDrawingCacheEnabled(true);

        this.setOnTouchListener(this::onTouch);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Just in case, let's size check
        if (this.getWidth() != 0 || this.getHeight() != 0) {
            // DRAW BITMAP
            if (toDrawBitmap) {
                processBitmap(canvas);
            }

            int textColor = 0xFF000000;

            if (AndroidUtils.isNightMode(getContext())) {
                textColor = 0xFFFFFFFF;
            }

            // DRAW DEBUG TEXT
            m_xPosPaint.setAntiAlias(true);
            m_xPosPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
            m_xPosPaint.setColor(textColor);

            m_yPosPaint.setAntiAlias(true);
            m_yPosPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
            m_yPosPaint.setColor(textColor);

            m_hexPaint.setAntiAlias(true);
            m_hexPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
            m_hexPaint.setColor(textColor);

            String xPos = "X: " + m_lastXPos;
            String yPos = "Y: " + m_lastYPos;
            String hexColor = "Hex: #" + Integer.toHexString(m_selectedHexColor);

            int xWidth = (int) m_xPosPaint.measureText(xPos);
            int yWidth = (int) m_yPosPaint.measureText(yPos);
            int hexWidth = (int) m_hexPaint.measureText(hexColor);

            StaticLayout.Builder builder = StaticLayout.Builder.obtain(xPos, 0, xPos.length(), m_xPosPaint, xWidth);
            StaticLayout xBuilder = builder.build();
            xBuilder.draw(canvas);

            canvas.translate(0, xBuilder.getHeight());

            builder = StaticLayout.Builder.obtain(yPos, 0, yPos.length(), m_yPosPaint, yWidth);
            StaticLayout yBuilder = builder.build();
            yBuilder.draw(canvas);

            canvas.translate(0, yBuilder.getHeight());

            builder = StaticLayout.Builder.obtain(hexColor, 0, hexColor.length(), m_hexPaint, hexWidth);
            StaticLayout hexBuilder = builder.build();
            hexBuilder.draw(canvas);

            // DRAW SQUARE CROSSHAIRS
            m_crossHairPaint.setColor(Color.RED);
            m_crossHairPaint.setStrokeWidth(10);
            m_crossHairPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect((int)m_lastXPos, (int)m_lastYPos, (int)m_lastXPos + m_crosshairWidth, (int)m_lastYPos + m_crosshairWidth, m_crossHairPaint);

            // DRAW TOP LINE
            m_topLinePaint.setColor(Color.RED);
            m_topLinePaint.setStrokeWidth(m_crosshairLineThickness);
            canvas.drawLine((int)(m_lastXPos + (m_crosshairWidth / 2.0)), 0, (int)(m_lastXPos + (m_crosshairWidth / 2.0)), (int)m_lastYPos, m_topLinePaint);

            // DRAW LEFT LINE
            m_leftLinePaint.setColor(Color.RED);
            m_leftLinePaint.setStrokeWidth(m_crosshairLineThickness);
            canvas.drawLine(0, (int)(m_lastYPos + (m_crosshairWidth / 2.0)), (int)m_lastXPos, (int)(m_lastYPos + (m_crosshairWidth / 2.0)), m_leftLinePaint);

            // DRAW BOTTOM LINE
            m_bottomLinePaint.setColor(Color.RED);
            m_bottomLinePaint.setStrokeWidth(m_crosshairLineThickness);
            canvas.drawLine((int)(m_lastXPos + (m_crosshairWidth / 2.0)), getHeight(), (int)(m_lastXPos + (m_crosshairWidth / 2.0)), (int)(m_lastYPos+m_crosshairWidth), m_topLinePaint);

            // DRAW RIGHT LINE
            m_rightLinePaint.setColor(Color.RED);
            m_rightLinePaint.setStrokeWidth(m_crosshairLineThickness);
            canvas.drawLine(getWidth(), (int)(m_lastYPos + (m_crosshairWidth / 2.0)), (int)(m_lastXPos+m_crosshairWidth), (int)(m_lastYPos + (m_crosshairWidth / 2.0)), m_leftLinePaint);

            int previewX = getWidth() - (getWidth()/4);

            try {
                GlobalSettings settings = GlobalSettings.getInstance();
                settings.setHexColor(m_selectedHexColor);
                m_previewPaint.setColor(m_selectedHexColor);
                canvas.drawRect(previewX, 0, previewX + 100, 100, m_previewPaint);
            } catch (IllegalArgumentException ignored) {}
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
        float x = getX() + event.getX();
        float y = getY() + event.getY();

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

        if (m_bitmap != null) {
            if (x < m_bitmap.getWidth() && y < m_bitmap.getHeight()) {
                m_selectedHexColor = m_bitmap.getPixel((int) x, (int) y);
            }
        }

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
            calculatedOutputWidth = (int)(calculatedOutputHeight * bitmapRatio);
        }

        // Only recreate bitmap if necessary
        // This avoids an expensive allocation
        if (calculatedOutputHeight != m_bitmap.getHeight() || calculatedOutputWidth != m_bitmap.getWidth()) {
            m_bitmap = Bitmap.createScaledBitmap(m_bitmap, calculatedOutputWidth, calculatedOutputHeight, false);
        }

        m_bitmapContainer.set(0, kMagicOffset, calculatedOutputWidth, calculatedOutputHeight+kMagicOffset);
        canvas.drawBitmap(m_bitmap, null, m_bitmapContainer, null);

    }

    public static void setImage(Bitmap image) {
        toDrawBitmap = true;
        m_bitmap = image;
    }
    
}
