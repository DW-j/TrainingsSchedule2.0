package de.TrainingsSchedule.commands.print.elements.content;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import de.TrainingsSchedule.commands.print.utility.PDFWriter;
import de.TrainingsSchedule.commands.print.utility.Properties;
import de.TrainingsSchedule.utility.other.Table;
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
	private String text;
	@Setter
	private Table table;
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
	
	public PDDocument create(PDDocument document) throws IOException {
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		PDFWriter pdfWriter = new PDFWriter();
		
		float yPosition = pdfWriter.addText(String.format("%d. %s", id, title), Properties.chapter_1, contentStream, page, false, 0);
		
		if(text!=null) {
			yPosition = pdfWriter.addText(text, Properties.text_1, contentStream, page, false, yPosition);
		}
		
		if(table!=null) {
			yPosition = pdfWriter.addTable(table, Properties.table_1, document, page, yPosition);
		}
		
		for(SubChapter subChapter: subChapters) {
			yPosition = subChapter.create(contentStream, document, document.getPage(document.getNumberOfPages()-1), yPosition, pdfWriter);
		}	
		
		contentStream.close();
		
		return document;
	}
	
}