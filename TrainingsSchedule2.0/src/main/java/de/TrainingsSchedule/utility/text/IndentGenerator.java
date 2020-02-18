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
	
	public String generateIndent(char c, float f) {
		return Stream.generate(() -> c+"").limit((int) f).collect(Collectors.joining());
	}
}