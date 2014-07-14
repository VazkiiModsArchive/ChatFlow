package vazkii.chatflow;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public final class MessageMeddler {

	private static final String CONTROL_CHARACTER_REGEX = "(?<!\\\\)~";

	@SubscribeEvent
	public void onMessageReceived(ClientChatReceivedEvent event) {
		String originalMessage = event.message.getUnformattedText();
		String message = originalMessage;

		message = meddleWithMessage(message, false, false);

		if(message.isEmpty())
			event.setCanceled(true);

		if(!message.equals(originalMessage))
			event.message = new ChatComponentText(message);
	}

	public static String meddleWithMessage(String message, boolean controlExclusive, boolean replaceControl) {
		if(replaceControl)
			message = message.replaceAll(CONTROL_CHARACTER_REGEX, "\u00A7");

		if(!controlExclusive)
			for(Replacement r : ChatFlow.replacements)
				message = meddleWithMessage(r, message);
		return message;
	}

	public static String meddleWithMessage(Replacement r, String message) {
		try {

			String matcher = r.matcher.replaceAll(CONTROL_CHARACTER_REGEX, "\u00A7");
			String replacement = r.replacement.replaceAll(CONTROL_CHARACTER_REGEX, "\u00A7");

			message = message.replaceAll(matcher, replacement);
		} catch(Exception e) {
			return EnumChatFormatting.RED + "" + EnumChatFormatting.UNDERLINE + "ERROR: " + e.getMessage();
		}
		return message;
	}

}
