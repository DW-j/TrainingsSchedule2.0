package de.TrainingsSchedule.commands.print.utility.elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.other.Table;
import lombok.Getter;

public class SubChapter {

	@Getter
	private String title;
	@Getter
	private double id;
	private String text;
	private Table table;
	private File chart;
	private String chart_caption;
	
	float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws MalformedURLException, BadElementException, IOException, DocumentException {
		yPosition = pdfAdder.addText(document, pdfContentByte, String.format("%d. %s", id, title), Properties.chapter_1, yPosition, false);
		if(text!=null) {
			yPosition = pdfAdder.addText(document, pdfContentByte, text, Properties.text_1, yPosition, false);
		}
		if(table!=null) {
			yPosition = pdfAdder.addTable(document, pdfContentByte, table, Properties.table_1, yPosition);
		}
		if(chart!=null) {
			yPosition = pdfAdder.addImage(document, pdfContentByte, FileReader.getInstance().getImage(chart), chart_caption, Properties.chart_1, yPosition);
		}
		return yPosition;
	}

}