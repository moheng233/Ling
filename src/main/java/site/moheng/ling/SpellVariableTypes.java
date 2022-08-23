package site.moheng.ling;

import site.moheng.ling.spell.data.SpellVariableBool;
import site.moheng.ling.spell.data.SpellVariableEntity;
import site.moheng.ling.spell.data.SpellVariableInt;
import site.moheng.ling.spell.data.SpellVariableNodeLink;
import site.moheng.ling.spell.data.SpellVariablePos;
import site.moheng.ling.spell.registry_container.SpellVariableRegistryContainer;

public class SpellVariableTypes implements SpellVariableRegistryContainer {
    public static final SpellVariableNodeLink NODE_LINK = new SpellVariableNodeLink();

    public static final SpellVariableInt INT = new SpellVariableInt();
    public static final SpellVariableBool BOOL = new SpellVariableBool();
    public static final SpellVariablePos POSITION = new SpellVariablePos();
    public static final SpellVariableEntity ENTITY = new SpellVariableEntity();
}
