package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {

	private static String dir = System.getProperty("user.dir");

	private static ExtentReports extent;
	private static String filePath = dir + "\\TestReport.html";
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentTest test;
	// static String workingDir = System.getProperty("user.dir");

	public static ExtentTest createTest(String name, String description) {
		test = extent.createTest(name, description);
		return test;
	}

	public static ExtentReports GetExtent(String reportTitle, String reportName) {
		if (extent != null) {
			return extent; // avoid creating new instance of html file
		}
		extent = new ExtentReports();
		extent.attachReporter(getHtmlReporter(reportTitle, reportName));
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter(String title, String name) {

		htmlReporter = new ExtentHtmlReporter(filePath);
		// htmlReporter.loadXMLConfig(dir + "\\lib\\extent-config.xml");

		htmlReporter.loadXMLConfig(dir + "\\extent-config.xml");
		// make the charts visible on report open

		htmlReporter.config().setChartVisibilityOnOpen(true);

		htmlReporter.config().setDocumentTitle(title);

		htmlReporter.config().setReportName(name);
		return htmlReporter;
	}

}
