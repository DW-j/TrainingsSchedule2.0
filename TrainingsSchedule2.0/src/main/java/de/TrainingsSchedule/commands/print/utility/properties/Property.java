package de.TrainingsSchedule.commands.print.utility.properties;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

import lombok.Getter;

@Getter
public class Property {

	private Font font;
	private float margin_top, margin_bottom, margin_left, margin_right;
	private float line_space;
	private float text_height;
	private BaseColor headerColor;
	private int chartHeight, chartWidth;
	
	
	public Property(String fontName, int fontSize, float margin_top, float margin_bottom, float margin_left, float margin_right, float line_space) {
		setProperties(fontName, fontSize, margin_top, margin_bottom, margin_left, margin_right, line_space);
	}
	
	public Property(String fontName, int fontSize, float margin_top, float margin_bottom, float margin_left, float margin_right, float line_space, int chartHeight, int chartWidth) {
		setProperties(fontName, fontSize, margin_top, margin_bottom, margin_left, margin_right, line_space);
		this.chartHeight = chartHeight;
		this.chartWidth = chartWidth;
	}
	
	public Property(String fontName, int fontSize, float margin_top, float margin_bottom, float margin_left, float margin_right, float line_space, BaseColor headerColor) {
		setProperties(fontName, fontSize, margin_top, margin_bottom, margin_left, margin_right, line_space);
		this.headerColor = headerColor;
	}
	
	private void setProperties(String fontName, int fontSize, float margin_top, float margin_bottom, float margin_left, float margin_right, float line_space) {
		this.font = FontFactory.getFont(fontName, fontSize);
		this.margin_top = margin_top;
		this.margin_bottom = margin_bottom;
		this.margin_left = margin_left;
		this.margin_right = margin_right;
		this.line_space = line_space;
		text_height = font.getBaseFont().getAscentPoint("A", font.getSize()) - font.getBaseFont().getDescentPoint("A", font.getSize()) ;
	}
	
}