package PdfReportGenerator.processor;

public class NoImageFile {

    public static final String BASE64_STR = "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";

}
