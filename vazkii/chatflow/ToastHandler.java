package vazkii.chatflow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ToastHandler {

	private static String currentTooltip;
	private static int initialTooltipDisplayTicks;
	private static int tooltipDisplayTicks;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void drawDislocationFocusHUD(RenderGameOverlayEvent.Post event) {
		if (event.type == ElementType.ALL && tooltipDisplayTicks > 0 && !MathHelper.stringNullOrLengthZero(currentTooltip)) {
			Minecraft mc = Minecraft.getMinecraft();
			TransientScaledResolution var5 = new TransientScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int var6 = var5.getScaledWidth();
			int var7 = var5.getScaledHeight();
			FontRenderer var8 = mc.fontRenderer;

			int tooltipStartX = (var6 - var8.getStringWidth(currentTooltip)) / 2;
			int tooltipStartY = var7 - 72;

			int opacity = Math.min(256, (int) (tooltipDisplayTicks * 256.0F / initialTooltipDisplayTicks));

			if(opacity > 0) {
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				Gui.drawRect(tooltipStartX - 2, tooltipStartY - 2, tooltipStartX + var8.getStringWidth(currentTooltip) + 2, tooltipStartY + 11, 0x111111 + (Math.min(150, opacity) << 24));
				GL11.glEnable(GL11.GL_BLEND);
				Gui.drawRect(tooltipStartX - 3, tooltipStartY - 3, tooltipStartX + var8.getStringWidth(currentTooltip) + 3, tooltipStartY + 12, 0x111111 + (Math.min(150, opacity) / 2 << 24));
				GL11.glEnable(GL11.GL_BLEND);
				var8.drawStringWithShadow(currentTooltip, tooltipStartX, tooltipStartY, 0xFFFFFF + (opacity << 24));
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}
	}


	@SubscribeEvent
	public void tickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END)
			clientTick();
	}

	public static void setTooltip(String tooltip, int time) {
		currentTooltip = tooltip;
		initialTooltipDisplayTicks = time;
		tooltipDisplayTicks = time;
	}

	public static void clientTick() {
		if(tooltipDisplayTicks > 0)
			--tooltipDisplayTicks;
	}

}
