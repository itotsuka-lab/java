package itotsuka.common.util;

import java.util.Calendar;

public class DateFormat {

	/**
	 * 日付変換処理（8桁変換）
	 *
	 * @param year
	 *              年
	 * @param month
	 *              月
	 * @param day
	 *              日
	 * @return 8桁日付（YYYYMMDD）
	 */
	public String dateConversion(int year, int month, int day) {

		String l_yyyymmdd = "";
		String l_year = String.valueOf(year);
		String l_month = String.valueOf(month);
		String l_day = String.valueOf(day);

		// 年の変換
		while (l_year.length() < 4) {
			l_year = "0" + l_year;
		}
		// 月の変換
		if (l_month.length() < 2) {
			l_month = "0" + l_month;
		}
		// 日の変換
		if (l_day.length() < 2) {
			l_day = "0" + l_day;
		}
		l_yyyymmdd = l_year + l_month + l_day;
		// 桁数チェック
		if (l_yyyymmdd.length() != 8) {
			l_yyyymmdd = "20000101";
		}
		return l_yyyymmdd;
	}

	/**
	 * 日付変換処理（文字区切り）
	 *
	 * @param yyyymmdd
	 *                 8桁日付
	 * @param section
	 *                 区切り文字
	 * @param weekFlg
	 *                 曜日付加フラグ
	 * @return String 文字区切り日付文字列
	 */
	public String dateConversion(String yyyymmdd, String section,
			boolean weekFlg) {

		String l_yyyymmdd = yyyymmdd;

		// 桁数チェック
		if (l_yyyymmdd.length() != 8) {
			l_yyyymmdd = "20000101";
		}

		String l_year = l_yyyymmdd.substring(0, 4);
		String l_month = l_yyyymmdd.substring(4, 6);
		String l_day = l_yyyymmdd.substring(6, 8);

		// 文字区切り
		if ("".equals(section)) {
			l_yyyymmdd = l_year + "年" + l_month + "月" + l_day + "日";
		} else {
			l_yyyymmdd = l_year + section + l_month + section + l_day;
		}
		// 曜日付加
		if (weekFlg) {
			try {
				Calendar l_cal = Calendar.getInstance();
				l_cal.set(Integer.parseInt(l_year),
						Integer.parseInt(l_month) - 1, Integer.parseInt(l_day));
				l_yyyymmdd += "("
						+ Const.WEEK_LIST[l_cal.get(Calendar.DAY_OF_WEEK)]
						+ ")";
			} catch (Exception e) {
				return "2000年01月01日(土)";
			}
		}
		return l_yyyymmdd;
	}

	/**
	 * 日付変換処理（区切り文字削除）
	 *
	 * @param date
	 *             区切りあり日付文字列
	 * @return String 区切りなし日付文字列
	 */
	public String dateConversion(String date) {

		String l_yyyymmdd = "";

		for (int i = 0; i < date.length(); i++) {
			try {
				l_yyyymmdd += String.valueOf(Integer.parseInt(date.substring(i,
						i + 1)));
			} catch (Exception e) {
				// 何もしない
			}
		}
		// 桁数チェック
		if (l_yyyymmdd.length() != 8) {
			l_yyyymmdd = "20000101";
		}
		return l_yyyymmdd;
	}
}
