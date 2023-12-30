package PdfReport.pdf.structure.paginate;

import static PdfReport.pdf.section.scenario.ScenarioStepDetails.TABLE_SPACE;
import static PdfReport.pdf.section.scenario.ScenarioStepDetails.featureNameTextOptimizer;
import static PdfReport.pdf.section.scenario.ScenarioStepDetails.featureNameTextUtil;
import static PdfReport.pdf.section.scenario.ScenarioStepDetails.headerRowHeight;
import static PdfReport.pdf.section.scenario.ScenarioStepDetails.scenarioNameTextOptimizer;

import lombok.Builder;
import PdfReport.pdf.data.ScenarioData;
import PdfReport.pdf.pojo.cucumber.Scenario;
import PdfReport.pdf.util.TextUtil;

@Builder
public class ScenarioPaginator {

    private ScenarioData data;
    private PaginatedSection section;
    private int maxScenariosPerPage;

    public void paginate() {

        float currentHeight = headerRowHeight();

        int fromIndex = 0;
        int toIndex = 0;

        TextUtil textUtilFeature = featureNameTextUtil();
        TextUtil textUtilScenario = featureNameTextUtil();

        for (int i = 0; i < data.getScenarios().size(); i++) {

            Scenario scenario = data.getScenarios().get(i);

            textUtilFeature.setText(featureNameTextOptimizer.optimizeTextLines(scenario.getFeature().getName()));
            float featureHeight = textUtilFeature.tableRowHeight();

            textUtilScenario.setText(scenarioNameTextOptimizer.optimizeTextLines(scenario.getName()));
            float scenarioHeight = textUtilScenario.tableRowHeight();

            currentHeight = currentHeight + (scenarioHeight > featureHeight ? scenarioHeight : featureHeight);

            //if (currentHeight > TABLE_SPACE || (toIndex - fromIndex) + 1 > maxScenariosPerPage)
            if (currentHeight > TABLE_SPACE) {
                section.generateDisplay(fromIndex, toIndex);
                fromIndex = toIndex;
                currentHeight = headerRowHeight();
                i--;
            } else {
                toIndex++;
            }
        }
        // Remaining data
        section.generateDisplay(fromIndex, toIndex);
    }
}
