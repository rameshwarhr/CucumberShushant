package PdfReport.pdf.structure.paginate;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.structure.Display;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public abstract class PaginatedDisplay extends Display {

	protected PaginationData paginationData;

}
