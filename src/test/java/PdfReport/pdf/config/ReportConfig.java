package PdfReport.pdf.config;

import java.awt.Color;

import PdfReport.pdf.pojo.cucumber.Status;
import PdfReportGenerator.ReportGenerator;
import lombok.Data;
import PdfReport.pdf.config.DetailedConfig.DetailedFeatureConfig;
import PdfReport.pdf.config.DetailedConfig.DetailedScenarioConfig;
import PdfReport.pdf.config.DetailedConfig.DetailedStepHookConfig;

@Data
public class ReportConfig {

    private String passColor;
    private String failColor;
    private String skipColor;
    private boolean displayFeature = true;
    private boolean displayScenario = true;
    private boolean displayDetailed = true;
    private boolean isDryRun = ReportGenerator.dryRun;

    private SummaryConfig summaryConfig = new SummaryConfig();
    private FeatureConfig featureConfig = new FeatureConfig();
    private ScenarioConfig scenarioConfig = new ScenarioConfig();
    private DetailedFeatureConfig detailedFeatureConfig = new DetailedFeatureConfig();
    private DetailedScenarioConfig detailedScenarioConfig = new DetailedScenarioConfig();
    private DetailedStepHookConfig detailedStepHookConfig = new DetailedStepHookConfig();

    public Color passedColor() {
        return createColor(passColor, Color.GREEN);
    }

    public Color failedColor() {
        return createColor(failColor, Color.RED);
    }

    public Color skippedColor() {
        return createColor(skipColor, Color.ORANGE);
    }

    public Color statusColor(Status status) {
        if (status.equals(Status.PASSED)) {
            return passedColor();
        }
        if (status.equals(Status.SKIPPED)) {
            return skippedColor();
        }
        return failedColor();
    }

    static Color createColor(String hexCode, Color defaultColor) {
        try {
            return Color.decode("#" + hexCode);
        } catch (NumberFormatException e) {
            return defaultColor;
        }
    }

    static int createCount(String itemCount, int defaultCount) {
        int count = 0;
        try {
            count = Integer.parseInt(itemCount);
            if (count > defaultCount)
                return defaultCount;
        } catch (NumberFormatException e) {
            return defaultCount;
        }
        return count;
    }

    @Data
    public static class FeatureConfig {

        private String featureCount;
        private String totalColor;
        private String durationColor;
        private final int defaultCount = 10;

        public int featureCount() {
            return createCount(featureCount, defaultCount);
        }

        public Color totalColor() {
            return createColor(totalColor, Color.BLACK);
        }

        public Color durationColor() {
            return createColor(durationColor, Color.BLACK);
        }
    }

    @Data
    public static class ScenarioConfig {

        private String scenarioCount;
        private String totalColor;
        private String durationColor;
        private final int defaultCount = 14;

        public int scenarioCount() {
            return createCount(scenarioCount, defaultCount);
        }

        public Color totalColor() {
            return createColor(totalColor, Color.BLACK);
        }

        public Color durationColor() {
            return createColor(durationColor, Color.BLACK);
        }
    }
}
