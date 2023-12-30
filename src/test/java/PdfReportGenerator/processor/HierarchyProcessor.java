package PdfReportGenerator.processor;

import PdfReportGenerator.pojo.Feature;
import PdfReportGenerator.pojo.Scenario;
import PdfReportGenerator.pojo.Step;
import PdfReportGenerator.properties.ReportProperties;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class HierarchyProcessor {

    private ReportProperties reportProperties;

    @Inject
    public HierarchyProcessor(ReportProperties reportProperties) {
        this.reportProperties = reportProperties;
    }

    public void process(List<Feature> features) {
        updateScenarioWithBackgroundSteps(features);
        updateStatusForStrict(features);
    }

    private void updateScenarioWithBackgroundSteps(List<Feature> features) {

        for (Feature feature : features) {
            if (feature.getElements().get(0).getKeyword().equalsIgnoreCase("background")) {
                List<Scenario> scenarios = feature.getElements();
                Scenario backgroundScenario = null;
                Iterator<Scenario> iterator = scenarios.iterator();

                while (iterator.hasNext()) {
                    Scenario scenario = iterator.next();
                    if (scenario.getKeyword().equalsIgnoreCase("background")) {
                        backgroundScenario = scenario;
                        iterator.remove();
                    } else
                        scenario.getSteps().addAll(0, backgroundScenario.getSteps());
                }
            }
        }
    }

    private void updateStatusForStrict(List<Feature> features) {

        List<Step> steps = features.stream().flatMap(f -> f.getElements().stream()).flatMap(s -> s.getSteps().stream())
                .collect(Collectors.toList());

        for (Step step : steps) {
            if (reportProperties.isStrictCucumber6Behavior()) {
                String status = step.getResult().getStatus();
                if (status.equalsIgnoreCase("pending") || status.equalsIgnoreCase("undefined"))
                    step.getResult().setStatus("failed");
            }
        }
    }
}
