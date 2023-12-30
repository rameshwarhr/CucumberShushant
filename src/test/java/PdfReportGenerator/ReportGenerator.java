package PdfReportGenerator;

import java.nio.file.Path;
import java.util.List;

import PdfReportGenerator.data.PdfReportDataGenerator;
import PdfReportGenerator.json.JsonFileConverter;
import PdfReportGenerator.json.JsonPathCollector;
import PdfReportGenerator.logging.PdfReportLogger;
import PdfReportGenerator.pojo.Feature;
import PdfReportGenerator.processor.EmbeddedProcessor;
import PdfReportGenerator.processor.HierarchyProcessor;
import PdfReportGenerator.properties.ReportProperties;
import Utilities.Constants;
import PdfReport.pdf.PDFCucumberReport;
import PdfReport.pdf.data.ReportData;

import javax.inject.Inject;

public class ReportGenerator {
    /*PdfReportLogger logger;
    ReportProperties reportProperties;
    JsonPathCollector jsonPathCollector;
    JsonFileConverter jsonFileConverter;
    HierarchyProcessor hierarchyProcessor;*/

    private static String strictCucumber6Behavior = "false";
    private static String cucumberJsonReportDirectory = Constants.USER_WORK_DIR + "//target//cucumber-report";
    private static String cucumberPdfReportDirectory = "pdf-report";
    public static boolean dryRun = true;

    /*@Inject
    public ReportGenerator(JsonPathCollector jsonPathCollector, JsonFileConverter jsonFileConverter, ReportProperties reportProperties, PdfReportLogger logger, HierarchyProcessor hierarchyProcessor) {
        this.logger = logger;
        this.reportProperties = reportProperties;
        this.jsonPathCollector = jsonPathCollector;
        this.jsonFileConverter = jsonFileConverter;
        this.hierarchyProcessor = hierarchyProcessor;
    }*/

    public static void execute() {
        //PdfReportLogger logger = new PdfReportLogger();
        ReportProperties reportProperties = new ReportProperties();
        JsonPathCollector jsonPathCollector = new JsonPathCollector();
        EmbeddedProcessor embeddedProcessor = new EmbeddedProcessor(reportProperties);
        JsonFileConverter jsonFileConverter = new JsonFileConverter(embeddedProcessor);
        HierarchyProcessor hierarchyProcessor = new HierarchyProcessor(reportProperties);
        try {

            //logger.info("STARTED CUCUMBER PDF REPORT GENERATION PLUGIN");

            reportProperties.setStrictCucumber6Behavior(strictCucumber6Behavior);

            List<Path> jsonFilePaths = jsonPathCollector.retrieveFilePaths(cucumberJsonReportDirectory);
            List<Feature> features = jsonFileConverter.retrieveFeaturesFromReport(jsonFilePaths);
            hierarchyProcessor.process(features);

            PdfReportDataGenerator generator = new PdfReportDataGenerator();
            ReportData reportData = generator.generateReportData(features);

            PDFCucumberReport pdfCucumberReport = new PDFCucumberReport(reportData, cucumberPdfReportDirectory);
            pdfCucumberReport.createReport();

            //logger.info("FINISHED CUCUMBER PDF REPORT GENERATION PLUGIN");
        } catch (Throwable t) {
            // Report will not result in build failure.
            t.printStackTrace();
            //logger.error(String.format("STOPPING CUCUMBER PDF REPORT GENERATION - %s", t.getMessage()));
        }
    }
}
