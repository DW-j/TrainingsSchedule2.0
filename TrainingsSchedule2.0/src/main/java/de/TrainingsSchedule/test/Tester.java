package de.TrainingsSchedule.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import de.TrainingsSchedule.commands.print.elements.content.utility.tables.TableBuilder;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.other.Table;

public class Tester {

	public static void main(String[] args) throws IOException, JAXBException {
		TrainingsSchedule trainingsSchedule = (TrainingsSchedule) FileReader.getInstance().readXml("trainingsschedule", TrainingsSchedule.class);
		TableBuilder tableBuilder = new TableBuilder();
		Table table = trainingsSchedule.getPlanTemplate().getDayTemplates().get(0).toTable();
//		tableBuilder.createTable(table.getHeader(), table.getContent());
	}

}