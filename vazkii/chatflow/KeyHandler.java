package vazkii.chatflow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public class KeyHandler {

	public KeyBinding key = new KeyBinding("ChatFlow", Keyboard.KEY_F12, "key.categories.multiplayer");

	public KeyHandler() {
		ClientRegistry.registerKeyBinding(key);
	}

	@SubscribeEvent
	public void playerTick(ClientTickEvent event) {
		if(event.phase == Phase.START) {
			if(key.getIsKeyPressed())
				Minecraft.getMinecraft().displayGuiScreen(new GuiEditor());
		}
	}

}
