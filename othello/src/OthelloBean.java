public class OthelloBean {

	public String[][] setMasu(int masuSeq, String[][] masu) {

		//マスの初期化
		for(int i=0;i<=masuSeq+2;i++){
			for(int j=0;j<=masuSeq+2;j++){
				masu[i][j] = OthelloContents.BLANK;
			}
		}
		masu[0][0] = "★";
		//横ラベル
		masu[0][1] = "Ａ";
		masu[0][2] = "Ｂ";
		masu[0][3] = "Ｃ";
		masu[0][4] = "Ｄ";
		masu[0][5] = "Ｅ";
		masu[0][6] = "Ｆ";
		masu[0][7] = "Ｇ";
		masu[0][8] = "Ｈ";
		//縦ラベル
		masu[1][0] = "１";
		masu[2][0] = "２";
		masu[3][0] = "３";
		masu[4][0] = "４";
		masu[5][0] = "５";
		masu[6][0] = "６";
		masu[7][0] = "７";
		masu[8][0] = "８";
		//初期駒
		masu[4][4] = OthelloContents.BLACK;
		masu[4][5] = OthelloContents.WHITE;
		masu[5][5] = OthelloContents.BLACK;
		masu[5][4] = OthelloContents.WHITE;

		return masu;
	}

	public String getMasu(int maxSeq) {
		//一番ポイントの高い座標を返す
		return String.valueOf(maxSeq / 8 + 1) + String.valueOf(maxSeq % 8 + 1);
	}
}
