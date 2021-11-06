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
import ohos.multimodalinput.event.TouchEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmojiconsFraction extends Fraction implements PageSlider.PageChangedListener, EmojiIconProvider.OnEmojiIconClickedListener {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");
    private EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener;
    private DirectionalLayout[] mEmojiTabs;


    public void input(TextField textField, Emojicon emojicon) {
        if (emojicon == null || textField == null) return;
        textField.append(emojicon.getEmoji());
    }

    public void backspace(TextField textField) {
        // TODO: find a better solution for this
        if (textField == null) return;
        String txt = textField.getText();
        String finalString = txt.substring(0,txt.length()-1);
        textField.setText(finalString);
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

        }
        rootComponent.findComponentById(ResourceTable.Id_emojis_backspace).setTouchEventListener((component, touchEvent) -> {

            if(onEmojiIconBackspaceClickedListener !=null){
                 onEmojiIconBackspaceClickedListener.onEmojiIconBackspaceClicked(component);
            }else{
                HiLog.warn(LABEL_LOG, "EmojiIconFraction: onEmojiIconBackspaceClickedListener ==null " + onEmojiIconBackspaceClickedListener);
            }
            return true;
        });

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
    private  OnEmojiIconBackspaceClickedListener onEmojiIconBackspaceClickedListener;

    public void setOnEmojiIconBackspaceClickedListener(OnEmojiIconBackspaceClickedListener onEmojiIconBackspaceClickedListener) {
        this.onEmojiIconBackspaceClickedListener = onEmojiIconBackspaceClickedListener;
    }

    public interface OnEmojiIconBackspaceClickedListener {
        void onEmojiIconBackspaceClicked(Component c);
    }

}
