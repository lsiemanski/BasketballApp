package android.basketballapp.utils;

import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;
import android.widget.TextView;

public class SpotLayout {
    public ImageView imageView;
    public TextView textView;

    public SpotLayout(ImageView imageView, TextView textView) {
        this.imageView = imageView;
        this.textView = textView;
    }

    public TransitionDrawable getDrawable() {
        return (TransitionDrawable) imageView.getDrawable();
    }
}
