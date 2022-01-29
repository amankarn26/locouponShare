package com.locoupon.common.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.locoupon.common.entity.Address;
import com.locoupon.common.entity.Order;
import com.locoupon.common.entity.OrderEntry;
import com.locoupon.common.enums.ApplicationConstants;
import com.locoupon.common.enums.ApplicationInit;
import com.locoupon.common.enums.DateValue;
import com.locoupon.common.enums.EntryType;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.Folder;
import com.locoupon.common.enums.Generation;
import com.locoupon.common.enums.OSCheck;
import com.locoupon.common.enums.OrderType;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.Duration.type;
import com.locoupon.common.enums.OSCheck.OSType;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.BookingMonthCount;
import com.locoupon.common.model.CompactCustomer;
import com.locoupon.common.model.InvoiceMap;
import com.locoupon.common.model.InvoiceObj;
import com.locoupon.common.model.MonthlyAccount;
import com.locoupon.common.model.OrderByDate;
import com.locoupon.common.model.ReturnObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class UtilFuncs {

	private static final Logger logger = LoggerFactory.getLogger(UtilFuncs.class);

	public static ErrorCodes validateAddress(Address address, ErrorCodes reqError) {
		reqError = AppStringUtils.validateStr(address.getLine1(), reqError);

		if (reqError != RequestTypes.ErrorCodes.NONE) {
			reqError.setValue(ErrorCodes.INVALID_ADDRESS_LINE.toString());
			return reqError;
		}

		if (null != address.getLine2() && !(address.getLine2().trim().isEmpty())) {
			reqError = AppStringUtils.validateStr(address.getLine2(), reqError);

			if (reqError != RequestTypes.ErrorCodes.NONE) {
				reqError.setValue(ErrorCodes.INVALID_ADDRESS_LINE.toString());
				return reqError;
			}
		}

		reqError = AppStringUtils.validateNameStr(address.getCity(), reqError);

		if (reqError != RequestTypes.ErrorCodes.NONE) {
			reqError.setValue(ErrorCodes.INVALID_LOCATION.toString());
			return reqError;
		}

		boolean valPin = AppStringUtils.validatePin(address.getPincode(), reqError);

		if (!valPin) {
			reqError.setValue(ErrorCodes.INVALID_PINCODE.toString());
			return reqError;
		}

		return reqError;
	}

	public static String getfileNameFromPath(String path) {
		String filename = path;

		if (path.contains("/")) {
			// String lastWord = test.substring(test.lastIndexOf(" ")+1);
			filename = path.substring(path.lastIndexOf("/") + 1);
		} else {
			filename = path.substring(path.lastIndexOf("\\") + 1);
			// filename = path.substring(0, path.lastIndexOf("\\"));
		}

		// logger.error("File Name= " + filename);
		return filename;

	}

	// first get the date - 2017-10-09 from the intime which is in 2017-10-09
	// 10:00:00
	public static Date getDateFromTs(Timestamp ts) {

		// String startDateString = "2017-02-14 00:13:25";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date intime = new Date();

		Date date = new Date();
		@SuppressWarnings("unused")
		Timestamp currTime = new Timestamp(ts.getTime());
		try {
			date = df.parse(ts.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return date;

	}

	public static String getMonthInString(Date date) {

		Integer monthno = getMonthInt(date);
		logger.error("monthno:" + monthno);
		String monthName = getMonthName(monthno);
		return monthName;

	}

	public static int getMonthInt(Date date) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		return Integer.parseInt(dateFormat.format(date));
	}

	public static String getMonthName(int monthNumber)

	{
		String monthName = null;
		switch (monthNumber) {
		case 1:
			monthName = "January";
			break;
		case 2:
			monthName = "February";
			break;
		case 3:
			monthName = "March";
			break;
		case 4:
			monthName = "April";
			break;
		case 5:
			monthName = "May";
			break;
		case 6:
			monthName = "June";
			break;
		case 7:
			monthName = "July";
			break;
		case 8:
			monthName = "August";
			break;
		case 9:
			monthName = "September";
			break;
		case 10:
			monthName = "October";
			break;
		case 11:
			monthName = "November";
			break;
		case 12:
			monthName = "December";
			break;
		default:
			monthName = "Invalid month";
			break;
		}
		return monthName;
	}

	public static float getTimeDiffInHours(Timestamp outtime, Timestamp intime) {

		long diff = outtime.getTime() - intime.getTime();// as given

		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
		float hours = minutes / 60;
		return hours;
	}

	public static String getFileUploadPath(String propdocPath) {
		String catalinaHome = ApplicationInit.tomcatpath;
		// logger.error("catalinaHome:"+catalinaHome);
		if (catalinaHome.contains("/")) {
			catalinaHome = catalinaHome + "/" + propdocPath;
		} else {
			catalinaHome = catalinaHome + "\\" + propdocPath;
		}
		return catalinaHome;
	}

	public static String getFilePath(String directoryName, String fileName) {
		String docPath = directoryName;
		if (directoryName.contains("/")) {
			docPath = directoryName + "/" + fileName;
		} else {
			docPath = directoryName + "\\" + fileName;
		}
		return docPath;
	}

	public static ErrorCodes createRecurrDirs(String docPath, String dir) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		// selfie//id
		//logger.error("REM:Docpath:"+docPath+":Directory:"+dir);
		List<String> itemList = null;
		try {
			if (dir.contains("/")) {
				String[] items = dir.split("/");
				itemList = Arrays.asList(items);
			} else if (dir.contains("\\")) {
				String[] items = dir.split("\\");
				itemList = Arrays.asList(items);
			} else {
				itemList = new ArrayList<String>();
				itemList.add(dir);
			}
		} catch (Exception e) {
			logger.error("Exception = ", e);
			reqError = ErrorCodes.ERROR_EXCEPTION;
			return reqError;
		}

		String rootdir = docPath;
		for (String item : itemList) {
			String directoryName = UtilFuncs.getFilePath(rootdir, item);// dirpath+"/"+caseName;
			 logger.error("Directory Name = " + directoryName);
			File directory = new File(String.valueOf(directoryName));
			if (!directory.exists()) {
				logger.error("Directory does not exist- create new directory:" + directoryName);
				directory.mkdir();
				// System.out.println("Directory created sucessfully:"+directoryName);
				//logger.error("Directory created sucessfully:" + directoryName);
			}
			rootdir = UtilFuncs.getFilePath(rootdir, item);
			// logger.error("rootdir = " + rootdir);
		}
		return reqError;
	}

	/*
	 * public static String getFullName(Employee emp) { String fullName =
	 * emp.getFname()+" " + emp.getLname();
	 * 
	 * return fullName; }
	 */
	/*
	 * public static String getFullName(Retailer cust) { String fullName =
	 * cust.getFname()+" " + cust.getLname();
	 * 
	 * return fullName; }
	 */
	public static String getDirName(String propdocpath, Long firm, String name, String sid) {
		String dir = getFileUploadPath(propdocpath);
		// logger.error("Sub dir name = " + type.codedesc().toLowerCase());
		// logger.error("Sub dir name = " + firm);
		if (propdocpath.contains("/")) {
			// dir = dir+"/"+type.codedesc().toLowerCase()+"/"+sid;
			dir = dir + "/" + firm + "/" + name.toLowerCase() + "/" + sid;
		} else {
			// dir = dir+"\\"+type.codedesc().toLowerCase()+"\\"+sid;
			dir = dir + "/" + firm + "/" + name.toLowerCase() + "/" + sid;
		}
		// logger.error("Return Dir name = " + dir);
		return dir;
	}

	public static Integer getIntFromStr(String sint) {
		try {
			if (null != sint && !(sint.trim().isEmpty())) {
				Integer convint = Integer.parseInt(sint);
				return convint;
			}
		} catch (NumberFormatException e) {
			logger.error("Exception in converting str to int " + e.getMessage());
		}
		return 0;
	}

	public static InternetAddress[] getMailRecipients(String tolist) {

		// private String recipient = "yamabs@gmail.com ,priya@gmail.com ";
		String[] recipientList = tolist.split(",");
		InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
		int counter = 0;
		for (String recipient : recipientList) {
			try {
				recipientAddress[counter] = new InternetAddress(recipient.trim());
			} catch (AddressException e) {
				e.printStackTrace();
			}
			counter++;
		}
		return recipientAddress;
	}

	public static String truncateString(String str, char c) {

		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == c) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static String getFullName(String fname, String mname, String lname) {
		String fullName = fname + " " + mname + " " + lname;

		return fullName;
	}

	public static Long getLongfromStr(String slong) {
		try {
			if (null != slong && !(slong.trim().isEmpty())) {
				Long conlong = Long.parseLong(slong);
				return conlong;
			}
		} catch (NumberFormatException e) {
			logger.error("Exception in converting str to long " + e.getMessage());
		}
		return (long) 0;
	}

	public static String getDirName(String propdocpath, String subdir) {
		// String dir = getFileUploadPath(propdocpath);

		String dir = propdocpath;
		// logger.error("Sub dir name = " + subdir);
		if (propdocpath.contains("/")) {
			dir = dir + "/" + subdir;
		} else {
			dir = dir + "\\" + subdir;
		}
		// logger.error("Return Dir name = " + dir);
		return dir;
	}

	public static String getSubdir(Folder.type folder) {
		String subdir = null;
		switch (folder) {
		case PRODUCT:
			subdir = "Product";
			break;
		case SPAREPART:
			subdir = "Sparepart";
			break;
		case EMPLOYEE:
			subdir = "Employee";
			break;
		case CUSTOMER:
			subdir = "Customer";
			break;
		case VOUCHER:
			subdir = "Voucher";
			break;
		case CAMPAIGN:
			subdir = "Campaign";
			break;
		default:
			subdir = "imba";
			break;
		}
		return subdir;
	}

	public static String getDirName(String propdocpath, String subdir, String sid) {
		// String dir = getFileUploadPath(propdocpath);

		String dir = propdocpath;
		// logger.error("Sub dir name = " + subdir);
		if (propdocpath.contains("/")) {
			dir = dir + "/" + subdir + "/" + sid;
		} else {
			dir = dir + "\\" + subdir + "\\" + sid;
		}
		// logger.error("Return Dir name = " + dir);
		return dir;
	}

	public static String concatenateDirs(String docPath, String subdir1, String subdir2) {
		// String dir = getFileUploadPath(propdocpath);

		String dir = "";
		// logger.error("Sub dir name = "+subdir);
		if (docPath.contains("/")) {
			dir = subdir1 + "/" + subdir2;
		} else {
			dir = subdir1 + "\\" + subdir2;
		}
		// logger.error("Return Dir name = " + dir);
		return dir;
	}

	public static String getPath(String propdocpath, Folder.type foldertype, String deployacct, String employer,
			String empid) {
		// logger.error(" creating path dir:" + foldertype + ":" + deployacct + ":" +
		// employer + ":" + empid);
		String folder = UtilFuncs.getSubdir(foldertype);
		String subdir = UtilFuncs.getDirName(deployacct, employer, folder);
		String fullDirPath = UtilFuncs.getDirName(propdocpath, subdir, empid);
		return fullDirPath;
	}

	public static Date getDatefromString(String sdate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static JSONObject getJsonFromByteArray(byte[] qtdata) {
		if (null == qtdata) {
			return null;
		}
		String s = new String(qtdata);
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(s);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static byte[] getByteArrayFromJson(JSONObject data) {
		if (null != data) {
			String json = data.toJSONString();
			if ((json != null) && (json.length() > 0)) {
				byte[] bytes = json.getBytes();
				return bytes;
			}
		}
		return null;
	}

	public static Double getDoubleFromStr(String sd) {
		if (null != sd) {
			Double value = Double.parseDouble(sd);
			return value;
		}
		return 0.0;
	}

	public static String getStrFromDouble(Integer integer) {
		if (null != integer) {
			String value = Double.toString(integer);
			return value;
		}
		return null;

	}

	public static Double getPercentValue(Double gstPerc, Double totalrate) {
		Double percval = totalrate * gstPerc / ApplicationConstants.PERCENT_CONSTANT;
		return percval;
	}

	public static Double truncateDouble(Double totalrate, int decimalPrecision) {

		Double truncatedDouble = 0.0;
		if (null != totalrate) {
			truncatedDouble = BigDecimal.valueOf(totalrate).setScale(decimalPrecision, RoundingMode.HALF_UP)
					.doubleValue();
		}

		return truncatedDouble;
	}

	public static double calculate(Integer qty, RateUnit.type rateunit, double dbrate, double ratelabelperc) {

		double totalrate = 0.0;
		Integer dividend = 1;
		Integer rateMul = 1;
		if (null != rateunit) {
			
		} else {
			logger.error("Rate Unit is NULL: " + rateunit);
		}

		logger.error("Total Rate: " + totalrate);
		if (0.0 != ratelabelperc) {
			logger.error("Calculating perc: " + ratelabelperc);
			Double percvalue = (ratelabelperc / ApplicationConstants.PERCENT_CONSTANT) * totalrate; // +-20
			totalrate = totalrate + (percvalue);
		}

		logger.error("Total Rate after applying ratelabel: " + totalrate);

		Double truncatedDouble = truncateDouble(totalrate, ApplicationConstants.DECIMAL_PRECISION);
		return truncatedDouble;

	}

	private static int getRateMultiplier(Integer qty, Integer dividend) {
		int mul = 0;
		int currqty = qty; // 120, 140,110,80,50,30
		while (currqty > 0) // 120,90,60,30,
		{
			// logger.error("currwt: "+currwt+" mul:"+mul);
			mul++; // 1,2,3
			currqty = currqty - dividend; // 40-30
			// logger.error("currwt after subtracting basewt:"+currwt +" mul:"+mul);
		}
		logger.error("Rate Multiplier " + mul);
		return mul;

	}

	public static int getDividend(RateUnit.type rateunit) {
		switch (rateunit) {

		}

		return 1;

	}

	public static Integer getRoundedVal(Double total, Integer roundto) {
		Integer itotal = (int) Math.round(total);
		int roundval = 0; // this will serve as the value to round off to
		if (itotal % roundto == 0) {
			roundval = itotal; // do nothing as x is already a multiple of 50
		} else {
			roundval = ((itotal / roundto) + 1) * roundto;
		}
		return roundval;

	}

	public static Integer getWeightMult(double weight) {
		Integer basewt = ApplicationConstants.BASE_TRAVEL_WEIGHT;
		Integer mul = 0;
		double currwt = weight; // 120, 140,110,80,50,30
		while (currwt > 0) // 120,90,60,30,
		{
			// logger.error("currwt: "+currwt+" mul:"+mul);
			mul++; // 1,2,3
			currwt = currwt - basewt; // 40-30
			// logger.error("currwt after subtracting basewt:"+currwt +" mul:"+mul);
		}
		logger.error("Weight Multiplier " + mul);
		return mul;

	}

	public static ReturnObject getNumdaysInMonth(int year, int month) {
		ReturnObject ret = new ReturnObject();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//		ret.setIvalue(days); // set num days as int
		return ret;
	}

	public static DateValue getStartAndEndDate(int month, int year) {
		DateValue dval = new DateValue();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);

		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		Date start = calendar.getTime();
		logger.error("getStartAndEndDate start: " + start);
		dval.setStartdate(start);

		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date end = calendar.getTime();
		logger.error("getStartAndEndDate end: " + end);
		dval.setEnddate(end);
		return dval;
	}

	public static boolean checkIfDateIsBefore(Date input, Date enddate) {
		logger.error("input: " + input);
		if (input.before(enddate)) {
			return true;
		} else {
			return false;
		}
	}

	public static EnumBoolean.type checkIfDatePassed(Date newdate) {

		logger.error("newdate: " + newdate);
		if (newdate.before(new Date())) {
			return EnumBoolean.type.YES;
		} else {
			return EnumBoolean.type.NO;
		}
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public static boolean checkIfDatesEqual(Date date1, Date date2) {
		logger.error("checkIfDatesEqual: " + date1 + ":" + date2);
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		return sameDay;
	}

	public static String generateLotNo(String name) {
		int namelength = name.length();
		String subname = "";
		if (namelength > 4) {
			subname = name.substring(0, 4);
		} else {
			subname = name.substring(0, namelength);
		}
		String lotno = subname + "-" + "#" + "001"; // BIKE-189090-001
		return lotno;
	}

	public static String getPlatformSpecificFileUploadPath(String windocpath, String lindocpath,String macdocpath) {

		String catalinaHome = ApplicationInit.tomcatpath;
		OSCheck.OSType ostype = getPlatformType();
		if (ostype == OSCheck.OSType.Windows) {
			catalinaHome = catalinaHome + "\\" + windocpath;
		} else if (ostype == OSCheck.OSType.Linux) {
			catalinaHome = catalinaHome + "/" + lindocpath;
		} else if (ostype==OSCheck.OSType.MacOS)
		{
			//catalinaHome = catalinaHome + "/" + macdocpath;   ---todo changes
			catalinaHome = "/Applications/tomcat" + "/" + macdocpath;

		}
		else {
			logger.error("Invalid ostype:" + ostype.toString());
		}
		return catalinaHome;
	}

	public static OSType getPlatformType() {
		OSCheck.OSType ostype = OSCheck.getOperatingSystemType();
		switch (ostype) {
		case Windows:
			break;
		case MacOS:
			break;
		case Linux:
			break;
		case Other:
			break;
		}
		return ostype;
	}

	public static String generateNewNumber(String lastnum) {
		if (null == lastnum) {
			return null;
		}
		if (lastnum.contains("#")) {
			String[] strarry = lastnum.split("#"); // 001
			logger.error("strarry[1]: " + strarry[1]);
			int newno = Integer.parseInt(strarry[1]); // 1
			if (newno > 0 && newno < 99) {
				String newnostr = null;
				newno = newno + 1;
				if (newno > 0 && newno < 10) {
					newnostr = strarry[0] + "#00" + newno;
				} else if (newno >= 10 && newno <= 99) {
					newnostr = strarry[0] + "#0" + newno;
				}
				logger.error("new no string: " + newnostr);
				return newnostr;
			} else {
				newno = newno + 1;
				String newnostr = strarry[0] + "#" + newno;
				logger.error("new no string: " + newnostr);
				return newnostr;
			}
		}
		return null;
	}

	/*
	 * public static void getLast12Months(List<QuoteMonthCount> qMonthList) {
	 * SimpleDateFormat sdfm = new SimpleDateFormat("MM"); SimpleDateFormat sdfy =
	 * new SimpleDateFormat("YYYY"); Calendar calendar = new GregorianCalendar();
	 * logger.error(sdfy.format(calendar.getTime()));
	 * 
	 * for (int i = 0; i <= 11; i++) { QuoteMonthCount qMonthCnt = new
	 * QuoteMonthCount(); calendar.add(Calendar.MONTH, -1); String monthNum =
	 * sdfm.format(calendar.getTime()); qMonthCnt.setMonthNo(monthNum); String month
	 * = setMonthName(Integer.parseInt(monthNum));
	 * logger.error("month :"+month+":"+monthNum); qMonthCnt.setMonth(month);
	 * qMonthCnt.setYear(sdfy.format(calendar.getTime()));
	 * logger.error("year:"+sdfy.format(calendar.getTime()));
	 * qMonthList.add(qMonthCnt); }
	 * 
	 * 
	 * }
	 */

	private static String setMonthName(int monthNumber) {
		String monthName = null;
		switch (monthNumber) {
		case 1:
			monthName = "January";
			break;
		case 2:
			monthName = "February";
			break;
		case 3:
			monthName = "March";
			break;
		case 4:
			monthName = "April";
			break;
		case 5:
			monthName = "May";
			break;
		case 6:
			monthName = "June";
			break;
		case 7:
			monthName = "July";
			break;
		case 8:
			monthName = "August";
			break;
		case 9:
			monthName = "September";
			break;
		case 10:
			monthName = "October";
			break;
		case 11:
			monthName = "November";
			break;
		case 12:
			monthName = "December";
			break;
		default:
			monthName = "Invalid month";
			break;
		}
		return monthName;
	}

	public static int getNumBundles(Integer quantity, int numunitsperpkg) {
		// Integer basewt = PPressConstants.BASE_TRAVEL_WEIGHT;
		Integer numbun = 0;
		Integer currqty = quantity; // 120, 140,110,80,50,30
		while (currqty > 0) // 120,90,60,30,
		{
			numbun++; // 1,2,3
			currqty = currqty - numunitsperpkg; // 40-30
		}
		logger.error("Numbundles= " + numbun);
		return numbun;

	}

	public static long getDaysBetweenDates(Date fromdate, Date todate) {
		long daycount = 0;
		long diff = todate.getTime() - fromdate.getTime();
		daycount = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		logger.error("Days: " + daycount);
		return daycount;
	}

	public static Date getFutureDate(Integer numdays) {
		Calendar c = Calendar.getInstance();

		c.setTime(new Date()); // Now use today date.

		c.add(Calendar.DATE, numdays);

		return c.getTime();
	}

	public static Date getBackDate(Integer numdays) {
		Calendar c = Calendar.getInstance();

		c.setTime(new Date()); // Now use today date.

		c.add(Calendar.DATE, -(numdays));

		return c.getTime();
	}

	public static void getLast12Monthsforvaoucher(List<MonthlyAccount> monthlyIncomeList) {
		SimpleDateFormat sdfm = new SimpleDateFormat("MM");
		SimpleDateFormat sdfy = new SimpleDateFormat("YYYY");
		SimpleDateFormat sdfym = new SimpleDateFormat("YYYY-MM-DD");
		Calendar calendar = new GregorianCalendar();
		logger.error(sdfy.format(calendar.getTime()));

		for (int i = 0; i <= 11; i++) {
			MonthlyAccount monthInc = new MonthlyAccount();
			calendar.add(Calendar.MONTH, -1);
			String monthNum = sdfm.format(calendar.getTime());

			String month = setMonthName(Integer.parseInt(monthNum));
			logger.error("month :" + month + ":" + monthNum);
			monthInc.setMonth(month);
			monthInc.setMonthNo(monthNum);
			monthInc.setYear(sdfy.format(calendar.getTime()));
			String strdate = sdfym.format(calendar.getTime());

			Date inDate = null;
			try {
				inDate = sdfym.parse(strdate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			monthInc.setDate(inDate);
			logger.error("Date:" + strdate);
			logger.error("year:" + sdfy.format(calendar.getTime()));
			monthlyIncomeList.add(monthInc);

		}

	}

	public static Date getCurrentDate() {
		Calendar calendar = new GregorianCalendar();
		Date currdate = calendar.getTime();
		return currdate;
	}

	public static Timestamp getCurrentTime() {
		Date ts = new Date();
		Timestamp currtime = new Timestamp(ts.getTime());
		return currtime;
	}

	public static Date getLastMonthStart() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date start = calendar.getTime();
		logger.error("getLastMonthStart:" + start);
		return start;

	}

	public static Date getLastMonthEnd() {

		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		Date enddate = calendar.getTime();
		logger.error("getLastMonthEnd:" + enddate);
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String date = sdf.format(new Date()); 
		logger.error("getLastMonthEnd format:" + date);*/
		
		return enddate;
	}

	public static Date getNextMonthStart() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date start = calendar.getTime();
		logger.error("getNextMonthStart:" + start);
		return start;

	}

	public static Date getNextMonthEnd() {

		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		Date enddate = calendar.getTime();
		logger.error("getNextMonthEnd:" + enddate);
		return enddate;
	}

	public static Date getCurrMonthStart() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		Date startdate = calendar.getTime();
		logger.error("getCurrMonthStart:" + startdate);
		return startdate;

	}

	public static Date getCurrMonthEnd() {
		// SimpleDateFormat sdfym = new SimpleDateFormat("YYYY-MM-DD");
		Calendar calendar = new GregorianCalendar();
		// logger.error(sdfym.format(calendar.getTime()));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		// String strdate= sdfym.format(calendar.getTime());

		Date enddate = calendar.getTime();
		logger.error("getCurrMonthStart:" + enddate);
		return enddate;

	}

	public static Date getCurrWeekStart() {
		Calendar cal = new GregorianCalendar();
		logger.error("getCurrWeekStart First Day Of Week:" + cal.getFirstDayOfWeek());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		Date startdate = cal.getTime();
		logger.error("getCurrWeekStart:" + startdate);
		return startdate;

	}

	public static Date getCurrWeekEnd() {
		Calendar cal = new GregorianCalendar();

		logger.error("getCurrWeekEnd  Day Of Week:" + cal.getFirstDayOfWeek());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.DATE, 6);
		Date enddate = cal.getTime();
		logger.error("getCurrWeekEnd:" + enddate);
		return enddate;

	}

	public static Date getDaysFromcurr(int day) {

		Calendar cal = new GregorianCalendar();
		Date currdate = cal.getTime();

		logger.error("getDayFromCurrentDatek:" + day);
		cal.add(Calendar.DATE, day);
		Date enddate = cal.getTime();
		logger.error("currentdate:" + currdate + ":date after:" + "day:" + enddate);
		return enddate;

	}

	public static Date getNextWeek() {
		Calendar cal = new GregorianCalendar();

		logger.error("getNextWeeK First Day Of Week:" + cal.getFirstDayOfWeek());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.DATE, 8);

		Date startdate = cal.getTime();
		logger.error("getNextWeek:" + startdate);
		return startdate;

	}

	public static String getDayName(int dayofweek) {
		if (dayofweek == Calendar.SATURDAY) {
			return "SATURDAY";
		} else if (dayofweek == Calendar.SUNDAY) {
			return "SUNDAY";
		}
		return null;

	}

	public static String generateSaleNo() {
		String sale = "SN-000";
		return sale;
	}

	public static String generateNum(String firmcode, Generation.type otype) {
		String lotno = null;
		if (otype == Generation.type.SALE) {
			lotno = firmcode.toString() + "-" + "SN#001";
		} else if (otype == Generation.type.PURCHASEORDER) {
			lotno = firmcode.toString() + "-" + "PO#001";
		} else if (otype == Generation.type.BOOKING) {
			lotno = firmcode.toString() + "-" + "BK#001";
		}

		else if (otype == Generation.type.INVOICE) {
			lotno = firmcode.toString() + "-" + "IN#001";
		} else if (otype == Generation.type.VOUCHER) {
			lotno = firmcode.toString() + "-" + "VC#001";
		} else if (otype == Generation.type.SERVICING) {
			lotno = firmcode.toString() + "-" + "SRVIN#001";
		} else if (otype == Generation.type.INSURANCE) {
			lotno = firmcode.toString() + "-" + "INS#001";
		} else if (otype == Generation.type.CHALLAN) {
			lotno = firmcode.toString() + "-" + "CH#001"; // SAM-CH#001
		} else if (otype == Generation.type.PACKING) {
			lotno = firmcode.toString() + "-" + "PKG#001"; // SAM-PKG#001
		} else if (otype == Generation.type.DISPATCH) {
			lotno = firmcode.toString() + "-" + "DIS#001"; // SAM-PKG#001
		}

		return lotno;
	}

