package assignment.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 날짜 포맷에 관한 유틸 메소드 제공
public class DateUtil {

	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static SimpleDateFormat FORMATTER = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	
	private DateUtil() {}
	
	public static String getFormattedDateString() {
		return FORMATTER.format(new Date());
	}
	
	public static String getFormattedDateString(Date date) {
		if (date == null) {
			return null;
		}
		return FORMATTER.format(date);
	}
	
	public static Date getFormattedDate(String dateString) throws ParseException {
		return FORMATTER.parse(dateString);
	}
	
}
