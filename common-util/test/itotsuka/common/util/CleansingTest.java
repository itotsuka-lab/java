import itotsuka.common.util.Cleansing;

public class CleansingTest {

	public static void main(String[] args) {
		final String str = "１ｚＺ*み高％髙ょうじょうりょ({{うてい劍せいめい＞のおつげ劔【】ヴ劒ヴァイオリン剱ありがとう釼aiueoおーおうおおGang★";
//		final String str = "a";
//		final String str = "高高高高";
//		final String str = null;

		Cleansing cln = new Cleansing();
		System.out.println(str + " → " + cln.cleansing(str, "F", true));
		System.out.println(str + " → " + cln.cleansing(str, "F", false));
		System.out.println(str + " → " + cln.cleansing(str, "L", true));
		System.out.println(str + " → " + cln.cleansing(str, "L", false));
	}
}
