package vazkii.chatflow.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import vazkii.chatflow.gui.GuiEditor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class KeyHandler {

	public KeyBinding key = new KeyBinding("ChatFlow", Keyboard.KEY_F12, "key.categories.multiplayer");

	public KeyHandler() {
		ClientRegistry.registerKeyBinding(key);
	}

	@SubscribeEvent
	public void playerTick(ClientTickEvent event) {
		if(event.phase == Phase.START) {
			if(key.isKeyDown())
				Minecraft.getMinecraft().displayGuiScreen(new GuiEditor());
		}
	}

}
