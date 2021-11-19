package com.sithagi.kitkatemoji.emoji;

import java.io.Serializable;

/**
 * @author Chathura Wijesinghe (cdanasiri@gmail.com)
 */
public class Emojicon implements Serializable {
    private static final long serialVersionUID = 1L;
    private int icon;
    private char value;
    private String values="";
    private String emoji="";
    public int code=-1;

    private Emojicon() {
    }

    public Emojicon(String emoji) {
        this.emoji = emoji;
    }

    public static Emojicon fromResource(int icon, int value) {
        Emojicon emoji = new Emojicon();
        emoji.icon = icon;
        emoji.value = (char) value;
        return emoji;
    }

    public static Emojicon fromCodePoint(int codePoint) {
        Emojicon emoji = new Emojicon();
        emoji.emoji = newString(codePoint);
        emoji.code = codePoint;
        return emoji;
    }

    public static Emojicon fromChar(char ch) {
        Emojicon emoji = new Emojicon();
        emoji.emoji = Character.toString(ch);
        emoji.value = ch;
        return emoji;
    }

    public static Emojicon fromChars(String chars) {
        Emojicon emoji = new Emojicon();
        emoji.emoji = chars;
        emoji.values = chars;
        return emoji;
    }

    public static final String newString(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf(codePoint);
        } else {
            return new String(Character.toChars(codePoint));
        }
    }

    public char getValue() {
        return value;
    }

    public int getIcon() {
        return icon;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getCode(){
        if(this.code!=-1) return String.valueOf(this.code);
        if(!String.valueOf(this.value).isEmpty()) return ""+(Integer.valueOf(this.value));
        if(!String.valueOf(this.values).isEmpty()) return ""+Integer.valueOf(this.values);
        return "NAN";

    }
}