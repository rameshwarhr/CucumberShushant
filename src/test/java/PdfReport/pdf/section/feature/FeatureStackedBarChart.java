package PdfReport.pdf.section.feature;

import java.awt.Color;
import java.util.stream.IntStream;

import org.knowm.xchart.style.CategoryStyler;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.chart.ReportStackedBarChart;
import PdfReport.pdf.data.FeatureData;
import PdfReport.pdf.image.ImageCreator;
import PdfReport.pdf.pojo.cucumber.Feature;
import PdfReport.pdf.structure.Display;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FeatureStackedBarChart extends Display {

	private int fromXData;
	private int toXData;

	@Override
	public void display() {
		createBarChart();
	}

	private void createBarChart() {
		int[] passed = new int[toXData - fromXData];
		int[] failed = new int[toXData - fromXData];
		int[] skipped = new int[toXData - fromXData];
		int[] xData = new int[toXData - fromXData];
		xData = IntStream.rangeClosed(fromXData + 1, toXData).toArray();

		ReportStackedBarChart chart = new ReportStackedBarChart(600, 200);
		chart.setYAxisTitle("# of Scenarios");
		updateBarChartStyler(chart.getStyler());
		createStackedBarChartData(passed, failed, skipped);
		chart.updateData(xData, passed, failed, skipped);

		ImageCreator.builder().document(document).chart(chart).content(content).build()
				.generateAndDisplayChartImage(120, 350);
	}

	private void updateBarChartStyler(CategoryStyler styler) {
		styler.setSeriesColors(
				new Color[] { reportConfig.passedColor(), reportConfig.failedColor(), reportConfig.skippedColor() });

		styler.setAvailableSpaceFill(0.4 * (toXData - fromXData) / 10);
	}

	private void createStackedBarChartData(int[] passed, int[] failed, int[] skipped) {
		FeatureData featureData = (FeatureData) displayData;
		for (int i = 0; i < featureData.getFeatures().size(); i++) {
			Feature feature = featureData.getFeatures().get(i);
			passed[i] = feature.getPassedScenarios();
			failed[i] = feature.getFailedScenarios();
			skipped[i] = feature.getSkippedScenarios();
		}
	}
}
