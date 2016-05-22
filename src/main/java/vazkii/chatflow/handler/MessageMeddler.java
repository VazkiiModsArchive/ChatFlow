package vazkii.chatflow.handler;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.chatflow.ChatFlow;
import vazkii.chatflow.helper.Replacement;

public final class MessageMeddler {

	private static final String CONTROL_CHARACTER_REGEX = "(?<!\\\\)~";
	static boolean sendToNotification = false;

	@SubscribeEvent
	public void onMessageReceived(ClientChatReceivedEvent event) {
		String originalMessage = event.getMessage().getFormattedText();
		
		while(originalMessage.startsWith(TextFormatting.RESET.toString()))
			originalMessage = originalMessage.substring(2);
		while(originalMessage.endsWith(TextFormatting.RESET.toString()))
			originalMessage = originalMessage.substring(0, originalMessage.length() - 2);
		
		System.out.println(originalMessage);

		if(originalMessage.length() > 0) {
			String regex = "\u00A7r(.*\u00A7r)";
			while(originalMessage.matches(".*" + regex + ".*"))
				originalMessage = originalMessage.replaceFirst(regex, "$1");

			String message = originalMessage;
			sendToNotification = false;

			message = meddleWithMessage(message, false, false);

			if(message.isEmpty())
				event.setCanceled(true);

			if(!event.isCanceled() && sendToNotification) {
				int time = 20 + message.length() / 2;
				ToastHandler.setTooltip(message, time);
				event.setCanceled(true);
			}

			if(!message.equals(originalMessage))
				event.setMessage(new TextComponentString(message));
		}
	}

	public static String meddleWithMessage(String message, boolean controlExclusive, boolean replaceControl) {
		if(replaceControl)
			message = message.replaceAll(CONTROL_CHARACTER_REGEX, "\u00A7");

		if(!controlExclusive)
			for(Replacement r : ChatFlow.replacements)
				if(r.enabled)
					message = meddleWithMessage(r, message);
		return message;
	}

	public static String meddleWithMessage(Replacement r, String message) {
		try {
			String matcher = r.matcher.replaceAll(CONTROL_CHARACTER_REGEX, "\u00A7");
			String replacement = r.replacement.replaceAll(CONTROL_CHARACTER_REGEX, "\u00A7");

			if(message.matches(matcher))
				sendToNotification = sendToNotification || r.notification;

			message = message.replaceAll(matcher, replacement);
		} catch(Exception e) {
			return TextFormatting.RED + "" + TextFormatting.UNDERLINE + "ERROR: " + e.getMessage();
		}
		return message;
	}

}
