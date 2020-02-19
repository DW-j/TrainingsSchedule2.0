package de.TrainingsSchedule.commands.print.utility.elements.other;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.elements.chapters.Chapter;
import de.TrainingsSchedule.commands.print.utility.other.ChapterBuilder;
import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.elements.specifics.Plan;
import de.TrainingsSchedule.elements.templates.PlanTemplate;
import lombok.Getter;


public class Content {

	@Getter
	private List<Chapter> chapters;
	
	public void create(TrainingsSchedule trainingsSchedule) throws IOException {
		PlanTemplate planTemplate = trainingsSchedule.getPlanTemplate();
		Plan plan = trainingsSchedule.getPlan();
		
		ChapterBuilder chapterBuilder = new ChapterBuilder();
		chapters = new ArrayList<Chapter>();
		
		chapters.add(chapterBuilder.getChapterDescription(chapters));
		chapters.add(chapterBuilder.getChapterTemplate(chapters, planTemplate));
		chapters.add(chapterBuilder.getChapterPlan(chapters, plan.getDays()));
		chapters.add(chapterBuilder.getChapterDays(chapters, planTemplate, plan.getDays()));
		chapters.add(chapterBuilder.getChapterExercises(chapters, plan.getDays()));
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws MalformedURLException, DocumentException, IOException {
		if(chapters!=null) {
			for(Chapter chapter: chapters) {
				float subChapterHeight = chapter.getSubChapters()!=null? chapter.getSubChapters().get(0).getHeight(document, pdfAdder):0;
				float subSubChapterHeight = subChapterHeight!=0? chapter.getSubChapters().get(0).getSubSubChapters()!=null? chapter.getSubChapters().get(0).getSubSubChapters().get(0).getHeight(document, pdfAdder): 0 :0;
				if(yPosition+chapter.getHeight(document, pdfAdder)+subChapterHeight+subSubChapterHeight >document.getPageSize().getHeight()) {
					yPosition = pdfAdder.addPagebreak(document);
				}
				yPosition = chapter.add(document, pdfContentByte, pdfAdder, yPosition);
			}
		}
		return yPosition;
	}
	
}