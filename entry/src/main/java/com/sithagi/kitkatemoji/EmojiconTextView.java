package com.sithagi.kitkatemoji;

import ohos.agp.components.AttrHelper;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Text;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class EmojiconTextView extends Text {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");
    private int mEmojiconSize;

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

    private void init(AttrSet attrSet) {
        HiLog.warn(LABEL_LOG, "attrSet == null ?: " + attrSet);
        if (attrSet == null) {
            mEmojiconSize = (int) getTextSize();
        } else {
            attrSet.getAttr(EmojiconTextViewAttrsConstants.ATTR_EMOJI_ICON_SIZE).ifPresent(attr -> {
                mEmojiconSize = attr.getDimensionValue();
                HiLog.warn(LABEL_LOG, "getDimensionValue:getIntegerValue " + attr.getIntegerValue());
                HiLog.warn(LABEL_LOG, "getDimensionValue:getStringValue " + attr.getStringValue());
                HiLog.warn(LABEL_LOG, "getDimensionValue:getName " + attr.getName());
                HiLog.warn(LABEL_LOG, "getDimensionValue:getFloatValue " + attr.getFloatValue());
                HiLog.warn(LABEL_LOG, "getDimensionValue:getLongValue " + attr.getLongValue());
                HiLog.warn(LABEL_LOG, "getDimensionValue:getDimensionValue " + attr.getDimensionValue());
            });
        }
        setTextSize(mEmojiconSize);
        setText(getText());
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
