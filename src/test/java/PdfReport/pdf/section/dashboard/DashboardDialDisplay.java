package PdfReport.pdf.section.dashboard;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.knowm.xchart.style.DialStyler;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table.TableBuilder;
import org.vandeseer.easytable.structure.cell.ImageCell;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.chart.ReportDialChart;
import PdfReport.pdf.config.SummaryConfig;
import PdfReport.pdf.data.SummaryData;
import PdfReport.pdf.image.ImageCreator;
import PdfReport.pdf.structure.Display;
import PdfReport.pdf.util.NumberUtil;

@SuperBuilder
public class DashboardDialDisplay extends Display {

	@Setter(value = AccessLevel.PACKAGE)
	private TableBuilder tableBuilder;

	private final SummaryConfig summaryConfig = reportConfig.getSummaryConfig();
	private final SummaryData summaryData = (SummaryData) displayData;

	@Override
	public void display() {

		final PDImageXObject featuresDial = createDialChart(
				NumberUtil.divideAndRound(summaryData.getPassedFeatures(), summaryData.getTotalFeatures()),
				NumberUtil.divideToPercent(summaryData.getPassedFeatures(), summaryData.getTotalFeatures()),
				summaryConfig.getDial().featureRange());

		final PDImageXObject scenariosDial = createDialChart(
				NumberUtil.divideAndRound(summaryData.getPassedScenarios(), summaryData.getTotalScenarios()),
				NumberUtil.divideToPercent(summaryData.getPassedScenarios(), summaryData.getTotalScenarios()),
				summaryConfig.getDial().scenarioRange());

		final PDImageXObject stepsDial = createDialChart(
				NumberUtil.divideAndRound(summaryData.getPassedSteps(), summaryData.getTotalSteps()),
				NumberUtil.divideToPercent(summaryData.getPassedSteps(), summaryData.getTotalSteps()),
				summaryConfig.getDial().stepRange());

		tableBuilder.addRow(Row.builder().add(ImageCell.builder().image(featuresDial).build())
				.add(DashboardDisplayUtil.spacerCell()).add(ImageCell.builder().image(scenariosDial).build())
				.add(DashboardDisplayUtil.spacerCell()).add(ImageCell.builder().image(stepsDial).build()).build())
				.addRow(DashboardDisplayUtil.spacerRow());
	}

	private PDImageXObject createDialChart(double dialValue, int dialDisplay, double[] range) {

		final ReportDialChart chart = new ReportDialChart(150, 150);
		updateDialChartStyler(chart.getStyler(), range);
		chart.updateData("Pass %", dialValue, dialDisplay);

		return ImageCreator.builder().chart(chart).document(document).build().generateChartImageXObject();
	}

	private void updateDialChartStyler(DialStyler styler, double[] range) {

		styler.setGreenColor(reportConfig.getSummaryConfig().getDial().badColor());
		styler.setGreenFrom(0);
		styler.setGreenTo(range[0]);
		styler.setNormalColor(reportConfig.getSummaryConfig().getDial().averageColor());
		styler.setNormalFrom(range[0]);
		styler.setNormalTo(range[1]);
		styler.setRedColor(reportConfig.getSummaryConfig().getDial().goodColor());
		styler.setRedFrom(range[1]);
		styler.setRedTo(1);
	}
}
