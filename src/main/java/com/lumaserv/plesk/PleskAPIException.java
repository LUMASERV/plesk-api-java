package com.lumaserv.plesk;

import lombok.Getter;

@Getter
public class PleskAPIException extends Exception {

    public PleskAPIException(int code, String text) {
        super("Error " + code + ": " + text);
        this.code = code;
        this.text = text;
    }

    final int code;
    final String text;

}
