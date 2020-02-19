package de.TrainingsSchedule.commands.print.utility.elements.chapters;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;

public class SubSubChapter extends ChapterTemplate {

	public SubSubChapter(String id, String title) {
		super(id, title);
	}

	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws MalformedURLException, BadElementException, IOException, DocumentException {
		yPosition = pdfAdder.addText(document, pdfContentByte, getHeadline(), Properties.chapter_3, yPosition, false);
		yPosition = super.add(document, pdfContentByte, pdfAdder, yPosition);
		return yPosition;
	}
	
}