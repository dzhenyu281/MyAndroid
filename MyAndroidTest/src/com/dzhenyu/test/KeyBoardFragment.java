package com.dzhenyu.test;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by onlymem on 2015/9/11.
 */
public class KeyBoardFragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener {
    private RelativeLayout relativeLayout, relativeLayout2;
    private EditText editText, editText2;
    private InputMethodManager inputMethodManager;

    public KeyBoardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.key_board_layout, null);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_nickname);
        editText = (EditText) view.findViewById(R.id.et_nickname);
        relativeLayout2 = (RelativeLayout) view.findViewById(R.id.rl_nickname2);
        editText2 = (EditText) view.findViewById(R.id.et_nickname2);
        relativeLayout.setOnClickListener(this);
        editText.setOnFocusChangeListener(this);
        relativeLayout2.setOnClickListener(this);
        editText2.setOnFocusChangeListener(this);
        return view;
    }

//    private TextWatcher textWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//        }
//    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_nickname:
                editText.setFocusable(true);
                editText.requestFocusFromTouch();
                editText.requestFocus();
                editText.setSelection(editText.getText().toString().length());
                break;
            case R.id.rl_nickname2:
                editText2.setFocusable(true);
                editText2.requestFocusFromTouch();
                editText2.requestFocus();
                editText2.setSelection(editText2.getText().toString().length());
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        showKeyBoard(view, hasFocus);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (clickOutSideOfView(relativeLayout, event)) {
                editText.clearFocus();
            } else if (clickOutSideOfView(relativeLayout2, event)) {
                editText2.clearFocus();
            }
        }
        return true;
    }

    public void showKeyBoard(View view, boolean hasFocus) {
        if (hasFocus) {
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//      inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public boolean clickOutSideOfView(View view, MotionEvent event) {
        int left = view.getLeft();
        int top = view.getTop();
        if (event.getX() >= left && event.getX() < (left + view.getWidth()) && event.getY() > top && event.getY() <= (top + view.getHeight())) {
            return false;
        }
        return true;
    }
}
