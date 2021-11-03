package com.sithagi.kitkatemoji;

import com.sithagi.kitkatemoji.emoji.*;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import java.util.ArrayList;
import java.util.Arrays;
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

        List< EmojiconGridFraction> emojiGridFractions = new ArrayList<>();


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
//            List<Emojicon> emojicons = new ArrayList<>();
//            for (int j = 0; j < 50; j++) {
//                emojicons.add(Emojicon.fromCodePoint(0x1f604));
//            }
            try {
//                Emojicon[] emojicons1 = new Emojicon[emojicons.size()];
//                emojicons.toArray(emojicons1);
                emojiGridFractions.add(

                        EmojiconGridFraction.newInstance(getContext(), People.DATA)
                );
            }catch (Exception ex){

                HiLog.warn(LABEL_LOG, "Exception: " + ex);
            }
        }


        EmojiPagerAdapter adapter = new EmojiPagerAdapter(Arrays.asList(

                EmojiconGridFraction.newInstance(getContext(), new Emojicon[0]),
                EmojiconGridFraction.newInstance(getContext(), People.DATA),
                EmojiconGridFraction.newInstance(getContext(), Nature.DATA),
                EmojiconGridFraction.newInstance(getContext(), Objects.DATA),
                EmojiconGridFraction.newInstance(getContext(), Places.DATA),
                EmojiconGridFraction.newInstance(getContext(), Symbols.DATA)
        ));

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
        private final List< EmojiconGridFraction> fractions;

        public EmojiPagerAdapter(List< EmojiconGridFraction> fractions) {
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

                EmojiconGridFraction component = fractions.get(i);

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
