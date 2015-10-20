package in.srain.cube.demos.uctoast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ViewContainer extends FrameLayout {

    public KeyEventHandler mKeyEventHandler;

    public ViewContainer(Context context) {
        super(context);
    }

    public ViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyEventHandler(KeyEventHandler handler) {
        mKeyEventHandler = handler;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mKeyEventHandler != null) {
            mKeyEventHandler.onKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    public interface KeyEventHandler {
        void onKeyEvent(KeyEvent event);
    }
}
