package PdfReportGenerator.logging;

import org.apache.commons.logging.Log;

public class PdfReportLogger {
    private Log logger;

    public void initializeLogger(Log mojoLogger) {
        this.logger = mojoLogger;
    }

    public void debug(CharSequence seq) {
        logger.debug(seq);
    }

    public void error(CharSequence seq) {
        logger.error(seq);
    }

    public void info(CharSequence seq) {
        logger.info(seq);
    }

    public void warn(CharSequence seq) {
        logger.warn(seq);
    }
}
