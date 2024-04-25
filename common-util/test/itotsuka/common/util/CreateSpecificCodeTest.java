import itotsuka.common.util.CreateSpecificCode;

public class CreateSpecificCodeTest {

	public static void main(String[] args) {

		CreateSpecificCode csc = new CreateSpecificCode();

		for (int i = 0; i < 10; i++) {
			System.out.println(i + " → " + csc.getSpecificCode());
			System.out.println(i + " → " + csc.getFinalSpecificCode());
			System.out.println("");
			try {
				Thread.sleep(1234);
			} catch (InterruptedException e) {
				// 何もしない
			}
		}
	}
}
