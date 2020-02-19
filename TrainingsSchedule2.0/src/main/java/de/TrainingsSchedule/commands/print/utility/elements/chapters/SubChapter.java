package de.TrainingsSchedule.commands.print.utility.elements.chapters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;
import lombok.Getter;

public class SubChapter extends ChapterTemplate{

	@Getter
	private List<SubSubChapter> subSubChapters;
	
	public SubChapter(String id, String title) {
		super(id, title);
	}
	
	public void addSubSubChapter(SubSubChapter subChapter) {
		if(subSubChapters==null) {
			subSubChapters = new ArrayList<SubSubChapter>();
		}
		subSubChapters.add(subChapter);
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws MalformedURLException, BadElementException, IOException, DocumentException {
		yPosition = pdfAdder.addText(document, pdfContentByte, getHeadline(), Properties.chapter_2, yPosition, false);
		yPosition = super.add(document, pdfContentByte, pdfAdder, yPosition);
		if(subSubChapters!=null) {
			for(SubSubChapter subSubChapter: subSubChapters) {
				if(yPosition+subSubChapter.getHeight(document, pdfAdder)>document.getPageSize().getHeight()) {
					yPosition = pdfAdder.addPagebreak(document);
				}
				yPosition = subSubChapter.add(document, pdfContentByte, pdfAdder, yPosition);
			}
		}
		return yPosition;
	}
	
	public float getHeight(Document document, PDFAdder pdfAdder) throws BadElementException, MalformedURLException, IOException {
		float height = pdfAdder.getTextHeight(document, getHeadline(), Properties.chapter_2);
		height += super.getHeight(document, pdfAdder);
		return height;
	}

}