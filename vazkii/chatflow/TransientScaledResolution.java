package vazkii.chatflow;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

// Copy of ScaledResolution with 1.7.2 and 1.7.10 support. Will drop once 1.7.10 is in full use
public class TransientScaledResolution {

	private int scaledWidth;
	private int scaledHeight;
	private double scaledWidthD;
	private double scaledHeightD;
	private int scaleFactor;

	public TransientScaledResolution(GameSettings par1GameSettings, int par2, int par3) {
		scaledWidth = par2;
		scaledHeight = par3;
		scaleFactor = 1;
		int k = par1GameSettings.guiScale;

		if (k == 0)
			k = 1000;

		while (scaleFactor < k && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240)
			++scaleFactor;

		scaledWidthD = (double)scaledWidth / (double)scaleFactor;
		scaledHeightD = (double)scaledHeight / (double)scaleFactor;
		scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
		scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
	}

	public int getScaledWidth() {
		return scaledWidth;
	}

	public int getScaledHeight() {
		return scaledHeight;
	}

	public double getScaledWidth_double() {
		return scaledWidthD;
	}

	public double getScaledHeight_double() {
		return scaledHeightD;
	}

	public int getScaleFactor() {
		return scaleFactor;
	}
}