package de.TrainingsSchedule.utility.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

public class FileReader {

	private final String filePath = System.getProperties().getProperty("user.dir")+"\\files\\%s";
	private static FileReader fileReader = null;
	
	private FileReader() {} 
	
	public static FileReader getInstance() {
		if(fileReader==null) {
			fileReader = new FileReader();
		}
		return fileReader;
	}
	
	private String createPath(String fileType, String fileName) {
		return String.format(filePath, fileType + "\\" + fileName + "." + fileType);
	}
	
	public String readTxt(String fileName) throws FileNotFoundException {
		String currentPath = createPath("txt", fileName);
		Scanner sc = new Scanner(new File(currentPath));
		StringBuilder output = new StringBuilder();
		while(sc.hasNext()) {
			output.append(sc.nextLine());
		}
		sc.close();
		return output.toString().trim();
	}
	
	public Set<Entry<Object, Object>> readPrp(String fileName) throws IOException {
		String currentPath = createPath("prp", fileName);
		Properties properties = new Properties();
		properties.load(Files.newInputStream(Paths.get(currentPath)));
		return properties.entrySet();
	}
	
	public Object readXml(String fileName, Class<?> c) throws JAXBException {
		String currentPath = createPath("xml", fileName);
		JAXBContext jaxbContext = JAXBContext.newInstance(c);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(new File(currentPath));
	}
	
	public Image getImage(File file) throws BadElementException, MalformedURLException, IOException {
		return Image.getInstance(file.getAbsolutePath());
	}
	
	public File getFolder(String fileType) {
		return new File(String.format(filePath, fileType));
	}
	
	public File getFile(String fileName, String fileType) {
		return new File(createPath(fileType, fileName));
	}
}