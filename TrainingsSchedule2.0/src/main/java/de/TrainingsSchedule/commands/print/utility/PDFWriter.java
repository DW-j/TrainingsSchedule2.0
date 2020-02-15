package de.TrainingsSchedule.commands.print.utility;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;

public class PDFWriter {

	public void addCenteredText(String text, PDFont font, int fontSize, PDPageContentStream content, PDPage page, float margin_top) throws IOException {
		content.setFont(font, fontSize);
	    content.beginText();

	    float pageCenter = getCenter(page);
	    float stringWidth = getStringWidth(text, font, fontSize);
	    float textX = pageCenter - stringWidth / 2F;
	    float textY = getY(page, margin_top);
	    content.setTextMatrix(Matrix.getTranslateInstance(textX, textY));
	    
	    content.showText(text);
	    content.endText();
	}
	
	private float getCenter(PDPage page){
		PDRectangle pageSize = page.getMediaBox();
	    float pageWidth = pageSize.getWidth();
	    return pageWidth / 2F;
	}
	
	private float getY(PDPage page, float margin_top) {
		PDRectangle pageSize = page.getMediaBox();
	    float pageHeight = pageSize.getHeight();
	    return pageHeight - (pageHeight / 100F ) * margin_top;
	}
	
	private float getStringWidth(String text, PDFont font, int fontSize) throws IOException {
	    return font.getStringWidth(text) * fontSize / 1000F;
	}
	
	public void addText(String text, PDFont font, int fontSize, PDPageContentStream content) {
		
	}
	
	public void addImage(File image, PDPageContentStream content) {
		
	}
	
}