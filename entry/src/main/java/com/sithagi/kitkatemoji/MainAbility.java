package com.sithagi.kitkatemoji;

import com.sithagi.kitkatemoji.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbility extends FractionAbility {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
//        super.setMainRoute(MainAbilitySlice.class.getName());

        super.setUIContent(ResourceTable.Layout_ability_main);
        EmojiconsFraction emojiconsFraction = new EmojiconsFraction();


        HiLog.warn(LABEL_LOG, "onStart: ");
        HiLog.warn(LABEL_LOG, "setClickedListener: ");
        getFractionManager().startFractionScheduler().add(ResourceTable.Id_main_fraction, emojiconsFraction).submit();


    }
}
