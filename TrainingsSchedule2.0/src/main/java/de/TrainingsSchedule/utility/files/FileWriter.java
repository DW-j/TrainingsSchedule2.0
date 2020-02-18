package de.TrainingsSchedule.utility.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public class FileWriter {

	private final String filePath = "C:\\Users\\wittn\\workspaces\\git\\TrainingsSchedule2.0\\TrainingsSchedule2.0\\src\\main\\java\\de\\TrainingsSchedule\\files\\%s";
	private static FileWriter fileWriter = null;
	
	private FileWriter() {}
	
	public static FileWriter getInstance() {
		if(fileWriter==null) {
			fileWriter = new FileWriter();
		}
		return fileWriter;
	}
	
	public String createPath(String fileType, String fileName) {
		return String.format(filePath, fileType + "\\" + fileName + "." + fileType);
	}
	
	public File writeXml(String fileName, Object object, Class<?> c) throws JAXBException {
		String currentPath = createPath("xml", fileName);
		File output = new File(currentPath);
		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(object, output);
		return output;
	}
	
	public File saveChart(String fileName, JFreeChart chart, int height, int width) throws IOException {
		File file = new File(createPath("jpg", fileName));
		ChartUtils.saveChartAsJPEG(file, chart, width, height);
		return file;
	}
	
	public PdfWriter writePdf(String fileName, Document document) throws FileNotFoundException, DocumentException {
		return PdfWriter.getInstance(document, new FileOutputStream(createPath("pdf", fileName)));
	}
	
}