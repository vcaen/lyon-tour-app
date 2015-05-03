package fr.vcaen.lyontour.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.models.PointInteret;
import fr.vcaen.lyontour.network.RestHelper;

/**
 * Created by vcaen on 28/04/2015.
 */
public class PointInteretView extends FrameLayout {

    private NetworkImageView image;
    private TextView title;
    private TextView description;

    public PointInteretView(Context context) {
        super(context);
        init();
    }

    public PointInteretView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointInteretView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.widget_timeline_card, null);
        addView(view);
        image = (NetworkImageView) view.findViewById(R.id.PointInteretImage);
        title = (TextView) view.findViewById(R.id.PointInteretTitle);
        description = (TextView) view.findViewById(R.id.PointInteretDesc);
    }


    public void setPointIntert(PointInteret pi) {
        setTitle(pi.getTitle());
        setDescription(pi.getDescription());
        image.setImageUrl(pi.getImageURL(), RestHelper.getInstance(getContext()).getImageLoader());
    }

    public void setImage(Bitmap image) {
        this.image.setImageBitmap(image);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }
}
