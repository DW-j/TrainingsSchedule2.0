package de.TrainingsSchedule.commands.print.utility.properties;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;

public class Properties {

	public final static Property title_1 = new Property(FontFactory.TIMES_BOLD, 34, 400, 25, 0, 0, 0, null);
	public final static Property title_2 = new Property(FontFactory.TIMES, 18, 10, 10, 0, 0, 0, null); 
	public final static Property title_3 = new Property(FontFactory.TIMES_ITALIC, 14, 10, 10, 0, 0, 0, null); 
	
	public final static Property contenttable_1 = new Property(FontFactory.TIMES, 20, 6, 2, 70, 70, 3, null);
	public final static Property contenttable_2 = new Property(FontFactory.TIMES, 16, 3, 3, 95, 110, 3, null);
	
	public final static Property chapter_1 = new Property(FontFactory.TIMES_BOLD, 24, 40, 10, 40, 40, 1, null);
	public final static Property chapter_2 = new Property(FontFactory.TIMES_BOLD, 20, 5, 5, 65, 65, 10, null);
	
	public final static Property text_1 = new Property(FontFactory.TIMES, 12, 5, 5, 80, 80, 8, null);
	
	public final static Property chart_1 = new Property(FontFactory.TIMES, 12, 5, 5, 80, 80, 8, null);
	
	public final static Property table_1 = new Property(FontFactory.TIMES, 12, 5, 5, 80, 80, 0, new BaseColor(204, 204, 204));
}