package itotsuka.common.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

public class CreateSpecificCode {

	/**
	 * 固有番号生成処理（1ヵ月で失効用）
	 *
	 * @return 8桁番号
	 */
	public String getSpecificCode() {

		String l_nowTime = new SimpleDateFormat("ddHHmmssSSS").format(new Date(System.currentTimeMillis()));
		String l_base32 = Long.toString(Long.parseLong(l_nowTime), 32);

		if (l_base32.length() < 7) {
			l_base32 = "0" + l_base32;
		}

		int l_base10 = 0;
		for (int i = 0; i < l_base32.length(); i++) {
			l_base10 += Integer.parseInt(l_base32.substring(i, i + 1), 32);
		}

		// チェックディジット設定
		l_base32 = l_base32 + Integer.toString(l_base10 % 32, 32);

		// 誤認文字変換
		l_base32 = mistakingCharChange(l_base32);

		// アナグラム化
		l_base32 = anagrammatization(l_base32);

		return l_base32.toUpperCase();
	}

	/**
	 * 固有番号生成処理（永久用）
	 *
	 * @return 10桁番号
	 */
	public String getFinalSpecificCode() {

		String l_nowTime = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
		String l_base32 = Long.toString(Long.parseLong(l_nowTime), 32);

		// 誤認文字変換
		l_base32 = mistakingCharChange(l_base32);

		// アナグラム化
		l_base32 = anagrammatization(l_base32);

		return l_base32.toUpperCase();
	}

	/**
	 * 誤認文字変換
	 *
	 * @param str
	 *            対象文字列
	 */
	private String mistakingCharChange(String str) {

		str = str.replace("0", "w");
		str = str.replace("o", "x");
		str = str.replace("1", "y");
		str = str.replace("i", "z");

		return str;
	}

	/**
	 * アナグラム化
	 *
	 * @param str
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	private String anagrammatization(String str) {

		char[] chars = str.toCharArray();
		Random random = new Random();
		int index;
		char temp;
		for (int i = chars.length - 1; i > 0; i--)
		{
		    index = random.nextInt(i + 1);
		    temp = chars[index];
		    chars[index] = chars[i];
		    chars[i] = temp;
		}
		return new String(chars);
	}
}
