package de.TrainingsSchedule.commands.print.utility.elements;

import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;

public class ContentTable {

	private List<Chapter> chapters;
	private String title;
	
	public void create(List<Chapter> chapters) {
		this.chapters = chapters;
		title = "Table of contents";
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) {
		yPosition = pdfAdder.addPagebreak(document);
		pdfAdder.addText(document, pdfContentByte, title, Properties.chapter_1, yPosition, false);
		if(chapters!=null) {
			for(Chapter chapter: chapters) {
				pdfAdder.addText(document, pdfContentByte, String.format("%d. %s", chapter.getId(), chapter.getTitle()),Properties.contenttable_1, yPosition, false);
				if(chapter.getSubChapters()!=null) {
					for(SubChapter subChapter: chapter.getSubChapters()) {
						pdfAdder.addText(document, pdfContentByte, String.format("%.2f %s",  subChapter.getId(), subChapter.getTitle()), Properties.chapter_2, yPosition, false);
					}
				}
			}
		}
		return yPosition;
	}
	
}
