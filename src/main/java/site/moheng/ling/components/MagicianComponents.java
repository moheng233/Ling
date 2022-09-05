package site.moheng.ling.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import site.moheng.ling.LingModComponents;

import java.util.LinkedList;
import java.util.Optional;

public class MagicianComponents implements PlayerComponent<MagicianComponents>, ServerTickingComponent, IMagician, AutoSyncedComponent {
    private final PlayerEntity entity;
    private final LinkedList<Object> runningStack = new LinkedList<>();

    @Environment(EnvType.CLIENT)
    public final LinkedList<Text> stackTexts = new LinkedList<>();

    public MagicianComponents(PlayerEntity entity) {
        this.entity = entity;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        
    }

    @Override
    public void serverTick() {
        
    }

    @Override
    public LinkedList<Object> getRunningStack() {
        return runningStack;
    }

    public PlayerEntity getPlayer() {
        return entity;
    }

    @Override
    public void sendMessage(Text str) {
        entity.sendMessage(str);        
    }

    @Override
    public void sendMessage(PlayerEntity player, Text text) {
        player.sendMessage(text);        
    }

    @Override
    public Optional<Object> pop() {
        var data = IMagician.super.pop();
        LingModComponents.MAGICIAN.sync(this.entity, this);
        return data;
    }

    @Override
    public void push(Object e) {
        IMagician.super.push(e);
        LingModComponents.MAGICIAN.sync(this.entity, this);
    }

    public Text getTextFromData(Object data) {
        if(data.equals(Optional.empty())) {
            return Text.empty().append("NULL").formatted(Formatting.RED);
        }
        if(data instanceof Entity player) {
            return Text.empty().append(player.getName()).formatted(Formatting.DARK_PURPLE);
        }
        if(data instanceof BlockHitResult hit) {
            return Text.of(hit.getBlockPos().toString());
        }

        return Text.of(data.toString());
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeInt(runningStack.size());
        for (var data : runningStack) {
            buf.writeText(getTextFromData(data));
        }
        AutoSyncedComponent.super.writeSyncPacket(buf, recipient);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        stackTexts.clear();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            stackTexts.add(buf.readText());
        }
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return entity.equals(player);
    }

    @Override
    public MinecraftServer getServer() {
        return entity.getServer();
    }
}
