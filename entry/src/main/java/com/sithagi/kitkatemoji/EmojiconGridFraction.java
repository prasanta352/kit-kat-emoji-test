package com.sithagi.kitkatemoji;

import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import com.sithagi.kitkatemoji.emoji.Emojicon;

import java.util.Optional;

public class EmojiconGridFraction extends Fraction implements EmojiIconProvider.OnEmojiIconClickedListener {

    private final Emojicon[] mData;
    private EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener;
    Component component;
    Context context;

    protected static EmojiconGridFraction newInstance(Context context, Emojicon[] emojicons, EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener) {
        EmojiconGridFraction emojiGridFragment = new EmojiconGridFraction(context, emojicons);
        emojiGridFragment.setOnEmojiIconClickedListener(onEmojiIconClickedListener);
        return emojiGridFragment;
    }

    private int getScreenWidth() {
        DisplayManager displayManager = DisplayManager.getInstance();
        Optional<Display> optDisplay = displayManager.getDefaultDisplay(context);
        Point point = new Point(0, 0);
        if (optDisplay.isPresent()) {
            Display display = optDisplay.get();
            display.getSize(point);
        }
        return (int) point.position[0];
    }

    public EmojiconGridFraction(Context context, Emojicon[] mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        component = scatter.parse(ResourceTable.Layout_emojicon_grid, container, false);
        return component;
    }

    @Override
    public Component getComponent() {
        component = LayoutScatter.getInstance(context).parse(
                ResourceTable.Layout_emojicon_grid,
                null,
                false
        );

        ListContainer listContainer = (ListContainer) component.findComponentById(ResourceTable.Id_Emoji_GridView);
        TableLayoutManager tableLayoutManager = new TableLayoutManager();
        EmojiIconProvider emojiIconProvider = new EmojiIconProvider(mData, context, this);

        int screenWidth = getScreenWidth();
        int cols = screenWidth / AttrHelper.fp2px(34f, context);

        tableLayoutManager.setColumnCount(cols);
        tableLayoutManager.setRowCount(mData.length / cols);

        listContainer.setLayoutManager(tableLayoutManager);
        listContainer.setItemProvider(emojiIconProvider);

        return component;
    }


    public void setOnEmojiIconClickedListener(EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener) {
        this.onEmojiIconClickedListener = onEmojiIconClickedListener;
    }

    @Override
    public void onEmojiIconClicked(Emojicon emojicon) {
        if (onEmojiIconClickedListener != null) {
            onEmojiIconClickedListener.onEmojiIconClicked(emojicon);
        }
    }

    @Override
    protected void onComponentDetach() {
        onEmojiIconClickedListener = null;
        super.onComponentDetach();
    }
}
