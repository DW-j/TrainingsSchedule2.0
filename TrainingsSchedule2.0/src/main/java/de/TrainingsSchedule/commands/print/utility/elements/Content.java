package de.TrainingsSchedule.commands.print.utility.elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.utility.files.FileReader;
import lombok.Getter;


public class Content {

	@Getter
	private List<Chapter> chapters;
	
	public void create() throws FileNotFoundException {
		chapters = new ArrayList<Chapter>();
		
		Chapter chapterDescription = new Chapter(createChapterId(), "Description", FileReader.getInstance().readTxt("pdfDescription"), null, null, null, null);
		chapters.add(chapterDescription);
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws MalformedURLException, DocumentException, IOException {
		if(chapters!=null) {
			for(Chapter chapter: chapters) {
				yPosition = chapter.add(document, pdfContentByte, pdfAdder, yPosition);
			}
		}
		return yPosition;
	}
	
	private int createChapterId() {
		return chapters.size()+1;
	}
	
	private double createSubChapterId(Chapter chapter) {
		return chapter.getId()+((chapter.getSubChapters().size()+1)/10);
	}
	
	
}