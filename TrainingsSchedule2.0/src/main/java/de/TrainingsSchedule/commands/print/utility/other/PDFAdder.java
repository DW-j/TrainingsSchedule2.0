package de.TrainingsSchedule.commands.print.utility.other;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import de.TrainingsSchedule.commands.print.utility.properties.Property;
import de.TrainingsSchedule.utility.other.Table;

public class PDFAdder {

	public float addText(Document document, PdfContentByte pdfContentByte, String text, Property property, float yPosition, boolean centered) {
		List<String> lines = splitText(text, property, document.getPageSize().getWidth());

		float xPosition = property.getMargin_left();
		if(centered) {
			xPosition += (document.getPageSize().getWidth() / 2F) - (property.getFont().getBaseFont().getWidthPoint(lines.get(0), property.getFont().getSize()) / 2F);
		}
		yPosition += property.getMargin_top();
		
		pdfContentByte.beginText();
		pdfContentByte.setFontAndSize(property.getFont().getBaseFont(), property.getFont().getSize());
		for(String line: lines) {
			pdfContentByte.setTextMatrix(xPosition, getYPosition(document, yPosition, property.getText_height()));
			pdfContentByte.showText(line);
			yPosition += property.getText_height() + property.getLine_space();
		}
		pdfContentByte.endText();
		
		return yPosition + property.getMargin_bottom();
	}
	
	public float getTextHeight(Document document, String text, Property property) {
		float height = property.getMargin_top() + property.getMargin_bottom();
		int lineNumber = splitText(text, property, document.getPageSize().getWidth()).size();
		height += lineNumber*(property.getText_height()+property.getLine_space());
		return height;
	}
	
	public float addImage(Document document, PdfContentByte pdfContentByte, Image image, String caption, Property property, float yPosition) throws MalformedURLException, IOException, DocumentException {
		if(caption!=null) {
			yPosition = addText(document, pdfContentByte, caption, property, yPosition, false) - property.getMargin_bottom() + property.getLine_space();
		}
		
		image.setAbsolutePosition(property.getMargin_left(), getYPosition(document, yPosition, image.getHeight()));
		document.add(image);
		
		return yPosition + property.getMargin_bottom() + image.getHeight();
	}
	
	public float getChartHeight(Document document, Image image, String caption, Property property) {
		float height = property.getMargin_top() + property.getMargin_bottom();
		if(caption!=null) {
			height += getTextHeight(document, caption, property);
		}
		height += image.getHeight();
		return height;
	}
	
	public float addTable(Document document, PdfContentByte pdfContentByte, Table table, String caption, Property property, float yPosition) throws DocumentException {
		if(caption!=null) {
			yPosition = addText(document, pdfContentByte, caption, property, yPosition, false) - property.getMargin_bottom() + property.getLine_space();
		}		
		yPosition += property.getMargin_top();
		
		PdfPTable pdfPTable = createTable(document, table, property);
		
		float tableHeight = pdfPTable.getTotalHeight();
		pdfPTable.writeSelectedRows(0, -1, property.getMargin_left(), getYPosition(document, yPosition, 0), pdfContentByte);
		return yPosition + property.getMargin_bottom() + tableHeight;
	}
	
	public float getTableHeight(Document document, Table table, String caption, Property property) {
		float height = property.getMargin_top() + property.getMargin_bottom();
		if(caption!=null) {
			height += getTextHeight(document, caption, property);
		}
		height += createTable(document, table, property).getTotalHeight();
		return height;
	}
	
	private PdfPTable createTable(Document document, Table table, Property property) {
		float[] columnWidth = ArrayUtils.toPrimitive(table.getIndents().toArray(new Float[table.getIndents().size()]));
		PdfPTable pdfPTable = new PdfPTable(columnWidth);	
		pdfPTable.setTotalWidth(document.getPageSize().getWidth() - property.getMargin_left() - property.getMargin_right());
		
		List<String> header = table.getHeader();
		for(String cellContent: header) {
			PdfPCell cell = new PdfPCell(new Phrase(cellContent, property.getFont()));
			cell.setBackgroundColor(property.getHeaderColor());
			pdfPTable.addCell(cell);
		}
		List<List<String>> content = table.getContent();
		for(int i=0; i<content.get(0).size(); i++) {
			for(int j=0; j<content.size(); j++) {
				String cellContent = content.get(j).get(i);
				pdfPTable.addCell(new PdfPCell(new Phrase(cellContent, property.getFont())));
			}
		}
		return pdfPTable;
	}
	
	public float addPagebreak(Document document) {
		document.newPage();
		return 20;
	}

	private List<String> splitText(String text, Property property, float pageWidth){
		List<String> lines = new ArrayList<String>();
		List<String> words = Arrays.asList(text.split(" "));
		float maxWidth = pageWidth - property.getMargin_left() - property.getMargin_right();
		
		lines.add("");
		for(String word: words) {
			String line = String.format("%s %s", lines.get(lines.size()-1), word);
			if(property.getFont().getBaseFont().getWidthPoint(line, property.getFont().getSize())>=maxWidth) {
				lines.add(word);
			}else {
				lines.set(lines.size()-1, line);
			}
		}
		
		return lines;
	}
	
	private float getYPosition(Document document, float yPosition, float text_height) {
		return document.getPageSize().getHeight() - yPosition - text_height;
	}
	
}