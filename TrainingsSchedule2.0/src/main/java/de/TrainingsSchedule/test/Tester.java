package de.TrainingsSchedule.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import de.TrainingsSchedule.commands.print.utility.other.ChartBuilder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;
import de.TrainingsSchedule.elements.main.TrainingsSchedule;
import de.TrainingsSchedule.utility.files.FileReader;

public class Tester {
	
	public static void main(String[] args)  {
		try {
			TrainingsSchedule trainingsSchedule = (TrainingsSchedule) FileReader.getInstance().readXml("trainingsschedule", TrainingsSchedule.class);
			
			List<Date> dates = new ArrayList<Date>();
			List<Double> data1 = new ArrayList<Double>(), data2 = new ArrayList<Double>();
			trainingsSchedule.getPlan().getDays().stream().forEach(d -> dates.add(d.getDate()));
			trainingsSchedule.getPlan().getDays().stream().forEach(d -> data1.add((double)d.getTime()));
			trainingsSchedule.getPlan().getDays().stream().forEach(d -> data2.add(d.getWeight()));
			
			ChartBuilder chartBuilder = new ChartBuilder();
//			chartBuilder.createLineChartTest("Test2", Properties.chart_1, dates, data1, "Time", data2, "Weight");
			
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
		
	}

}