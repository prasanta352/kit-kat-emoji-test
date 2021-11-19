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

    static int getIcon(int i) {
        switch (i) {
            case 1:
                return ResourceTable.Media_ic_emoji_people_light_normal;
            case 2:
                return ResourceTable.Media_ic_emoji_nature_light_normal;
            case 3:
                return ResourceTable.Media_ic_emoji_objects_light_normal;
            case 4:
                return ResourceTable.Media_ic_emoji_places_light_normal;
            case 5:
                return ResourceTable.Media_ic_emoji_symbols_light_normal;
            case 0:
            default:
                return ResourceTable.Media_ic_emoji_recent_light_normal;
        }
    }

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


//    @Override
//    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
//        HiLog.warn(LABEL_LOG, "build: ");
//        ListContainer listContainer = (ListContainer) scatter.parse(ResourceTable.Layout_emojicon_grid, null, false);
//        listContainer.setLayoutConfig(new ComponentContainer.LayoutConfig(
//                ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_PARENT
//        ));
//        ArrayList<Emojicon> emojicons = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            emojicons.add(new Emojicon(getIcon(i)));
//        }
//        int Height = EmojiconsFraction.Utils.getScreenHeight(getApplicationContext());
//        int Width = EmojiconsFraction.Utils.getScreenWidth(getApplicationContext());
//        int cols = (int) Width / 50;
//        HiLog.warn(LABEL_LOG, "Height: " + Height);
//        HiLog.warn(LABEL_LOG, "Width: " + Width);
//        HiLog.warn(LABEL_LOG, "cols: " + cols);
//
//        TableLayoutManager tableLayoutManager = new TableLayoutManager();
//        tableLayoutManager.setColumnCount(cols);
//        tableLayoutManager.setRowCount(emojicons.size()/cols);
//        EmojiIconProvider emojiIconProvider = new EmojiIconProvider(emojicons, getApplicationContext());
//        listContainer.setLayoutManager(tableLayoutManager);
//        listContainer.setItemProvider(emojiIconProvider);
//
//        container.addComponent(listContainer);
//        return listContainer;
//    }
}
