package PdfReportGenerator.exception;

public class PdfReportPluginException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public PdfReportPluginException(String message) {
        super(message);
    }

    public PdfReportPluginException(String message, Exception exception) {
        super(message, exception);
    }
}
