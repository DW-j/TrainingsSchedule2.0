package de.TrainingsSchedule.commands.print.elements.main;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import de.TrainingsSchedule.commands.print.elements.content.Content;
import de.TrainingsSchedule.commands.print.elements.headpage.ContentTable;
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
		
		Headpage headpage = new Headpage();
		headpage.create();
		
		Content content = new Content();
		content.create(trainingsSchedule);
		
		ContentTable contentTable = new ContentTable();
		contentTable.create(content, document);
		
		document = new PDDocument();
		document = headpage.print(document);
		document = contentTable.print(document);
		document = content.print(document);
	}

}