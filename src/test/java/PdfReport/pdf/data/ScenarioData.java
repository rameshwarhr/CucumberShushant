package PdfReport.pdf.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import PdfReport.pdf.pojo.cucumber.Scenario;

@Getter
@Builder
public class ScenarioData implements DisplayData {

	@Default
	private List<Scenario> scenarios = new ArrayList<>();
}