//	public static void getLast12Months(List<QuoteMonthCount> qMonthList) {
//		SimpleDateFormat sdfm = new SimpleDateFormat("MM"); 
//		SimpleDateFormat sdfy = new SimpleDateFormat("YYYY"); 
//		Calendar calendar = new GregorianCalendar();
//		logger.error(sdfy.format(calendar.getTime()));
//
//		for (int i = 0; i <= 11; i++) {
//			QuoteMonthCount qMonthCnt = new QuoteMonthCount();
//			calendar.add(Calendar.MONTH, -1);
//			String monthNum = sdfm.format(calendar.getTime());
//			qMonthCnt.setMonthNo(monthNum);
//			String month = setMonthName(Integer.parseInt(monthNum));
//			logger.error("month :"+month+":"+monthNum);
//			qMonthCnt.setMonth(month);
//			qMonthCnt.setYear(sdfy.format(calendar.getTime()));
//			logger.error("year:"+sdfy.format(calendar.getTime()));
//			qMonthList.add(qMonthCnt);
//		}
//
//		
//	}

	public static type getGenerationType(EntryType.type etype) {
		return null;

		/*if (etype == EntryType.type.SALE) {
			return Generation.type.SALE;
		} else if (etype == EntryType.type.PACKING) {
			return Generation.type.PACKING;
		} else if (etype == EntryType.type.DISPATCH) {
			return Generation.type.DISPATCH;
		} else {
			return Generation.type.BOOKING;
		}
*/
	}

	public static Double addPercentValue(Double gst, Double ongoingrate) {
		Double percval = UtilFuncs.getPercentValue(gst, ongoingrate);
		Double ratewithgst = ongoingrate + percval;
		return ratewithgst;
	}

	/*
	 * public static MailContentData createMailContent(Insurance insuobj) {
	 * 
	 * MailContentData mcontentdata = new MailContentData(); Map content = new
	 * HashMap(); content.put("name", "Dear "+insuobj.getCustname()+"\n");
	 * content.put("message",
	 * "Your Vehicle Insurance is expiring soon!! Kindly renew your Vehicle Insurance. Policy Details as follows: \n"
	 * ); content.put("dt", UtilFuncs.getCurrentDate().toString());
	 * //content.put("subject",
	 * "Renew Your Insurance for Vehicle - "+insuobj.getVehiclenum());
	 * 
	 * mcontentdata.setSubject("Renew Your Insurance for Vehicle No - "
	 * +" Today!!!"); mcontentdata.setContent(content);
	 * 
	 * 
	 * List<Insurance> instable = new ArrayList<Insurance>(); Insurance ins = new
	 * Insurance(); ins.setId(ApplicationConstants.MAIL_SERIAL_NUM_INS);
	 * ins.setVehiclenum(insuobj.getVehiclenum());
	 * ins.setStartdate(insuobj.getStartdate());
	 * ins.setEnddate(insuobj.getEnddate());
	 * ins.setCompanyname(insuobj.getCompanyname());
	 * ins.setPolicynum(insuobj.getPolicynum());
	 * ins.setChassisnum(insuobj.getChassisnum()); instable.add(ins);
	 * 
	 * List<JSONObject> instable = new ArrayList<JSONObject>();
	 * 
	 * JSONObject json = new JSONObject(); json.put("No", "1");
	 * json.put("Policy-No", insuobj.getPolicynum()); json.put("Issue-Date",
	 * UtilFuncs.getStringfromDatewithoutTime(insuobj.getStartdate()));
	 * json.put("Expiry-Date",
	 * UtilFuncs.getStringfromDatewithoutTime(insuobj.getEnddate()));
	 * 
	 * 
	 * instable.add(json);
	 * 
	 * mcontentdata.setTable(instable);
	 * 
	 * return mcontentdata;
	 * 
	 * }
	 */

	/*
	 * public static MailContentData
	 * createMailContentForServicingJob(ServicingDetail detail, String
	 * vehicledetails, String vregno, String custname) { MailContentData
	 * mcontentdata = new MailContentData(); Map content = new HashMap(); String
	 * nextservdueon =
	 * UtilFuncs.getStringfromDatewithoutTime(detail.getNextservicedueon());
	 * content.put("name", "Dear "+custname+"\n"); content.put("message",
	 * "Your Servicing for Vehicle No "+vregno+" is due on "
	 * +nextservdueon+".Kindly visit our exquisitive workshop for availing the service now. Please ignore if already done\n"
	 * + ". Previous Servicing Details as follows: \n"); content.put("dt",
	 * UtilFuncs.getCurrentDate().toString()); //content.put("subject",
	 * "Renew Your Insurance for Vehicle - "+insuobj.getVehiclenum());
	 * 
	 * mcontentdata.setSubject("Get your Vehicle "+vregno+" Serviced Today!!!");
	 * mcontentdata.setContent(content);
	 * 
	 * 
	 * List<JSONObject> instable = new ArrayList<JSONObject>();
	 * 
	 * JSONObject json = new JSONObject(); json.put("No", "1");
	 * json.put("Vehicle-No", vregno); json.put("Details", vehicledetails);
	 * json.put("Availed On",
	 * UtilFuncs.getStringfromDatewithoutTime(detail.getAvailedon()));
	 * json.put("Next Service Due On",
	 * UtilFuncs.getStringfromDatewithoutTime(detail.getNextservicedueon()));
	 * instable.add(json); mcontentdata.setTable(instable);
	 * 
	 * return mcontentdata; }
	 */

	public static String getStringfromDatewithoutTime(Date nextservicedueon) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// current date into your required format
		String str = dateFormat.format(new Date());
		return str;
	}

	public static Timestamp DateAfterEighteenDays(Timestamp currtime) {
		Date ts = new Date();
		Timestamp curtime = new Timestamp(ts.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(curtime);
		c.add(Calendar.DAY_OF_WEEK, 17);
		ts.setTime(c.getTime().getTime()); // or
		// ts = new Timestamp(c.getTime().getTime());
		Timestamp afterfiften = new Timestamp(ts.getTime());

		return afterfiften;
	}

	

	public static EnumBoolean.type checkForsale(String forsale) {

		logger.error("newdate: " + forsale);
		if (forsale.contentEquals("YES")) {
			return EnumBoolean.type.YES;
		} else {
			return EnumBoolean.type.NO;
		}
	}

	public static String getDecodeStr(String sprodtype) {
		try {
			URLDecoder.decode(sprodtype, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sprodtype;
	}

	public static Date getCurrentDateOnly() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = null;
		try {
			dateWithoutTime = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateWithoutTime;
	}

	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<T>();

		for (T t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	public static Date getLastMonthStart(int startofiirregularlimit) {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, startofiirregularlimit);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date start = calendar.getTime();
		logger.error("getLastMonthStart:" + start);
		return start;

	}

	public static Date getLastMonthEnd(int endofiirregularlimit) {

		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, endofiirregularlimit);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		Date enddate = calendar.getTime();
		logger.error("getLastMonthEnd:" + enddate);
		return enddate;
	}

	public static Date subtractDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);

		return cal.getTime();
	}

	public static List<CompactCustomer> intersectionCust(List<CompactCustomer> list1, List<CompactCustomer> list2) {
		Map<Long, CompactCustomer> retmap = new HashMap<Long, CompactCustomer>();
		Map<Long, CompactCustomer> custmap = new HashMap<Long, CompactCustomer>();
		List<CompactCustomer> list = new ArrayList<CompactCustomer>();

		for (CompactCustomer t : list1) {
			if (!custmap.containsKey(t.getId())) {
				custmap.put(t.getId(), t);
			}
		}

		for (CompactCustomer t2 : list2) // loop t2
		{
			if (custmap.containsKey(t2.getId())) // if t1 contains t2
			{
				logger.error("Adding cust to map:" + t2.getId());
				if (!retmap.containsKey(t2.getId())) // add t2 to map to avoid duplicates
				{
					retmap.put(t2.getId(), t2);
					list.add(t2); // add t2 to ret list
				}
			}
		}
		return list;
	}

	public static Double getGSTAmount(Double gst, Double rateperunit) {
		Double percval = UtilFuncs.getPercentValue(gst, rateperunit);

		return percval;
	}

	public static double CaluculateDistance1(Double latitute1, Double longitude1, Double latitute2, Double longitude2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(latitute2 - latitute1);
		double dLng = Math.toRadians(longitude2 - longitude1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(latitute1)) * Math.cos(Math.toRadians(latitute2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;

	}

	public static double CaluculateDistance(Double latitute1, Double longitude1, Double latitute2, Double longitude2) {
		double R = 6378137; // Earth’s mean radius in meter
		double dLat = rad(latitute2 - latitute1);
		double dLong = rad(longitude2 - longitude1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(rad(latitute1)) * Math.cos(rad(latitute2)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;
		return d; // returns the distance in meter

	}

	private static double rad(double d) {

		return d * Math.PI / 180;
	}

	public static Timestamp AddHours(int day) {
		Date ts = new Date();
		Timestamp curtime = new Timestamp(ts.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(curtime);
		c.add(Calendar.DAY_OF_WEEK, day);
		ts.setTime(c.getTime().getTime()); // or
		// ts = new Timestamp(c.getTime().getTime());
		Timestamp afterfiften = new Timestamp(ts.getTime());

		return afterfiften;
	}

	public static void getDateFromUnix(Date date) {

		// Date dte = new Date(date*1000L);

	}

	public static void addOrderToMap(Date date, OrderByDate newentry, HashMap<Date, List<OrderByDate>> ordermap) {

		List<OrderByDate> OrderList = getOrderList(date, ordermap);
		OrderList.add(newentry);
	}

	private static List<OrderByDate> getOrderList(Date date, HashMap<Date, List<OrderByDate>> ordermap) {

		if (!ordermap.containsKey(date)) {
			// ocsnot present
			ordermap.put(date, new ArrayList<OrderByDate>());
		}
		return ordermap.get(date);
	}

	public static String concatenateDirName(String docPath, String subdir) {
		String dir = docPath;
		// logger.error("Sub dir name = "+subdir);
		if (docPath.contains("/")) {
			dir = dir + "/" + subdir;
		} else {
			dir = dir + "\\" + subdir;
		}
		logger.error("concatenateDirName: Return Dir name = " + dir);
		return dir;

	}

	public static Double getDeliveryChange(Double distance) {
		Double rs = 00.0;
		if (distance >= 0 && distance <= 2) {
			return rs = ApplicationConstants.DISTANCE_2_KM;
		} else if (distance >= 2 && distance <= 3) {
			return rs = ApplicationConstants.DISTANCE_3_KM;
		} else if (distance >= 3 && distance <= 4) {
			return rs = ApplicationConstants.DISTANCE_4_KM;
		} else if (distance >= 4 && distance <= 5) {
			return rs = ApplicationConstants.DISTANCE_5_KM;
		} else if (distance >= 5 && distance <= 6) {
			return rs = ApplicationConstants.DISTANCE_6_KM;
		} else if (distance >= 6 && distance <= 7) {
			return rs = ApplicationConstants.DISTANCE_7_KM;
		} else if (distance >= 7 && distance <= 8) {
			return rs = ApplicationConstants.DISTANCE_8_KM;
		} else if (distance >= 8 && distance <= 9) {
			return rs = ApplicationConstants.DISTANCE_9_KM;
		} else if (distance >= 9 && distance <= 12) {
			return rs = ApplicationConstants.DISTANCE_12_KM;
		} else if (distance >= 12 && distance <= 15) {
			return rs = ApplicationConstants.DISTANCE_15_KM;
		} else {
			return rs = ApplicationConstants.DISTANCE_MORETHAN_15_KM;
		}

	}

	public static String getNormalizeName(String name) {
		String nameWithProperSpacing = StringUtils.normalizeSpace(name);
		return nameWithProperSpacing;
	}

	public static String getEntryDetail(OrderEntry entry) {
		String rateunit = getLesserUnit(entry.getQty(), entry.getRateunit());
		String detail = entry.getPname() + ":" + ",Qty:" + entry.getQty() + ",Unit:" + rateunit
				+ ",Rate:" + entry.getAmount();
		return detail;
	}

	public static Date getAdditionalTimeToDate(Date toDate) {
		Date requiredDate = new Date();

		String argTime = "23:30"; // 6 PM - 24 hour format :- Set your time.
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		String dateTime = sdf.format(toDate) + " " + argTime;
		try {
			requiredDate = dateFormat.parse(dateTime);
			logger.error("requiredDate:" + requiredDate);
		} catch (ParseException e) {
			logger.error("exception in add time to date ", e);
		}

		return requiredDate;
	}

	public static Date getYesterdayDate() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		logger.error("Yesterday:" + cal.getTime());
		return cal.getTime();
	}

	public static Date get2YesterdayDate() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		logger.error("Yesterday2:" + cal.getTime());
		return cal.getTime();
	}
	
	public static Date get3YesterdayDate(Date mk) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(mk);
		cal.add(Calendar.DATE, -3);
		logger.error("Yesterday2:" + cal.getTime());
		return cal.getTime();
	}

	public static Date get3DayBeforeDate(Date mk) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(mk);
		cal.add(Calendar.DATE, -4);
		logger.error("get3DayBeforeDate:" + cal.getTime());
		return cal.getTime();
	}

	public static String getMondayDay() {
		final Calendar cal = Calendar.getInstance();
		String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"Saturday" };
		String day = (strDays[cal.get(Calendar.DAY_OF_WEEK) - 1]);
		logger.error("getMondayDate:" + day);
		return day;
	}

	public static Date getOneDaybeforeFormDate(Date fromdate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromdate);
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		logger.error("getOneDaybeforeFormDate:" + yesterday);
		return yesterday;
	}

	public static String getOrderIds(List<Order> olist) {
		String orderids = "";
		for (Order o : olist) {
			orderids += o.getId() + ",";
		}
		return orderids = orderids.substring(0, orderids.length() - 1);
	}

	public static String getFirstNChars(String s, Integer n) {
		String upToNCharacters = s.substring(0, Math.min(s.length(), n));
		return upToNCharacters;
	}

	public static void getLast12Months(List<BookingMonthCount> qMonthList) {
		SimpleDateFormat sdfm = new SimpleDateFormat("MM");
		SimpleDateFormat sdfy = new SimpleDateFormat("YYYY");
		Calendar calendar = new GregorianCalendar();
		logger.error(sdfy.format(calendar.getTime()));

		for (int i = 0; i <= 11; i++) {
			BookingMonthCount qMonthCnt = new BookingMonthCount();
			calendar.add(Calendar.MONTH, -1);
			String monthNum = sdfm.format(calendar.getTime());
			qMonthCnt.setMonthNo(monthNum);
			String month = setMonthName(Integer.parseInt(monthNum));
			logger.error("month :" + month + ":" + monthNum);
			qMonthCnt.setMonth(month);
			qMonthCnt.setYear(sdfy.format(calendar.getTime()));
			logger.error("year:" + sdfy.format(calendar.getTime()));
			qMonthList.add(qMonthCnt);
		}

	}

	public static String getLesserUnit(Integer integer, RateUnit.type rateunit) {

		// String unit = null;
		switch (rateunit) {
		
		default:

			String unit = UtilFuncs.getStrFromDouble(integer);
			return unit;

		}

	}

	public static String getEntryDetails(OrderEntry entry) {
		
		String detail = "Product:"+entry.getPname() +",Variant:" +entry.getVariant()+ ", Qty:" + entry.getQty() + ", Rate:" + entry.getAmount();
		return detail;
	}

	public static float seviceAmount(float servicecharge) {
		int GSTAmt = (int) (servicecharge * 0.18);
		float servAmt = servicecharge - GSTAmt;
		return servAmt;
	}

	public static float gstAmount(float servicecharge) {
		float GSTAmt = (float) (servicecharge * 0.18);
		return GSTAmt;
	}

	public static String invoicenumGenerate(Long id, String todate, String fromdate) {
		String num = null;
		InvoiceMap map = new InvoiceMap();
		InvoiceObj notice = new InvoiceObj();
		List<InvoiceObj> keySet = (List<InvoiceObj>) map.getVendorInvoice(id);
		if (keySet != null) {
			for (InvoiceObj m : keySet) {
				if (m.getId().equals(id) && m.getFromdate().contains(fromdate)) {
					num = m.getInvoiceid().toString();
				} else {
					num = genInvoiceData(id, map.getUniqueId());
					notice.setFromdate(fromdate);
					notice.setTodate(todate);
					notice.setInvoiceid(num);
					notice.setId(id);
					map.clearAllVendor(id);
					map.addInvoice(id, notice);
				}
			}

		} else {
			num = genInvoiceData(id, map.getUniqueId());
			notice.setFromdate(fromdate);
			notice.setTodate(todate);
			notice.setInvoiceid(num);
			notice.setId(id);
			map.addInvoice(id, notice);
		}

		return num;

	}

	public static String getMothOnly(String fromdate) {
		LocalDate currentDate = LocalDate.parse(fromdate);
		Month month = currentDate.getMonth();
		int year = currentDate.getYear();
		System.out.println("Month: " + month);
		System.out.println("Year: " + year);
		return null;
	}

	public static String genInvoiceData(Long id, int j) {
		String lotno = id + "IN#00" + j;
		return lotno;
	}

	

	public static Date getSundayDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		logger.error("Date " + c.getTime());
		return c.getTime();
	}

	public static Date getWednesDayDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		logger.error("Date " + c.getTime());
		return c.getTime();
	}

	public static String convertToCamelCase(String str) {
	    if (str == null || str.isEmpty()) {
	        return str;
	    }

	    StringBuilder converted = new StringBuilder();

	    boolean convertNext = true;
	    for (char ch : str.toCharArray()) {
	        if (Character.isSpaceChar(ch)) {
	            convertNext = true;
	        } else if (convertNext) {
	            ch = Character.toTitleCase(ch);
	            convertNext = false;
	        } else {
	            ch = Character.toLowerCase(ch);
	        }
	        converted.append(ch);
	    }

	    return getNormalizeName(converted.toString());}

	/*public static String getPlatformSpecificFileUploadPath(String windocpath, String lindocpath,String macdocpath) {

		String catalinaHome = ApplicationInit.tomcatpath;
		OSCheck.OSType ostype = getPlatformType();
		if (ostype == OSCheck.OSType.Windows) {
			catalinaHome = catalinaHome + "\\" + windocpath;
		} else if (ostype == OSCheck.OSType.Linux) {
			catalinaHome = catalinaHome + "/" + lindocpath;
		} else if (ostype==OSCheck.OSType.MacOS)
		{
			//catalinaHome = catalinaHome + "/" + macdocpath;   ---todo changes
			catalinaHome = "/Applications/tomcat" + "/" + macdocpath;

		}else {
			logger.error("Invalid ostype:" + ostype.toString());
		}
		return catalinaHome;
	}*/

	public static String getDistance(String source, String destination) throws IOException {
		String API_KEY = "AIzaSyD7pL-XgLWeMSGO8Eq1NQdEiKgTp1oeL2s";
		OkHttpClient client = new OkHttpClient();

		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + source
				+ "&destinations=" + destination + "&key=" + API_KEY;
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		return response.body().string();

	}

	public static Address convertAddressToCamelCase(Address address) {
		
		Address newaddress= new Address();
		if(null!=address.getLine1())
		{
			newaddress.setLine1(UtilFuncs.convertToCamelCase(address.getLine1()));
		}
		if(null!=address.getLine2())
		{
			newaddress.setLine2(UtilFuncs.convertToCamelCase(address.getLine2()));
		}
		if(null!=address.getCity()) {
			newaddress.setCity(UtilFuncs.convertToCamelCase(address.getCity()));
		}
		
	    if(null!= address.getCountry())
	    {
	    	newaddress.setCountry(UtilFuncs.convertToCamelCase(address.getCountry()));
	    }
	    if(null!=address.getStates())
	    {
	    	newaddress.setStates(UtilFuncs.convertToCamelCase(address.getStates()));
	    }
	    newaddress.setLatitude(address.getLatitude());
	    newaddress.setLongitude(address.getLongitude());
	    newaddress.setPincode(address.getPincode());
	    return newaddress;
	}



}
