package de.TrainingsSchedule.commands.print.elements.content;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import de.TrainingsSchedule.commands.print.utility.PDFWriter;
import de.TrainingsSchedule.commands.print.utility.Properties;
import de.TrainingsSchedule.utility.other.Table;
import lombok.Getter;
import lombok.Setter;

public class SubChapter {

	@Getter
	private double id;
	@Getter
	private String title;
	@Setter
	private String text;
	@Setter
	private Table table;
	@Setter
	private File image;
	
	public SubChapter(double id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public float create(PDPageContentStream contentStream, PDDocument document, PDPage page, float yPosition, PDFWriter pdfWriter) throws IOException {
		yPosition = pdfWriter.addText(String.format("%.2f %s", id, title), Properties.chapter_2, contentStream, page, false, yPosition);
		if(text!=null) {
			yPosition = pdfWriter.addText(text, Properties.text_1, contentStream, page, false, yPosition);
		}
		if(table!=null) {
			yPosition = pdfWriter.addTable(table, Properties.table_1, document, page, yPosition);
		}
		return yPosition;
	}
	
}