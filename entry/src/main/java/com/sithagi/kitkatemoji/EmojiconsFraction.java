package com.sithagi.kitkatemoji;

import com.sithagi.kitkatemoji.emoji.*;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.miscservices.timeutility.Time;
import ohos.multimodalinput.event.TouchEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;

public class EmojiconsFraction extends Fraction implements PageSlider.PageChangedListener,
        EmojiIconProvider.OnEmojiIconClickedListener {
    private final Context context;
    private Button[] mEmojiTabs;
    private int mEmojiTabLastSelectedIndex = -1;
    private EmojiIconProvider.OnEmojiIconClickedListener onEmojiIconClickedListener;
    private OnEmojiIconBackspaceClickedListener onEmojiIconBackspaceClickedListener;

    public EmojiconsFraction(Context context) {
        this.context = context;
    }

    public void input(TextField textField, Emojicon emojicon) {
        if (emojicon == null || textField == null) return;
        // TODO: find a better solution for this
        // because there is no api for putting character based on cursor position
        // so for now we are appending new emoji to the end of the text
        textField.append(emojicon.getEmoji());
    }

    public void backspace(TextField textField) {
        // TODO: find a better solution for this maybe there is a api for it ??
        // because there is no api for removing character based on cursor position
        // so for now we are removing character from end of the text
        if (textField == null) return;
        String txt = textField.getText();
        if (txt.isEmpty()) return;
        String finalString = txt.substring(0, txt.length() - 1);
        textField.setText(finalString);
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        Component rootComponent = scatter.parse(ResourceTable.Layout_emojiicons, container, false);

        PageSlider pageSlider = (PageSlider) rootComponent.findComponentById(ResourceTable.Id_emoji_pager);
        pageSlider.addPageChangedListener(this);
        EmojiPagerAdapter adapter = new EmojiPagerAdapter(Arrays.asList(
                EmojiconGridFraction.newInstance(context, new Emojicon[0], this),
                EmojiconGridFraction.newInstance(context, People.DATA, this),
                EmojiconGridFraction.newInstance(context, Nature.DATA, this),
                EmojiconGridFraction.newInstance(context, Objects.DATA, this),
                EmojiconGridFraction.newInstance(context, Places.DATA, this),
                EmojiconGridFraction.newInstance(context, Symbols.DATA, this)
        ));
        pageSlider.setProvider(adapter);


        mEmojiTabs = new Button[6];

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

        rootComponent.findComponentById(ResourceTable.Id_emojis_backspace).setTouchEventListener(new RepeatListener(1000, 50, c -> {
            if (onEmojiIconBackspaceClickedListener != null) {
                onEmojiIconBackspaceClickedListener.onEmojiIconBackspaceClicked(c);
            }
        }));

        onPageChosen(0);
        return rootComponent;
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
        if (mEmojiTabLastSelectedIndex == i) {
            return;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                if (mEmojiTabLastSelectedIndex >= 0 && mEmojiTabLastSelectedIndex < mEmojiTabs.length) {
                    mEmojiTabs[mEmojiTabLastSelectedIndex].setSelected(false);
                }
                mEmojiTabs[i].setSelected(true);
                mEmojiTabLastSelectedIndex = i;
                break;
        }
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

    public void setOnEmojiIconBackspaceClickedListener(OnEmojiIconBackspaceClickedListener onEmojiIconBackspaceClickedListener) {
        this.onEmojiIconBackspaceClickedListener = onEmojiIconBackspaceClickedListener;
    }

    public interface OnEmojiIconBackspaceClickedListener {
        void onEmojiIconBackspaceClicked(Component c);
    }
    //#endregion Event handler

    //#region inner classes
    private static class EmojiPagerAdapter extends PageSliderProvider {
        private final List<EmojiconGridFraction> fractions;

        public EmojiPagerAdapter(List<EmojiconGridFraction> fractions) {
            this.fractions = fractions;
        }

        @Override
        public int getCount() {
            return fractions.size();
        }

        @Override
        public Object createPageInContainer(ComponentContainer componentContainer, int i) {
            EmojiconGridFraction component = fractions.get(i);
            componentContainer.removeAllComponents();
            componentContainer.addComponent(component.getComponent());

            return componentContainer;
        }

        @Override
        public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
            componentContainer.removeAllComponents();
        }

        @Override
        public boolean isPageMatchToObject(Component component, Object o) {
            return false;
        }
    }

    /**
     * A class, that can be used as a TouchListener on any view (e.g. a Button).
     * It cyclically runs a clickListener, emulating keyboard-like behaviour. First
     * click is fired immediately, next before initialInterval, and subsequent before
     * normalInterval.
     * <p/>
     * <p>Interval is scheduled before the onClick completes, so it has to run fast.
     * If it runs slow, it does not generate skipped onClicks.
     */
    public static class RepeatListener implements Component.TouchEventListener {
        private final int normalInterval;
        private Component downView;
        private final Component.ClickedListener clickListener;
        private final EventHandler handler;
        private final int initialInterval;
        private final Runnable handlerRunnable = new Runnable() {
            @Override
            public void run() {
                if (downView == null) {
                    return;
                }
                handler.removeAllEvent();
                handler.postTask(this, normalInterval);
                clickListener.onClick(downView);
            }
        };


        /**
         * @param initialInterval The interval before first click event
         * @param normalInterval  The interval before second and subsequent click
         *                        events
         * @param clickListener   The OnClickListener, that will be called
         *                        periodically
         */
        public RepeatListener(int initialInterval, int normalInterval, Component.ClickedListener clickListener) {
            handler = new EventHandler(EventRunner.getMainEventRunner());
            if (clickListener == null)
                throw new IllegalArgumentException("null runnable");
            if (initialInterval < 0 || normalInterval < 0)
                throw new IllegalArgumentException("negative interval");

            this.initialInterval = initialInterval;
            this.normalInterval = normalInterval;
            this.clickListener = clickListener;
        }


        @Override
        public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
            switch (touchEvent.getAction()) {
                case TouchEvent.PRIMARY_POINT_DOWN:
                case TouchEvent.OTHER_POINT_DOWN:
                    downView = component;
                    handler.removeTask(handlerRunnable);
                    handler.postTask(handlerRunnable, initialInterval);
                    clickListener.onClick(component);
                    return true;
                case TouchEvent.OTHER_POINT_UP:
                case TouchEvent.PRIMARY_POINT_UP:
                case TouchEvent.CANCEL:
                    handler.removeAllEvent();
                    downView = null;
                    return true;
            }
            return false;
        }
    }
    //#endregion inner classes

}
