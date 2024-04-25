import itotsuka.common.util.*;

public class CreateInquiryCodeTest {

	public static void main(String[] args) {

		CreateInquiryCode cic = new CreateInquiryCode();

		for (int i = 0; i < 10; i++) {
			System.out.println(i + " → " + cic.getInquiryCode());
			System.out.println("");
			try {
				Thread.sleep(1234);
			} catch (InterruptedException e) {
			}
		}
	}
}
