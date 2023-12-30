package PdfReport.pdf.section.scenario;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;

import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.destination.Destination;
import PdfReport.pdf.destination.DestinationAware;
import PdfReport.pdf.outline.Outline;
import PdfReport.pdf.structure.PageCreator;
import PdfReport.pdf.structure.paginate.PaginatedDisplay;

@SuperBuilder
public class ScenarioDisplay extends PaginatedDisplay implements DestinationAware {

	@Override
	@SneakyThrows
	public void display() {

		page = PageCreator.builder().document(document).build()
				.createLandscapePageWithHeaderAndNumberAndAddToDocument(ScenarioSection.SECTION_TITLE);

		content = new PDPageContentStream(document, page, AppendMode.APPEND, true);
		//comment below line to remove step bar chart from pdf report
		//createStackedBarChart();
		createTable();
		createDestination();

		content.close();
	}

	private void createStackedBarChart() {
		ScenarioStackedBarChart.builder().document(document).content(content).displayData(displayData)
				.reportConfig(reportConfig).fromXData(paginationData.getItemFromIndex())
				.toXData(paginationData.getItemToIndex()).build().display();
	}

	private void createTable() {
		ScenarioStepDetails.builder().displayData(displayData).content(content).reportConfig(reportConfig)
				.document(document).paginationData(paginationData).build().display();
	}

	@Override
	public void createDestination() {
		Destination destination = Destination.builder()
				.name(Outline.SCENARIOS_SECTION_TEXT + " - " + (paginationData.getItemFromIndex() + 1) + " to "
						+ paginationData.getItemToIndex())
				.yCoord((int) page.getMediaBox().getHeight()).page(page).build();
		destinations.addScenarioDestination(destination);
	}
}
