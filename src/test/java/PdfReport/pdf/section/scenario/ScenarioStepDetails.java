package PdfReport.pdf.section.scenario;

import java.awt.Color;
import java.util.List;

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
import PdfReport.pdf.data.ScenarioData;
import PdfReport.pdf.font.ReportFont;
import PdfReport.pdf.optimizer.TextLengthOptimizer;
import PdfReport.pdf.optimizer.TextSanitizer;
import PdfReport.pdf.pojo.cucumber.Scenario;
import PdfReport.pdf.structure.Display;
import PdfReport.pdf.structure.PageCreator;
import PdfReport.pdf.structure.TableCreator;
import PdfReport.pdf.structure.footer.CroppedMessage;
import PdfReport.pdf.structure.paginate.PaginationData;
import PdfReport.pdf.util.DateUtil;
import PdfReport.pdf.util.TextUtil;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ScenarioStepDetails extends Display {

    private PaginationData paginationData;

    private TableBuilder tableBuilder;

    private static final int TABLE_X_AXIS_START = 40;
    //updated y-axis start from 330 to 530 to pull table upwards on the pdf
    private static final int TABLE_Y_AXIS_START = 530;

    private static final PDFont HEADER_FONT = ReportFont.BOLD_ITALIC_FONT;
    private static final int HEADER_FONT_SIZE = 12;

    private static final PDFont NAME_FONT = ReportFont.ITALIC_FONT;
    private static final int NAME_FONT_SIZE = 11;

    //reduced from 210 to 150
    private static final float FEATURE_NAME_COLUMN_WIDTH = 150f;
    //reduced from 310 to 280 to accomodate tags column
    private static final float SCENARIO_NAME_COLUMN_WIDTH = 280f;
    private static final float HEADER_PADDING = 7f;
    private static final float DATA_PADDING = 6f;

    //set table space to 700 after removing bar chart
    //public static final float TABLE_SPACE = TABLE_Y_AXIS_START - Display.CONTENT_END_Y;
    public static final float TABLE_SPACE = 700;

    public static final TextLengthOptimizer featureNameTextOptimizer = TextLengthOptimizer.builder().font(NAME_FONT)
            .fontsize(NAME_FONT_SIZE).availableSpace(FEATURE_NAME_COLUMN_WIDTH - 2 * DATA_PADDING).maxLines(2).build();

    public static final TextLengthOptimizer scenarioNameTextOptimizer = TextLengthOptimizer.builder().font(NAME_FONT)
            .fontsize(NAME_FONT_SIZE).availableSpace(SCENARIO_NAME_COLUMN_WIDTH - 2 * DATA_PADDING).maxLines(2).build();

    private static final String CROPPED_MESSAGE = "* The feature name and/or scenario name has been cropped to fit in the available space.";

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean nameCropped;

    public static float headerRowHeight() {
        return TextUtil.builder().font(HEADER_FONT).fontSize(HEADER_FONT_SIZE).text("Scenario Name")
                .width(SCENARIO_NAME_COLUMN_WIDTH).padding(HEADER_PADDING).build().tableRowHeight();
    }

    public static TextUtil featureNameTextUtil() {
        return TextUtil.builder().font(NAME_FONT).fontSize(NAME_FONT_SIZE).text("").width(FEATURE_NAME_COLUMN_WIDTH)
                .padding(DATA_PADDING).build();
    }

    public static TextUtil scenarioNameTextUtil() {
        return TextUtil.builder().font(NAME_FONT).fontSize(NAME_FONT_SIZE).text("").width(SCENARIO_NAME_COLUMN_WIDTH)
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
            tableBuilder = Table.builder()
                    .addColumnsOfWidth(50f, 195, 445, 90f)
                    .borderColor(Color.LIGHT_GRAY).borderWidth(1);
        } else {
            tableBuilder = Table.builder()
                    .addColumnsOfWidth(50f, FEATURE_NAME_COLUMN_WIDTH, SCENARIO_NAME_COLUMN_WIDTH, 90f, 120f, 95f)
                    .borderColor(Color.LIGHT_GRAY).borderWidth(1);
        }
    }

    private void createHeaderRow() {

        if (reportConfig.isDryRun()) {
            tableBuilder.addRow(Row.builder().padding(HEADER_PADDING).horizontalAlignment(HorizontalAlignment.CENTER)
                    .verticalAlignment(VerticalAlignment.MIDDLE).font(HEADER_FONT).fontSize(HEADER_FONT_SIZE)
                    .add(TextCell.builder().text("#").build())
                    .add(TextCell.builder().text("Feature Name").horizontalAlignment(HorizontalAlignment.LEFT).build())
                    .add(TextCell.builder().text("Scenario Name").horizontalAlignment(HorizontalAlignment.LEFT).build())
                    //added header row for scenario tags
                    .add(TextCell.builder().text("Tags").textColor(reportConfig.getFeatureConfig().totalColor()).build())
                    .build());
        } else {
            tableBuilder.addRow(Row.builder().padding(HEADER_PADDING).horizontalAlignment(HorizontalAlignment.CENTER)
                    .verticalAlignment(VerticalAlignment.MIDDLE).font(HEADER_FONT).fontSize(HEADER_FONT_SIZE)
                    .add(TextCell.builder().text("#").build())
                    .add(TextCell.builder().text("Feature Name").horizontalAlignment(HorizontalAlignment.LEFT).build())
                    .add(TextCell.builder().text("Scenario Name").horizontalAlignment(HorizontalAlignment.LEFT).build())
                    //added header row for scenario tags
                    .add(TextCell.builder().text("Tags").textColor(reportConfig.getFeatureConfig().totalColor()).build())
                    .add(TextCell.builder().text("Status").textColor(reportConfig.getFeatureConfig().totalColor()).build())
                    .add(TextCell.builder().text("Duration").textColor(reportConfig.getFeatureConfig().durationColor())
                            .horizontalAlignment(HorizontalAlignment.LEFT).build())
                    .build());
        }
    }

    private void createDataRows() {

        final TextSanitizer sanitizer = TextSanitizer.builder().build();
        int sNo = paginationData.getItemFromIndex() + 1;
        final ScenarioData scenarioData = (ScenarioData) displayData;
        final List<Scenario> scenarios = scenarioData.getScenarios();

        for (int i = 0; i < scenarios.size(); i++) {
            final Scenario scenario = scenarios.get(i);

            final String featureName = sanitizer
                    .sanitizeText(featureNameTextOptimizer.optimizeTextLines(scenario.getFeature().getName()));
            final String scenarioName = sanitizer
                    .sanitizeText(scenarioNameTextOptimizer.optimizeTextLines(scenario.getName()));

            if (featureNameTextOptimizer.isTextTrimmed() || scenarioNameTextOptimizer.isTextTrimmed())
                nameCropped = true;

            Annotation featureAnnotation = Annotation.builder().title(featureName).build();
            Annotation scenarioAnnotation = Annotation.builder().title(scenarioName).build();
            if (reportConfig.isDryRun()) {
                tableBuilder.addRow(Row.builder().padding(DATA_PADDING).font(NAME_FONT).fontSize(NAME_FONT_SIZE)
                        .horizontalAlignment(HorizontalAlignment.CENTER).verticalAlignment(VerticalAlignment.TOP)

                        .add(TextCell.builder().text(String.valueOf(sNo)).fontSize(8).build())

                        .add(createNameCell(featureName, featureAnnotation))
                        .add(createNameCell(scenarioName, scenarioAnnotation))
                        //added builder for scenario tag info 24 March 2021
                        .add(TextCell.builder().text(String.valueOf(scenario.getTestIdTag()))
                                .textColor(reportConfig.getFeatureConfig().totalColor()).build())
                        .build());
            } else {
                tableBuilder.addRow(Row.builder().padding(DATA_PADDING).font(NAME_FONT).fontSize(NAME_FONT_SIZE)
                        .horizontalAlignment(HorizontalAlignment.CENTER).verticalAlignment(VerticalAlignment.TOP)

                        .add(TextCell.builder().text(String.valueOf(sNo)).fontSize(8).build())

                        .add(createNameCell(featureName, featureAnnotation))
                        .add(createNameCell(scenarioName, scenarioAnnotation))
                        //added builder for scenario tag info 24 March 2021
                        .add(TextCell.builder().text(String.valueOf(scenario.getTestIdTag()))
                                .textColor(reportConfig.getFeatureConfig().totalColor()).build())
                        //added scenario status column data
                        .add(TextCell.builder().text(String.valueOf(scenario.getStatus()))
                                .textColor(reportConfig.statusColor(scenario.getStatus())).build())
                        .add(TextCell.builder().text(DateUtil.durationValue(scenario.calculatedDuration()))
                                .textColor(reportConfig.getFeatureConfig().durationColor())
                                .horizontalAlignment(HorizontalAlignment.LEFT).build())
                        .build());
            }

            scenario.getFeature().addAnnotation(featureAnnotation);
            scenario.addAnnotation(scenarioAnnotation);

            sNo++;
        }
    }

    private void drawTable() {

        TableCreator tableDrawer = TableCreator.builder().tableBuilder(tableBuilder).document(document)
                .startX(TABLE_X_AXIS_START).startY(TABLE_Y_AXIS_START)
                .pageSupplier(PageCreator.builder().document(document).build().landscapePageSupplier()).build();
        tableDrawer.displayTable();
    }

    private AbstractCell createNameCell(String title, Annotation annotation) {

        if (reportConfig.isDisplayScenario() && reportConfig.isDisplayDetailed()) {
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
