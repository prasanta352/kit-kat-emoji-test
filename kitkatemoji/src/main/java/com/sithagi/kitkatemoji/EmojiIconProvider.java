package com.sithagi.kitkatemoji;

import com.sithagi.kitkatemoji.emoji.Emojicon;
import ohos.agp.components.*;
import ohos.app.Context;

public class EmojiIconProvider extends BaseItemProvider {
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
        return list == null ? 0 : list.length;
    }

    @Override
    public Object getItem(int position) {
        if (list != null && position >= 0 && position < list.length) {
            return list[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
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
            cpt = component;
        }
        return cpt;
    }

    public interface OnEmojiIconClickedListener {
        void onEmojiIconClicked(Emojicon emojicon);
    }
}
