package site.moheng.ling.util;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NinePatchHelper {
	public Identifier texture;
	private final int uvX;
	private final int uvX1;
	private final int uvX2;
	private final int uvY;
	private final int uvY1;
	private final int uvY2;

	public final int lPadding;
	public final int tPadding;
	public final int rPadding;
	public final int bPadding;

	public final int hPadding;
	public final int vPadding;

	private final int cWidth;
	private final int cHeight;

	public NinePatchHelper(Identifier texture, int uvX, int uvX1, int uvX2, int uvW, int uvY, int uvY1, int uvY2,
			int uvH) {
		this.texture = texture;
		this.uvX = uvX;
		this.uvX1 = uvX1;
		this.uvX2 = uvX2;
		this.uvY = uvY;
		this.uvY1 = uvY1;
		this.uvY2 = uvY2;

		lPadding = uvX1 - uvX;
		rPadding = uvW - uvX2;

		tPadding = uvY1 - uvY;
		bPadding = uvH - uvY2;

		hPadding = lPadding + rPadding;
		vPadding = tPadding + bPadding;

		cWidth = uvX2 - uvX1;
		cHeight = uvY2 - uvY1;
	}

	public void draw(MatrixStack matrices, int x, int y, int w, int h) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.enableTexture();

		matrices.push();
		matrices.translate(x - lPadding, y - tPadding, 0);
		w += hPadding;
		h += vPadding;
		// *--
		// ---
		// ---
		DrawableHelper.drawTexture(matrices, 0, 0, uvX, uvY, lPadding, tPadding, 256, 256);
		// **-
		// ---
		// ---
		DrawableHelper.drawTexture(matrices, lPadding, 0, w - hPadding, tPadding, uvX1, uvY, cWidth, tPadding, 256,
				256);
		// ***
		// ---
		// ---
		DrawableHelper.drawTexture(matrices, w - rPadding, 0, uvX2, uvY, rPadding, tPadding, 256, 256);
		// ***
		// *--
		// ---
		DrawableHelper.drawTexture(matrices, 0, tPadding, lPadding, h - hPadding, uvX, uvY1, lPadding, cHeight, 256,
				256);
		// ***
		// **-
		// ---
		DrawableHelper.drawTexture(matrices, lPadding, tPadding, w - hPadding, h - vPadding, uvX1, uvY1, cWidth, cWidth,
				256, 256);
		// ***
		// ***
		// ---
		DrawableHelper.drawTexture(matrices, w - rPadding, tPadding, rPadding, h - hPadding, uvX2, uvY1, rPadding,
				cHeight, 256, 256);
		// ***
		// ***
		// *--
		DrawableHelper.drawTexture(matrices, 0, h - bPadding, uvX, uvY2, lPadding, bPadding, 256, 256);
		// ***
		// ***
		// **-
		DrawableHelper.drawTexture(matrices, lPadding, h - bPadding, w - vPadding, bPadding, uvX1, uvY2, cWidth,
				bPadding, 256, 256);
		// ***
		// ***
		// ***
		DrawableHelper.drawTexture(matrices, w - rPadding, h - bPadding, uvX2, uvY2, rPadding, bPadding, 256, 256);

		matrices.pop();
	}
}
