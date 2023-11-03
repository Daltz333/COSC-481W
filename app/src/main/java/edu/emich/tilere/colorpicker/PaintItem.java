package edu.emich.tilere.colorpicker;

import android.graphics.Paint;

/**
 * Represents an abstracted object to draw to our canvas
 */
public class PaintItem {
    /**
     * Paint, this gets drawn to the canvas
     */
    public Paint m_paint;

    /**
     * Whether or not this should be permanent, aka reused
     */
    public boolean isPersistent;

    /**
     * Constructs a new PaintItem
     * @param paint Paint to draw
     * @param isPersistent Whether this is persistent
     */
    public PaintItem(Paint paint, boolean isPersistent) {
        m_paint = paint;
        this.isPersistent = isPersistent;
    }
}
