package cs.frame.util;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("DefaultLocale")
public class StringUtil {

	public static boolean isEmpty(String string) {
		if (string != null && string.trim().length() > 0) {
			return false;
		}
		return true;
	}

	public static String toMD5String(String string) {

		char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};

		byte[] btInput = string.getBytes();
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");

			mdInst.update(btInput);

			byte[] md = mdInst.digest();

			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = md5String[byte0 >>> 4 & 0xf];
				str[k++] = md5String[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	public static String genNewMsgCountString(int newMsgCount) {
		if (newMsgCount != 0) {
			if (newMsgCount > 99) {
				return "99+";
			} else {
				return "" + newMsgCount;
			}
		} else {
			return "";
		}
	}

	/**
	 * 过滤字符串的前后空格以及所有的回车换行符
	 *
	 * @param src
	 * @return
	 */
	public static String trim(String src) {
		src = src.trim(); //先过滤掉前后的空格

		Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");
		//正则表达式的匹配一定要是这样，否则单个替换\r|\n的时候会错误
		Matcher matcher = pattern.matcher(src);
		String dest = matcher.replaceAll(" "); //将回车或者换行符替换为空字符串

		return dest;
	}

	public static boolean verifyPhoneNumber(String phoneNumber) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

		Matcher m = p.matcher(phoneNumber);

		return m.matches();
	}

	/**
	 * 字符串转换成十六进制值
	 *
	 * @param bin String 我们看到的要转换成十六进制的字符串
	 * @return
	 */
	public static String bin2hex(String bin) {
		char[] digital = "0123456789ABCDEF".toCharArray();
		StringBuffer sb = new StringBuffer("");
		byte[] bs = bin.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(digital[bit]);
			bit = bs[i] & 0x0f;
			sb.append(digital[bit]);
		}
		return sb.toString();
	}

	/**
	 * 十六进制转换字符串
	 *
	 * @param hex String 十六进制
	 * @return String 转换后的字符串
	 */
	public static String hex2bin(String hex) {
		String digital = "0123456789ABCDEF";
		char[] hex2char = hex.toCharArray();
		byte[] bytes = new byte[hex.length() / 2];
		int temp;
		for (int i = 0; i < bytes.length; i++) {
			temp = digital.indexOf(hex2char[2 * i]) * 16;
			temp += digital.indexOf(hex2char[2 * i + 1]);
			bytes[i] = (byte) (temp & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * java字节码转字符串
	 *
	 * @param b
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String byte2hex(byte[] b) { //一个字节的数组，

		// 转成16进制字符串

		String hs = "";
		String tmp = "";
		for (int n = 0; n < b.length; n++) {
			//整数转成十六进制表示

			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs = hs + "0" + tmp;
			} else {
				hs = hs + tmp;
			}
		}
		tmp = null;
		return hs.toUpperCase(); //转成大写

	}

	/**
	 * 字符串转java字节码
	 *
	 * @param b
	 * @return
	 */
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节

			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		b = null;
		return b2;
	}

	public static int genMinusUniqueId() {
		long l = System.currentTimeMillis();
		String s = String.valueOf(l);
		int i = -Integer.parseInt(s.substring(4));

		return i;
	}

	/**
	 * 在字符串特定位置插入字符串
	 *
	 * @param src      原字符串
	 * @param dec      插入字符串
	 * @param position 插入位置
	 * @return
	 */
	public static String insertStringInParticularPosition(String src, String dec, int position) {
		StringBuffer stringBuffer = new StringBuffer(src);

		return stringBuffer.insert(position, dec).toString();

	}

	public static BigDecimal digitalDisplay(Object value) {
		BigDecimal digital = convToDecimal(value);
		BigDecimal digitalDisp = new BigDecimal("0");
		if (digital.compareTo(new BigDecimal("0")) == 0) {
			return digital;
		}

		// 大于亿的场合,以亿为单位;大于万的以万为单位;否则以元为单位
		if (digital.compareTo(new BigDecimal("100000000")) >= 0) {
			digitalDisp = divide(digital, new BigDecimal("100000000")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		} else if (digital.compareTo(new BigDecimal("10000")) >= 0) {
			digitalDisp = divide(digital, new BigDecimal("10000")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		} else {
			digitalDisp = digital;
		}

		return digitalDisp;
	}

	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
		if (dividend == null || divisor == null) {
			throw new RuntimeException("不能除 NULL值。");
		}
		BigDecimal result = null;

		try {
			result = dividend.divide(divisor);
		} catch (ArithmeticException ex) {
			result = dividend.divide(divisor, 29, BigDecimal.ROUND_HALF_EVEN);
		}

		return result;
	}

	/**
	 * 转换值为BigDecimal型。
	 *
	 * @return BigDecimal型
	 */
	public static BigDecimal convToDecimal(Object value) {
		BigDecimal dec = new BigDecimal("0");
		if (value == null) {
			return dec;
		} else if (value instanceof BigDecimal) {
			dec = (BigDecimal) value;
		} else {
			try {
				dec = new BigDecimal(String.valueOf(value).trim().replace(",", ""));
			} catch (Exception ex) {
				return dec;
			}
		}
		return dec;
	}

	public static String digitalUnitDisplay(BigDecimal digital) {
		String digitalStyleDisp = "元";
		if (digital.compareTo(new BigDecimal("0")) == 0) {
			return digitalStyleDisp;
		}

		// 大于亿的场合,以亿为单位;大于万的以万为单位;否则以元为单位
		if (digital.compareTo(new BigDecimal("100000000")) >= 0) {
			digitalStyleDisp = "亿";
		} else if (digital.compareTo(new BigDecimal("10000")) >= 0) {
			digitalStyleDisp = "万";
		} else {
			digitalStyleDisp = "元";
		}

		return digitalStyleDisp;
	}

	public static String convToMoney(Object pValue) {
		return String.format("%1$,.2f", convToDecimal(pValue));
	}
}
