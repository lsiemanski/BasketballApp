package android.basketballapp.utils;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

public class BlockedCursorEditText extends TextInputEditText {

    public BlockedCursorEditText(Context context) {
        super(context);
    }

    public BlockedCursorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockedCursorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        CharSequence text = getText();

        if (text != null) {
            if (selStart != text.length() || selEnd != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }

        super.onSelectionChanged(selStart, selEnd);
    }
}
