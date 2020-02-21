package de.TrainingsSchedule.commands.print.utility.other;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.optim.nonlinear.vector.Weight;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import de.TrainingsSchedule.commands.print.utility.properties.Property;
import de.TrainingsSchedule.utility.files.FileWriter;
import de.TrainingsSchedule.utility.other.TimeFormat;
import lombok.Getter;

public class ChartBuilder {

	@Getter
	private File chart;
	
	public void createLineChart(String fileName, Property property, List<Date> dates, List<Double> data1, String data1_title) throws IOException {
		TimeSeries series1 = new TimeSeries(data1_title);
		for(int i=0; i<dates.size(); i++) {
			if(data1.get(i)!=null) {
				series1.add(new Day(dates.get(i)), data1.get(i));
			}
		}
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		dataset1.addSeries(series1);
		
	    XYPlot plot = new XYPlot();
	    plot.setRangeAxis(0, new NumberAxis(data1_title));
	    plot.setDataset(0, dataset1);
	    plot = setPlot(plot);

	    JFreeChart freeChart = new JFreeChart(plot);
	    chart = FileWriter.getInstance().saveChart(fileName, freeChart, property.getChartHeight(), property.getChartWidth());
	}	

	public void createLineChart(String fileName, Property property, List<Date> dates, List<Double> data1, String data1_title, List<Double> data2, String data2_title) throws IOException {
		TimeSeries series1 = new TimeSeries(data1_title), series2 = new TimeSeries(data2_title);
		for(int i=0; i<dates.size(); i++) {
			if(data1.get(i)!=null) {
				series1.add(new Day(dates.get(i)), data1.get(i));
				series2.add(new Day(dates.get(i)), data2.get(i));
			}
		}
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		dataset1.addSeries(series1);
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		dataset2.addSeries(series2);
		
	    XYPlot plot = new XYPlot();
	    plot.setRangeAxis(0, new NumberAxis(data1_title));
	    plot.setDataset(0, dataset1);
	    plot.setRangeAxis(1, new NumberAxis(data2_title));
	    plot.setDataset(1, dataset2);
	    plot = setPlot(plot);  

	    JFreeChart freeChart = new JFreeChart(plot);
	    chart = FileWriter.getInstance().saveChart(fileName, freeChart, property.getChartHeight(), property.getChartWidth());
	}
	
	private XYPlot setPlot(XYPlot plot) {
		plot.setRenderer(0, new XYSplineRenderer());
	    XYSplineRenderer splinerenderer = new XYSplineRenderer();
	    splinerenderer.setSeriesFillPaint(0, Color.BLUE);
	    plot.setRenderer(1, splinerenderer);
	    plot.setDomainAxis(new DateAxis("Date"));
	    DateAxis dateaxis = (DateAxis)plot.getDomainAxis();
	    dateaxis.setDateFormatOverride(TimeFormat.getDateformat());

	    plot.mapDatasetToRangeAxis(0, 0);
	    plot.mapDatasetToRangeAxis(1, 1);   
	    return plot;
	}
	
}