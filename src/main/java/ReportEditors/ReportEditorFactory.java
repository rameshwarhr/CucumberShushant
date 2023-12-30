package ReportEditors;

public class ReportEditorFactory {

    public ReportEditor getReportEditor(String reportName, String sectionName, String tabName) {
        if (reportName.equalsIgnoreCase("OB Ultrasound Final Report")) {
            return new UltrasoundFinalReportEditor(sectionName, tabName);
        }
        if (reportName.equalsIgnoreCase("Pelvic Ultrasound Final Report")) {
            return new PelvicUltrasoundFinalReportEditor(sectionName, tabName);
        }
        if (reportName.equalsIgnoreCase("Fetal TTE Report(Standard)")) {
            return new FetalTTEReportStandardEditor(sectionName, tabName);
        }
        if (reportName.equalsIgnoreCase("General - Final Report")) {
            return new GeneralUltrasoundFinalReportEditor(sectionName, tabName);
        }
        if (reportName.equalsIgnoreCase("General Abdominal Final Report")) {
            return new GeneralAbdominalFinalReportEditor(reportName, sectionName, tabName);
        }
        if (reportName.equalsIgnoreCase("Breast - Final Report")) {
            return new BreastFinalReportEditor(reportName, sectionName, tabName);
        }
        if (reportName.equalsIgnoreCase("Thyroid - Final Report")) {
            return new ThyroidFinalReportEditor(reportName, sectionName, tabName);
        }
        return null;
    }
}
