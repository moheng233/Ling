package site.moheng.ling.rune;

import site.moheng.ling.components.IMagician;

public class RuneRemoveFirst extends AbsSpellRune {
    @Override
    public void exec(IMagician magician) {
        magician.pop();
        super.exec(magician);
    }
}
