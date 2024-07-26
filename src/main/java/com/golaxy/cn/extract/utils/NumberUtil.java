package com.golaxy.cn.extract.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 数字工具类
 * 
 * @author 牛鹏飞
 * @version 创建日期：2013-09-09 16：40：10
 * 
 */
public class NumberUtil {



	/**
	 * 格式化double到小数点后几位
	 * 
	 * @param d
	 *            原数据
	 * @param ws
	 *            保留后面几位
	 * @return
	 */
	public static double format(double d, int ws) {
		String pattern = "0.";
		for (int i = 0; i < ws; i++) {
			pattern += "0";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return Double.parseDouble(df.format(d));
	}

	/**
	 * 判断是否是int
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isInt(String num) {
		if (StringUtil.isEmpty(num)) {
			return false;
		} else {
			try {
				Integer.parseInt(num);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	/**
	 * 判断是否是double
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isDouble(String num) {
		if (StringUtil.isEmpty(num)) {
			return false;
		} else {
			try {
				Double.parseDouble(num);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	/**
	 * 判断是否是float
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isFloat(String num) {
		if (StringUtil.isEmpty(num)) {
			return false;
		} else {
			try {
				Float.parseFloat(num);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}






}
