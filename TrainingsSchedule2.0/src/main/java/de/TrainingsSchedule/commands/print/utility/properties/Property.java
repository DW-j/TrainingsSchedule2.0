package de.TrainingsSchedule.commands.print.utility.properties;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

import lombok.Getter;

@Getter
public class Property {

	Font font;
	float margin_top, margin_bottom, margin_left, margin_right;
	float line_space;
	float text_height;
	BaseColor headerColor;
	
	public Property(String fontName, int fontSize, float margin_top, float margin_bottom, float margin_left, float margin_right, float line_space, BaseColor headerColor) {
		this.font = FontFactory.getFont(fontName, fontSize);
		this.margin_top = margin_top;
		this.margin_bottom = margin_bottom;
		this.margin_left = margin_left;
		this.margin_right = margin_right;
		this.line_space = line_space;
		this.headerColor = headerColor;
		text_height = font.getBaseFont().getAscentPoint("A", font.getSize()) - font.getBaseFont().getDescentPoint("A", font.getSize()) ;
	}
	
	
}