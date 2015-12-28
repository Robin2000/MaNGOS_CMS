package com.ficus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChineseCheck {

	static String regEx = "[\u4e00-\u9fbb]";
	static Pattern pat = Pattern.compile(regEx);

	public static void main(String[] args) {
		String input = "Hell world!";
		System.out.println(isContainsChinese(input));
		input = "hello world";
		System.out.println(isContainsChinese(input));
	}

	public static boolean isContainsChinese(String str) {
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()) {
			flg = true;
		}
		return flg;
	}
}
