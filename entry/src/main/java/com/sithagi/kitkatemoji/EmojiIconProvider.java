package com.sithagi.kitkatemoji;

import ohos.agp.components.*;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import com.sithagi.kitkatemoji.emoji.Emojicon;

public class EmojiIconProvider extends BaseItemProvider {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");

    private Emojicon[] list;
    private Context slice;
    private OnEmojiIconClickedListener onEmojiIconClickedListener;

    public EmojiIconProvider(Emojicon[] list, Context ability, OnEmojiIconClickedListener onEmojiIconClickedListener) {
        this.list = list;
        this.slice = ability;
        this.onEmojiIconClickedListener = onEmojiIconClickedListener;
    }


    @Override
    public int getCount() {
        int i = list == null ? 0 : list.length;
        HiLog.warn(LABEL_LOG, "getCount: " + i);
        return i;
    }

    @Override
    public Object getItem(int position) {
        HiLog.warn(LABEL_LOG, "getItem: " + position);
        if (list != null && position >= 0 && position < list.length) {
            return list[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        HiLog.warn(LABEL_LOG, "getItemId: " + position);
        return position;
    }


    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
//        HiLog.warn(LABEL_LOG, "EmojiIconProvider: getComponent: " + i);
        final Component cpt;
        if (component == null) {
            DirectionalLayout directionalLayout = new DirectionalLayout(slice);

            Component newCpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_emojicon_item, null, false);
            EmojiconTextView image = (EmojiconTextView) newCpt.findComponentById(ResourceTable.Id_emoji_icon);
            Emojicon emojicon = list[i];

            image.setText(emojicon.getEmoji());

            directionalLayout.addComponent(newCpt);
            cpt = directionalLayout;
            directionalLayout.setClickedListener(c -> onEmojiIconClickedListener.onEmojiIconClicked(emojicon));

        } else {
            HiLog.warn(LABEL_LOG, "EmojiIconProvider: getComponent(reuse): " + i);
            cpt = component;
        }
        return cpt;
    }

    public interface OnEmojiIconClickedListener {
        void onEmojiIconClicked(Emojicon emojicon);
    }
}
