
package com.ficus.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public final class Util {

	public static long lastid = 0L;

	public Util() {
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String curDate() {
		return curDate("yyyy-MM-dd");
	}

	public static String curDate(String dormat) {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.format(DateTimeFormatter.ofPattern(dormat));
	}

	public static boolean isEmpty(String s) {
		if (s == null)
			return true;
		return s.trim().length() == 0;
	}

	public static boolean isEmpty(Object s) {
		if (s == null)
			return true;
		return s.toString().trim().length() == 0;
	}

	public static boolean hasChinese(String s) {
		if (s == null)
			return false;
		if (s.trim().length() == 0)
			return false;

		return ChineseCheck.isContainsChinese(s);
	}

	public static boolean hasChinese(Object s) {
		if (s == null)
			return false;
		if (s.toString().trim().length() == 0)
			return false;
		return ChineseCheck.isContainsChinese(s.toString());
	}

	public static String createId() {
		long id;
		do {
			id = System.currentTimeMillis() - Long.parseLong("999089069099");
		} while (lastid == id);
		lastid = id;
		return "" + id;
	}

	public static void deleteFile(String file) throws Exception {
		println("delete file '" + file + "'");
		File f = new File(file);
		if (!f.exists()) {
			println("file '" + file + "' not found!");
			return;
		} else {
			f.delete();
			return;
		}
	}

	public static void deleteFileAs(String dir, String name) {
		try {
			File file = new File(dir);
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++)
				if (files[i].getName().startsWith(name))
					files[i].delete();

		} catch (Exception _ex) {
		}
	}

	public static String getLineSeparator() {
		return (String) System.getProperties().get("line.separator");
	}

	public static String getSpace(int num) {
		String space = "";
		for (int i = 0; i < num; i++)
			space = space + "&nbsp;";

		return space;
	}

	public static void println(Object obj) {
		if (obj == null) {
			System.out.println();
			return;
		}
		if (obj instanceof Throwable) {
			((Throwable) obj).printStackTrace();
			return;
		} else {
			System.out.println(obj);
			return;
		}
	}

	public static String readFile(String file) throws Exception {
		return readFile(file, "UTF-8");
	}

	public static String readFile(String file, String charSet) throws Exception {
		StringBuffer txt;
		label0: {
			// println("read file '" + file + "'");
			File f = new File(file);
			if (!f.exists())
				throw new Exception("file not found " + file);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
			txt = new StringBuffer();
			try {
				for (String line = in.readLine(); line != null; line = in.readLine()) {
					txt.append(line);
					txt.append(getLineSeparator());
				}

				break label0;
			} finally {
				try {
					in.close();
				} catch (Exception _ex) {
				}
			}
		}
		return txt.toString();
	}

	public static String replace(String source, String tobereplace, String usetoreplace) {
		StringBuffer sourceBuffer = new StringBuffer(source);
		int start = source.toLowerCase().indexOf(tobereplace.toLowerCase());
		int end = 0;
		if (start == -1)
			return source;
		do {
			end = start + tobereplace.length();
			sourceBuffer = sourceBuffer.replace(start, end, usetoreplace);
			end = (end + usetoreplace.length()) - tobereplace.length();
			start = sourceBuffer.substring(end).toLowerCase().indexOf(tobereplace.toLowerCase());
			if (start != -1)
				start += end;
			else
				return sourceBuffer.toString();
		} while (true);
	}

	public static String replaceModel(String src, Object oldStr[], Object newStr[]) throws Exception {
		if (oldStr != null) {
			for (int i = 0; i < oldStr.length; i++)
				src = replace(src, oldStr[i] + "", newStr[i] + "");

		}
		return src;
	}

	public static String txtToHtm(String s) {
		if (s.trim().indexOf("<p>") != -1 || s.trim().indexOf("<br>") != -1) {
			return s;
		} else {
			s = replace(s, "\n", "<br>");
			return s;
		}
	}

	public static void writeFile(String file, String text) throws Exception {
		writeFile(file, text, "UTF-8", false);
	}

	public static void appendFile(String file, String text) throws Exception {
		writeFile(file, text, "UTF-8", true);
	}

	public static void writeFile(String file, String text, String charset, boolean append) throws Exception {
		label0: {
			if (text == null)
				return;
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file, append), charset));
			try {
				out.write(text);
				break label0;
			} finally {
				try {
					out.close();
				} catch (Exception _ex) {
				}
			}
		}
	}

	public static String cn(String str) throws UnsupportedEncodingException {
		String temp_p = str;
		byte temp_t[] = temp_p.getBytes();
		String temp = new String(temp_t, "GB2312");
		return temp;
	}

	protected static void encodeByte(byte byte0, StringWriter stringwriter) {
		if (byte0 == 0) {
			stringwriter.write(103);
		} else {
			String s = Integer.toHexString(byte0);
			if (s.length() < 2) {
				stringwriter.write(48);
				stringwriter.write(s);
			} else {
				char ac[] = new char[2];
				s.getChars(s.length() - 2, s.length(), ac, 0);
				stringwriter.write(ac, 0, 2);
			}
		}
	}

	public static String encodeData(byte abyte0[], int i) {
		StringWriter stringwriter = new StringWriter();
		for (int j = 0; j < abyte0.length; j++) {
			encodeByte(abyte0[j], stringwriter);
			if ((j + 1) % i == 0)
				stringwriter.write("\n");
		}

		return stringwriter.toString();
	}

	public static String encodePassword(String s) {
		s = "acedg0574g" + Integer.toHexString(s.length()) + new String(encodeData(s.getBytes(), s.length()));
		return s.trim();
	}

	public static String gbToUnicode(String src) {
		if (src == null || src.length() == 0)
			return src;
		char c[] = src.toCharArray();
		int n = c.length;
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
			b[i] = (byte) c[i];

		return new String(b);
	}

	public static String to8859_1(String src) throws UnsupportedEncodingException {
		byte b[] = src.getBytes("GB2312");
		return new String(b, "8859_1");
	}

	public static String toGB2312(String src) throws UnsupportedEncodingException {
		if (src == null) {
			return null;
		} else {
			byte b[] = src.getBytes("8859_1");
			return new String(b, "GB2312");
		}
	}

	public static String unicodeToGb(String src) {
		if (src == null || src.length() == 0)
			return src;
		byte b[] = src.getBytes();
		int n = b.length;
		char c[] = new char[n];
		for (int i = 0; i < n; i++)
			c[i] = (char) ((short) b[i] & 0xff);

		return new String(c);
	}

	public static boolean checkCode(String kenid[], String code[], int kencount, int codecount) {
		int i = 0;
		String codeup = null;
		String codecurrent = null;
		do {
			codeup = code[i];
			i++;
			codecurrent = code[i];
			if (!codecurrent.substring(0, codeup.length()).equals(codeup)
					|| codecurrent.length() - codeup.length() != Integer.parseInt(kenid[i]))
				return false;
		} while (i != codecount);
		return true;
	}

	public StringBuffer replace(StringBuffer data, char oldchar, String newString) {
		int i = 0;
		if (data.length() == 0)
			return data;
		do
			if (data.charAt(i) == oldchar) {
				data.replace(i, i + 1, newString);
				i += newString.length();
			} else {
				i++;
			}
		while (i < data.length());
		return data;
	}

	/**
	 * insert method comment here。 create time：(2002-5-26 12:05:42)
	 * 
	 * @return java.lang.String
	 * @param src
	 *            java.lang.String
	 * @param startStr
	 *            java.lang.String
	 * @param endStr
	 *            java.lang.String
	 * @param replaceStr
	 *            java.lang.String
	 */
	public static String replace(String src, String startStr, String endStr, String replaceStr) {
		StringBuffer sb = new StringBuffer();
		int idx1 = src.toLowerCase().indexOf(startStr.toLowerCase());
		int idx2 = src.toLowerCase().indexOf(endStr.toLowerCase());

		if (idx1 == -1 || idx2 == -1)
			return src;

		sb.append(src.substring(0, idx1 + startStr.toLowerCase().length()));
		sb.append(replaceStr);
		sb.append(src.substring(idx2));
		src = sb.toString();

		return src;
	}

}