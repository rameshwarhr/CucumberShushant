package Utilities;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import io.restassured.RestAssured;

public class PdfReader {

    private String readPDFContent() throws Exception {

        PDDocument document = PDDocument.load(new File(System.getProperty("user.dir") + "\\target\\report.pdf"));
        StringBuilder s = new StringBuilder();
        if (!document.isEncrypted()) {

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);

            // split by whitespace
            String[] lines = pdfFileInText.split("\\r?\\n");
            for (String line : lines) {
                s.append(line);
            }
        }
        return s.toString();
    }

    private String readPDFContent(String filePath) throws Exception {

        PDDocument document = PDDocument.load(new File(filePath));
        String pdfFileInText = "";
        if (!document.isEncrypted()) {

            PDFTextStripper tStripper = new PDFTextStripper();
            tStripper.setAddMoreFormatting(true);
            tStripper.setSortByPosition(true);
            pdfFileInText = tStripper.getText(document);
        }
        document.close();
        return pdfFileInText;
    }

    private String readPDFContentWithLayout(String filePath) throws Exception {

        PDDocument document = PDDocument.load(new File(filePath));
        String pdfFileInText = "";
        if (!document.isEncrypted()) {

            LayoutTextStripper tStripper = new LayoutTextStripper();
            tStripper.setSortByPosition(true);
            tStripper.fixedCharWidth = 4;
            pdfFileInText = tStripper.getText(document);
        }
        document.close();
        return pdfFileInText;
    }

    public static void truncatePdfReport(boolean isDryRun) {
        try {
            String fileName = isDryRun ? "ValidationProtocol.pdf" : "ValidationExecutionReport.pdf";
            File file = new File(Constants.USER_WORK_DIR + "\\pdf-report\\" + fileName);
            PDDocument testDetailsFile = PDDocument.load(file);
            PDDocument stepDetailsFile = PDDocument.load(file);
            testDetailsFile = removeDocumentSection(testDetailsFile, "DETAILED SECTION");
            testDetailsFile.save(file);
            testDetailsFile.close();
            if (isDryRun) {
                stepDetailsFile = removeDocumentSection(stepDetailsFile, "SCENARIOS SUMMARY");
                stepDetailsFile.save(new File(Constants.USER_WORK_DIR + "\\pdf-report\\ExecutionSteps.pdf"));
                stepDetailsFile.close();
            }
        } catch (Exception e) {
            Log.printError("Error while truncating report pdf");
        }
    }

    private static PDDocument removeDocumentSection(PDDocument doc, String sectionName) {
        try {
            PDFTextStripper tStripper = new PDFTextStripper();
            int numberOfPages = doc.getNumberOfPages();
            int trimStartPage = 0;
            int sectionPageCount = 0;
            for (int pageNo = 1; pageNo <= numberOfPages; pageNo++) {
                tStripper.setStartPage(pageNo);
                tStripper.setEndPage(pageNo);
                if (tStripper.getText(doc).contains(sectionName)) {
                    if (trimStartPage < 1)
                        trimStartPage = pageNo;
                    sectionPageCount++;
                }
            }
            for (int pageCount = 0; pageCount < sectionPageCount; pageCount++) {
                doc.removePage(trimStartPage - 1);
            }
            return doc;
        } catch (Exception e) {
            Log.printError("Error while truncating document");
            return doc;
        }
    }

    public String getPDFData(String pdfUrl, String username, String password) {

        String text = "";
        byte[] pdf = RestAssured.given().relaxedHTTPSValidation().contentType("application/pdf").auth()
                .basic(username, password).accept("*/*").when().post(pdfUrl).then().statusCode(200).extract()
                .asByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "\\target\\report.pdf");
            fos.write(pdf);
            fos.close();
            text = readPDFContent();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;

    }

    public String getPDFData(String pdfFilepath) {
        String text;
        try {
            text = readPDFContent(pdfFilepath);
            return text;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String getPDFDataWithLayout(String pdfFilepath) {
        String text;
        try {
            text = readPDFContentWithLayout(pdfFilepath);
            return text;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
