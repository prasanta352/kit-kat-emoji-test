package com.sithagi.kitkatemoji;

import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmojiconsFraction extends Fraction implements PageSlider.PageChangedListener {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");

    private DirectionalLayout[] mEmojiTabs;

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

    static Component build(Context context, int index) {
        HiLog.warn(LABEL_LOG, "build: ");
        ListContainer listContainer = (ListContainer) LayoutScatter.getInstance(context).parse(ResourceTable.Layout_emojicon_grid, null, false);
        listContainer.setLayoutConfig(new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_PARENT
        ));
        ArrayList<Emojicon> emojicons = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            emojicons.add(new Emojicon(getIcon(index)));
        }
        int Height = Utils.getScreenHeight(context);
        int Width = Utils.getScreenWidth(context);
        int cols = (int) Width / 50;
        HiLog.warn(LABEL_LOG, "Height: " + Height);
        HiLog.warn(LABEL_LOG, "Width: " + Width);
        HiLog.warn(LABEL_LOG, "cols: " + cols);

        TableLayoutManager tableLayoutManager = new TableLayoutManager();
        tableLayoutManager.setColumnCount(cols);
        tableLayoutManager.setRowCount(emojicons.size()/cols);
        EmojiIconProvider emojiIconProvider = new EmojiIconProvider(emojicons, context);
        listContainer.setLayoutManager(tableLayoutManager);
        listContainer.setItemProvider(emojiIconProvider);
        return listContainer;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        // set layout parameters
        DirectionalLayout directionalLayout = new DirectionalLayout(getApplicationContext());
        directionalLayout.setLayoutConfig(new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_CONTENT)
        );


        Component rootComponent = scatter.parse(ResourceTable.Layout_emojiicons, directionalLayout, false);
        PageSlider pageSlider = (PageSlider) rootComponent.findComponentById(ResourceTable.Id_emoji_pager);
        pageSlider.addPageChangedListener(this);

        List<Component> emojiGridFractions = new ArrayList<>();


        mEmojiTabs = new DirectionalLayout[6];

        mEmojiTabs[0] = (DirectionalLayout) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_00_recent);
        mEmojiTabs[1] = (DirectionalLayout) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_0_people);
        mEmojiTabs[2] = (DirectionalLayout) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_1_nature);
        mEmojiTabs[3] = (DirectionalLayout) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_2_objects);
        mEmojiTabs[4] = (DirectionalLayout) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_3_cars);
        mEmojiTabs[5] = (DirectionalLayout) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_4_punctuation);

        for (int i = 0; i < mEmojiTabs.length; i++) {
            final int position = i;
            mEmojiTabs[i].setClickedListener(c -> pageSlider.setCurrentPage(position));
            emojiGridFractions.add(build(getContext(), i));
        }


        EmojiPagerAdapter adapter = new EmojiPagerAdapter(emojiGridFractions);

        pageSlider.setProvider(adapter);

        directionalLayout.addComponent(rootComponent);
        return directionalLayout;
    }

    //#region Event handler
    @Override
    public void onPageSliding(int i, float v, int i1) {

    }

    @Override
    public void onPageSlideStateChanged(int i) {

    }

    @Override
    public void onPageChosen(int i) {

    }

    static class Utils {

        private Utils() {
        }

        public static int getScreenWidth(Context context) {
            DisplayManager displayManager = DisplayManager.getInstance();
            Optional<Display> optDisplay = displayManager.getDefaultDisplay(context);
            Point point = new Point(0, 0);
            if (optDisplay.isPresent()) {
                Display display = optDisplay.get();
                display.getSize(point);
            }
            return (int) point.position[0];
        }

        public static int getScreenHeight(Context context) {
            DisplayManager displayManager = DisplayManager.getInstance();
            Optional<Display> optDisplay = displayManager.getDefaultDisplay(context);
            Point point = new Point(0, 0);
            if (optDisplay.isPresent()) {
                Display display = optDisplay.get();
                display.getSize(point);
            }
            return (int) point.position[1];
        }


    }
    //#endregion Event handler

    private static class EmojiPagerAdapter extends PageSliderProvider {
        //        private final Context context;
        private final List<Component> fractions;

        public EmojiPagerAdapter(List<Component> fractions) {
            this.fractions = fractions;
        }

        @Override
        public int getCount() {
            HiLog.warn(LABEL_LOG, "getCount: ");
            return fractions.size();
        }

        @Override
        public Object createPageInContainer(ComponentContainer componentContainer, int i) {
            try {

                Component component = fractions.get(i);
                componentContainer.addComponent(component);
            } catch (Exception ex) {
                HiLog.warn(LABEL_LOG, "Exception: " + ex);
            }
            return componentContainer;
        }

        @Override
        public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
            HiLog.warn(LABEL_LOG, "destroyPageFromContainer: ");

        }

        @Override
        public boolean isPageMatchToObject(Component component, Object o) {
            HiLog.warn(LABEL_LOG, "isPageMatchToObject: ");
            return false;
        }
    }

}
