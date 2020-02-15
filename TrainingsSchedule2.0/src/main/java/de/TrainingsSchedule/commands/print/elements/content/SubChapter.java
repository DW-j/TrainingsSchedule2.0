package de.TrainingsSchedule.commands.print.elements.content;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

public class SubChapter {

	@Getter
	private double id;
	@Getter
	private String title;
	@Setter
	private String text, table, tableHeadline, tableText, imageText;
	@Setter
	private File image;
}