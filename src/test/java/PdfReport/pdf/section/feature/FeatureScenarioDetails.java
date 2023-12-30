package PdfReport.pdf.section.feature;

import java.awt.Color;
import java.time.Duration;
import java.util.List;

import PdfReportGenerator.ReportGenerator;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.Table.TableBuilder;
import org.vandeseer.easytable.structure.cell.AbstractCell;
import org.vandeseer.easytable.structure.cell.TextCell;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.annotation.Annotation;
import PdfReport.pdf.annotation.cell.TextLinkCell;
import PdfReport.pdf.data.FeatureData;
import PdfReport.pdf.font.ReportFont;
import PdfReport.pdf.optimizer.TextLengthOptimizer;
import PdfReport.pdf.optimizer.TextSanitizer;
import PdfReport.pdf.pojo.cucumber.Feature;
import PdfReport.pdf.structure.Display;
import PdfReport.pdf.structure.PageCreator;
import PdfReport.pdf.structure.TableCreator;
import PdfReport.pdf.structure.footer.CroppedMessage;
import PdfReport.pdf.structure.paginate.PaginationData;
import PdfReport.pdf.util.DateUtil;
import PdfReport.pdf.util.TextUtil;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class FeatureScenarioDetails extends Display {

    private PaginationData paginationData;

    private TableBuilder tableBuilder;

    private static final int TABLE_X_AXIS_START = 40;
    //updating from 330 to 530
    private static final int TABLE_Y_AXIS_START = ReportGenerator.dryRun ? 530 : 330;

    private static final PDFont HEADER_FONT = ReportFont.BOLD_ITALIC_FONT;
    private static final int HEADER_FONT_SIZE = 12;

    private static final PDFont NAME_FONT = ReportFont.ITALIC_FONT;
    private static final int NAME_FONT_SIZE = 11;

    private static final float FEATURE_NAME_COLUMN_WIDTH = 490f;
    private static final float HEADER_PADDING = 7f;
    private static final float DATA_PADDING = 6f;

    public static final float TABLE_SPACE = TABLE_Y_AXIS_START - Display.CONTENT_END_Y;

    public static final TextLengthOptimizer featureNameTextOptimizer = TextLengthOptimizer.builder().font(NAME_FONT)
            .fontsize(NAME_FONT_SIZE).availableSpace(FEATURE_NAME_COLUMN_WIDTH - 2 * DATA_PADDING).maxLines(2).build();

    private static final String CROPPED_MESSAGE = "* The feature name has been cropped to fit in the available space.";

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean nameCropped;

    public static float headerRowHeight() {
        return TextUtil.builder().font(HEADER_FONT).fontSize(HEADER_FONT_SIZE).text("Feature Name")
                .width(FEATURE_NAME_COLUMN_WIDTH).padding(HEADER_PADDING).build().tableRowHeight();
    }

    public static TextUtil featureNameTextUtil() {
        return TextUtil.builder().font(NAME_FONT).fontSize(NAME_FONT_SIZE).text("").width(FEATURE_NAME_COLUMN_WIDTH)
                .padding(DATA_PADDING).build();
    }

    @Override
    public void display() {

        createTableBuilder();
        createHeaderRow();
        createDataRows();
        drawTable();
        croppedMessageDisplay();
    }

    private void createTableBuilder() {
        if (reportConfig.isDryRun()) {
            tableBuilder = Table.builder().addColumnsOfWidth(50f, FEATURE_NAME_COLUMN_WIDTH, 220f)
                    .borderColor(Color.LIGHT_GRAY).borderWidth(1);
        } else {
            tableBuilder = Table.builder().addColumnsOfWidth(30f, FEATURE_NAME_COLUMN_WIDTH, 35f, 35f, 35f, 35f, 100f)
                    .borderColor(Color.LIGHT_GRAY).borderWidth(1);
        }
    }

    private void createHeaderRow() {
        if (reportConfig.isDryRun()) {
            tableBuilder.addRow(Row.builder().padding(HEADER_PADDING).horizontalAlignment(HorizontalAlignment.CENTER)
                    .font(HEADER_FONT).fontSize(HEADER_FONT_SIZE).verticalAlignment(VerticalAlignment.MIDDLE)
                    .add(TextCell.builder().text("#").build())
                    .add(TextCell.builder().text("Feature Name").horizontalAlignment(HorizontalAlignment.LEFT).build())
                    .add(TextCell.builder().text("Scenario Count").textColor(reportConfig.getFeatureConfig().totalColor()).build())
                    .build());
        } else {
            tableBuilder.addRow(Row.builder().padding(HEADER_PADDING).horizontalAlignment(HorizontalAlignment.CENTER)
                    .font(HEADER_FONT).fontSize(HEADER_FONT_SIZE).verticalAlignment(VerticalAlignment.MIDDLE)
                    .add(TextCell.builder().text("#").build())
                    .add(TextCell.builder().text("Feature Name").horizontalAlignment(HorizontalAlignment.LEFT).build())
                    .add(TextCell.builder().text("T").textColor(reportConfig.getFeatureConfig().totalColor()).build())
                    .add(TextCell.builder().text("P").textColor(reportConfig.passedColor()).build())
                    .add(TextCell.builder().text("F").textColor(reportConfig.failedColor()).build())
                    .add(TextCell.builder().text("S").textColor(reportConfig.skippedColor()).build())
                    .add(TextCell.builder().text("Duration").textColor(reportConfig.getFeatureConfig().durationColor())
                            .horizontalAlignment(HorizontalAlignment.LEFT).build())
                    .build());
        }
    }

    private void createDataRows() {

        final TextSanitizer sanitizer = TextSanitizer.builder().build();
        int sNo = paginationData.getItemFromIndex() + 1;
        final FeatureData featureData = (FeatureData) displayData;
        final List<Feature> features = featureData.getFeatures();

        for (int i = 0; i < features.size(); i++) {
            Feature feature = features.get(i);

            final String featureName = sanitizer
                    .sanitizeText(featureNameTextOptimizer.optimizeTextLines(feature.getName()));
            final Annotation annotation = Annotation.builder().title(featureName).build();

            if (featureNameTextOptimizer.isTextTrimmed())
                nameCropped = true;
            if (reportConfig.isDryRun()) {
                tableBuilder.addRow(Row.builder().padding(DATA_PADDING).font(NAME_FONT).fontSize(NAME_FONT_SIZE)
                        .horizontalAlignment(HorizontalAlignment.CENTER).verticalAlignment(VerticalAlignment.TOP)

                        .add(TextCell.builder().text(String.valueOf(sNo)).fontSize(8).build())

                        .add(createFeatureNameCell(featureName, annotation))

                        .add(TextCell.builder().text(String.valueOf(feature.getTotalScenarios()))
                                .textColor(reportConfig.getFeatureConfig().totalColor()).build())
                        .build());
            } else {
                tableBuilder.addRow(Row.builder().padding(DATA_PADDING).font(NAME_FONT).fontSize(NAME_FONT_SIZE)
                        .horizontalAlignment(HorizontalAlignment.CENTER).verticalAlignment(VerticalAlignment.TOP)

                        .add(TextCell.builder().text(String.valueOf(sNo)).fontSize(8).build())

                        .add(createFeatureNameCell(featureName, annotation))

                        .add(TextCell.builder().text(String.valueOf(feature.getTotalScenarios()))
                                .textColor(reportConfig.getFeatureConfig().totalColor()).build())
                        .add(TextCell.builder().text(String.valueOf(feature.getPassedScenarios()))
                                .textColor(reportConfig.passedColor()).build())
                        .add(TextCell.builder().text(String.valueOf(feature.getFailedScenarios()))
                                .textColor(reportConfig.failedColor()).build())
                        .add(TextCell.builder().text(String.valueOf(feature.getSkippedScenarios()))
                                .textColor(reportConfig.skippedColor()).build())

                        .add(TextCell.builder().text(DateUtil.durationValue(feature.calculatedDuration()))
                                .textColor(reportConfig.getFeatureConfig().durationColor())
                                .horizontalAlignment(HorizontalAlignment.LEFT).build())
                        .build());
            }
            if (i == features.size() - 1) {
                Integer totalScenarios = 0;
                Integer totalPassed = 0;
                Integer totalFailed = 0;
                Integer totalSkipped = 0;
                Duration totalDuration = Duration.ZERO;
                for (Feature f : features) {
                    totalScenarios = totalScenarios + f.getTotalScenarios();
                    totalPassed = totalPassed + f.getPassedScenarios();
                    totalFailed = totalFailed + f.getFailedScenarios();
                    totalSkipped = totalSkipped + f.getSkippedScenarios();
                    totalDuration = totalDuration.plus(f.calculatedDuration());
                }
                if (reportConfig.isDryRun()) {
                    tableBuilder.addRow(Row.builder().padding(DATA_PADDING).font(NAME_FONT).fontSize(NAME_FONT_SIZE)
                            .horizontalAlignment(HorizontalAlignment.CENTER).verticalAlignment(VerticalAlignment.TOP)
                            .add(TextCell.builder().text("")
                                    .textColor(reportConfig.getFeatureConfig().totalColor()).build())
                            .add(TextCell.builder().text("Total")
                                    .textColor(reportConfig.getFeatureConfig().totalColor()).horizontalAlignment(HorizontalAlignment.LEFT)
                                    .build())
                            .add(TextCell.builder().text(totalScenarios.toString())
                                    .textColor(reportConfig.getFeatureConfig().totalColor())
                                    .build())
                            .build()
                    ).build();
                } else {
                    tableBuilder.addRow(Row.builder().padding(DATA_PADDING).font(NAME_FONT).fontSize(NAME_FONT_SIZE)
                            .horizontalAlignment(HorizontalAlignment.CENTER).verticalAlignment(VerticalAlignment.TOP)
                            .add(TextCell.builder().text("")
                                    .textColor(reportConfig.getFeatureConfig().totalColor()).build())
                            .add(TextCell.builder().text("Total")
                                    .textColor(reportConfig.getFeatureConfig().totalColor()).horizontalAlignment(HorizontalAlignment.LEFT)
                                    .build())
                            .add(TextCell.builder().text(totalScenarios.toString())
                                    .textColor(reportConfig.getFeatureConfig().totalColor())
                                    .build())
                            .add(TextCell.builder().text(totalPassed.toString())
                                    .textColor(reportConfig.passedColor())
                                    .build())
                            .add(TextCell.builder().text(totalFailed.toString())
                                    .textColor(reportConfig.failedColor())
                                    .build())
                            .add(TextCell.builder().text(totalSkipped.toString())
                                    .textColor(reportConfig.skippedColor())
                                    .build())
                            .add(TextCell.builder().text(DateUtil.durationValue(totalDuration))
                                    .textColor(reportConfig.getFeatureConfig().durationColor()).horizontalAlignment(HorizontalAlignment.LEFT)
                                    .build())
                            .build()
                    ).build();
                }
            }
            feature.addAnnotation(annotation);
            sNo++;
        }
    }

    private void drawTable() {

        TableCreator tableDrawer = TableCreator.builder().tableBuilder(tableBuilder).document(document)
                .startX(TABLE_X_AXIS_START).startY(TABLE_Y_AXIS_START)
                .pageSupplier(PageCreator.builder().document(document).build().landscapePageSupplier()).build();
        tableDrawer.displayTable();
    }

    private AbstractCell createFeatureNameCell(String title, Annotation annotation) {

        if (reportConfig.isDisplayFeature() && reportConfig.isDisplayDetailed()) {
            return TextLinkCell.builder().annotation(annotation).text(title)
                    .horizontalAlignment(HorizontalAlignment.LEFT).build();
        }
        return TextCell.builder().text(title).horizontalAlignment(HorizontalAlignment.LEFT).build();
    }

    private void croppedMessageDisplay() {

        if (nameCropped)
            CroppedMessage.builder().content(content).message(CROPPED_MESSAGE).build().displayMessage();
    }
}
