package com.sithagi.kitkatemoji;

import ohos.agp.components.AttrHelper;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Text;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class EmojiconTextView extends Text {
    private int mEmojiconSize;

    //#region constructor
    public EmojiconTextView(Context context) {
        super(context);
        init(null);
    }

    public EmojiconTextView(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(attrSet);
    }

    public EmojiconTextView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(attrSet);
    }
    //#endregion constructor

    private void init(AttrSet attrSet) {
        mEmojiconSize = getTextSize();
        if (attrSet != null) {
            attrSet.getAttr(EmojiconTextViewAttrsConstants.ATTR_EMOJI_ICON_SIZE).ifPresent(attr -> {
                mEmojiconSize = attr.getDimensionValue();
            });
        }
        setTextSize(mEmojiconSize);
    }

    /**
     * Set the size of emojicon in pixels.
     */
    public void setEmojiconSize(int pixels) {
        mEmojiconSize = pixels;
    }

    public static class EmojiconTextViewAttrsConstants {
        //if set to true, arrow will be shown on right side of the text
        public static final String ATTR_EMOJI_ICON_SIZE = "emojiconSize";
    }
}
