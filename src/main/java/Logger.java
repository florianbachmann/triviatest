import java.util.ArrayList;


public class Logger {
	private static ArrayList<String> logger = new ArrayList<String>();
	
	public static void Log(String logging) {
		System.out.println(logging);
		logger.add(logging);
	}
	
	public static String getLoggingAsString() {
		StringBuffer buf = new StringBuffer();
		for (String loggedElement : logger) {
			buf.append(loggedElement);
		}
		return buf.toString();
	}
}
