package Utilities;

import static Utilities.Constants.ReportScreens;
import static Utilities.Constants.USER_WORK_DIR;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Common {

	public static void sleep(long number, TimeUnit units) {
		try {
			units.sleep(number);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void killTask(String processName) {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM " + processName + ".exe /T");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String GetNvalue() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.format(date);
	}

	public static String GetTestDate(int numOfDays) {
		String N = GetNvalue();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(N));
		} catch (ParseException e) {
			e.getMessage();
		}
		c.add(Calendar.DAY_OF_MONTH, numOfDays);
		return sdf.format(c.getTime());
	}

	public static Date StringToDate(String dateForConversion) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try {
			date = formatter.parse(dateForConversion);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void deleteRichViewerCache() {
		killTask("viewerc");
		String filepath = Constants.USER_HOME_DIR + Constants.appdataCache2Folder;
		File directoryName = new File(filepath);
		if (!directoryName.exists()) {
			System.out.println("Directory does not exist.");
		} else {
			deleteDirectory(directoryName);
		}
		System.out.println("cache2 folder successfully deleted");
	}

	public static void deleteDirectory(File file) {
		if (file.isDirectory()) {
			if (Objects.requireNonNull(file.list()).length != 0) {
				String[] files = file.list();

				for (String temp : Objects.requireNonNull(files)) {
					File fileDelete = new File(file, temp);
					deleteDirectory(fileDelete);
				}
			}
		}
		//noinspection ResultOfMethodCallIgnored
		file.delete();
	}

	public static ZonedDateTime getCurrentEasternDateTime() {
		ZoneId estTimeZoneId = ZoneId.of("US/Eastern");
		return ZonedDateTime.now(estTimeZoneId).truncatedTo(ChronoUnit.MINUTES);
	}

	public static void deleteExistingFile(String filepath) {
		File fileToBeDeleted = new File(filepath);
		if (fileToBeDeleted.exists()) {
			//noinspection ResultOfMethodCallIgnored
			fileToBeDeleted.delete();
		} else {
			Log.printInfo("No existing file found");
		}
	}

	public static String getLatestModifiedFile(String filePath) {
		File latestModifiedFile;
		String latestModifiedFileName = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*");
		File[] files = dir.listFiles(fileFilter);

		if (Objects.requireNonNull(files).length > 0) {
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			latestModifiedFile = files[0];
			latestModifiedFileName = latestModifiedFile.toString();
		}
		return latestModifiedFileName;
	}

	public static void unzipFile(String zipFileName, String unzipDestination) {
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			zipFile.extractAll(unzipDestination);
		} catch (ZipException e) {
			Log.printError("No zip file found");
		}
	}

	public static void deleteExistingFolder(String existingFolderPath) {
		if (existingFolderPath == null) {
			Log.printInfo("No existing folder found");
		}

		else {
			File directoryName = new File(existingFolderPath);
			if (!directoryName.exists()) {
				Log.printInfo("File does not exist");
			} else {
				deleteDirectory(directoryName);
				Log.printInfo("Folder successfully deleted");
			}
		}
	}

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		return sdf.format(c.getTime());
	}

	public static void deleteAllFilesWithinFolder(String directoryPath) {
		File file = new File(directoryPath);
		try {
			FileUtils.cleanDirectory(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void openImagesInSystemImageViewer(String imagePath) {
		File file = new File(imagePath);
		Desktop desktop = Desktop.getDesktop();
		if (file.exists()) {
			try {
				desktop.open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getFilesCount(String filePath, String fileExtension) {
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + fileExtension);
		File[] files = dir.listFiles(fileFilter);
		return Objects.requireNonNull(files).length;
	}

	public static void createDirectory(String directoryPath) {
		File file = new File(directoryPath);
		if (file.exists()) {
			Log.printInfo("Folder already present" + directoryPath);
		} else {
			//noinspection ResultOfMethodCallIgnored
			file.mkdir();
			Log.printInfo("Folder created" + directoryPath);
		}
	}

	public static int getRowCountFromCsv(String filepath) {
		List<String> list = null;
		try {
			list = Files.readAllLines(Paths.get(filepath));
		} catch (IOException e) {
			Log.printError("File does not exist");
		}
		return Objects.requireNonNull(list).size() - 1;
	}

	public static int getColumnIndexFromCsv(String filepath, String columnName) {
		List<String> columnList;
		int columnIndex = 0;
		try {
			columnList = Files.readAllLines(Paths.get(filepath));
			List<String> columns = Arrays.asList(columnList.get(0).split(","));
			columns = columns.stream().map(name -> name.replace("\"", "").toUpperCase()).collect(Collectors.toList());
			columnIndex = columns.indexOf(columnName);
		} catch (IOException e) {
			Log.printError("File does not exist");
		}
		return columnIndex;
	}

	public static List<String> getColumnDataFromCsv(String columnName, String filepath) {
		List<String> columnValueList;
		List<String> filteredColumnList = new ArrayList<>();
		String exportedFile = Common.getLatestModifiedFile(filepath);
		try {
			columnValueList = Files.readAllLines(Paths.get(exportedFile));
			for (String lines : columnValueList) {
				filteredColumnList.add(lines.split(",")[Common.getColumnIndexFromCsv(exportedFile, columnName)]);
			}
			filteredColumnList = filteredColumnList.stream().map(name -> name.replace("\"", ""))
					.collect(Collectors.toList());
			filteredColumnList.remove(0);

		} catch (Exception e) {
			Log.printError("File does not exist");
		}
		return filteredColumnList;
	}

	public static boolean isFolderPresent(String filepath) {
		File fileToCheck = new File(filepath);
		return fileToCheck.exists();
	}

	public static String getReportLocation(String reportName) {
		String reportFolder = USER_WORK_DIR + ReportScreens + reportName + "\\";
		if (!isFolderPresent(reportFolder)) {
			reportFolder = USER_WORK_DIR + ReportScreens + reportName.replace(" ", "") + "\\";
		}
		return reportFolder;
	}

	public static void waitForFileToExist(String filepath) {
		File fileToCheck = new File(filepath);
		while(!fileToCheck.exists()) {
			Common.sleep(1, TimeUnit.SECONDS);
		}
	}
}
