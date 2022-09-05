package site.moheng.ling.rune;

import net.minecraft.entity.Entity;
import site.moheng.ling.components.IMagician;

import java.util.Optional;

public class RuneGetPos extends AbsSpellRune {
    @Override
    public void exec(IMagician magician) {
        Optional<Object> data = magician.pop();
        if(data.isPresent() && data.get() instanceof Entity) {
            magician.push(((Entity)data.get()).getBlockPos());
            return;
        }
        
        magician.push(Optional.empty());
    }
}
