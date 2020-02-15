package de.TrainingsSchedule.commands.print.elements.contenttable;

import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import de.TrainingsSchedule.commands.print.elements.content.Chapter;
import de.TrainingsSchedule.commands.print.elements.content.Content;
import de.TrainingsSchedule.commands.print.elements.content.SubChapter;

public class ContentTable {

	private List<Entry> entries;
	
	public PDDocument create(Content content, PDDocument document) {
			
		entries = new ArrayList<Entry>();
		for(Chapter chapter: content.getChapters()) {
			List<SubEntry> subentries = new ArrayList<SubEntry>();
			for(SubChapter subChapter: chapter.getSubChapters()) {
				SubEntry subEntry = new SubEntry(subChapter.getId(), subChapter.getTitle()); 
				subentries.add(subEntry);
			}
			Entry entry = new Entry(chapter.getId(), chapter.getTitle(), subentries);
			entries.add(entry);
		}
		
		return document;
	}

}
