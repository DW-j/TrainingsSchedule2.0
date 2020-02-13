package de.TrainingsSchedule.test;

import java.util.Arrays;

import de.TrainingsSchedule.utility.other.Table;

public class Tester {

	public static void main(String[] args) {
		Table T = new Table();
		T.setHeader(Arrays.asList(new String[] {"ID", "Variation"}));	
		T.addRow(Arrays.asList(new String[] {"0", "geh"}));
		T.addRow(Arrays.asList(new String[] {"1", "meh"}));
		System.out.println(T.toString());
	}

}
