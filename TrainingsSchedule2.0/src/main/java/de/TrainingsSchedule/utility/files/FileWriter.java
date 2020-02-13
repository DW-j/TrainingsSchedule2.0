package de.TrainingsSchedule.utility.files;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class FileWriter {

	private final String filePath = "C:\\Users\\wittn\\workspaces\\local workspace\\TrainingsSchedule\\src\\main\\java\\de\\TrainingsSchedule\\files\\%s";
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
	
	public void writeXML(String fileName, Object object, Class<?> c) throws JAXBException {
		String currentPath = createPath("xml", fileName);
		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(object, new File(currentPath));
	}
}