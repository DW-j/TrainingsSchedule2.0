package de.TrainingsSchedule.utility.other;

import java.util.ArrayList;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import de.TrainingsSchedule.utility.text.IndentGenerator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class Table {

	private List<String> header = new ArrayList<String>();
	@Setter(AccessLevel.NONE)
	private List<List<String>> content = new ArrayList<List<String>>();
	private String headline;
	private Set<Entry<Object, Object>> fixedColumnWidths;
	
	public Table(List<String> header) {
		this.header = header;
	}
	
	public Table(List<String> header, String headline) {
		this.header = header;
		this.headline = headline;
	}
	
	public void addRow(List<String> row) {
		for(int i=0; i<row.size(); i++) {
			if(content.size()<=i) {
				content.add(new ArrayList<String>());
			}
			content.get(i).add(row.get(i));
		}
	}
	
	public void addColumn(List<String> column) {
		content.add(column);
	}
	
	public List<Float> getIndents(){
		List<Float>indents = new ArrayList<Float>();
		List<List<String>> contentHeader = new ArrayList<List<String>>();
		if(header.size()==0) {
			contentHeader = content;
		}
		for(int i=0; i<header.size(); i++) {
			contentHeader.add(new ArrayList<String>());
			contentHeader.get(i).add(header.get(i));
			if(content.size()>0) {
				contentHeader.get(i).addAll(content.get(i));
			}
		}
		for(int i=0; i<contentHeader.size(); i++) {
			indents.add(0f);
			for(int j=0; j<contentHeader.get(i).size(); j++) {
				String cell = contentHeader.get(i).get(j);
				if(cell.length()+1>indents.get(indents.size()-1)) {
					indents.set(indents.size()-1, (float)cell.length()+1);
				}
			}
		}
		return indents;
	}
	
	public String toString() {
		List<Float> indents = getIndents();		
		
		List<String> printTable = new ArrayList<String>();
		if(headline!=null) {
			printTable.add(headline);
		}
		List<String> printHeader = new ArrayList<String>();
		for(int i=0; i<header.size(); i++) {
			String cell = header.get(i);
			printHeader.add(cell+IndentGenerator.getInstance().generateIndent(' ', indents.get(i).intValue()-cell.length()));
		}
		printTable.add(String.join("|", printHeader));
		if(headline!=null) {
			printTable.add(IndentGenerator.getInstance().generateIndent('-', String.join(" ", printTable).toString().length()-headline.length()));
		}else {
			printTable.add(IndentGenerator.getInstance().generateIndent('-', String.join(" ", printTable).toString().length()));
		}
		if(content.size()>0) {
			for(int i=0; i<content.get(0).size(); i++) {
				List<String> printRow = new ArrayList<String>();
				for(int j=0; j<content.size(); j++) {
					String cell = content.get(j).get(i);
					printRow.add(cell+IndentGenerator.getInstance().generateIndent(' ', indents.get(j).intValue()-cell.length()));
				}
				printTable.add(String.join("|", printRow));
			}
		}
		
		return String.format("%s\n", String.join("\n", printTable));
	}

}