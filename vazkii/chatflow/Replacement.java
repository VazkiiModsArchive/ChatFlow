/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Jul 13, 2014, 11:31:38 PM (GMT)]
 */
package vazkii.chatflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Replacement {

	public String comment = "", matcher = "", replacement = "";

	public void save(BufferedWriter writer) throws IOException {
		writer.write(comment + "\n");
		writer.write(matcher + "\n");
		writer.write(replacement + "\n");
	}

	public static Replacement read(BufferedReader reader) throws IOException {
		Replacement r = new Replacement();
		r.comment = reader.readLine();
		if(r.comment == null || r.comment.equals(":eof" + (char) 26))
			return null;

		r.matcher = reader.readLine();
		r.replacement = reader.readLine();
		return r;
	}

}
