package vazkii.chatflow.handler;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToastHandler {

	private static String currentTooltip;
	private static int initialTooltipDisplayTicks;
	private static int tooltipDisplayTicks;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void drawDislocationFocusHUD(RenderGameOverlayEvent.Post event) {
		if (event.getType() == ElementType.ALL && tooltipDisplayTicks > 0 && !(currentTooltip==null||currentTooltip.length()==0)) {
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution var5 = new ScaledResolution(mc);
			int var6 = var5.getScaledWidth();
			int var7 = var5.getScaledHeight();
			FontRenderer var8 = mc.fontRendererObj;

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
