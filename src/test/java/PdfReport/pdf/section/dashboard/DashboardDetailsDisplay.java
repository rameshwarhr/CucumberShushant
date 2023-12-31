package PdfReport.pdf.section.dashboard;

import static PdfReport.pdf.section.dashboard.Dashboard.DATA_COLUMN_WIDTH;
import static PdfReport.pdf.section.dashboard.Dashboard.SPACE_COLUMN_WIDTH;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import Utilities.Property;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table.TableBuilder;
import org.vandeseer.easytable.structure.cell.TextCell;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.config.SummaryConfig;
import PdfReport.pdf.data.SummaryData;
import PdfReport.pdf.font.ReportFont;
import PdfReport.pdf.optimizer.TextLengthOptimizer;
import PdfReport.pdf.optimizer.TextSanitizer;
import PdfReport.pdf.pojo.cucumber.Status;
import PdfReport.pdf.structure.Display;
import PdfReport.pdf.structure.footer.CroppedMessage;
import PdfReport.pdf.util.DateUtil;

@SuperBuilder
public class DashboardDetailsDisplay extends Display {

    @Setter(value = AccessLevel.PACKAGE)
    private TableBuilder tableBuilder;

    private static final PDFont TITLE_FONT = ReportFont.BOLD_FONT;
    private static final int TITLE_FONT_SIZE = 20;
    private static final float TITLE_PADDING = 10f;

    private final TextLengthOptimizer optimizer = TextLengthOptimizer.builder().font(TITLE_FONT)
            .fontsize(TITLE_FONT_SIZE).availableSpace(2 * (DATA_COLUMN_WIDTH + SPACE_COLUMN_WIDTH - TITLE_PADDING))
            .build();

    private final SummaryConfig summaryConfig = reportConfig.getSummaryConfig();
    private final SummaryData summaryData = (SummaryData) displayData;

    private static final String CROPPED_MESSAGE = "* The report title has been cropped to fit in the available space.";

    @Override
    public void display() {
        if (reportConfig.isDryRun()) {
            headerRowDisplay();
            buildVersionDisplay();
            croppedMessageDisplay();
        } else {
            headerRowDisplay();
            buildVersionDisplay();
            statisticsRowDisplay();
            chartTitleRowDisplay();
            chartDataRowDisplay();
            croppedMessageDisplay();
        }
    }

    private void headerRowDisplay() {

        final String now = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        final TextSanitizer sanitizer = TextSanitizer.builder().build();
        final String title = sanitizer.sanitizeText(optimizer.optimizeTextLines(summaryConfig.getTitle()));

        tableBuilder.addRow(Row.builder().padding(TITLE_PADDING).verticalAlignment(VerticalAlignment.BOTTOM)

                .add(TextCell.builder().font(TITLE_FONT).fontSize(TITLE_FONT_SIZE).colSpan(4)
                        .horizontalAlignment(HorizontalAlignment.LEFT).text(title).textColor(summaryConfig.titleColor())
                        .build())

                .add(TextCell.builder().font(ReportFont.REGULAR_FONT).fontSize(14)
                        .horizontalAlignment(HorizontalAlignment.RIGHT).text(now).textColor(summaryConfig.dateColor())
                        .build())
                .build()).addRow(DashboardDisplayUtil.spacerRow());

    }

    private void buildVersionDisplay() {
        List<String> reportProperties = Property.getReportProperties();
        for (String key : reportProperties) {
            tableBuilder
                    .addRow(Row.builder().font(ReportFont.ITALIC_FONT).fontSize(14).borderWidth(1f)
                            .borderColor(Color.LIGHT_GRAY).padding(8f)
                            .add(TextCell.builder().text(key).colSpan(2).build())
                            .add(TextCell.builder().text(Property.getReportPropertyValue(key)).colSpan(3).build())
                            .build())
                    .addRow(DashboardDisplayUtil.spacerRow());
        }
    }

    private void statisticsRowDisplay() {

        final String testRunStart = "Start : " + DateUtil.formatDateTimeWithMillis(summaryData.getTestRunStartTime());
        final String testRunEnd = "End : " + DateUtil.formatDateTimeWithMillis(summaryData.getTestRunEndTime());
        final String testRunDuration = "Duration : " + DateUtil.durationValue(summaryData.getTestRunDuration());

        tableBuilder.addRow(Row.builder().font(ReportFont.BOLD_ITALIC_FONT).fontSize(13)
                .backgroundColor(Color.LIGHT_GRAY).borderWidth(1f).borderColor(Color.BLACK).padding(8f)

                .add(TextCell.builder().text(testRunStart).textColor(summaryConfig.timeColor()).build())
                .add(DashboardDisplayUtil.spacerCell())

                .add(TextCell.builder().text(testRunEnd).textColor(summaryConfig.timeColor()).build())
                .add(DashboardDisplayUtil.spacerCell())

                .add(TextCell.builder().text(testRunDuration).fontSize(15).textColor(summaryConfig.timeColor()).build())
                .build()).addRow(DashboardDisplayUtil.spacerRow());
    }

    private void chartTitleRowDisplay() {

        tableBuilder
                .addRow(Row.builder().font(ReportFont.ITALIC_FONT).fontSize(14).borderWidth(1f)
                        .borderColor(Color.LIGHT_GRAY).padding(8f).add(TextCell.builder().text("Features").build())
                        .add(DashboardDisplayUtil.spacerCell()).add(TextCell.builder().text("Scenarios").build())
                        .add(DashboardDisplayUtil.spacerCell()).add(TextCell.builder().text("Steps").build()).build())
                .addRow(DashboardDisplayUtil.spacerRow());
    }

    private void chartDataRowDisplay() {

        tableBuilder
                .addRow(chartDataRowDisplay(Status.PASSED, summaryData.getPassedFeatures(),
                        summaryData.getPassedScenarios(), summaryData.getPassedSteps(), reportConfig.passedColor()))
                .addRow(chartDataRowDisplay(Status.FAILED, summaryData.getFailedFeatures(),
                        summaryData.getFailedScenarios(), summaryData.getFailedSteps(), reportConfig.failedColor()))
                .addRow(chartDataRowDisplay(Status.SKIPPED, summaryData.getSkippedFeatures(),
                        summaryData.getSkippedScenarios(), summaryData.getSkippedSteps(), reportConfig.skippedColor()))
                .addRow(DashboardDisplayUtil.spacerRow());
    }

    private Row chartDataRowDisplay(Status status, int featureCount, int scenarioCount, int stepCount,
                                    Color textColor) {

        return Row.builder().font(ReportFont.ITALIC_FONT).fontSize(12).backgroundColor(Color.DARK_GRAY)
                .textColor(textColor)

                .add(TextCell.builder().text(status + " - " + featureCount).build())
                .add(DashboardDisplayUtil.spacerCell())

                .add(TextCell.builder().text(status + " - " + scenarioCount).build())
                .add(DashboardDisplayUtil.spacerCell())

                .add(TextCell.builder().text(status + " - " + stepCount).build()).build();
    }

    private void croppedMessageDisplay() {

        if (optimizer.isTextTrimmed())
            CroppedMessage.builder().content(content).message(CROPPED_MESSAGE).build().displayMessage();
    }
}
