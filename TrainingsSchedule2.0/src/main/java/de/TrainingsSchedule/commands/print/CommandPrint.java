package de.TrainingsSchedule.commands.print;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.xml.sax.SAXException;

import de.TrainingsSchedule.commands.print.elements.main.Document;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileWriter;

public class CommandPrint {

	public String print(TrainingsSchedule trainingsSchedule) throws JAXBException, SAXException, IOException, TransformerException {
	
		Document document = new Document(trainingsSchedule);
		document.create();	 
		PDDocument pdfContent = document.getDocument();

		FileWriter.getInstance().writePdf("TrainingsSchedule", pdfContent);
		
		return "PDF created successfully.";
	}
	
}