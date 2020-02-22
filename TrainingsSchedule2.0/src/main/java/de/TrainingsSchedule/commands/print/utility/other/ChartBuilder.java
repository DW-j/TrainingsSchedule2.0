package de.TrainingsSchedule.commands.print.utility.other;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import de.TrainingsSchedule.commands.print.utility.properties.Property;
import de.TrainingsSchedule.utility.files.FileWriter;
import lombok.Getter;

public class ChartBuilder {

	@Getter
	private File chart;
	
	public void createLineChart(String fileName, Property property, List<Date> dates, List<Double> data1, String data1_title) throws IOException {
		JFreeChart freeChart = createSimpleChart(dates, data1, data1_title);		
	    chart = FileWriter.getInstance().saveChart(fileName, freeChart, property.getChartHeight(), property.getChartWidth());
	}	
	
	public void createLineChart(String fileName, Property property, List<Date> dates, List<Double> data1, String data1_title, List<Double> data2, String data2_title) throws IOException {
		
		JFreeChart freeChart = createSimpleChart(dates, data1, data1_title);
		
		TimeSeries series2 = new TimeSeries(data2_title);
		for(int i=0; i<dates.size(); i++) {
			if(data1.get(i)!=null) {
				series2.add(new Day(dates.get(i)), data2.get(i));
			}
		}
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		dataset2.addSeries(series2);
		
		XYPlot plot = freeChart.getXYPlot();
		NumberAxis numberAxis = new NumberAxis(data2_title);
		numberAxis.setAutoRangeIncludesZero(false);
		plot.setRangeAxis(1, numberAxis);
		plot.setDataset(1, dataset2);
        plot.mapDatasetToRangeAxis(1, 1);
                
        StandardXYItemRenderer renderer = new StandardXYItemRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesStroke(0, new BasicStroke(2f));
        renderer.setBaseShapesVisible(true);
        renderer.setDefaultToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        plot.setRenderer(1, renderer);
                
	    chart = FileWriter.getInstance().saveChart(fileName, freeChart, property.getChartHeight(), property.getChartWidth());
	}
	
	private JFreeChart createSimpleChart(List<Date> dates, List<Double> data1, String data1_title) {
		TimeSeries series1 = new TimeSeries(data1_title);
		for(int i=0; i<dates.size(); i++) {
			if(data1.get(i)!=null) {
				series1.add(new Day(dates.get(i)), data1.get(i));
			}
		}
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		dataset1.addSeries(series1);
		
		JFreeChart freeChart = ChartFactory.createTimeSeriesChart("", "Date", data1_title, dataset1);
		
		XYPlot plot = freeChart.getXYPlot();
		NumberAxis numberAxis = new NumberAxis(data1_title);
		numberAxis.setAutoRangeIncludesZero(false);
		plot.setRangeAxis(0, numberAxis);
		plot.setDataset(0, dataset1);
        plot.mapDatasetToRangeAxis(1, 1);
        
        StandardXYItemRenderer renderer = new StandardXYItemRenderer();
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesStroke(0, new BasicStroke(2));
        renderer.setBaseShapesVisible(true);
        renderer.setDefaultToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        plot.setRenderer(0, renderer);
		
		DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
	    dateAxis.setDateFormatOverride(new SimpleDateFormat("MMMM-yyyy"));

		return freeChart;
	}	
	
}