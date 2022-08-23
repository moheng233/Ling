package site.moheng.ling.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3i;
import site.moheng.ling.util.MatrixExpInter;

@Mixin(Matrix4f.class)
public abstract class Matrix4fMixin implements MatrixExpInter {
    @Shadow
    public float a00;
    @Shadow
    public float a01;
    @Shadow
    public float a02;
    @Shadow
    public float a03;
    @Shadow
    public float a10;
    @Shadow
    public float a11;
    @Shadow
    public float a12;
    @Shadow
    public float a13;
    @Shadow
    public float a20;
    @Shadow
    public float a21;
    @Shadow
    public float a22;
    @Shadow
    public float a23;
    @Shadow
    public float a30;
    @Shadow
    public float a31;
    @Shadow
    public float a32;
    @Shadow
    public float a33;

    @Override
    public Vec3i getPosVec() {
        return new Vec3i(a03, a13, a23);
    }
}
