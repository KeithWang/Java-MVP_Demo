package vicwang.demo.util;

import android.view.View;

public abstract class ViewClick implements View.OnClickListener {
    private long mFastClickReturn = 0;

    private boolean fastClickCheck() {
        if (Math.abs(System.currentTimeMillis() - mFastClickReturn) < 500) {
            return true;
        }
        mFastClickReturn = System.currentTimeMillis();
        return false;
    }

    @Override
    public void onClick(View view) {
        if (fastClickCheck() || view == null)
            return;
        CustomOnClick(view);
    }

    protected abstract void CustomOnClick(View view);
}
