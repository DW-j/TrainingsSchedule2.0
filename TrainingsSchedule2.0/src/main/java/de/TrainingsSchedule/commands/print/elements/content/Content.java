package de.TrainingsSchedule.commands.print.elements.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.templates.DayTemplate;
import de.TrainingsSchedule.utility.files.FileReader;
import lombok.Getter;

public class Content {

	@Getter
	private List<Chapter> chapters;
	
	public void create(TrainingsSchedule trainingsSchedule) throws IOException {
		
		chapters = new ArrayList<Chapter>();
		
		Chapter chapterDescription = new Chapter(getChapterId(chapters), "Description");
		chapterDescription.setText(FileReader.getInstance().readTxt("pdfDescription"));
		chapters.add(chapterDescription);
		
		Chapter chapterTemplate = new Chapter(getChapterId(chapters), "Plan template");
		for(DayTemplate dayTemplate: trainingsSchedule.getPlanTemplate().getDayTemplates()) {
			SubChapter subChapter = new SubChapter(getSubChapterId(chapterTemplate), String.format("%s %d", "Day", dayTemplate.getId()));
			subChapter.setTable(dayTemplate.toTable());
			chapterTemplate.addSubChapter(subChapter);
		}
		chapters.add(chapterTemplate);
	}
	
	public PDDocument print(PDDocument document) throws IOException {
		for(Chapter chapter: chapters) {
			document = chapter.create(document);
		}
		return document;
	}
	
	private int getChapterId(List<Chapter> chapters) {
		return chapters.size()+1;
	}
	
	private double getSubChapterId(Chapter chapter) {
		String subChapterId = new String(chapter.getId()+"."+chapter.getSubChapters().size()+1);
		return Double.parseDouble(subChapterId);
	}

}