package de.TrainingsSchedule.commands.print.utility;

import org.apache.pdfbox.pdmodel.font.PDFont;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Property {

	private int fontSize;
	private PDFont fontType;
	private float margin_top, margin_sides, margin_bottom;

}