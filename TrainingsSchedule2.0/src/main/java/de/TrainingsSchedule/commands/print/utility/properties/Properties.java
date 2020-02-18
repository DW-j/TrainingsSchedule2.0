package de.TrainingsSchedule.commands.print.utility.properties;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;

public class Properties {

	public final static Property title_1 = new Property(FontFactory.TIMES_BOLD, 34, 400, 25, 0, 0, 0, null);
	public final static Property title_2 = new Property(FontFactory.TIMES, 18, 10, 10, 0, 0, 0, null); 
	public final static Property title_3 = new Property(FontFactory.TIMES_ITALIC, 14, 10, 10, 0, 0, 0, null); 
	
	public final static Property contenttable_1 = new Property(FontFactory.TIMES, 14, 15, 15, 40, 40, 3, null);
	public final static Property contenttable_2 = new Property(FontFactory.TIMES, 12, 5, 5, 50, 50, 3, null);
	
	public final static Property chapter_1 = new Property(FontFactory.TIMES_BOLD, 22, 50, 30, 100, 100, 10, null);
	public final static Property chapter_2 = new Property(FontFactory.TIMES_BOLD, 18, 20, 15, 130, 130, 10, null);
	
	public final static Property text_1 = new Property(FontFactory.TIMES, 12, 60, 60, 60, 60, 8, null);
	
	public final static Property chart_1 = new Property(FontFactory.TIMES, 12, 60, 60, 60, 60, 8, null);
	
	public final static Property table_1 = new Property(FontFactory.TIMES, 12, 60, 60, 30, 30, 0, new BaseColor(204, 204, 204));
}