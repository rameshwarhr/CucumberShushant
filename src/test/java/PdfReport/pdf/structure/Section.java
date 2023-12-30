package PdfReport.pdf.structure;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.config.ReportConfig;
import PdfReport.pdf.data.DisplayData;
import PdfReport.pdf.destination.Destination.DestinationStore;

@Data
@SuperBuilder
public abstract class Section {

	@NonNull
	protected PDDocument document;

	@NonNull
	protected DisplayData displayData;

	protected ReportConfig reportConfig;

	protected DestinationStore destinations;

	public abstract void createSection();

	protected PDPage createPage() {
		return PageCreator.builder().document(document).build().createLandscapePageAndAddToDocument();
	}
}
