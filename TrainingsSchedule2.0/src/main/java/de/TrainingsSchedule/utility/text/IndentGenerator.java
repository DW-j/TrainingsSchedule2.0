package de.TrainingsSchedule.utility.text;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IndentGenerator {

	private static IndentGenerator indentGenerator = null;
	
	private IndentGenerator() {}
	
	public static IndentGenerator getInstance() {
		if(indentGenerator==null) {
			indentGenerator = new IndentGenerator();
		}
		return indentGenerator;
	}
	
	public String generateIndent(char c, int length) {
		return Stream.generate(() -> c+"").limit(length).collect(Collectors.joining());
	}
}