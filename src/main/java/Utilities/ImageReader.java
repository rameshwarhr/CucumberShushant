package Utilities;

import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ImageReader {
	Tesseract instance;

	public ImageReader() {
		instance = new Tesseract();
	}

	public String getImageNumbers(BufferedImage image, int engineMode) {
		try {
			instance.setDatapath(Constants.USER_WORK_DIR + "\\tessdata");
			instance.setOcrEngineMode(engineMode);
			if (engineMode == 0) {
				instance.setTessVariable("tessedit_char_whitelist", "0123456789");
			}
			return instance.doOCR(image);
		} catch (TesseractException e) {
			e.getMessage();
			return "Error while reading image";
		}
	}
}
