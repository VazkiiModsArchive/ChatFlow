package vazkii.chatflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "ChatFlow", name = "ChatFlow", version = "1")
public class ChatFlow {

	public static List<Replacement> replacements = new ArrayList();

	static File saveFile;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new MessageMeddler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());

		saveFile = event.getSuggestedConfigurationFile();
		loadReplacements();
	}

	public static void saveReplacements() {
		try {
			if(!saveFile.exists())
				saveFile.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
			writer.write("# ChatFlow config file. Do *NOT* edit this manually unless you have a very good idea of what you're doing.\n");

			for(Replacement r : replacements)
				r.save(writer);

			writer.write(":eof" + (char) 26);

			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadReplacements() {
		try {
			if(!saveFile.exists())
				saveReplacements();

			replacements.clear();

			BufferedReader reader = new BufferedReader(new FileReader(saveFile));
			reader.readLine(); // Skip start comment
			Replacement r = null;
			while((r = Replacement.read(reader)) != null)
				replacements.add(r);

			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
