package PdfReport.pdf.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import PdfReport.pdf.pojo.cucumber.Feature;

@Getter
@Builder
public class FeatureData implements DisplayData {

	@Default
	private List<Feature> features = new ArrayList<>();
}
