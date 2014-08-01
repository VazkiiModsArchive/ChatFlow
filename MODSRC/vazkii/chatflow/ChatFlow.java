package vazkii.chatflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vazkii.chatflow.handler.KeyHandler;
import vazkii.chatflow.handler.MessageMeddler;
import vazkii.chatflow.handler.ToastHandler;
import vazkii.chatflow.helper.NBTHelper;
import vazkii.chatflow.helper.Replacement;
import vazkii.chatflow.lib.LibMisc;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.MOD_VERSION)
public class ChatFlow {

	public static List<Replacement> replacements = new ArrayList();

	static File saveFileLegacy;
	static File saveFile;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new MessageMeddler());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
		ToastHandler h = new ToastHandler();
		MinecraftForge.EVENT_BUS.register(h);
		FMLCommonHandler.instance().bus().register(h);

		saveFileLegacy = event.getSuggestedConfigurationFile();
		saveFile = new File(saveFileLegacy.getParentFile(), "ChatFlow.dat");

		loadReplacements();
	}

	public static void saveReplacements() {
		try {
			if(!saveFile.exists())
				saveFile.createNewFile();

			NBTTagCompound cmp = new NBTTagCompound();
			int count = replacements.size();
			cmp.setInteger("count", count);

			for(int i = 0; i < replacements.size(); i++) {
				NBTTagCompound cmp_ = new NBTTagCompound();
				replacements.get(i).write(cmp_);
				cmp.setTag("repl" + i, cmp_);
			}

			NBTHelper.injectNBTToFile(cmp, saveFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadReplacements() {
		if(saveFileLegacy.exists())
			loadReplacementsLegacy();
		else {
			replacements.clear();

			NBTTagCompound cmp = NBTHelper.getCacheCompound(saveFile);
			int count = cmp.getInteger("count");
			for(int i = 0; i < count; i++) {
				NBTTagCompound cmp_ = cmp.getCompoundTag("repl" + i);
				Replacement r = Replacement.read(cmp_);
				replacements.add(r);
			}
		}
	}

	public static void loadReplacementsLegacy() {
		try {
			if(!saveFileLegacy.exists())
				return;

			replacements.clear();

			BufferedReader reader = new BufferedReader(new FileReader(saveFileLegacy));
			reader.readLine(); // Skip start comment
			Replacement r = null;
			while((r = Replacement.readLegacy(reader)) != null)
				replacements.add(r);

			reader.close();
			saveFileLegacy.delete();
			saveReplacements();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
