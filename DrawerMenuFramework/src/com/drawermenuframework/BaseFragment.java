package com.drawermenuframework;


import android.support.v4.app.Fragment;
import android.view.MotionEvent;

/**
 * Created by onlymem on 2015/10/26.
 */
public class BaseFragment extends Fragment {


    protected boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
