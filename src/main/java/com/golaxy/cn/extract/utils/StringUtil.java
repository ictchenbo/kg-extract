package com.golaxy.cn.extract.utils;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author 牛鹏飞
 * @date 2020-11-07 下午4:39:26
 */
public class StringUtil {


	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	/**
	 * 
	 * 说明:获取32位无-号的UUID
	 *
	 * @return
	 *
	 * @author 牛鹏飞
	 * @date 2020-11-12
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}



	public static String getShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}


	
	/**
	 * 字符串为空返回true
	 * 
	 * @param argValue
	 *            将要判断的字符串
	 * @return 为null或者""返回true
	 */
	public static boolean isEmpty(String argValue) {
		if (argValue == null || "".equals(argValue) || argValue.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串不为空返回true
	 * 
	 * @param argValue
	 *            将要判断的字符串
	 * @return 为null或者""返回false
	 */
	public static boolean isNotEmpty(Object argValue) {
		if (argValue == null || "".equals(argValue)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumberic(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断一个字符串是否为字母
	 * @param fstrData
	 * @return
	 */
	public static boolean isEnglistLetter(String fstrData) {
		char c = fstrData.charAt(0);
		if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为汉字
	 * @param str
	 * @return
	 */
	public static boolean vd(String str) {

		char[] chars = str.toCharArray();
		boolean isUtf8 = false;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;

				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
					isUtf8 = true;
					break;
				}
			}
		}
		return isUtf8;
	}


	/**
	 * 多个字符串不为空返回true
	 * 
	 * @param argValue
	 *            将要判断的字符串
	 * @return 为null或者""返回false
	 */
	public static boolean isNotEmpty(String... argValue) {
		for (String s : argValue) {
			if (s == null || "".equals(s) || s.length() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符串中包含特殊字符返回true
	 * 
	 * @param argString
	 *            将要判断的字符串
	 * @return 有特殊字符 返回true
	 */
	public static boolean isHaveSymbol(String argString) {
		String signalRex = ".*[!£$%^&*@?<>+_';\"].*"; // 过滤特殊符号
		if (Pattern.matches(signalRex, argString)) {
			return true;
		}
		return false;
	}

	/**
	 * 剔除html字符串中的标记
	 * 
	 * @param argHtmlstring
	 *            将要转换的字符串
	 * @return
	 */
	public static String cleanHTML(String argHtmlstring) {
		String rtnString = argHtmlstring;
		rtnString = rtnString.replaceAll("</?[^>]+>", "");
		return rtnString;
	}

	/**
	 * MD5加密
	 * 
	 * @param @param string
	 * @return String
	 */
	public static String getMD5old(String string) {
		byte[] source = string.getBytes();
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
			// 进制需要 32 个字符
			int k = 0;// 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
				// 进制字符的转换
				byte byte0 = tmp[i];// 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
				// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

			}
			s = new String(str);// 换后的结果转换为字符串

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * MD5加密
	 * 
	 * @param @param string
	 * @return String
	 */
	public static String getMD5(String string) {
		byte[] source = string.getBytes();
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
			// 进制需要 32 个字符
			int k = 0;// 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
				// 进制字符的转换
				byte byte0 = tmp[i];// 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
				// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

			}
			s = new String(str);// 换后的结果转换为字符串

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 替换关键字专用方法
	 * 
	 * @param str
	 * @param keys
	 * @return
	 */
	public static String keyReplace(String str, String keys) {
		String[] keysArray = keys.split(",");
		for (int i = 0; i < keysArray.length; i++) {
			String oneKey = keysArray[i];
			if (str.indexOf(oneKey) != -1) {
				str = str.replace(oneKey, StringUtil.getKeyCount(oneKey));
			}
		}
		return str;
	}

	/**
	 * 用于过滤字符串关键字
	 * 
	 * @param keys
	 * @return
	 */
	public static String getKeyCount(String keys) {
		String rtKeys = "";
		for (int i = 0; i < keys.length(); i++)
			rtKeys += "*";
		return rtKeys;
	};

	/**
	 * 判断是否是日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isNotDateStringValid(String date) {
		Pattern p = Pattern.compile("[-]");
		String[] dataArr = p.split(date);
		for (String strTmp : dataArr) {
			System.out.println(strTmp);
		}
		return false;
	}

	/**
	 * 获取字符串编码格式
	 * 
	 * @param @param str
	 * @param @return
	 * @return String
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (!(str.equals(new String(str.getBytes(encode), encode)))) {
				return encode;
			}
			String s = encode;
			return s;
		} catch (Exception localException3) {
			encode = "ISO-8859-1";
			try {
				if (!(str.equals(new String(str.getBytes(encode), encode))))
					return encode;
				String s1 = encode;
				return s1;
			} catch (Exception e) {
				encode = "UTF-8";
				try {
					if (!(str.equals(new String(str.getBytes(encode), encode))))
						return encode;
					String s2 = encode;
					return s2;
				} catch (Exception e1) {
					encode = "GBK";
					try {
						if (str.equals(new String(str.getBytes(encode), encode))) {
							String s3 = encode;
							return s3;
						}
					} catch (Exception e2) {
					}
				}
			}
		}
		return "";
	}

	/**
	 * 数组连接字符串
	 * 
	 * @param obj
	 * @param key
	 * @return
	 */
	public static String connBykey(Object[] obj, String key) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < obj.length; i++) {
			sb.append(obj[i].toString());
			if (i != obj.length - 1)
				sb.append(key);
		}
		return sb.toString();
	}

	/**
	 * 字符串转化为小写
	 * 
	 * @param @param str
	 * @param @return
	 * @return String
	 */
	@SuppressWarnings("unused")
	private static String toLowerCase(String str) {
		return str.toLowerCase();
	}

	/**
	 * 字符串转化为大写
	 * 
	 * @param @param str
	 * @param @return
	 * @return String
	 */
	@SuppressWarnings("unused")
	private static String toUpperCase(String str) {
		return str.toUpperCase();
	}
}
