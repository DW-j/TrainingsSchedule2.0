package de.TrainingsSchedule.commands.print.command;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.files.FileWriter;

public class CommandPrint {

	public String print(TrainingsSchedule trainingsSchedule) throws DocumentException, MalformedURLException, IOException {
		
		Document document = new Document();
		PdfWriter pdfWriter = FileWriter.getInstance().writePdf("TrainingsSchedule", document);
		document.open();
		PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
		
		PDFAdder pdfAdder = new PDFAdder();
		float yPosition = pdfAdder.addText(document, pdfContentByte, FileReader.getInstance().readTxt("pdfDescription"), Properties.text_1, 0, false);
		yPosition = pdfAdder.addImage(document, pdfContentByte, FileReader.getInstance().getImage("Jokah_Profilbild"), "Test_Chart", Properties.chart_1, yPosition);
		yPosition = pdfAdder.addTable(document, pdfContentByte, trainingsSchedule.getPlan().getDays().get(0).toTable(), Properties.table_1, yPosition);
		document.close();
		return "PDF created succesfully.";
	}

}