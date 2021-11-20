package com.sithagi.kitkatemoji;

import ohos.agp.components.*;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import com.sithagi.kitkatemoji.emoji.Emojicon;

public class EmojiconGridFraction extends ComponentContainer implements EmojiIconProvider.OnEmojiIconClickedListener {

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");
    private Emojicon[] mData;
    private EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener;
    Context context;

    //#region initializer
    public EmojiconGridFraction(Context context) {
        super(context);
        init(context);
    }

    public EmojiconGridFraction(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(context);
    }

    public EmojiconGridFraction(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(context);

    }
    //#endregion initializer

    protected static EmojiconGridFraction newInstance(Context context, Emojicon[] emojicons, EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener) {
        HiLog.warn(LABEL_LOG, "EmojiconGridFraction: newInstance");
        EmojiconGridFraction emojiGridFragment = new EmojiconGridFraction(context);
        emojiGridFragment.setOnEmojiIconClickedListener(onEmojiIconClickedListener);

        emojiGridFragment.loadData(emojicons);
        return emojiGridFragment;
    }

    private void init(Context context) {
        this.context = context;
        HiLog.warn(LABEL_LOG, "EmojiconGridFraction: init " + context);
        ListContainer listContainer = (ListContainer) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_emojicon_grid, null, false);

        addComponent(listContainer);
    }

    public void loadData(Emojicon[] mData) {

        HiLog.warn(LABEL_LOG, "build: ");
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_Emoji_GridView);

        int Height = EmojiconsFraction.Utils.getScreenHeight(context);
        int Width = EmojiconsFraction.Utils.getScreenWidth(context);
        int cols = Width / AttrHelper.fp2px(34f,context);
        HiLog.warn(LABEL_LOG, "Height: " + Height);
        HiLog.warn(LABEL_LOG, "Width: " + Width);
        HiLog.warn(LABEL_LOG, "cols: " + cols);

        TableLayoutManager tableLayoutManager = new TableLayoutManager();
        tableLayoutManager.setColumnCount(cols);
        tableLayoutManager.setRowCount(mData.length / cols);
        EmojiIconProvider emojiIconProvider = new EmojiIconProvider(mData, context, this);
        listContainer.setLayoutManager(tableLayoutManager);
        listContainer.setItemProvider(emojiIconProvider);

    }

    public void setOnEmojiIconClickedListener(EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener) {
        this.onEmojiIconClickedListener = onEmojiIconClickedListener;
    }

    @Override
    public void onEmojiIconClicked(Emojicon emojicon) {
        if (onEmojiIconClickedListener instanceof EmojiIconProvider.OnEmojiIconClickedListener) {
            onEmojiIconClickedListener.onEmojiIconClicked(emojicon);
        } else {
            HiLog.warn(LABEL_LOG, "EmojiIconProvider: EmojiconGridFraction.onEmojiIconClickedListener!! " + onEmojiIconClickedListener);

        }
    }


}
