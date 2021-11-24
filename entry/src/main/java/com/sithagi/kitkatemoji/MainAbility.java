package com.sithagi.kitkatemoji;

import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.content.Intent;
import ohos.accessibility.ability.AccessibleAbility;
import ohos.accessibility.ability.SoftKeyBoardController;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.TextField;
import static ohos.agp.window.service.WindowManager.LayoutConfig.INPUT_ADJUST_RESIZE;


/**
 * MainAbility.
 */
public class MainAbility extends FractionAbility {
    TextField messageEd;
    DirectionalLayout emojiIconsCover;
    EmojiconsFraction emojiconsFraction;
    EmojiconTextView messageTx;
    Image btnChatEmoji;
    boolean isEmojiVisible = false;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        super.setUIContent(ResourceTable.Layout_ability_main);
        getWindow().setInputPanelDisplayType(INPUT_ADJUST_RESIZE);


        emojiconsFraction = new EmojiconsFraction(getContext());

        messageEd = (TextField) findComponentById(ResourceTable.Id_edit_chat_message);
        messageTx = (EmojiconTextView) findComponentById(ResourceTable.Id_txt_sentMessage);
        emojiIconsCover = (DirectionalLayout) findComponentById(ResourceTable.Id_main_fraction);
        btnChatEmoji = (Image) findComponentById(ResourceTable.Id_btn_chat_emoji);
        Image sendButton = (Image) findComponentById(ResourceTable.Id_btn_send);

        messageEd.setClickedListener(component -> {
            if (isEmojiVisible) {
                messageEd.clearFocus();
            } else {
                messageEd.requestFocus();
            }
        });


        sendButton.setClickedListener(c -> {
            String chat = messageEd.getText().trim();
            if (!chat.isEmpty()) {
                messageTx.setText(chat);
                messageEd.setText("");

            }
            if (isEmojiVisible) {
                changeEmojiLayout();
            }

        });

        btnChatEmoji.setClickedListener(c -> changeEmojiLayout());

        emojiconsFraction.setOnEmojiIconClickedListener(emojicon -> emojiconsFraction.input(messageEd, emojicon));

        emojiconsFraction.setOnEmojiIconBackspaceClickedListener(c -> emojiconsFraction.backspace(messageEd));

        getFractionManager().startFractionScheduler().add(ResourceTable.Id_main_fraction, emojiconsFraction)
                .submit();


    }


    protected void changeEmojiLayout() {
        // TODO: find a way to set it so when the soft-keyboard shows up the message send footer
        // goes up with it
        if (isEmojiVisible) {
            btnChatEmoji
                    .setPixelMap(ResourceTable.Media_ic_vp_smileys);
            emojiIconsCover
                    .setVisibility(Component.HIDE);
            isEmojiVisible = false;
            showKeyBoard();
        } else {
            btnChatEmoji
                    .setPixelMap(ResourceTable.Media_ic_vp_keypad);
            emojiIconsCover
                    .setVisibility(Component.VISIBLE);
            isEmojiVisible = true;
            hideKeyBoard();
        }
    }

    void showKeyBoard() {
        messageEd.requestFocus();
        SoftKeyBoardController ime = new SoftKeyBoardController(AccessibleAbility.SHOW_MODE_AUTO, null);
        ime.setShowMode(AccessibleAbility.SHOW_MODE_AUTO);
    }

    void hideKeyBoard() {
        messageEd.clearFocus();
        SoftKeyBoardController ime = new SoftKeyBoardController(AccessibleAbility.SHOW_MODE_AUTO, null);
        ime.setShowMode(AccessibleAbility.SHOW_MODE_HIDE);
    }


}
