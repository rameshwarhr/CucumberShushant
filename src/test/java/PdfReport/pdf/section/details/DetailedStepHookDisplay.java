package PdfReport.pdf.section.details;

import java.awt.Color;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.Table.TableBuilder;
import org.vandeseer.easytable.structure.cell.TextCell;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.font.ReportFont;
import PdfReport.pdf.pojo.cucumber.Executable;
import PdfReport.pdf.section.details.executable.ExecutableDisplay;
import PdfReport.pdf.structure.Display;
import PdfReport.pdf.structure.PageCreator;
import PdfReport.pdf.structure.TableCreator;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DetailedStepHookDisplay extends Display {

	private List<Executable> executables;

	public static final float STEP_HOOK_TEXT_COLUMN_WIDTH = 735f;
	public static final PDFont STEP_HOOK_TEXT_FONT = ReportFont.REGULAR_FONT;
	public static final int STEP_HOOK_TEXT_FONT_SIZE = 10;
	public static final float STEP_HOOK_TEXT_PADDING = 4f;

	@Getter
	private float finalY;

	@Override
	public void display() {

		TableBuilder tableBuilder = Table.builder().addColumnsOfWidth(25f, STEP_HOOK_TEXT_COLUMN_WIDTH)
				.borderColor(Color.GRAY).borderWidth(1).font(STEP_HOOK_TEXT_FONT).fontSize(STEP_HOOK_TEXT_FONT_SIZE)
				.horizontalAlignment(HorizontalAlignment.LEFT).verticalAlignment(VerticalAlignment.TOP)
				.padding(STEP_HOOK_TEXT_PADDING)

				.addRow(Row.builder().horizontalAlignment(HorizontalAlignment.CENTER)
						.verticalAlignment(VerticalAlignment.MIDDLE).font(ReportFont.BOLD_FONT).fontSize(11)
						.add(TextCell.builder().text("#").build())
						.add(TextCell.builder().text("Step / Hook Details").build())
						//.add(TextCell.builder().text("Status").build()).add(TextCell.builder().text("Duration").build())
						.build());

		int sNo = 0;
		for (int i = 0; i < executables.size(); i++) {

			ExecutableDisplay executableDisplay = executables.get(i).getDisplay();
			executableDisplay.setTableBuilder(tableBuilder);
			executableDisplay.setReportConfig(reportConfig);
			executableDisplay.setDocument(document);

			sNo = executableDisplay.processSNo(sNo);
			executableDisplay.setSNo(sNo);

			executableDisplay.display();
		}

		TableCreator tableCreator = TableCreator.builder().tableBuilder(tableBuilder).document(document)
				.startX(CONTENT_START_X).startY(ylocation).endY(DETAILED_CONTENT_END_Y)
				.pageSupplier(PageCreator.builder().document(document).build()
						.landscapePageWithHeaderAndNumberSupplier(DetailedSection.SECTION_TITLE))
				.build();
		tableCreator.displayTable();

		finalY = tableCreator.getFinalY();
	}
}
