package itotsuka.common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class Cleansing {

	/**
	 * クレンジング処理
	 *
	 * @param creanStr
	 *                   クレンジング対象文字列
	 * @param creanType
	 *                   クレンジングタイプ（F:完全,L:簡易）
	 * @param codeChgFlg
	 *                   文字コード化フラグ
	 * @return クレンジング後文字列
	 */
	public String cleansing(String creanStr, String creanType, boolean codeChgFlg) {

		String l_creanStr = "";

		// 入力チェック
		if (creanStr == null || creanStr.trim().length() == 0) {
			return l_creanStr;
		}

		// 一文字変換
		l_creanStr = singleCharChange(creanStr);

		// 記号文字変換
		l_creanStr = symbolCharChange(l_creanStr);

		// クレンジングタイプ判定
		if ("F".equals(creanType)) {
			// 表記揺れ文字変換
			l_creanStr = swingCharChange(l_creanStr);
			// 異体字変換
			l_creanStr = variantsCharChange(l_creanStr);
		} else if ("L".equals(creanType)) {
			// 何もしない
		} else {
			// 何もしない
		}
		// 文字コード化判定
		if (codeChgFlg) {
			return charCodeChange(l_creanStr);
		}
		return l_creanStr;
	}

	/**
	 * 一文字変換
	 *
	 * @param str
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	private String singleCharChange(String str) {

		String l_str = str;
		for (int i = 0; i < Const.CLEAN_SINGLE_CHAR.length; i++) {
			for (int j = 0; j < Const.CLEAN_SINGLE_CHAR[i].length; j++) {
				l_str = l_str.replaceAll(Pattern.quote(Const.CLEAN_SINGLE_CHAR[i][j]), Const.CLEANED_SINGLE_CHAR[i]);
			}
		}
		return l_str.toUpperCase();
	}

	/**
	 * 記号文字変換
	 *
	 * @param str
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	private String symbolCharChange(String str) {

		String l_str = str;
		for (int i = 0; i < Const.CLEAN_SYMBOL_CHAR.length; i++) {
			l_str = l_str.replaceAll(Pattern.quote(Const.CLEAN_SYMBOL_CHAR[i]), "");
		}
		return l_str.toUpperCase();
	}

	/**
	 * 表記揺れ文字変換
	 *
	 * @param str
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	private String swingCharChange(String str) {

		String l_str = str;
		for (int i = 0; i < Const.CLEAN_SWING.length; i++) {
			for (int j = 0; j < Const.CLEAN_SWING[i].length; j++) {
				l_str = l_str.replaceAll(Const.CLEAN_SWING[i][j], Const.CLEANED_SWING[i]);
			}
		}
		return l_str;
	}

	/**
	 * 異体字変換
	 *
	 * @param str
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	private String variantsCharChange(String str) {

		String l_str = str;
		for (int i = 0; i < Const.CLEAN_VARIANTS.length; i++) {
			for (int j = 0; j < Const.CLEAN_VARIANTS[i].length; j++) {
				l_str = l_str.replaceAll(Const.CLEAN_VARIANTS[i][j], Const.CLEANED_VARIANTS[i]);
			}
		}
		return l_str;
	}

	/**
	 * 文字コード変換（UTF-8）
	 *
	 * @param str
	 *            変換前文字列
	 * @return 変換後文字列
	 */
	private String charCodeChange(String str) {

		StringBuilder l_returnStr = new StringBuilder();
		for (char c : str.toCharArray()) {
			try {
				String hexStr = "";
				byte[] buf = Character.toString(c).getBytes("UTF-8");
				for (byte b : buf) {
					hexStr += String.format("%02x", b);
				}
				if (hexStr.length() == 2) {
					l_returnStr.append("0000" + hexStr);
				} else if (hexStr.length() == 4) {
					l_returnStr.append("00" + hexStr);
				} else {
					l_returnStr.append(hexStr);
				}
			} catch (UnsupportedEncodingException e) {
				// 異常処理の場合そのまま値を返却する
				return str;
			}
		}
		return l_returnStr.toString().toUpperCase();
	}
}
