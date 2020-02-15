package de.TrainingsSchedule.commands.print.elements.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Chapter {

	@Getter
	private int id;
	@Getter
	private String title;
	@Getter
	private List<SubChapter> subChapters;
	@Setter
	private String text, table, tableHeadline, tableText, imageText;
	@Setter
	private File image;
	
	public Chapter(int id, String title) {
		this.id = id;
		this.title = title;
		subChapters = new ArrayList<SubChapter>();
	}

	public void addSubChapter(SubChapter subChapter) {
		if(subChapters==null) {
			subChapters = new ArrayList<SubChapter>();
		}
		subChapters.add(subChapter);
	} 
	
}