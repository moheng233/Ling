package site.moheng.ling;

import site.moheng.ling.spell.node.SpellNodeBreakBlock;
import site.moheng.ling.spell.node.SpellNodeConstPos;
import site.moheng.ling.spell.node.SpellNodeGetEntityPos;
import site.moheng.ling.spell.node.SpellNodeGetOwer;
import site.moheng.ling.spell.node.SpellNodeOnUse;
import site.moheng.ling.spell.node.SpellNodePosAdd;
import site.moheng.ling.spell.registry_container.SpellBlockRegistryContainer;

public class SpellNodes implements SpellBlockRegistryContainer {

    public static final SpellNodeOnUse ON_USE = new SpellNodeOnUse();

    public static final SpellNodeGetOwer GET_OWER = new SpellNodeGetOwer();

    public static final SpellNodeGetEntityPos GET_ENTITY_POS = new SpellNodeGetEntityPos();
    public static final SpellNodeConstPos CONST_POS = new SpellNodeConstPos();
    public static final SpellNodePosAdd POS_ADD = new SpellNodePosAdd();

    public static final SpellNodeBreakBlock BREAK_BLOCK = new SpellNodeBreakBlock();
}
