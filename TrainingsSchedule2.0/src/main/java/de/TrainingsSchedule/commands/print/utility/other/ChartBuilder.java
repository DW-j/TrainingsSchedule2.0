package de.TrainingsSchedule.commands.print.utility.other;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import de.TrainingsSchedule.utility.files.FileWriter;
import lombok.Getter;

public class ChartBuilder {

	@Getter
	private File chart;
	
	public void createLineChart(String title, String fileName, int height, int width, List<String> data1, String data1_title, List<Double> data2, String data2_title) throws IOException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for(int i=0; i<data1.size(); i++) {
			dataSet.addValue(data2.get(i), data1_title, data1.get(i));
		}
		JFreeChart lineChart = ChartFactory.createLineChart(title, data1_title, data2_title, dataSet);
		chart = FileWriter.getInstance().saveChart(fileName, lineChart, height, width);
	}
	
	public void deleteFile() {
		chart.delete();
	}
	
}