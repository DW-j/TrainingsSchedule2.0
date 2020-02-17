package de.TrainingsSchedule.commands.print.elements.content.utility.tables;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;

public class TableBuilder {

	public void createTable(List<String> header, List<List<String>> data, float yText, float top_margin_new, float margin_bottom, float width, PDDocument document, PDPage page, PDFont font) throws IOException {
		BaseTable table = new BaseTable(yText, top_margin_new, margin_bottom, width, 1, document, page, true, true);
		Row<PDPage> headerRow = table.createRow(15f);
		Cell<PDPage> cell = headerRow.createCell(100, "Awesome Facts About Belgium");
		cell.setFont(font);
		table.addHeaderRow(headerRow);
		for (List<String> data_row: data) {
		            Row<PDPage> row = table.createRow(10f);
		            cell = row.createCell((100 / 3.0f) * 2, data_row.get(0));
		            for (int i = 1; i < data_row.size(); i++) {
		               cell = row.createCell((100 / 9f), data_row.get(i));
		            }
		}
		table.draw();
//		https://github.com/dhorions/boxable/blob/master/src/test/java/be/quodlibet/boxable/TableTest.java
	}

}