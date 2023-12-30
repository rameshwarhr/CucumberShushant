package TestData;

import static Utilities.Constants.USER_WORK_DIR;
import static Utilities.Constants.UserDetailsFile;
import static Utilities.Constants.PatientDetailsFile;

import Utilities.YmlReader;

public class TestData {

	public static UserDetails userDetails = YmlReader.getObjectRepository(USER_WORK_DIR + UserDetailsFile,
			UserDetails.class);
	public static PatientDetails patientDetails = YmlReader.getObjectRepository(USER_WORK_DIR + PatientDetailsFile,
			PatientDetails.class);
}