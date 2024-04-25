import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

//バージョン１
public class OthelloMain {

	public  String masu[][] = new String[11][11];	//全体マス(本体)
	private String koma1;							//自分コマ(常に入れ替わり)
	private String koma2;							//相手コマ(常に入れ替わり)
	private String inTate = "";						//入力された縦マス
	private String inYoko = "";						//入力された横マス
	private String freeStr = "";					//多目的String変数
	private String masuBu1[][] = new String[11][11];//全体マス(バックアップ1)
	private String masuBu2[][] = new String[11][11];//全体マス(バックアップ2)
	private String masuBu3[][] = new String[11][11];//全体マス(バックアップ3)
	private String masuBu4[][] = new String[11][11];//全体マス(バックアップ4)
	private String masuBu5[][] = new String[11][11];//全体マス(バックアップ5)
	private String masuBu6[][] = new String[11][11];//全体マス(バックアップ6)
	private String masuBu7[][] = new String[11][11];//全体マス(バックアップ7)
	private boolean gameEndFlag = false;			//ゲーム終了フラグ
	private boolean inCheckFlag = false;			//入力チェックフラグ
	private boolean komaOkFlag = false;				//設置コマのOKフラグ
	private boolean passFlag = false;				//パスフラグ
	private boolean player = true;					//プレイヤーフラグ
	private double point1[]= new double[64];		//点数一覧1
	private int masuSeq = 8;						//マスの大きさ
	private int BCount = 0;							//黒のコマ数
	private int WCount = 0;							//白のコマ数
	private int inX = 0;							//入力された縦座標
	private int inY = 0;							//入力された横座標
	private int count1 = 0;							//比較ポイント1
	private int count2 = 0;							//比較ポイント2
	private int changeCount1 = 0;					//比較チェンジカウント1
	private int changeCount2 = 0;					//比較チェンジカウント2
	private int pattern =0;							//考えたパターン数
	private int maxPattern =0;						//考えた最多パターン数
	private int pointHyou[][] ={
			{0,  0,   0,   0,   0,   0,   0,   0,   0,0},
			{0,999, -30,  20,   5,   5,  20, -30, 999,0},
			{0,-30, -60,  -5,  -5,  -5,  -5, -60, -30,0},
			{0, 20,  -5,  15,   3,   3,  15,  -5,  20,0},
			{0,  5,  -5,   3,   3,   3,   3,  -5,   5,0},
			{0,  5,  -5,   3,   3,   3,   3,  -5,   5,0},
			{0, 20,  -5,  15,   3,   3,  15,  -5,  20,0},
			{0,-30, -60,  -5,  -5,  -5,  -5, -60, -30,0},
			{0,999, -30,  20,   5,   5,  20, -30, 999,0},
			{0,  0,   0,   0,   0,   0,   0,   0,   0,0}};
//メイン-----------------------------------------------------------------------
	public static void main(String[] args)throws IOException, InterruptedException{

		OthelloMain Othello = new OthelloMain();
		//初期処理
		Othello.inz();
		while(Othello.gameEndFlag==false){
			//ゲーム
			Othello.game();
		}
		if(Othello.BCount == Othello.WCount){
			System.out.print("引き分けです！");
		}else if(Othello.BCount > Othello.WCount){
			System.out.print("黒の勝ちです！");
		}else{
			System.out.print("白の勝ちです！");
		}
	}
//初期処理---------------------------------------------------------------------
	private void inz() {

		OthelloBean ob = new OthelloBean();

		masu = ob.setMasu(masuSeq,masu);
		masuBu1 = ob.setMasu(masuSeq,masuBu1);
		masuBu2 = ob.setMasu(masuSeq,masuBu2);
		masuBu3 = ob.setMasu(masuSeq,masuBu3);
		masuBu4 = ob.setMasu(masuSeq,masuBu4);
		masuBu5 = ob.setMasu(masuSeq,masuBu5);
		masuBu6 = ob.setMasu(masuSeq,masuBu6);
		masuBu7 = ob.setMasu(masuSeq,masuBu7);
		//CPU対戦選択
		try {
			inCheckFlag=false;	//フラグの初期化
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while(inCheckFlag==false){
				System.out.print("先行にしますか？(Y/N)：");
				freeStr = "";
				freeStr = br.readLine();
				if(freeStr.matches("[n-yN-Y]")){
					freeStr = freeStr.toUpperCase();
					if(freeStr.equals("Y")){
						player = true;
						inCheckFlag = true;
					}else if(freeStr.equals("N")){
						player = false;
						inCheckFlag = true;
					}else{
						System.out.println("入力された値が正しくありません。");
					}
				}else{
					System.out.println("入力された値が正しくありません。");
				}
			}
			dsplay();	//画面表示
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//ゲーム-----------------------------------------------------------------------
	private void game() throws InterruptedException {

		if(!player){
			System.out.println("ＣＰＵの番です（白）\n");
		}
		komaOkFlag = false;
		masuBu1 = buckUp(masuBu1,masu);//バックアップ
		//コマが置けない間
		while(!komaOkFlag){
			//Playerの場合
			if(player){
				setKoma(true);
				masuBu1 = buckUp(masuBu1,masu);//バックアップ
				passFlag = true;
				for(int y = 0 ; y < 8 ; y++){
					for(int x = 0 ; x < 8 ; x++){
						if(masuCheck(x+1,y+1)){//コマ置き換えチェック
							passFlag = false;
							masu = buckUp(masu,masuBu1);//元に戻す
						}
					}
				}
				if(!passFlag){
					inCheckFlag=false;	//フラグの初期化
					while(inCheckFlag==false){
						System.out.println("あなたの番です（黒）");
						System.out.println("座標を半角英数字で指定してください。\n");
						try {
							BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
						System.out.print("横：");
							inYoko = br.readLine();
							System.out.print("縦：");
							inTate = br.readLine();
							//入力チェック
							check();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
						if(masuCheck(inX,inY)){//コマ置き換えチェック
							komaOkFlag = true;
						}else{
							System.out.println("そこには置けません。");
						}
						dsplay();	//画面表示
				}else{
					System.out.println("あなたの番です（黒）");
					Thread.sleep(1000);
					System.out.println("\n\nどこにも置けないのでpassします。\n\n");
					Thread.sleep(1000);
					dsplay();	//画面表示
					komaOkFlag = true;
					//CPUが置けるか判断
					setKoma(false);
					masuBu1 = buckUp(masuBu1,masu);//バックアップ
					passFlag = true;
					for(int y = 0 ; y < 8 ; y++){
						for(int x = 0 ; x < 8 ; x++){
							if(masuCheck(x+1,y+1)){//コマ置き換えチェック
								passFlag = false;
							}
							masu = buckUp(masu,masuBu1);//元に戻す
						}
					}
					if(passFlag){
						System.out.println("どちらも置けないのでゲームを終了します。");
						gameEndFlag=true;
					}
				}
			//CPUの場合
			}else{
				//処理開始時間
				long start = System.currentTimeMillis();
				autoPlay();//CPUプレイ
				//処理終了時間
				long stop = System.currentTimeMillis();
				System.out.print("ＣＰＵは " + (stop - start)/1000 + "秒 ");
				System.out.println(new DecimalFormat("###,###,###").format(pattern) +"パターン考えた\n");
				dsplay();	//画面表示
			}
		}
		//Player交代
		if(player){
			player = false;
		}else{
			player = true;
		}
		//ゲーム終了判定
		if(BCount + WCount == masuSeq * masuSeq || BCount == 0 || WCount == 0){
			gameEndFlag=true;
		}
	}
//入力チェック-----------------------------------------------------------------
	private void check() {

		if(inTate.matches("[1-8]") && inYoko.matches("[a-hA-H]")){
			inY = Integer.parseInt(inTate);
			inYoko = inYoko.toUpperCase();
			if(inYoko.equals("A")){
				inX = 1;
			}else if(inYoko.equals("B")){
				inX = 2;
			}else if(inYoko.equals("C")){
				inX = 3;
			}else if(inYoko.equals("D")){
				inX = 4;
			}else if(inYoko.equals("E")){
				inX = 5;
			}else if(inYoko.equals("F")){
				inX = 6;
			}else if(inYoko.equals("G")){
				inX = 7;
			}else if(inYoko.equals("H")){
				inX = 8;
			}
			inCheckFlag=true;
		}else{
			System.out.println("入力エラー");
			dsplay();	//画面表示
		}
	}
//画面表示---------------------------------------------------------------------
	private void dsplay() {

		for(int i=0;i<masuSeq+1;i++){
			for(int j=0;j<masuSeq+1;j++){
				System.out.print(masu[i][j]);
			}
			System.out.println("");
		}
		komaCount();//コマのカウント
		System.out.print("黒："+ BCount +"個／");
		System.out.println("白："+ WCount +"個\n");
	}
//コマカウント-----------------------------------------------------------------
	private void komaCount() {

		BCount = 0;
		WCount = 0;
		for(int i=1;i<masuSeq+1;i++){
			for(int j=1;j<masuSeq+1;j++){
				if(masu[i][j].equals(OthelloContents.BLACK)){
					BCount++;
				}else if(masu[i][j].equals(OthelloContents.WHITE)){
					WCount++;
				}
			}
		}
	}
//CPUプレイ--------------------------------------------------------------------
	public void autoPlay() {

		setKoma(false);
		int cnt1 = 0;
		pattern =0;
		masuBu1 = buckUp(masuBu1,masu);//バックアップ
		for(int y = 0 ; y < 8 ; y++){
			for(int x = 0 ; x < 8 ; x++){
				point1[cnt1] = -99999;
				if(masuCheck(x+1,y+1)){//コマ置き換えチェック
					if(BCount + WCount >= 63){
						pattern++;//パターン計算
						pointCheck();
						point1[cnt1] = changeCount1 - changeCount2;
					}
//22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222相手p
					masuBu2 = buckUp(masuBu2,masu);//バックアップ
					setKoma(true);
					int maxPoint2 = -99999;
					for(int y2 = 0 ; y2 < 8 ; y2++){
						for(int x2 = 0 ; x2 < 8 ; x2++){
							if(masuCheck(x2+1,y2+1)){//コマ置き換えチェック
								if(BCount + WCount >= 62){
									pattern++;//パターン計算
									pointCheck();
									if(maxPoint2 < changeCount1 - changeCount2){
										maxPoint2 = changeCount1 - changeCount2;
									}
								}
//33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333自分c
								masuBu3 = buckUp(masuBu3,masu);//バックアップ
								setKoma(false);
								int maxPoint3 = -99999;
								for(int y3 = 0 ; y3 < 8 ; y3++){
									for(int x3 = 0 ; x3 < 8 ; x3++){
										if(masuCheck(x3+1,y3+1)){//コマ置き換えチェック
											if(BCount + WCount >= 61){
												pattern++;//パターン計算
												pointCheck();
												if(maxPoint3 < changeCount1 - changeCount2){
													maxPoint3 = changeCount1 - changeCount2;
												}
											}
//44444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444相手p
											masuBu4 = buckUp(masuBu4,masu);//バックアップ
											setKoma(true);
											int maxPoint4 = -99999;
											for(int y4 = 0 ; y4 < 8 ; y4++){
												for(int x4 = 0 ; x4 < 8 ; x4++){
													if(masuCheck(x4+1,y4+1)){//コマ置き換えチェック
														if(BCount + WCount >= 60){
															pattern++;//パターン計算
															pointCheck();
															if(maxPoint4 < changeCount1 - changeCount2){
																maxPoint4 = changeCount1 - changeCount2;
															}
														}
//55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555自分c
														masuBu5 = buckUp(masuBu5,masu);//バックアップ
														setKoma(false);
														int maxPoint5 = -99999;
														for(int y5 = 0 ; y5 < 8 ; y5++){
															for(int x5 = 0 ; x5 < 8 ; x5++){
																if(masuCheck(x5+1,y5+1)){//コマ置き換えチェック
																	if(BCount + WCount >= 59){
																		pattern++;//パターン計算
																		pointCheck();
																		if(maxPoint5 < changeCount1 - changeCount2){
																			maxPoint5 = changeCount1 - changeCount2;
																		}
																	}
//66666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666相手p
																	masuBu6 = buckUp(masuBu6,masu);//バックアップ
																	setKoma(true);
																	int maxPoint6 = -99999;
																	for(int y6 = 0 ; y6 < 8 ; y6++){
																		for(int x6 = 0 ; x6 < 8 ; x6++){
																			if(masuCheck(x6+1,y6+1)){//コマ置き換えチェック
																				pattern++;//パターン計算
																				pointCheck();
																				if(BCount + WCount >= 58){
																					if(maxPoint6 < changeCount1 - changeCount2){
																						maxPoint6 = changeCount1 - changeCount2;
																					}
																				}else{
																					if(maxPoint6 < count1 - count2){
																						maxPoint6 = count1 - count2;
																					}
																				}
////@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
////@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
																				masu = buckUp(masu,masuBu6);//元に戻す
																			}else{
																				pointCheck();
																				if(maxPoint6 < count1 - count2){
																					maxPoint6 = count1 - count2;
																				}
																			}
																		}
																	}
																	if(maxPoint5 < maxPoint6 * -1){
																		maxPoint5 = maxPoint6 * -1;
																	}
																	setKoma(false);
//6666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666相手p
																	masu = buckUp(masu,masuBu5);//元に戻す
																}
															}
														}
														if(maxPoint4 < maxPoint5 * -1){
															maxPoint4 = maxPoint5 * -1;
														}
														setKoma(true);
//5555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555自分c
														masu = buckUp(masu,masuBu4);//元に戻す
													}else{
														pointCheck();
														if(maxPoint4 < count1 - count2){
															maxPoint4 = count1 - count2;
														}
													}
												}
											}
											if(maxPoint3 < maxPoint4 * -1){
												maxPoint3 = maxPoint4 * -1;
											}
											setKoma(false);
//44444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444相手p
											masu = buckUp(masu,masuBu3);//元に戻す
										}
									}
								}
								if(maxPoint2 < maxPoint3 * -1){
									maxPoint2 = maxPoint3 * -1;
								}
								setKoma(true);
//33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333自分c
								masu = buckUp(masu,masuBu2);//元に戻す
							}else{
								point1[cnt1] = 999999;
							}
						}
					}
					point1[cnt1] += maxPoint2 * -1;
					setKoma(false);
//22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222相手p
					masu = buckUp(masu,masuBu1);//元に戻す
				}
				cnt1++;
			}
		}
		int maxSeq = 0;
		for(int i = 1;i < 64; i++){
			if(point1[maxSeq] < point1[i]){
				maxSeq = i;
			}
		}
		freeStr = "";
		if(point1[maxSeq] != -99999){
			OthelloBean ob = new OthelloBean();
			freeStr = ob.getMasu(maxSeq);
			masuCheck(Integer.parseInt(freeStr.substring(1,2)),Integer.parseInt(freeStr.substring(0,1)));
		}else{
			System.out.println("pass");
		}
		//最多パターン数より多いか
		if(maxPattern < pattern){
			maxPattern += pattern;
		}

		komaOkFlag = true;
	}
//全方向コマチェック＆仮置き換え-----------------------------------------------
	public boolean masuCheck(int x ,int y) {

		int pieceChangeFlag = 0;

		if(!masu[y][x].equals(OthelloContents.BLANK)){
			return false;
		}
		if(!masu[y-1][x-1].equals(koma2) && !masu[y][x-1].equals(koma2) && !masu[y+1][x-1].equals(koma2) && !masu[y-1][x].equals(koma2)
				&& !masu[y+1][x].equals(koma2) && !masu[y-1][x+1].equals(koma2) && !masu[y][x+1].equals(koma2) && !masu[y+1][x+1].equals(koma2)){
			return false;
		}
		pieceChangeFlag += eachDirectionCheck(x,y,-1,-1);	//左上チェック
		pieceChangeFlag += eachDirectionCheck(x,y,0,-1);	//上チェック
		pieceChangeFlag += eachDirectionCheck(x,y,1,-1);	//右上チェック
		pieceChangeFlag += eachDirectionCheck(x,y,-1,0);	//左チェック
		pieceChangeFlag += eachDirectionCheck(x,y,1,0);		//右チェック
		pieceChangeFlag += eachDirectionCheck(x,y,-1,1);	//左下チェック
		pieceChangeFlag += eachDirectionCheck(x,y,0,1);		//下チェック
		pieceChangeFlag += eachDirectionCheck(x,y,1,1);		//右下チェック

		if(pieceChangeFlag > 0){
			masu[y][x] = koma1;
			return true;
		}else{
			return false;
		}
	}
	private int eachDirectionCheck(int ix , int iy , int x ,int y){

		int wx = x;
		int wy = y;
		int x2 = x;
		int y2 = y;

		while(masu[iy + y][ix + x].equals(koma2)){
			if(masu[iy + y + y2][ix + x + x2].equals(koma1)){
				while(masu[iy + wy][ix + wx].equals(koma2)){
					masu[iy + wy][ix + wx] = koma1;
					wx += x2;
					wy += y2;
				}
				return 1;
			}
			x += x2;
			y += y2;
		}
		return 0;
	}
//ポイント計算-----------------------------------------------------------------
	private void pointCheck() {

		count1 = 0;
		count2 = 0;
		changeCount1 = 0;
		changeCount2 = 0;
		for(int i=1;i<masuSeq+1;i++){
			for(int j=1;j<masuSeq+1;j++){
				if(masu[i][j].equals(koma1)){
					count1 += pointHyou[i][j];
					changeCount1++;
				}else if(masu[i][j].equals(koma2)){
					count2 += pointHyou[i][j];
					changeCount2++;
				}
			}
		}
	}
//バックアップ-----------------------------------------------------------------
	private String[][] buckUp(String tbl1[][],String tbl2[][]) {

		for(int i = 0 ; i < 8 ; i++){
			for(int j = 0 ; j < 8 ; j++){
				freeStr = "";
				freeStr = tbl2[i+1][j+1];
				tbl1[i+1][j+1] = freeStr;
			}
		}
		return tbl1;
	}
//コマセット1------------------------------------------------------------------
	private void setKoma(boolean flag) {
		//if(flag)とif(!flag)で切り替え
		if(!flag){
			koma1 = OthelloContents.WHITE;
			koma2 = OthelloContents.BLACK;
		}else{
			koma1 = OthelloContents.BLACK;
			koma2 = OthelloContents.WHITE;
		}
	}
}