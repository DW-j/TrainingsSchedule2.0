package de.TrainingsSchedule.commands.print.command;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.TrainingsSchedule.commands.print.utility.elements.other.Content;
import de.TrainingsSchedule.commands.print.utility.elements.other.ContentTable;
import de.TrainingsSchedule.commands.print.utility.elements.other.Headpage;
import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileWriter;

public class CommandPrint {

	public String print(TrainingsSchedule trainingsSchedule) throws DocumentException, MalformedURLException, IOException {
		
		Headpage headpage = new Headpage();
		ContentTable contentTable = new ContentTable();
		Content content = new Content();
		headpage.create();
		content.create(trainingsSchedule);
		contentTable.create(content.getChapters());
		
		Document document = new Document();
		PdfWriter pdfWriter = FileWriter.getInstance().writePdf("TrainingsSchedule", document);
		document.open();
		PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
		float yPosition = 0;
		
		PDFAdder pdfAdder = new PDFAdder();
		yPosition = headpage.add(document, pdfContentByte, pdfAdder, yPosition);
		yPosition = contentTable.add(document, pdfContentByte, pdfAdder, yPosition);
		yPosition = content.add(document, pdfContentByte, pdfAdder, yPosition);
		
		document.close();
		return "PDF created succesfully.";
	}

}