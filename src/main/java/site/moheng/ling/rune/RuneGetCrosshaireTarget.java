package site.moheng.ling.rune;

import site.moheng.ling.components.IMagician;
import site.moheng.ling.components.MagicianComponents;

import java.util.Optional;

public class RuneGetCrosshaireTarget extends AbsSpellRune {
    @Override
    public void exec(IMagician magician) {
        if (magician instanceof MagicianComponents entity_magician) {
            magician.push(entity_magician.getPlayer().raycast(10, 0, false));
        } else {
            magician.push(Optional.empty());
        }
        super.exec(magician);
    }
}
