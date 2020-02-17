package de.TrainingsSchedule.commands.print.utility;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Properties {
	
	final public static Property title_1 = new Property(34, PDType1Font.TIMES_BOLD, 0, 0, 5f);
	final public static Property title_2 = new Property(17, PDType1Font.TIMES_ROMAN, 0, 0, 2f);
	final public static Property title_3 = new Property(11, PDType1Font.TIMES_ROMAN, 0, 0, 0f);

	final public static Property chapter_1 = new Property(28, PDType1Font.TIMES_BOLD, 7f, 10f, 3f);
	final public static Property chapter_2 = new Property(20, PDType1Font.TIMES_ROMAN, 0, 12f, 3f);
	
	final public static Property contenttable_1 = new Property(16, PDType1Font.TIMES_ROMAN, 1.5f, 15f, 2f);
	final public static Property contenttable_2 = new Property(12, PDType1Font.TIMES_ROMAN, 0, 17f, 2f);
	
	final public static Property text_1 = new Property(12, PDType1Font.TIMES_ROMAN, 0, 15f, 2f);
	
	final public static Property table_1 = new Property(12, PDType1Font.TIMES_ROMAN, 7f, 15f, 2f);
}