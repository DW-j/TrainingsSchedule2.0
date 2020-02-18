package de.TrainingsSchedule.commands.print.utility.elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.ChapterBuilder;
import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.specifics.Plan;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import lombok.Getter;


public class Content {

	@Getter
	private List<Chapter> chapters;
	
	public void create(TrainingsSchedule trainingsSchedule) throws FileNotFoundException {
		PlanTemplate planTemplate = trainingsSchedule.getPlanTemplate();
		Plan plan = trainingsSchedule.getPlan();
		
		ChapterBuilder chapterBuilder = new ChapterBuilder();
		chapters = new ArrayList<Chapter>();
		
		chapters.add(chapterBuilder.getChapterDescription(chapters));
		chapters.add(chapterBuilder.getChapterTemplate(chapters, planTemplate));
		chapters.add(chapterBuilder.getChapterPlan(chapters, plan.getDays()));
		chapters.addAll(chapterBuilder.getChaptersDays(chapters, planTemplate, plan.getDays()));
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws MalformedURLException, DocumentException, IOException {
		if(chapters!=null) {
			for(Chapter chapter: chapters) {
				yPosition = chapter.add(document, pdfContentByte, pdfAdder, yPosition);
			}
		}
		return yPosition;
	}
	
}