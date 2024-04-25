import itotsuka.common.util.*;

public class DateFormatTest {

	public static void main(String[] args) {

		DateFormat df = new DateFormat();
		System.out.println("メソッド1 → " + df.dateConversion(1, 2, 3));
		System.out.println("メソッド1 → " + df.dateConversion(1234, 2, 3));
		System.out.println("メソッド1 → " + df.dateConversion(12345, 2, 3));
		System.out.println("メソッド1 → " + df.dateConversion(1, 12, 3));
		System.out.println("メソッド1 → " + df.dateConversion(1, 123, 3));
		System.out.println("メソッド1 → " + df.dateConversion(1, 2, 13));
		System.out.println("メソッド1 → " + df.dateConversion(1, 2, 134));
		System.out.println("メソッド1 → " + df.dateConversion(0, 0, 0));

		System.out.println("");

		System.out.println("メソッド2 → " + df.dateConversion("20150226", "", true));
		System.out.println("メソッド2 → " + df.dateConversion("20150226", "", false));
		System.out.println("メソッド2 → " + df.dateConversion("20150226", "/", true));
		System.out.println("メソッド2 → " + df.dateConversion("20150226", "/", false));
		System.out.println("メソッド2 → " + df.dateConversion("20150226", "---", true));
		System.out.println("メソッド2 → " + df.dateConversion("20150226", "---", false));
		System.out.println("メソッド2 → " + df.dateConversion("20150226", " ", true));
		System.out.println("メソッド2 → " + df.dateConversion("20150226", " ", false));
		System.out.println("メソッド2 → " + df.dateConversion("2015226", "", true));
		System.out.println("メソッド2 → " + df.dateConversion("2015226", "", false));
		System.out.println("メソッド2 → " + df.dateConversion("201502269", "", true));
		System.out.println("メソッド2 → " + df.dateConversion("201502269", "", false));

		System.out.println("");

		System.out.println("メソッド3 → " + df.dateConversion(""));
		System.out.println("メソッド3 → " + df.dateConversion("2015/12/31"));
		System.out.println("メソッド3 → " + df.dateConversion("2013aaa03bbbb11cc"));
		System.out.println("メソッド3 → " + df.dateConversion("２０１７/０７/２３"));
		System.out.println("メソッド3 → " + df.dateConversion("201211269"));
		System.out.println("メソッド3 → " + df.dateConversion("200111"));
		System.out.println("メソッド3 → " + df.dateConversion("aaaaaaaaaaaaa"));
	}
}
