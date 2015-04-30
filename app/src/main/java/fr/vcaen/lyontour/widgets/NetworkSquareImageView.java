package fr.vcaen.lyontour.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by vcaen on 29/04/15.
 */
public class NetworkSquareImageView extends NetworkImageView {

    private BaseSide baseSide = BaseSide.WIDTH;

    public NetworkSquareImageView(Context context) {
        super(context);
        init();
    }

    public NetworkSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NetworkSquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public enum BaseSide {
        WIDTH, HEIGHT
    }

    private void init() {
        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = (baseSide == BaseSide.WIDTH) ? getMeasuredWidth()
                : getMeasuredHeight();
        setMeasuredDimension(size, size);
    }


    /**
     * Set which side of the image we should use for the length of the square's
     * sides
     *
     * @param bs
     *            {@link BaseSide} Side of the base image to use as square's
     *            sides' length
     */
    public void setBaseSide(BaseSide bs) {
        baseSide = bs;
    }
}
