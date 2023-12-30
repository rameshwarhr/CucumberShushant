package PdfReport.pdf.section.dashboard;

import PdfReport.pdf.config.ReportConfig;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.Table.TableBuilder;

import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import PdfReport.pdf.destination.Destination;
import PdfReport.pdf.destination.DestinationAware;
import PdfReport.pdf.outline.Outline;
import PdfReport.pdf.structure.Display;
import PdfReport.pdf.structure.SinglePageSection;

@SuperBuilder
public class Dashboard extends SinglePageSection implements DestinationAware {

    static final float DATA_COLUMN_WIDTH = 220;
    static final float SPACE_COLUMN_WIDTH = 20;

    @Override
    @SneakyThrows
    public void createSection() {
        boolean isDryRun = reportConfig.isDryRun();
        if (isDryRun) {
            createDryRunSection();
        } else {
            createExecutionSection();
        }
    }

    @SneakyThrows
    public void createDryRunSection() {
        page = createPage();

        try (final PDPageContentStream content = new PDPageContentStream(document, page, AppendMode.APPEND, true)) {

            final TableBuilder tableBuilder = Table.builder()
                    .addColumnsOfWidth(DATA_COLUMN_WIDTH, SPACE_COLUMN_WIDTH, DATA_COLUMN_WIDTH, SPACE_COLUMN_WIDTH,
                            DATA_COLUMN_WIDTH)
                    .borderWidth(0f).horizontalAlignment(HorizontalAlignment.CENTER)
                    .verticalAlignment(VerticalAlignment.MIDDLE).padding(5f);

            DashboardDetailsDisplay.builder().tableBuilder(tableBuilder).reportConfig(reportConfig)
                    .displayData(displayData).content(content).build().display();

            /*DashboardDonutDisplay.builder().tableBuilder(tableBuilder).reportConfig(reportConfig)
                    .displayData(displayData).document(document).build().display();*/

            TableDrawer.builder().table(tableBuilder.build()).startX(70f).startY(Display.CONTENT_START_Y)
                    .contentStream(content).build().draw();
        }
        createDestination();

    }

    @SneakyThrows
    public void createExecutionSection() {
        page = createPage();

        try (final PDPageContentStream content = new PDPageContentStream(document, page, AppendMode.APPEND, true)) {

            final TableBuilder tableBuilder = Table.builder()
                    .addColumnsOfWidth(DATA_COLUMN_WIDTH, SPACE_COLUMN_WIDTH, DATA_COLUMN_WIDTH, SPACE_COLUMN_WIDTH,
                            DATA_COLUMN_WIDTH)
                    .borderWidth(0f).horizontalAlignment(HorizontalAlignment.CENTER)
                    .verticalAlignment(VerticalAlignment.MIDDLE).padding(5f);

            DashboardDetailsDisplay.builder().tableBuilder(tableBuilder).reportConfig(reportConfig)
                    .displayData(displayData).content(content).build().display();

            DashboardDonutDisplay.builder().tableBuilder(tableBuilder).reportConfig(reportConfig)
                    .displayData(displayData).document(document).build().display();

            TableDrawer.builder().table(tableBuilder.build()).startX(70f).startY(Display.CONTENT_START_Y)
                    .contentStream(content).build().draw();
        }
        createDestination();
    }

    @Override
    public void createDestination() {
        Destination destination = Destination.builder().name(Outline.DASHBOARD_SECTION_TEXT)
                .yCoord((int) page.getMediaBox().getHeight()).page(page).build();
        destinations.setDashboardDestination(destination);
    }
}
