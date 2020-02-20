package de.TrainingsSchedule.commands.print.utility.elements.chapters;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;

import de.TrainingsSchedule.commands.print.utility.other.PDFAdder;
import de.TrainingsSchedule.commands.print.utility.properties.Properties;
import de.TrainingsSchedule.commands.print.utility.properties.Property;
import de.TrainingsSchedule.utility.files.FileReader;
import de.TrainingsSchedule.utility.other.Table;
import lombok.Getter;
import lombok.Setter;

public class ChapterTemplate {

	@Getter
	protected String id, title, headline;
	@Setter
	private List<String> texts;
	private List<Table> tables;
	private List<String> table_captions;
	private List<File> charts;
	private List<String> chart_captions;
	
	public ChapterTemplate(String id, String title) {
		this.id = id;
		this.title = title;
		this.headline = id + " " + title;
	}
	
	public void addText(String text) {
		if(texts==null) {
			texts = new ArrayList<String>();
		}
		texts.add(text);
	}
	
	public void addTable(Table table, String table_caption) {
		if(tables==null) {
			tables = new ArrayList<Table>();
		}
		tables.add(table);
		if(table_captions==null) {
			table_captions = new ArrayList<String>();
		}
		table_captions.add(table_caption);
	}
	
	public void addChart(File chart, String chart_caption) {
		if(charts==null) {
			charts = new ArrayList<File>();
		}
		charts.add(chart);
		if(chart_captions==null) {
			chart_captions = new ArrayList<String>();
		}
		chart_captions.add(chart_caption);
	}

	public void setTables(List<Table> tables, List<String> table_captions) {
		this.tables = tables;
		this.table_captions = table_captions;
	}

	public void setCharts(List<File> charts, List<String> chart_captions) {
		this.charts = charts;
		this.chart_captions = chart_captions;
	}
	
	public float add(Document document, PdfContentByte pdfContentByte, PDFAdder pdfAdder, float yPosition) throws DocumentException, MalformedURLException, IOException {
		if(texts!=null) {
			for(String text: texts) {
				yPosition = testYPosition(document, pdfAdder, yPosition, pdfAdder.getTextHeight(document, text, Properties.text_1), Properties.text_1);
				yPosition = pdfAdder.addText(document, pdfContentByte, text, Properties.text_1, yPosition, false);
			}
		}
		if(tables!=null) {
			for(int i=0; i<tables.size(); i++) {
				yPosition = testYPosition(document, pdfAdder, yPosition, pdfAdder.getTableHeight(document, tables.get(i), table_captions.get(i), Properties.table_1), Properties.table_1);
				yPosition = pdfAdder.addTable(document, pdfContentByte, tables.get(i), table_captions.get(i), Properties.table_1, yPosition);
			}
		}
		if(charts!=null) {
			for(int i=0; i<charts.size(); i++) {
				yPosition = testYPosition(document, pdfAdder, yPosition, pdfAdder.getChartHeight(document, FileReader.getInstance().getImage(charts.get(i)), chart_captions.get(i), Properties.chart_1), Properties.chart_1);
				yPosition = pdfAdder.addImage(document, pdfContentByte, FileReader.getInstance().getImage(charts.get(i)), chart_captions.get(i), Properties.chart_1, yPosition);
				charts.get(i).delete();
			}
		}
		return yPosition;
	}
	
	private float testYPosition(Document document, PDFAdder pdfAdder, float yPosition, float itemHeight, Property property) {
		if(yPosition+property.getMargin_bottom()+itemHeight > document.getPageSize().getHeight()) {
			return pdfAdder.addPagebreak(document);
		}
		return yPosition;
	}

	public float getHeight(Document document, PDFAdder pdfAdder) throws MalformedURLException, IOException, DocumentException {
		float height = 0;
		if(texts!=null) {
			for(String text: texts) {
				height += pdfAdder.getTextHeight(document, text, Properties.text_1);
			}
		}
		if(tables!=null) {
			for(int i=0; i<tables.size(); i++) {
				height += pdfAdder.getTableHeight(document, tables.get(i), table_captions.get(i), Properties.table_1);
			}
		}
		if(charts!=null) {
			for(int i=0; i<charts.size(); i++) {
				height += pdfAdder.getChartHeight(document, FileReader.getInstance().getImage(charts.get(i)), chart_captions.get(i), Properties.table_1);
			}
		}
		return height;
	}
	
}