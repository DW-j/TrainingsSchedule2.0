package de.TrainingsSchedule.test;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Tester {

	public static void main(String[] args) throws IOException {
		String filePath = "C:\\Users\\wittn\\workspaces\\git\\TrainingsSchedule2.0\\TrainingsSchedule2.0\\src\\main\\java\\de\\TrainingsSchedule\\files\\pdf\\TrainingsSchedule.pdf";
		PDDocument document = new PDDocument();
	       
	      //Retrieving the pages of the document 
	      PDPage page = new PDPage();
	      document.addPage(page);
	      PDPageContentStream contentStream = new PDPageContentStream(document, page);
	      
	      //Begin the Content stream 
	      contentStream.beginText(); 
	       
	      //Setting the font to the Content stream  
	      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

	      //Setting the position for the line 
	      contentStream.newLineAtOffset(25, 500);

	      String text = "This is the sample document and we are adding content to it.";

	      //Adding text in the form of string 
	      contentStream.showText(text);      

	      //Ending the content stream
	      contentStream.endText();

	      System.out.println("Content added");

	      //Closing the content stream
	      contentStream.close();

	      //Saving the document
	      document.save(new File(filePath));

	      //Closing the document
	      document.close();
		
	}

}