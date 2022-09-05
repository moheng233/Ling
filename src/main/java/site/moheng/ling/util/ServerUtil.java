package site.moheng.ling.util;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.SettableFuture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import site.moheng.ling.LingModNetwork;

public class ServerUtil {
    private static HashMap<PlayerEntity, SettableFuture<Optional<BlockPos>>> getCrosshaireTargetCallback = new HashMap<>();

    public static void init() {
        LingModNetwork.CHANNEL.registerServerbound(RPCGetCrosshaireTargetReturn.class, (pack, access) -> {
            getCrosshaireTargetCallback.get(access.player()).set(pack.pos);
        });
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        LingModNetwork.CHANNEL.registerClientbound(RPCGetCrosshaireTarget.class, (pack, access) -> {
            MinecraftClient client = access.runtime();
            Optional<BlockPos> pos = Optional.empty();
            if(client.crosshairTarget.getType() == Type.ENTITY) {
                var entity_target = (EntityHitResult)client.crosshairTarget;
                pos = Optional.of(entity_target.getEntity().getBlockPos());
            }
            if(client.crosshairTarget.getType() == Type.BLOCK) {
                var block_target = (BlockHitResult)client.crosshairTarget;
                pos = Optional.of(block_target.getBlockPos());
            }
            LingModNetwork.CHANNEL.clientHandle().send(new RPCGetCrosshaireTargetReturn(pos));
        });
    }

    public static Future<BlockPos> GetCrosshaireTarget(PlayerEntity entity) {
        SettableFuture<BlockPos> task = SettableFuture.create();
        LingModNetwork.CHANNEL.serverHandle(entity).send(new RPCGetCrosshaireTarget());
        return task;
    }

    public static record RPCGetCrosshaireTarget() {
    }

    public static record RPCGetCrosshaireTargetReturn(Optional<BlockPos> pos) {
    }
}
