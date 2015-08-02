package vazkii.chatflow.helper;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NBTHelper {

    public static NBTTagCompound getCacheCompound(File cache) {
        if (cache == null)
            throw new RuntimeException("No cache file!");

        try {
            NBTTagCompound cmp = CompressedStreamTools.readCompressed(new FileInputStream(cache));
            return cmp;
        } catch (IOException e) {
            NBTTagCompound cmp = new NBTTagCompound();

            try {
                CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(cache));
                return getCacheCompound(cache);
            } catch (IOException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    public static void injectNBTToFile(NBTTagCompound cmp, File f) {
        try {
            CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
