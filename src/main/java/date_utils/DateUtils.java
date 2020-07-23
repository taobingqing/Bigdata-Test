package date_utils;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.MutableDateTime;
import org.joda.time.chrono.CopticChronology;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DateUtils {

//	public static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public final static int Data_Live_Seconds = 1 * 12 * 60 * 60;

	public final static int Hour4_To_Seconds = 4 * 60 * 60;

	public final static int Hour2_To_Seconds = 2 * 60 * 60;

	public final static int Hour24_To_Seconds = 24 * 60 * 60;

	private final static String TIME_FORMAT_MI = "yyMMddHHmm";

	private final static String TIME_FORMAT_DAY = "yyMMdd";

	private final static String TIME_FORMAT_SS = "yyMMddHHmmss";

	private final static String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";

	private final static DateTimeField minutes5Field = new DividedDateTimeField(
			CopticChronology.getInstance().minuteOfDay(), DateTimeFieldType.minuteOfDay(), 5);

	private final static DateTimeField minutes15Field = new DividedDateTimeField(
			CopticChronology.getInstance().minuteOfDay(), DateTimeFieldType.minuteOfDay(), 15);

	public static String format(long date) {
		return DateTimeFormat.forPattern(TIME_FORMAT_NORMAL).print(date);
	}

	public static String formatToMm5(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.setRounding(minutes5Field);
		return mdt.toString(TIME_FORMAT_MI);
	}

	public static String formatToMm15(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.setRounding(minutes15Field);
		return mdt.toString(TIME_FORMAT_MI);
	}

	public static long roundToMm5(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.setRounding(minutes5Field);
		return mdt.getMillis();
	}

	public static String formatToMm1(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.minuteOfHour().roundFloor();
		return mdt.toString(TIME_FORMAT_MI);
	}

	public static String formatToSs(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		return mdt.toString(TIME_FORMAT_SS);
	}

	public static long toMm5Millis(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.setRounding(minutes5Field);
		return mdt.getMillis();
	}

	public static long timeToHh1(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.hourOfDay().roundFloor();
		return mdt.getMillis();
	}

	public static String formatToMm60(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.hourOfDay().roundFloor();
		return mdt.toString(TIME_FORMAT_MI);
	}

	public static String formatToDay(long date) {
		MutableDateTime mdt = new MutableDateTime(date);
		mdt.dayOfMonth().roundFloor();
		return mdt.toString(TIME_FORMAT_DAY);
	}

	public static long betwwen(long date1,long date2){
		return Math.abs(date1-date2);
	}

	public static void sleep(long n) {
		try {
			TimeUnit.MILLISECONDS.sleep(n);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
