package android.basketballapp.utils;

import android.graphics.drawable.Drawable;
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

    public void update(int made, int taken) {
        this.textView.setText(made + "/" + taken);
    }

    public void reverseTransition(int duration) {
        getTransitionDrawable().reverseTransition(duration);
    }

    public void startTransition(int duration) {
        getTransitionDrawable().startTransition(duration);
    }

    public void setColorDrawable(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    private TransitionDrawable getTransitionDrawable() {
        return (TransitionDrawable) imageView.getDrawable();
    }
}
