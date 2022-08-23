package site.moheng.ling.spell.node;

import java.util.Optional;

import io.wispforest.owo.nbt.NbtKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import site.moheng.ling.SpellVariableTypes;
import site.moheng.ling.gui.menu.AbsEditMenu;
import site.moheng.ling.gui.menu.PostionEditMenu;
import site.moheng.ling.spell.SpellHandleNode;

public class SpellNodeConstPos extends SpellHandleNode {
    public NodeIOPoint<BlockPos> vecOutput = NodeIOPoint.create("vector", IOType.OUTPUT, SpellVariableTypes.POSITION);

    protected NbtKey<NbtCompound> vecKey = new NbtKey<>("vector", NbtKey.Type.COMPOUND);

    @Override
    public int getHeight() {
        return super.getHeight() + 10;
    }

    public SpellNodeConstPos() {
        io.define(vecOutput);
    }

    @Override
    public Optional<AbsEditMenu> getExtaMenu(MinecraftClient client,Vec2f pos, NbtCompound exta) {
        BlockPos vec = BlockPos.ORIGIN;
        if(exta.has(vecKey)) {
            vec = NbtHelper.toBlockPos(exta.get(vecKey));
        }

        return Optional.of(new PostionEditMenu(client, (int)pos.x, (int)pos.y, vec, (change) -> {
            exta.put(vecKey, NbtHelper.fromBlockPos(change));
        }));
    }

    @Override
    protected void drawExta(MatrixStack matrices, DrawableHelper helper, TextRenderer textRenderer, NbtCompound exta) {
        super.drawExta(matrices, helper, textRenderer, exta);
        BlockPos pos = BlockPos.ORIGIN;
        if(exta.has(vecKey)) {
            pos = NbtHelper.toBlockPos(exta.get(vecKey));
        }
        var text = Text.of(pos.toShortString());

        textRenderer.drawWithShadow(matrices, text, (getWidth() - textRenderer.getWidth(text)) / 2, 0, 0xffffffff);
    }

}
