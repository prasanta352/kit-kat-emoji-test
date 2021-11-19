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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmojiconsFraction extends Fraction implements PageSlider.PageChangedListener,
        EmojiIconProvider.OnEmojiIconClickedListener {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");
    private EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener;
    private Button[] mEmojiTabs;


    public void input(TextField textField, Emojicon emojicon) {
        if (emojicon == null || textField == null) return;
        textField.append(emojicon.getEmoji());
    }

    public void backspace(TextField textField) {
        // TODO: find a better solution for this
        if (textField == null) return;
        String txt = textField.getText();
        String finalString = txt.substring(0, txt.length() - 1);
        textField.setText(finalString);
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        // set layout parameters


        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached 0");
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached this " + this);
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached getContext " + getContext());
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached getApplicationContext " + getApplicationContext());
        DirectionalLayout directionalLayout = new DirectionalLayout(this);
        directionalLayout.setLayoutConfig(new ComponentContainer.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_CONTENT)
        );
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached getApplicationContext() " + getContext());
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached scatter " + scatter);
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached directionalLayout " + directionalLayout);

        Component rootComponent = scatter.parse(ResourceTable.Layout_emojiicons, directionalLayout, false);
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached 2");
        PageSlider pageSlider = (PageSlider) rootComponent.findComponentById(ResourceTable.Id_emoji_pager);
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached 3");
        pageSlider.addPageChangedListener(this);
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached 4 " + rootComponent);
        mEmojiTabs = new Button[6];
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached 5 " + mEmojiTabs);

        mEmojiTabs[0] = (Button) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_00_recent);
        mEmojiTabs[1] = (Button) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_0_people);
        mEmojiTabs[2] = (Button) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_1_nature);
        mEmojiTabs[3] = (Button) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_2_objects);
        mEmojiTabs[4] = (Button) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_3_cars);
        mEmojiTabs[5] = (Button) rootComponent.findComponentById(ResourceTable.Id_emojis_tab_4_punctuation);

        for (int i = 0; i < mEmojiTabs.length; i++) {
            final int position = i;
            mEmojiTabs[i].setClickedListener(c -> pageSlider.setCurrentPage(position));

        }
        rootComponent.findComponentById(ResourceTable.Id_emojis_backspace).setTouchEventListener((component, touchEvent) -> {
            if (onEmojiIconBackspaceClickedListener != null) {
                onEmojiIconBackspaceClickedListener.onEmojiIconBackspaceClicked(component);
            } else {
                HiLog.warn(LABEL_LOG, "EmojiIconFraction: onEmojiIconBackspaceClickedListener ==null " + onEmojiIconBackspaceClickedListener);
            }
            return true;
        });

        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached: this " + getApplicationContext());
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onComponentAttached: getContext() " + getApplicationContext());

        EmojiPagerAdapter adapter = new EmojiPagerAdapter(Arrays.asList(
                EmojiconGridFraction.newInstance(getContext(), new Emojicon[0], this),
                EmojiconGridFraction.newInstance(getContext(), People.DATA, this),
                EmojiconGridFraction.newInstance(getContext(), Nature.DATA, this),
                EmojiconGridFraction.newInstance(getContext(), Objects.DATA, this),
                EmojiconGridFraction.newInstance(getContext(), Places.DATA, this),
                EmojiconGridFraction.newInstance(getContext(), Symbols.DATA, this)
        ));

        pageSlider.setProvider(adapter);

        directionalLayout.addComponent(rootComponent);
        return directionalLayout;
//        try {
//        } catch (Exception ex) {
//            HiLog.warn(LABEL_LOG, "MainAbility: onStart  " + ex);
//            for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
//                HiLog.warn(LABEL_LOG, "" + stackTraceElement);
//            }
//        }
//        return null;
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
        HiLog.warn(LABEL_LOG, "EmojiconsFraction: onPageChosen "+i);
        for (Button btn: mEmojiTabs){
            btn.setSelected(false);
        }
        mEmojiTabs[i].setSelected(true);
    }

    public void setOnEmojiIconClickedListener(EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener) {
        this.onEmojiIconClickedListener = onEmojiIconClickedListener;
    }

    @Override
    public void onEmojiIconClicked(Emojicon emojicon) {
        if (onEmojiIconClickedListener instanceof EmojiIconProvider.OnEmojiIconClickedListener) {
            onEmojiIconClickedListener.onEmojiIconClicked(emojicon);
        } else {
            HiLog.warn(LABEL_LOG, "EmojiIconProvider: EmojiconsFraction.onEmojiIconClickedListener!! " + onEmojiIconClickedListener);
        }
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
        private final List<EmojiconGridFraction> fractions;

        public EmojiPagerAdapter(List<EmojiconGridFraction> fractions) {
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
                componentContainer.removeAllComponents();
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

    private OnEmojiIconBackspaceClickedListener onEmojiIconBackspaceClickedListener;

    public void setOnEmojiIconBackspaceClickedListener(OnEmojiIconBackspaceClickedListener onEmojiIconBackspaceClickedListener) {
        this.onEmojiIconBackspaceClickedListener = onEmojiIconBackspaceClickedListener;
    }

    public interface OnEmojiIconBackspaceClickedListener {
        void onEmojiIconBackspaceClicked(Component c);
    }

}
