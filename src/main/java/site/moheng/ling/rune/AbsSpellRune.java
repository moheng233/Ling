package site.moheng.ling.rune;

import net.minecraft.util.Identifier;

import java.util.Optional;

public abstract class AbsSpellRune implements ISpellRune {
    private Optional<Identifier> id = Optional.empty();

    @Override
    public Identifier getID() {
        if(id.isEmpty()) {
            id = Optional.of(ISpellRune.super.getID());
        }

        return id.get();
    }
}
