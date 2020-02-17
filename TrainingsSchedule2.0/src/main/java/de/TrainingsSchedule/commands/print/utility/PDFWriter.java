package de.TrainingsSchedule.commands.print.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;

import de.TrainingsSchedule.commands.print.elements.content.utility.tables.TableBuilder;
import de.TrainingsSchedule.utility.other.Table;

public class PDFWriter {
	
	public float addText(String text, Property property, PDPageContentStream contentStream, PDPage page, boolean centered, float yPosition) throws IOException {
		if(property.getMargin_top()!=0) {
			yPosition += property.getMargin_top();
		}
		List<String> lines = splitText(text, property, page.getMediaBox().getWidth());
		for(String line: lines) {
			float textY = getYPosition(page, yPosition);
			float textX = getXPosition(page, property.getMargin_sides());
			if(centered) {
				float pageCenter = getXPosition(page, 50F);
			    float stringWidth = getStringWidth(text, property.getFontType(), property.getFontSize());
			    textX = pageCenter - stringWidth / 2F;
			}
			
			yPosition = writeText(line, property.getFontType(), property.getFontSize(), contentStream, page, textX, textY, yPosition, property.getMargin_bottom());
		}
		return yPosition;
	}
	
	public float addTable(Table table, Property property, PDDocument document, PDPage page, float yPosition) throws IOException {
		TableBuilder tableBuilder = new TableBuilder();
		tableBuilder.createTable(table.getHeader(), table.getContent(), yPosition, getYPosition(page, property.getMargin_top()), getXPosition(page, property.getMargin_bottom()), getXPosition(page, property.getMargin_sides()), document, document.getPages().get(document.getNumberOfPages()-1), property.getFontType());
		return 0;
	}
	
	public void addImage(File image, PDPageContentStream content) {
		
	}
	
	private float writeText(String text, PDFont font, int fontSize, PDPageContentStream content, PDPage page, float textX, float textY, float yPosition, float margin_bottom) throws IOException {
		content.setFont(font, fontSize);
	    content.beginText();
	    content.setTextMatrix(Matrix.getTranslateInstance(textX, textY));
	    content.showText(text);
	    content.endText();
	    return yPosition + getStringHeight(page, font, fontSize) + margin_bottom;
	}
	
	private float getXPosition(PDPage page, float percentage){
		PDRectangle pageSize = page.getMediaBox();
	    float pageWidth = pageSize.getWidth();
	    return pageWidth / (100F / percentage);
	}
	
	private float getYPosition(PDPage page, float percentage) {
		PDRectangle pageSize = page.getMediaBox();
	    float pageHeight = pageSize.getHeight();
	    return pageHeight - (pageHeight / 100F ) * percentage;
	}
	
	private float getStringWidth(String text, PDFont font, int fontSize) throws IOException {
	    return font.getStringWidth(text) * fontSize / 1000F;
	}
	
	private float getStringHeight(PDPage page, PDFont font, int fontSize) throws IOException {
		float fontHeight =  font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
		float pageHeight = page.getMediaBox().getHeight();
		return fontHeight * 10F / pageHeight;
	}
	
	private List<String> splitText(String text, Property property, float pageWidth) throws IOException{
		List<String> lines = new ArrayList<String>();
		lines.add("");
		
		List<String> words = Arrays.asList(text.split(" "));
		float maxWidth = pageWidth - ((pageWidth/100*property.getMargin_sides())*2);
		
		while(words.size()>0) {
			String line = String.format("%s %s", lines.get(lines.size()-1), words.get(0));
			if(getStringWidth(line, property.getFontType(), property.getFontSize())>maxWidth) {
				lines.add(words.get(0));
			}else {
				lines.set(lines.size()-1, line);
			}
			words = words.subList(1, words.size());
		}
		
		return lines;
	}
	
}