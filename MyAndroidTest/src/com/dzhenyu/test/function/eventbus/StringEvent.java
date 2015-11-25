package com.dzhenyu.test.function.eventbus;

/**
 * Created by onlymem on 2015/11/21.
 */
public class StringEvent {

    private String message;

    StringEvent(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
