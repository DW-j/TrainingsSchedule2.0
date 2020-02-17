package de.TrainingsSchedule.commands.print.elements.headpage;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import de.TrainingsSchedule.commands.print.utility.Properties;
import de.TrainingsSchedule.commands.print.utility.PDFWriter;

public class Headpage {
	
	String title1, title2, title3;
	
	public void create() throws IOException {
		title1 = "TrainingsSchedule";
		title2 = "A summary of your personal TrainingsSchedule";
		title3 = "by DW-j";
	}
	
	public PDDocument print(PDDocument document) throws IOException {
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		PDFWriter pdfWriter = new PDFWriter();
		
		float yPosition = 45f;
		yPosition = pdfWriter.addText(title1, Properties.title_1, contentStream, page, true, yPosition);
		yPosition = pdfWriter.addText(title2, Properties.title_2, contentStream, page, true, yPosition);
		yPosition = pdfWriter.addText(title3, Properties.title_3, contentStream, page, true, yPosition);

	    contentStream.close();
	    
	    return document;
	}
	
}