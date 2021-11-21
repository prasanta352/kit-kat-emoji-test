package com.sithagi.kitkatemoji.emoji;

import java.io.Serializable;

/**
 * @author Chathura Wijesinghe (cdanasiri@gmail.com)
 */
public class Emojicon implements Serializable {
    private static final long serialVersionUID = 1L;
    private String emoji = "";


    private Emojicon() {
    }

    public Emojicon(String emoji) {
        this.emoji = emoji;
    }


    public static Emojicon fromCodePoint(int codePoint) {
        Emojicon emoji = new Emojicon();
        emoji.emoji = newString(codePoint);

        return emoji;
    }

    public static Emojicon fromChar(char ch) {
        Emojicon emoji = new Emojicon();
        emoji.emoji = Character.toString(ch);

        return emoji;
    }

    public static Emojicon fromChars(String chars) {
        Emojicon emoji = new Emojicon();
        emoji.emoji = chars;

        return emoji;
    }

    public static String newString(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf(codePoint);
        } else {
            return new String(Character.toChars(codePoint));
        }
    }

    public String getEmoji() {
        return emoji;
    }

}