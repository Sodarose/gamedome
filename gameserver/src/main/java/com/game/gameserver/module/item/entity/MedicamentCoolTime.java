package com.game.gameserver.module.monster.entity;

import com.game.gameserver.module.cooltime.entity.CoolTime;
import com.game.gameserver.module.item.entity.Medicament;
import com.game.gameserver.module.player.object.PlayerObject;
import com.game.gameserver.util.TransFromUtil;
import com.game.protocol.Message;

import java.util.concurrent.TimeUnit;

/**
 * 药剂类冷却时间
 *
 * @author xuewenkang
 * @date 2020/6/5 2:46
 */
public class MedicamentCoolTime implements CoolTime<Medicament> {

    /**
     * cd 开始时间
     */
    private long startTime;
    /**
     * cd 持续时间
     */
    private long duration;
    /**
     * cd 结束时间
     */
    private long endTime;

    private boolean expire = false;

    private Medicament medicament;
    private PlayerObject playerObject;

    public MedicamentCoolTime(Medicament medicament, PlayerObject playerObject) {
        this.medicament = medicament;
        this.playerObject = playerObject;
    }

    public void entryCD() {
        startTime = System.nanoTime();
        duration = TimeUnit.NANOSECONDS.convert(medicament.getMedicamentData()
                .getCoolTime(), TimeUnit.SECONDS);
        endTime = duration + startTime;
        medicament.setState(Medicament.COOLING);
    }

    @Override
    public void update() {
        long currTime = System.nanoTime();
        long coolTimeResidue = endTime - currTime;
        if (coolTimeResidue <= 0) {
            medicament.setState(Medicament.NORMAL);
            medicament.setCoolTimeResidue(0);
            Message message = TransFromUtil.createTipMessage(0, "药剂" + medicament
                    .getItemData().getName() + "冷却完毕");
            playerObject.getChannel().writeAndFlush(message);
            expire = true;
            return;
        }
        coolTimeResidue = TimeUnit.SECONDS.convert(coolTimeResidue,TimeUnit.NANOSECONDS);
        medicament.setCoolTimeResidue(coolTimeResidue);
        Message message = TransFromUtil.createTipMessage(0, "药剂" + medicament
                .getItemData().getName() + "剩余冷却时间"+coolTimeResidue);
        playerObject.getChannel().writeAndFlush(message);
    }

    @Override
    public boolean isExpire() {
        return expire;
    }
}
