package de.TrainingsSchedule.commands.print.elements.content;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileReader;
import lombok.Getter;

public class Content {

	@Getter
	private List<Chapter> chapters;
	
	public PDDocument create(TrainingsSchedule trainingsSchedule, PDDocument document) throws FileNotFoundException {
		
		chapters = new ArrayList<Chapter>();
		
		Chapter chapterDescription = new Chapter(chapters.size()+1, "Description");
		chapterDescription.setTable(FileReader.getInstance().readTxt("pdfDescription"));
		chapters.add(chapterDescription);
	
		//		description, days - exercises, plan, days - day, exercise: name - table history | chart
		return document;
	}

}