package de.TrainingsSchedule.utility.files;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.pdfbox.pdmodel.PDDocument;

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
	
	private String createPath(String fileType, String fileName) {
		return String.format(filePath, fileType + "\\" + fileName + "." + fileType);
	}
	
	public File writeXML(String fileName, Object object, Class<?> c) throws JAXBException {
		String currentPath = createPath("xml", fileName);
		File output = new File(currentPath);
		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(object, output);
		return output;
	}
	
	public void writePdf(String fileName, PDDocument document) throws IOException {
		String currentPath = createPath("pdf", fileName);
		document.save(currentPath);
		document.close();
	}
}