package de.TrainingsSchedule.commands.print.utility.elements;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;

public class Headpage {

	private String title1, title2, title3;
	
	public void create() {
		title1 = "TrainingsSchedule";
		title2 = "A summary of your personal TrainingsSchedule";
		title3 = "by DW-j";
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) {
		yPosition = pdfAdder.addText(document, pdfContentByte, title1, Properties.title_1, yPosition, true);
		yPosition = pdfAdder.addText(document, pdfContentByte, title2, Properties.title_2, yPosition, true);
		yPosition = pdfAdder.addText(document, pdfContentByte, title3, Properties.title_3, yPosition, true);
		return yPosition;
	}
	
}