package PdfReport.pdf.section.details;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.knowm.xchart.style.PieStyler;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.Table.TableBuilder;
import org.vandeseer.easytable.structure.cell.ImageCell;
import org.vandeseer.easytable.structure.cell.TextCell;
import org.vandeseer.easytable.structure.cell.paragraph.ParagraphCell;
import org.vandeseer.easytable.structure.cell.paragraph.ParagraphCell.Paragraph;
import org.vandeseer.easytable.structure.cell.paragraph.StyledText;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.chart.ReportDonutChart;
import PdfReport.pdf.destination.Destination;
import PdfReport.pdf.destination.DestinationAware;
import PdfReport.pdf.font.ReportFont;
import PdfReport.pdf.image.ImageCreator;
import PdfReport.pdf.optimizer.TextSanitizer;
import PdfReport.pdf.pojo.cucumber.Feature;
import PdfReport.pdf.structure.Display;
import PdfReport.pdf.structure.PageCreator;
import PdfReport.pdf.structure.TableCreator;
import PdfReport.pdf.util.DateUtil;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DetailedFeatureDisplay extends Display implements DestinationAware {

	private Feature feature;

	@Getter
	private float finalY;

	private int destinationY;

	@Override
	public void display() {

		TextSanitizer sanitizer = TextSanitizer.builder().build();
		PDPage initialPage = document.getPage(document.getNumberOfPages() - 1);
		destinationY = (int) ylocation;

		String tags = feature.getTags().stream().collect(Collectors.joining(" "));

		TableBuilder tableBuilder = Table.builder().addColumnsOfWidth(100f, 300f, 70f, 110f, 70f, 110f).borderWidth(1f)
				.borderColor(Color.GRAY).horizontalAlignment(HorizontalAlignment.LEFT)
				.verticalAlignment(VerticalAlignment.TOP).font(ReportFont.REGULAR_FONT)

				.addRow(Row.builder().font(ReportFont.BOLD_FONT).fontSize(14).borderWidth(0f).padding(7f)
						.add(TextCell.builder().colSpan(6).wordBreak(true)
								.text("(Feature)- " + sanitizer.sanitizeText(feature.getName()))
								.textColor(reportConfig.getDetailedFeatureConfig().featureNameColor()).build())
						.build())

				/*.addRow(Row.builder().fontSize(13).font(ReportFont.ITALIC_FONT)
						.add(TextCell.builder().text(feature.getStatus().toString())
								.backgroundColor(statusColor(feature.getStatus())).build())
						.add(TextCell.builder()
								.text("DURATION - " + DateUtil.durationValue(feature.calculatedDuration()))
								.textColor(reportConfig.getDetailedFeatureConfig().durationColor())
								.backgroundColor(reportConfig.getDetailedScenarioConfig().durationBackgroundColor())
								.build())*/

						/*.add(ParagraphCell.builder().lineSpacing(1.4f).rowSpan(3).paragraph(scenariosData())
								.font(ReportFont.REGULAR_FONT)
								.backgroundColor(reportConfig.getDetailedFeatureConfig().dataBackgroundColor()).build())
						.add(ImageCell.builder().rowSpan(3).image(scenariosDonut())
								.horizontalAlignment(HorizontalAlignment.CENTER)
								.verticalAlignment(VerticalAlignment.MIDDLE).build())*/

						/*.add(ParagraphCell.builder().lineSpacing(1.4f).rowSpan(3).paragraph(stepsData())
								.font(ReportFont.REGULAR_FONT)
								.backgroundColor(reportConfig.getDetailedFeatureConfig().dataBackgroundColor()).build())
						.add(ImageCell.builder().rowSpan(3).image(stepsDonut())
								.horizontalAlignment(HorizontalAlignment.CENTER)
								.verticalAlignment(VerticalAlignment.MIDDLE).build())
						.build())*/

				/*.addRow(Row.builder().fontSize(12).font(ReportFont.ITALIC_FONT)
						.add(TextCell.builder().colSpan(2)
								.text("/ " + DateUtil.formatTimeWithMillis(feature.getStartTime()) + " // "
										+ DateUtil.formatTimeWithMillis(feature.getEndTime()) + " /")
								.textColor(reportConfig.getDetailedFeatureConfig().startEndTimeColor()).build())
						.build())*/

				.addRow(Row.builder().fontSize(11).add(TextCell.builder().colSpan(6).text(sanitizer.sanitizeText(tags))
						.textColor(reportConfig.getDetailedFeatureConfig().tagColor()).build()).build());

		TableCreator tableCreator = TableCreator.builder().tableBuilder(tableBuilder).document(document)
				.startX(CONTENT_START_X).startY(ylocation).endY(DETAILED_CONTENT_END_Y).repeatRows(2)
				.pageSupplier(PageCreator.builder().document(document).build()
						.landscapePageWithHeaderAndNumberSupplier(DetailedSection.SECTION_TITLE))
				.build();
		tableCreator.displayTable();

		finalY = tableCreator.getFinalY();

		page = tableCreator.getTableStartPage();

		if (!initialPage.equals(page))
			destinationY = (int) Display.CONTENT_START_Y;

		createDestination();
	}

	@Override
	public void createDestination() {

		Destination destination = Destination.builder().yCoord(destinationY).page(page).build();
		feature.setDestination(destination);
	}

	private Paragraph scenariosData() {

		return createData("Scenarios", feature.getTotalScenarios(), feature.getPassedScenarios(),
				feature.getFailedScenarios(), feature.getSkippedScenarios());
	}

	private PDImageXObject scenariosDonut() {
		ReportDonutChart chart = new ReportDonutChart(80, 80);
		updateChartStyler(chart.getStyler());

		Map<String, Number> data = new HashMap<>();
		data.put("Passed", feature.getPassedScenarios());
		data.put("Failed", feature.getFailedScenarios());
		data.put("Skipped", feature.getSkippedScenarios());

		chart.updateData(data);
		return ImageCreator.builder().chart(chart).document(document).build().generateChartImageXObject();
	}

	private Paragraph stepsData() {

		return createData("Steps", feature.getTotalSteps(), feature.getPassedSteps(), feature.getFailedSteps(),
				feature.getSkippedSteps());
	}

	private PDImageXObject stepsDonut() {
		ReportDonutChart chart = new ReportDonutChart(80, 80);
		updateChartStyler(chart.getStyler());

		Map<String, Number> data = new HashMap<>();
		data.put("Passed", feature.getPassedSteps());
		data.put("Failed", feature.getFailedSteps());
		data.put("Skipped", feature.getSkippedSteps());

		chart.updateData(data);
		return ImageCreator.builder().chart(chart).document(document).build().generateChartImageXObject();
	}

	private void updateChartStyler(PieStyler styler) {
		styler.setSumFontSize(16f);
		styler.setDonutThickness(0.4);
		styler.setSeriesColors(
				new Color[] { reportConfig.passedColor(), reportConfig.failedColor(), reportConfig.skippedColor() });
	}

	private Paragraph createData(String header, int total, int pass, int fail, int skip) {

		return Paragraph.builder()
				.append(StyledText.builder().fontSize(11f).text(header)
						.color(reportConfig.getDetailedFeatureConfig().dataHeaderColor()).build())
				.appendNewLine().append(createDataTitle("Total", reportConfig.getDetailedFeatureConfig().totalColor()))
				.append(createDataValue(total, reportConfig.getDetailedFeatureConfig().totalColor())).appendNewLine()

				.append(createDataTitle("Pass", reportConfig.passedColor()))
				.append(createDataValue(pass, reportConfig.passedColor())).appendNewLine()

				.append(createDataTitle("Fail", reportConfig.failedColor()))
				.append(createDataValue(fail, reportConfig.failedColor())).appendNewLine()

				.append(createDataTitle("Skip", reportConfig.skippedColor()))
				.append(createDataValue(skip, reportConfig.skippedColor())).appendNewLine().build();
	}

	private StyledText createDataTitle(String text, Color color) {
		return StyledText.builder().fontSize(10f).text(text + " - ").color(color).build();
	}

	private StyledText createDataValue(int value, Color color) {
		return StyledText.builder().fontSize(11f).text(String.valueOf(value)).color(color).build();
	}
}
