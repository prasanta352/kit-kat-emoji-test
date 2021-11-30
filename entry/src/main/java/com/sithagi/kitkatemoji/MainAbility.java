package com.sithagi.kitkatemoji;

import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.content.Intent;
import ohos.accessibility.AccessibilityEventInfo;
import ohos.accessibility.ability.AccessibleAbility;
import ohos.accessibility.ability.SoftKeyBoardController;
import ohos.accessibility.ability.SoftKeyBoardListener;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.TextField;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.miscservices.inputmethodability.InputMethodAbility;

import static ohos.agp.window.service.WindowManager.LayoutConfig.INPUT_ADJUST_RESIZE;


/**
 * MainAbility.
 */
public class MainAbility extends AccessibleAbility {
    TextField messageEd;
    DirectionalLayout emojiIconsCover;
    EmojiconsFraction emojiconsFraction;
    TextField messageTx;
    Image btnChatEmoji;
    boolean isEmojiKeyboardVisible = false;
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        super.setUIContent(ResourceTable.Layout_ability_main);
        getWindow().setInputPanelDisplayType(INPUT_ADJUST_RESIZE);
        messageTx = (TextField) findComponentById(ResourceTable.Id_txt_sentMessage);

        findComponentById(ResourceTable.Id_openKeyboard).setClickedListener(c -> {
            showKeyBoard();
        });

        findComponentById(ResourceTable.Id_closeKeyboard).setClickedListener(c -> {
            hideKeyBoard();
        });


        findComponentById(ResourceTable.Id_focusKeyboard).setClickedListener(c -> {
            messageTx.requestFocus();
            new ToastDialog(getContext()).setText("MainAbility: requestFocus ").setAlignment(LayoutAlignment.BOTTOM).show();
        });


        findComponentById(ResourceTable.Id_unFocusKeyboard).setClickedListener(c -> {
            messageTx.clearFocus();
            new ToastDialog(getContext()).setText("MainAbility: clearFocus ").setAlignment(LayoutAlignment.BOTTOM).show();
        });


        getSoftKeyBoardController().addListener((softKeyBoardController, i) -> {
            HiLog.warn(LABEL_LOG, "MainAbility: onSoftKeyBoardShowModeChanged " + i);
            new ToastDialog(getContext()).setText("MainAbility: onSoftKeyBoardShowModeChanged " + i).setAlignment(LayoutAlignment.TOP).show();
        });
//
//
//        emojiconsFraction = new EmojiconsFraction(getContext());
//
//        messageEd = (TextField) findComponentById(ResourceTable.Id_edit_chat_message);
//        messageTx = (EmojiconTextView) findComponentById(ResourceTable.Id_txt_sentMessage);
//        emojiIconsCover = (DirectionalLayout) findComponentById(ResourceTable.Id_main_fraction);
//        btnChatEmoji = (Image) findComponentById(ResourceTable.Id_btn_chat_emoji);
//        Image sendButton = (Image) findComponentById(ResourceTable.Id_btn_send);
//
//        messageEd.setClickedListener(component -> {
//            if (isEmojiKeyboardVisible) {
//                messageEd.clearFocus();
//            } else {
//                messageEd.requestFocus();
//            }
//        });
//
//
//        sendButton.setClickedListener(c -> {
//            String chat = messageEd.getText().trim();
//            if (!chat.isEmpty()) {
//                messageTx.setText(chat);
//                messageEd.setText("");
//
//            }
//            if (isEmojiKeyboardVisible) {
//                changeEmojiLayout();
//            }
//
//        });
//
//        btnChatEmoji.setClickedListener(c -> changeEmojiLayout());
//
//        emojiconsFraction.setOnEmojiIconClickedListener(emojicon -> emojiconsFraction.input(messageEd, emojicon));
//
//        emojiconsFraction.setOnEmojiIconBackspaceClickedListener(c -> emojiconsFraction.backspace(messageEd));
//
//        getFractionManager().startFractionScheduler().add(ResourceTable.Id_main_fraction, emojiconsFraction)
//                .submit();
    }

    protected void changeEmojiLayout() {
        // TODO: find a way to set it so when the soft-keyboard shows up the message send footer
        // goes up with it
        if (isEmojiKeyboardVisible) {
            btnChatEmoji
                    .setPixelMap(ResourceTable.Media_ic_vp_smileys);
            emojiIconsCover
                    .setVisibility(Component.HIDE);
            isEmojiKeyboardVisible = false;
            showKeyBoard();
        } else {
            btnChatEmoji
                    .setPixelMap(ResourceTable.Media_ic_vp_keypad);
            emojiIconsCover
                    .setVisibility(Component.VISIBLE);
            isEmojiKeyboardVisible = true;
            hideKeyBoard();
        }
    }

    //    void showKeyBoard() {
////        messageEd.requestFocus();
//        try {
//            HiLog.warn(LABEL_LOG, "MainAbility: showKeyBoard");
//            SoftKeyBoardController ime = new SoftKeyBoardController(AccessibleAbility.SHOW_MODE_AUTO, null);
//            ime.setShowMode(AccessibleAbility.SHOW_MODE_AUTO);
//            getSoftKeyBoardController().setShowMode(AccessibleAbility.SHOW_MODE_AUTO);
//
//            new ToastDialog(getContext()).setText("showKeyBoard").setAlignment(LayoutAlignment.BOTTOM).show();
//        } catch (Exception ex) {
//            for (StackTraceElement st : ex.getStackTrace()) {
//                HiLog.warn(LABEL_LOG, "" + st);
//            }
//        }
//    }
//
//    void hideKeyBoard() {
////        messageEd.clearFocus();
//        try {
//            new ToastDialog(getContext()).setText("hideKeyBoard").setAlignment(LayoutAlignment.BOTTOM).show();
//            getSoftKeyBoardController().setShowMode(AccessibleAbility.SHOW_MODE_HIDE);
//            HiLog.warn(LABEL_LOG, "MainAbility: hideKeyBoard");
//            SoftKeyBoardController ime = new SoftKeyBoardController(AccessibleAbility.SHOW_MODE_AUTO, null);
//            ime.setShowMode(AccessibleAbility.SHOW_MODE_HIDE);
//        } catch (Exception ex) {
//            for (StackTraceElement st : ex.getStackTrace()) {
//                HiLog.warn(LABEL_LOG, "" + st);
//            }
//        }
//    }
    private void showKeyBoard() {
        messageTx.requestFocus();//set focus in text field
        messageTx.simulateClick(); // brings keyboard up
        new ToastDialog(getContext()).setText("showKeyBoard").setAlignment(LayoutAlignment.BOTTOM).show();
    }

    private void hideKeyBoard() {
        messageTx.clearFocus();
        new ToastDialog(getContext()).setText("hideKeyBoard").setAlignment(LayoutAlignment.BOTTOM).show();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEventInfo accessibilityEventInfo) {

    }

    @Override
    public void onInterrupt() {

    }
}
