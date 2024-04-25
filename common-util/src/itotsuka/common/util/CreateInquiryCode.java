package itotsuka.common.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CreateInquiryCode {

	/**
	 * 問合せ番号生成処理
	 *
	 * @return 14桁番号
	 */
	public String getInquiryCode() {

		String l_inquiryCode = "";
		String l_nowTime = new SimpleDateFormat("MMddHHmmssSSS").format(new Date(System.currentTimeMillis()));

		int l_sum = 0;
		for (int i = 0; i < l_nowTime.length(); i++) {
			l_sum += Integer.parseInt(l_nowTime.substring(i, i + 1));
		}

		// チェックディジット設定
		while (l_sum >= 10) {
			l_sum = (l_sum / 10) + (l_sum % 10);
		}

		// 番号編成
		String w_inquiryCode = l_nowTime + String.valueOf(l_sum);
		for (int i = 0; i < w_inquiryCode.length() / 2; i++) {
			l_inquiryCode = l_inquiryCode + w_inquiryCode.substring(i, i + 1);
			l_inquiryCode = l_inquiryCode + w_inquiryCode.substring(i + 7, i + 8);
		}

		return l_inquiryCode;
	}
}
