package de.TrainingsSchedule.commands.print.elements.contenttable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Entry {

	private int id;
	private String title;
	private List<SubEntry> subEntries;
	
}