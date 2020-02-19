package de.TrainingsSchedule.commands.print.utility.elements.chapters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;
import lombok.Getter;

public class Chapter extends ChapterTemplate{

	@Getter
	private List<SubChapter> subChapters;
	
	public Chapter(String id, String title) {
		super(id, title);
		this.headline = id + ". " + title;
	}
	
	public void addSubChapter(SubChapter subChapter) {
		if(subChapters==null) {
			subChapters = new ArrayList<SubChapter>();
		}
		subChapters.add(subChapter);
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws DocumentException, MalformedURLException, IOException {
		yPosition = pdfAdder.addPagebreak(document);
		yPosition = pdfAdder.addText(document, pdfContentByte, getHeadline(), Properties.chapter_1, yPosition, false);
		yPosition = super.add(document, pdfContentByte, pdfAdder, yPosition);
		if(subChapters!=null) {
			for(SubChapter subChapter: subChapters) {
				yPosition = subChapter.add(document, pdfContentByte, pdfAdder, yPosition);
			}
		}
		return yPosition;
	}
	
}