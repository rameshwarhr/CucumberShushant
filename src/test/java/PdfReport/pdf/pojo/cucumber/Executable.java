package PdfReport.pdf.pojo.cucumber;

import java.util.List;

import PdfReport.pdf.section.details.executable.ExecutableDisplay;

public interface Executable {

	ExecutableDisplay getDisplay();

	List<String> getOutput();

	Status getStatus();

	String getErrorMessage();

	List<String> getMedia();

	void checkData();
}
