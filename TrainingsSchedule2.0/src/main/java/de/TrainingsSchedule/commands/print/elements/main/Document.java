package de.TrainingsSchedule.commands.print.elements.main;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import de.TrainingsSchedule.commands.print.elements.content.Content;
import de.TrainingsSchedule.commands.print.elements.contenttable.ContentTable;
import de.TrainingsSchedule.commands.print.elements.headpage.Headpage;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import lombok.Getter;

public class Document {

	private TrainingsSchedule trainingsSchedule;
	@Getter
	private PDDocument document;
	
	public Document(TrainingsSchedule trainingsSchedule) {
		this.trainingsSchedule = trainingsSchedule;
	}
	
	public void create() throws IOException{
		
		document = new PDDocument();
		
		Headpage headpage = new Headpage();
		document = headpage.create(document);
		
		Content content = new Content();
		document = content.create(trainingsSchedule, document);
		
		ContentTable contentTable = new ContentTable();
		document = contentTable.create(content, document);
	}

}