package de.TrainingsSchedule.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import de.TrainingsSchedule.commands.print.utility.elements.chapters.Chapter;
import de.TrainingsSchedule.commands.print.utility.other.ChapterBuilder;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileReader;

public class Tester {
	
	public static void main(String[] args)  {
		try {
			TrainingsSchedule trainingsSchedule = (TrainingsSchedule) FileReader.getInstance().readXml("TrainingsSchedule", TrainingsSchedule.class);
			List<Chapter> chapters = new ArrayList<Chapter>();
			ChapterBuilder builder = new ChapterBuilder();
			builder.getChapterExercises(chapters, trainingsSchedule.getPlanTemplate().getDayTemplates(), trainingsSchedule.getPlan().getDays());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}