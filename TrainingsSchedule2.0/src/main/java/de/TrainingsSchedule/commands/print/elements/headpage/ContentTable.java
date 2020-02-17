package de.TrainingsSchedule.commands.print.elements.headpage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import de.TrainingsSchedule.commands.print.elements.content.Chapter;
import de.TrainingsSchedule.commands.print.elements.content.Content;
import de.TrainingsSchedule.commands.print.elements.content.SubChapter;
import de.TrainingsSchedule.commands.print.utility.PDFWriter;
import de.TrainingsSchedule.commands.print.utility.Properties;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ContentTable {
	
	private List<Entry> entries;
	String title;
	
	public void create(Content content, PDDocument document) throws IOException {
		
		title = "Table of contents";
		
		entries = new ArrayList<Entry>();
		for(Chapter chapter: content.getChapters()) {
			List<SubEntry> subEntries = new ArrayList<SubEntry>();
			for(SubChapter subChapter: chapter.getSubChapters()) {
				SubEntry subEntry = new SubEntry(subChapter.getId(), subChapter.getTitle());
				subEntries.add(subEntry);
			}
			Entry entry = new Entry(chapter.getId(), chapter.getTitle(), subEntries);
			entries.add(entry);
		}	
		
	}
	
	public PDDocument print(PDDocument document) throws IOException {
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		PDFWriter pdfWriter = new PDFWriter();
		
		float yPosition = pdfWriter.addText(title, Properties.chapter_1, contentStream, page, false, 0);
		
		for(Entry entry: entries) {
			yPosition = pdfWriter.addText(entry.toString(), Properties.contenttable_1, contentStream, page, false, yPosition);
			for(SubEntry subEntry: entry.getSubEntries()) {
				yPosition = pdfWriter.addText(subEntry.toString(), Properties.contenttable_2, contentStream, page, false, yPosition);
			}
		}
		
		contentStream.close();
		
		return document;
	}

}

@AllArgsConstructor
class Entry{
	int id;
	String title;
	@Getter
	List<SubEntry> subEntries = new ArrayList<SubEntry>();
	
	public String toString() {
		return String.format("%d. %s", id, title);
	}
}

@AllArgsConstructor
class SubEntry{
	double id;
	String title;
	
	public String toString() {
		return String.format("%.2f %s", id, title);
	}
}