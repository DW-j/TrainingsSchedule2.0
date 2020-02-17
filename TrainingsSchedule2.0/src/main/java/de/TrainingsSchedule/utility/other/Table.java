package de.TrainingsSchedule.utility.other;

import java.util.ArrayList;
import java.util.List;

import de.TrainingsSchedule.utility.text.IndentGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Table {

	@Setter
	@Getter
	private List<String> header = new ArrayList<String>();
	@Getter
	private List<List<String>> content = new ArrayList<List<String>>();
	@Getter
	@Setter
	String headline;
	
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
	
	public String toString() {
		
		List<Integer> indents = new ArrayList<Integer>();
		List<List<String>> contentHeader = new ArrayList<List<String>>();
		
		for(int i=0; i<header.size(); i++) {
			contentHeader.add(new ArrayList<String>());
			contentHeader.get(i).add(header.get(i));
			if(content.size()>0) {
				contentHeader.get(i).addAll(content.get(i));
			}
		}
		for(int i=0; i<contentHeader.size(); i++) {
			indents.add(0);
			for(int j=0; j<contentHeader.get(i).size(); j++) {
				String cell = contentHeader.get(i).get(j);
				if(cell.length()+1>indents.get(indents.size()-1)) {
					indents.set(indents.size()-1, cell.length()+1);
				}
			}
		}
		
		List<String> printTable = new ArrayList<String>();
		if(headline!=null) {
			printTable.add(headline);
		}
		List<String> printHeader = new ArrayList<String>();
		for(int i=0; i<header.size(); i++) {
			String cell = header.get(i);
			printHeader.add(cell+IndentGenerator.getInstance().generateIndent(' ', indents.get(i)-cell.length()));
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
					printRow.add(cell+IndentGenerator.getInstance().generateIndent(' ', indents.get(j)-cell.length()));
				}
				printTable.add(String.join("|", printRow));
			}
		}
		
		return String.join(String.join("\n", printTable), "\n", "\n");
	}

}