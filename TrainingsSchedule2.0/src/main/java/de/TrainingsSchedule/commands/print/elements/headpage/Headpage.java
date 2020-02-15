package de.TrainingsSchedule.commands.print.elements.headpage;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import de.TrainingsSchedule.commands.print.utility.Fonts;
import de.TrainingsSchedule.commands.print.utility.PDFWriter;

public class Headpage {
	
	public PDDocument create(PDDocument document) throws IOException {
		
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		PDFWriter pdfWriter = new PDFWriter();
		
		String title1 = "TrainingsSchedule";
		String title2 = "A summary of your personal TrainingsSchedule";
		String title3 = "by DW-j";
		
		pdfWriter.addCenteredText(title1, Fonts.title_1_font, Fonts.title_1_size, contentStream, page, 45);
		pdfWriter.addCenteredText(title2, Fonts.title_2_font, Fonts.title_2_size, contentStream, page, 50);
		pdfWriter.addCenteredText(title3, Fonts.title_3_font, Fonts.title_3_size, contentStream, page, 52);
	    
	    contentStream.close();
	    
		return document;
	}
	
}