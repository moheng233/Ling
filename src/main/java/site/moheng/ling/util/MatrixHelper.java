package site.moheng.ling.util;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3i;

public class MatrixHelper {
    
    public static Vec3i getPosVec(Matrix4f matrix) {
        return ((MatrixExpInter) (Object) matrix).getPosVec();
    }

    public static Vec3i getPosVec(MatrixStack m) {
        return getPosVec(m.peek().getPositionMatrix());
    }

}
