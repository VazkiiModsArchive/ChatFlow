package vazkii.chatflow;

import java.io.BufferedReader;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

public class Replacement {

	public String comment = "", matcher = "", replacement = "";
	public boolean notification = false;
	public boolean enabled = true;

	public static Replacement read(NBTTagCompound cmp) {
		Replacement r = new Replacement();
		r.comment = cmp.getString("comment");
		r.matcher = cmp.getString("matcher");
		r.replacement = cmp.getString("replacement");
		r.notification = cmp.getBoolean("notification");
		r.enabled = cmp.getBoolean("enabled");

		return r;
	}

	public void write(NBTTagCompound cmp) {
		cmp.setString("comment", comment);
		cmp.setString("matcher", matcher);
		cmp.setString("replacement", replacement);
		cmp.setBoolean("notification", notification);
		cmp.setBoolean("enabled", enabled);
	}

	public static Replacement readLegacy(BufferedReader reader) throws IOException {
		Replacement r = new Replacement();
		r.comment = reader.readLine();
		if(r.comment == null || r.comment.equals(":eof" + (char) 26))
			return null;

		r.matcher = reader.readLine();
		r.replacement = reader.readLine();
		return r;
	}

}
