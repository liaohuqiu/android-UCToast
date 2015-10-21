package in.srain.cube.demos.uctoast;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.TextUtils;
import android.view.*;
import android.widget.TextView;
import in.srain.cube.demos.uctoast.clipboard.ClipboardManagerCompat;

public final class ListenClipboardService extends Service {

    private static final String KEY_FOR_WEAK_LOCK = "weak-lock";
    private static final String KEY_FOR_CMD = "cmd";
    private static final String KEY_FOR_CONTENT = "content";
    private static final String CMD_TEST = "test";

    private static CharSequence sLastContent = null;
    private ClipboardManagerCompat mClipboardWatcher;

    private ClipboardManagerCompat.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener = new ClipboardManagerCompat.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    public static void start(Context context) {
        Intent serviceIntent = new Intent(context, ListenClipboardService.class);
        context.startService(serviceIntent);
    }

    /**
     * for dev
     */
    public static void startForTest(Context context, String content) {

        Intent serviceIntent = new Intent(context, ListenClipboardService.class);
        serviceIntent.putExtra(KEY_FOR_CMD, CMD_TEST);
        serviceIntent.putExtra(KEY_FOR_CONTENT, content);
        context.startService(serviceIntent);
    }

    public static void startForWeakLock(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, ListenClipboardService.class);
        context.startService(serviceIntent);

        intent.putExtra(ListenClipboardService.KEY_FOR_WEAK_LOCK, true);
        Intent myIntent = new Intent(context, ListenClipboardService.class);

        // using wake lock to start service
        WakefulBroadcastReceiver.startWakefulService(context, myIntent);
    }

    @Override
    public void onCreate() {
        mClipboardWatcher = ClipboardManagerCompat.create(this);
        mClipboardWatcher.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mClipboardWatcher.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Utils.printIntent("onStartCommand", intent);

        if (intent != null) {
            // remove wake lock
            if (intent.getBooleanExtra(KEY_FOR_WEAK_LOCK, false)) {
                BootCompletedReceiver.completeWakefulIntent(intent);
            }
            String cmd = intent.getStringExtra(KEY_FOR_CMD);
            if (!TextUtils.isEmpty(cmd)) {
                if (cmd.equals(CMD_TEST)) {
                    String content = intent.getStringExtra(KEY_FOR_CONTENT);
                    showContent(content);
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        CharSequence content = mClipboardWatcher.getText();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        showContent(content);
    }

    private void showContent(CharSequence content) {
        if (sLastContent != null && sLastContent.equals(content) || content == null) {
            return;
        }
        sLastContent = content;

        TipViewController controller = new TipViewController(getApplication(), content);
        controller.show();
    }

    final static class TipViewController implements View.OnClickListener, View.OnTouchListener, ViewContainer.KeyEventHandler {

        private WindowManager mWindowManager;
        private Context mContext;
        private ViewContainer mWholeView;
        private View mContentView;

        private CharSequence mContent;

        TipViewController(Context application, CharSequence content) {
            mContext = application;
            mContent = content;
            mWindowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        }

        private void show() {

            ViewContainer view = (ViewContainer) View.inflate(mContext, R.layout.pop_view, null);

            // display content
            TextView textView = (TextView) view.findViewById(R.id.pop_view_text);
            textView.setText(mContent);

            mWholeView = view;
            mContentView = view.findViewById(R.id.pop_view_content_view);

            // event listeners
            mContentView.setOnClickListener(this);
            mWholeView.setOnTouchListener(this);
            mWholeView.setKeyEventHandler(this);

            int w = WindowManager.LayoutParams.MATCH_PARENT;
            int h = WindowManager.LayoutParams.MATCH_PARENT;

            int flags = 0;
            int type = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                type = WindowManager.LayoutParams.TYPE_PHONE;
            }

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
            layoutParams.gravity = Gravity.TOP;

            mWindowManager.addView(mWholeView, layoutParams);
        }

        @Override
        public void onClick(View v) {
            removePoppedViewAndClear();
            MainActivity.startForContent(mContext, mContent.toString());
        }

        private void removePoppedViewAndClear() {

            // remove view
            if (mWindowManager != null && mWholeView != null) {
                mWindowManager.removeView(mWholeView);
            }

            // clear content
            sLastContent = null;

            // remove listeners
            mContentView.setOnClickListener(null);
            mWholeView.setOnTouchListener(null);
            mWholeView.setKeyEventHandler(null);
        }

        /**
         * touch the outside of the content view, remove the popped view
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Rect rect = new Rect();
            mContentView.getGlobalVisibleRect(rect);
            if (!rect.contains(x, y)) {
                removePoppedViewAndClear();
            }
            return false;
        }

        @Override
        public void onKeyEvent(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                removePoppedViewAndClear();
            }
        }
    }
}