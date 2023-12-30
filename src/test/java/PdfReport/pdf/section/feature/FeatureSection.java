package PdfReport.pdf.section.feature;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.data.DisplayData;
import PdfReport.pdf.data.FeatureData;
import PdfReport.pdf.pojo.cucumber.Feature;
import PdfReport.pdf.structure.paginate.FeaturePaginator;
import PdfReport.pdf.structure.paginate.PaginatedSection;
import PdfReport.pdf.structure.paginate.PaginationData;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FeatureSection extends PaginatedSection {

	static final String SECTION_TITLE = "FEATURES SUMMARY";

	private final int maxFeaturesPerPage = reportConfig.getFeatureConfig().featureCount();

	private FeatureData featureData;

	@Override
	public void createSection() {

		featureData = (FeatureData) displayData;
		FeaturePaginator paginator = FeaturePaginator.builder().data(featureData).maxFeaturesPerPage(maxFeaturesPerPage)
				.section(this).build();
		paginator.paginate();
	}

	@Override
	public void generateDisplay(int fromIndex, int toIndex) {

		FeatureDisplay.builder().displayData(createDisplayData(fromIndex, toIndex)).document(document)
				.reportConfig(reportConfig).destinations(destinations).paginationData(PaginationData.builder()
						.itemsPerPage(maxFeaturesPerPage).itemFromIndex(fromIndex).itemToIndex(toIndex).build())
				.build().display();
	}

	@Override
	public DisplayData createDisplayData(int fromIndex, int toIndex) {

		List<Feature> pageFeatures = featureData.getFeatures().subList(fromIndex, toIndex);
		return FeatureData.builder().features(pageFeatures).build();
	}
}
