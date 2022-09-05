package site.moheng.ling.rune;

import net.minecraft.text.Text;
import site.moheng.ling.components.IMagician;

public class RuneVoid extends AbsSpellRune {
    @Override
    public void exec(IMagician magician) {
        magician.sendMessage(Text.of("你使用了空符文"));
        super.exec(magician);
    }
}
