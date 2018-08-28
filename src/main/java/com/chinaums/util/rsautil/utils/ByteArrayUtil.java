package com.chinaums.util.rsautil.utils;

public class ByteArrayUtil {

	/**
	 * 填充byte数组
	 * 
	 * @param buf
	 * @param b
	 */
	public static void fill(byte[] buf, byte b) {
		for (int i = 0; i < buf.length; i++) {
			buf[i] = b;
		}
	}

	/**
	 * 两个byte数组进行异或运算
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static byte[] or(byte data1[], byte data2[]) {
		int len = (data1.length <= data2.length) ? data1.length : data2.length;
		byte[] res = new byte[len];
		for (int i = 0; i < len; i++) {
			res[i] = (byte) (data1[i] ^ data2[i]);
		}
		return res;
	}

	public static String hexChars = "0123456789ABCDEF";

	/**
	 * byte数组转换成Hex字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String byteArray2HexString(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			byte lo = (byte) (0x0f & data[i]);
			byte hi = (byte) ((0xf0 & data[i]) >>> 4);
			sb.append(hexChars.charAt(hi)).append(hexChars.charAt(lo));
		}
		return sb.toString();
	}

	/**
	 * Hex字符串转换成byte数组
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexString2ByteArray(String hexStr) {
		if (hexStr == null)
			return null;
		if (hexStr.length() % 2 != 0) {
			return null;
		}
		byte[] data = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			char hc = hexStr.charAt(2 * i);
			char lc = hexStr.charAt(2 * i + 1);
			byte hb = ByteUtils.hexChar2Byte(hc);
			byte lb = ByteUtils.hexChar2Byte(lc);
			if (hb < 0 || lb < 0) {
				return null;
			}
			int n = hb << 4;
			data[i] = (byte) (n + lb);
		}
		return data;
	}

	/**
	 * 整形转换成Hex字符串
	 * 
	 * @param n
	 * @return
	 */
	public static String int2HexString(int n) {
		return Integer.toHexString(n).toUpperCase();
	}

	/**
	 * 格式化Hex字符串的宽度，不足左补'0'
	 * 
	 * @param width
	 * @param hexStr
	 * @return
	 */
	public static String formatHexStr(int width, String hexStr) {
		if (hexStr.length() >= width) {
			return hexStr.substring(hexStr.length() - width);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < width - hexStr.length(); i++) {
			sb.append("0");
		}
		sb.append(hexStr);
		return sb.toString();
	}

	/**
	 * 将byte数组转换成打印格式字符串，方便输出调试信息
	 * 
	 * @param data
	 * @param dataLen
	 * @return
	 */
	public static String toPrintString(byte[] data, int dataLen) {
		if (data == null)
			return "";
		if (dataLen < 0)
			return "";
		int printLen = 0;
		if (dataLen > data.length)
			printLen = data.length;
		else
			printLen = dataLen;
		StringBuffer sb = new StringBuffer();
		String lenStr = int2HexString(data.length);
		int width = lenStr.length();
		String printStr = "";
		int loopLen = 0;
		loopLen = (printLen / 16 + 1) * 16;
		for (int i = 0; i < loopLen; i++) {
			if (i % 16 == 0) {
				sb.append("0x").append(formatHexStr(width, int2HexString(i)))
						.append(": ");
				printStr = "";
			}
			if (i % 16 == 8) {
				sb.append(" ");
			}
			if (i < printLen) {
				sb.append(" ").append(formatHexStr(2, int2HexString(data[i])));
				if (data[i] > 31 && data[i] < 127)
					printStr += (char) data[i];
				else
					printStr += '.';
			} else {
				sb.append("   ");
			}
			if (i % 16 == 15) {
				sb.append(" ").append(printStr).append("\r\n");
			}
		}
		return sb.toString();
	}
}
