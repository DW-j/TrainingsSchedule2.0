package de.TrainingsSchedule.commands.print.utility.properties;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;

public class Properties {

	public final static Property title_1 = new Property(FontFactory.TIMES_BOLD, 34, 400, 25, 0, 0, 0);
	public final static Property title_2 = new Property(FontFactory.TIMES, 18, 10, 10, 0, 0, 0); 
	public final static Property title_3 = new Property(FontFactory.TIMES_ITALIC, 14, 10, 10, 0, 0, 0); 
	
	public final static Property contenttable_1 = new Property(FontFactory.TIMES, 20, 8, 2, 70, 70, 3);
	public final static Property contenttable_2 = new Property(FontFactory.TIMES, 16, 5, 5, 95, 95, 3);
	public final static Property contenttable_3 = new Property(FontFactory.TIMES, 12, 3, 3, 110, 110, 3);
	
	public final static Property chapter_1 = new Property(FontFactory.TIMES_BOLD, 28, 10, 15, 40, 40, 1);
	public final static Property chapter_2 = new Property(FontFactory.TIMES_BOLD, 24, 5, 5, 65, 65, 10);
	public final static Property chapter_3 = new Property(FontFactory.TIMES, 20, 2, 1, 65, 65, 10);
	
	public final static Property text_1 = new Property(FontFactory.TIMES, 12, 2, 20, 80, 80, 8);
	
	public final static Property chart_1 = new Property(FontFactory.TIMES, 12, 2, 20, 50, 50, 0, 250, 490);
	
	public final static Property table_1 = new Property(FontFactory.TIMES, 12, 2, 20, 80, 80, 0, new BaseColor(204, 204, 204));
}