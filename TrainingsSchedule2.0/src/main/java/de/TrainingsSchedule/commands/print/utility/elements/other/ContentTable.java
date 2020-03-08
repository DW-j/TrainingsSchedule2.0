package de.TrainingsSchedule.commands.print.utility.elements.other;

import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.elements.chapters.Chapter;
import de.TrainingsSchedule.commands.print.utility.elements.chapters.SubChapter;
import de.TrainingsSchedule.commands.print.utility.elements.chapters.SubSubChapter;
import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;

public class ContentTable {

	private List<Chapter> chapters;
	private String title;
	
	public void create(List<Chapter> chapters) {
		this.chapters = chapters;
		title = "Table of contents";
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) {
		yPosition = pdfAdder.addText(document, pdfContentByte, title, Properties.chapter_1, yPosition, false);
		if(chapters!=null) {
			for(Chapter chapter: chapters) {
				float chapterHeight = pdfAdder.getTextHeight(document, chapter.getHeadline(), Properties.contenttable_2);
				float subChaptersHeight = chapter.getSubChapters()!=null?pdfAdder.getTextHeight(document, chapter.getSubChapters().get(0).getHeadline(), Properties.contenttable_2):0;
				float subSubChaptersHeight = 0;
				if(chapter.getSubChapters()!=null && chapter.getSubChapters().get(0).getSubSubChapters()!=null) {
					for(SubSubChapter subSubChapter: chapter.getSubChapters().get(0).getSubSubChapters()) {
						subSubChaptersHeight += pdfAdder.getTextHeight(document, subSubChapter.getHeadline(), Properties.contenttable_3);
					}
				}
				if(yPosition + chapterHeight + subChaptersHeight + subSubChaptersHeight > document.getPageSize().getHeight()) {
					yPosition = pdfAdder.addPagebreak(document);
				}
				yPosition = pdfAdder.addText(document, pdfContentByte, chapter.getHeadline(),Properties.contenttable_1, yPosition, false);
				if(chapter.getSubChapters()!=null) {
					for(SubChapter subChapter: chapter.getSubChapters()) {
						float subChapterHeight = pdfAdder.getTextHeight(document, subChapter.getHeadline(), Properties.contenttable_2);
						subSubChaptersHeight = subChapter.getSubSubChapters()!=null? pdfAdder.getTextHeight(document, subChapter.getSubSubChapters().get(0).getHeadline(), Properties.contenttable_3)*subChapter.getSubSubChapters().size():0;
						if(yPosition + subChapterHeight + subSubChaptersHeight > document.getPageSize().getHeight()) {
							yPosition = pdfAdder.addPagebreak(document);
						}
						yPosition = pdfAdder.addText(document, pdfContentByte, subChapter.getHeadline(), Properties.contenttable_2, yPosition, false);
						if(subChapter.getSubSubChapters()!=null) {
							for(SubSubChapter subSubChapter: subChapter.getSubSubChapters()) {
								yPosition = pdfAdder.addText(document, pdfContentByte, subSubChapter.getHeadline(), Properties.contenttable_3, yPosition, false);
							}
						}
					}
				}
			}
		}
		yPosition = pdfAdder.addPagebreak(document);
		return yPosition;
	}
	
}
