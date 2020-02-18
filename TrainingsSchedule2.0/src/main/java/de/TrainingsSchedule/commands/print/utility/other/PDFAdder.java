package de.TrainingsSchedule.commands.print.utility.other;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.properties.Property;

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
	
	public float addImage(Document document, PdfContentByte pdfContentByte, Image image, String caption, Property property, float yPosition) throws MalformedURLException, IOException, DocumentException {
		
		addText(document, pdfContentByte, caption, property, yPosition, false);
		
		image.setAbsolutePosition(property.getMargin_left(), getYPosition(document, yPosition, image.getScaledHeight()));
		document.add(image);
		
		return yPosition + property.getMargin_bottom() + image.getHeight();
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