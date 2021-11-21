package com.sithagi.kitkatemoji;

import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Image;
import ohos.agp.components.TextField;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;


public class MainAbility extends FractionAbility {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");
    TextField messageEd;
    DirectionalLayout emojiIconsCover;
    Image btn_chat_emoji;
    boolean isEmojiVisible = false;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        try {
            super.setUIContent(ResourceTable.Layout_ability_main);

            EmojiconsFraction emojiconsFraction = new EmojiconsFraction(getContext());

            messageEd = (TextField) findComponentById(ResourceTable.Id_edit_chat_message);
            EmojiconTextView messageTx = (EmojiconTextView) findComponentById(ResourceTable.Id_txt_sentMessage);
            emojiIconsCover = (DirectionalLayout) findComponentById(ResourceTable.Id_main_fraction);
            btn_chat_emoji = (Image) findComponentById(ResourceTable.Id_btn_chat_emoji);
            Image sendButton = (Image) findComponentById(ResourceTable.Id_btn_send);

            messageEd.setClickedListener(component -> {
                if (isEmojiVisible) {
                    component.clearFocus();
                } else {
                    component.requestFocus();
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

            btn_chat_emoji.setClickedListener(c -> changeEmojiLayout());

            emojiconsFraction.setOnEmojiIconClickedListener(emojicon -> emojiconsFraction.input(messageEd, emojicon));

            emojiconsFraction.setOnEmojiIconBackspaceClickedListener(c -> emojiconsFraction.backspace(messageEd));

            getFractionManager().startFractionScheduler().add(ResourceTable.Id_main_fraction, emojiconsFraction).submit();

        } catch (Exception ex) {
//            HiLog.warn(LABEL_LOG, "MainAbility: onStart  " + ex);
//            for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
//                HiLog.warn(LABEL_LOG, "" + stackTraceElement);
//            }
        }
    }


    protected void changeEmojiLayout() {
        // TODO: find a way to set it so when the soft-keyboard shows up the message send footer
        // goes up with it
        if (isEmojiVisible) {
            btn_chat_emoji
                    .setPixelMap(ResourceTable.Media_ic_vp_smileys);
            emojiIconsCover
                    .setVisibility(Component.HIDE);
            emojiIconsCover
                    .setVisibility(Component.HIDE);
            isEmojiVisible = false;
            messageEd.requestFocus();
        } else {
            btn_chat_emoji
                    .setPixelMap(ResourceTable.Media_ic_vp_keypad);
            emojiIconsCover
                    .setVisibility(Component.VISIBLE);
            isEmojiVisible = true;
        }
    }


}
