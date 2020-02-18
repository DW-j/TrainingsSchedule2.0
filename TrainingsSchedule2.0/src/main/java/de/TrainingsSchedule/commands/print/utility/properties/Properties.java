package de.TrainingsSchedule.commands.print.utility.properties;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;

public class Properties {

	public final static Property text_1 = new Property(FontFactory.TIMES, 12, 60, 60, 60, 60, 8, null);
	
	public final static Property chart_1 = new Property(FontFactory.TIMES, 12, 60, 60, 60, 60, 8, null);
	
	public final static Property table_1 = new Property(FontFactory.TIMES, 12, 60, 60, 30, 30, 0, new BaseColor(204, 204, 204));
}