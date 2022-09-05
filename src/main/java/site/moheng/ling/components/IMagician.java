package site.moheng.ling.components;

import java.util.LinkedList;
import java.util.Optional;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import site.moheng.ling.rune.ISpellRune;

/**
 * 可以作为施法者的接口
 */
public interface IMagician {
    LinkedList<Object> getRunningStack();

    default void push(Object e) {
        getRunningStack().addFirst(e);
    }
    /**
     * 让栈顶元素出栈
     * 注意！不管元素类型是否符合都会出栈
     * @return 不存在或者与想要获取类型不匹配的时候返回
     */
    default Optional<Object> pop() {
        try {
            return Optional.ofNullable(getRunningStack().removeFirst());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    /**
     * 获取运行栈栈顶元素
     * @return 不存在或者与想要获取类型不匹配的时候返回
     */
    default Optional<Object> peek() {
        try {
            return Optional.ofNullable(getRunningStack().peekFirst());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    MinecraftServer getServer();

    default void execRune(ISpellRune rune) {
        getServer().submit(() -> rune.exec(this));
    }

    void sendMessage(Text str);
    void sendMessage(PlayerEntity player, Text text);
}
