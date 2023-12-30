package PdfReport.pdf.structure.header;

import java.awt.Color;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.vandeseer.easytable.drawing.DrawingUtil;
import org.vandeseer.easytable.drawing.PositionedStyledText;

import lombok.Builder;
import lombok.Setter;
import lombok.SneakyThrows;
import PdfReport.pdf.font.ReportFont;
import PdfReport.pdf.structure.Display;

@Builder
public class PageNumber {

	@Setter
	private PDPageContentStream content;

	@Setter
	private int number;

	@SneakyThrows
	public void displayNumber() {

		PositionedStyledText textDetails = PositionedStyledText.builder().x(Display.HEADER_PAGE_NUMBER_START_X)
				.y(Display.HEADER_START_Y).text("-- " + number + " --").font(ReportFont.ITALIC_FONT).fontSize(11)
				.color(Color.LIGHT_GRAY).build();

		DrawingUtil.drawText(content, textDetails);
	}
}
