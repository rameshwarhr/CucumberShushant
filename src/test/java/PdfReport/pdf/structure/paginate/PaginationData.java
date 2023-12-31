package PdfReport.pdf.structure.paginate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationData {

	private int itemFromIndex;
	private int itemToIndex;
	private int itemsPerPage;
}
