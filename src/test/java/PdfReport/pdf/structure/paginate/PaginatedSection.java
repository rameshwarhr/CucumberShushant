package PdfReport.pdf.structure.paginate;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.data.DisplayData;
import PdfReport.pdf.structure.Section;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public abstract class PaginatedSection extends Section {

	public abstract void generateDisplay(int fromIndex, int toIndex);

	public abstract DisplayData createDisplayData(int fromIndex, int toIndex);
}
