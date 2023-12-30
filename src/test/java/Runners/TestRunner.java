package Runners;

import PdfReport.pdf.config.ReportConfig;
import PdfReportGenerator.ReportGenerator;
import Utilities.PdfReader;
import Utilities.WinDriver;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src\\test\\java\\Features",
        glue = "Steps",
        plugin = {"json:target/cucumber-report/cucumber.json", "pretty"},
        monochrome = true)
public class TestRunner {

    @AfterClass
    public static void generatePdfReport() {
        ReportGenerator.execute();
        PdfReader.truncatePdfReport(ReportGenerator.dryRun);
        WinDriver.stopWiniumService();
    }
}