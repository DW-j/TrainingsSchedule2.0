package de.TrainingsSchedule.commands.other;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.text.IndentGenerator;

public class CommandHelp {

	private Set<Entry<Object, Object>> entries;
	
	public CommandHelp() throws IOException {
		entries = FileReader.getInstance().readPrp("commandlist");
	}
	
	public String toString() {
		List<String> commands = new ArrayList<String>();
		for(Entry<Object, Object> e: entries) {
			String indent = IndentGenerator.getInstance().generateIndent(' ', 45-e.getKey().toString().length());
			commands.add(String.format("%s"+indent+"%s", e.getKey().toString().replace("$", " "), e.getValue()));
		}
		return String.join("\n", commands);
	}
	
}