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
