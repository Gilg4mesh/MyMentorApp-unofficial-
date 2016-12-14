package com.example.mediaproject_v3;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.http.message.BasicNameValuePair;

import com.example.mediaprojectv3.db.DepartmentElectiveEntity;
import com.example.mediaprojectv3.db.DepartmentObligatoryEntity;
import com.example.mediaprojectv3.db.GeneralElectiveEntity;
import com.example.mediaprojectv3.db.GeneralObligatoryEntity;
import com.example.mediaprojectv3.db.SimpleEntity;
import com.example.mediaprojectv3.db.TotalEntity;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyMentor extends Activity {
	private Button btn;
	private EditText id, passwd;
	@SuppressWarnings("unused")
	private TextView text;
	private Elements rows;
	public static ConnectivityManager mConnectivityManager ;
	public static NetworkInfo mNetworkInfo ;

	private static String s_Server_Ip = "cmap.cycu.edu.tw:8080";
	private static String s_Server_Index_URL = "http://" + s_Server_Ip
			+ "/MyMentor/index.do";
	private static String s_Server_Login_URL = "http://" + s_Server_Ip
			+ "/MyMentor/stdLogin.do";
	private static String s_Server_courseCreditStructure_URL = "http://"
			+ s_Server_Ip + "/MyMentor/courseCreditStructure.do";
	private static String s_temp_response = "";
	private static String s_temp_params = "";
	private static String s_temp_JSESSIONID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymentor);

		btn = (Button) findViewById(R.id.btn);
		id = (EditText) findViewById(R.id.id0);
		passwd = (EditText) findViewById(R.id.passwd0);
		text = (TextView) findViewById(R.id.text0);

		final ProgressDialog progress = new ProgressDialog(this);
		progress.setTitle("Loading");
		progress.setMessage("Wait while loading...");
		progress.setCanceledOnTouchOutside(false);

		final Dialog progressState = new Dialog(this);
		progressState.setTitle("Done Loading !!!");

		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
				if (mNetworkInfo != null && mNetworkInfo.isConnected()) {

					new AsyncTask<Void, Void, Void>() {

						@Override
						protected void onPostExecute(Void result) {
							progress.dismiss();
							progressState.show();
						}

						@Override
						protected void onPreExecute() {
							progress.show();

						}

						@Override
						protected Void doInBackground(Void... params) {

							try {
								Temp();

							} catch (Exception e1) {
							}

							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
							}

							return null;

						}

					}.execute(new Void[] {});
				} // if
				else {
					progressState.setTitle("Network not connected !!!");
					progressState.show();
				} // else
			} // onClick()
		});
		;
	}

	public void Temp() throws Exception {
		Log.e("tst", "entet it");

		String userId = "";
		String password = "";
		userId = id.getText().toString();
		;
		password = passwd.getText().toString();
		;

		// GET JSESSIONID

		try {
			s_temp_response = ClientGET(s_Server_Index_URL, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String response1 = s_temp_response;

		String JSESSIONID = "";
		JSESSIONID = response1.substring(0, response1.indexOf(";"));
		s_temp_JSESSIONID = JSESSIONID;
		Log.e("tst", JSESSIONID);
		// System.out.println("[*]JSESSIONID            : "+JSESSIONID);

		// Login and register JSESSIONID
		String params = "";
		params += "userId=" + userId;
		params += "&password=" + password;
		s_temp_params = params;

		try {
			s_temp_response = ClientPOST(s_Server_Login_URL, s_temp_params,
					s_temp_JSESSIONID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		// GET courseCreditStructure

		try {
			s_temp_response = ClientGET(s_Server_courseCreditStructure_URL,
					s_temp_JSESSIONID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String response3 = s_temp_response;

		// get table data
		System.out.println("[*]----Done Login------");
		// System.out.println("") ;
		Document doc = Jsoup.parse(response3);
		rows = doc.select("tr");

		try {
			LoadData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		// Thread thread5 = new Thread() {
		// public void run() {
		// try {
		// Total.add() ;
		// Simples.add() ;
		// General_O.add() ;
		// General_E.add() ;
		// Department_O.add() ;
		// Department_E.add() ;
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// };
		// thread5.start();
		// thread5.join();

		Total.add();
		Simples.add();
		General_O.add();
		General_E.add();
		Department_O.add();
		Department_E.add();

		/*
		 * for (int i = 0; i < rows.size(); i++) { Element row = rows.get(i);
		 * String s_row = row.text(); String[] array_row = s_row.split(" ");
		 * Log.e("tst", s_row); text.setText(text.getText().toString() + "\n" +
		 * s_row); } // for
		 */
	}

	// //////////////////////////////////////////////////////////////////////////////
	// Method : ClientPOST
	// Check : Correct
	//
	public static String ClientPOST(String s_URL, String s_Post, String s_Cookie)
			throws Exception {
		Log.e("ClientPOST", "entet it");
		Log.e("ClientPOST", s_Post);
		Log.e("ClientPOST", s_Cookie);
		// POST
		String CompleteContet = ""; // 處理完的字串

		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(s_URL);

		httppost.setHeader("Host", "cmap.cycu.edu.tw:8080");
		httppost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/16.0");
		httppost.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httppost.setHeader("Accept-Language",
				"zh-tw,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httppost.setHeader("Accept-Encoding", "gzip, deflate");
		httppost.setHeader("DNT", "1");
		httppost.setHeader("Proxy-Connection", "keep-alive");
		httppost.setHeader("Referer",
				"http://cmap.cycu.edu.tw:8080/MyMentor/logout.do");
		httppost.setHeader("Cookie", s_Cookie);
		// httppost.setHeader("Content-Type",
		// "application/x-www-form-urlencoded") ;
		// Set Content-Type

		// 參數
		if (s_Post != "") {
			// X=1&X=1

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(
					s_Post.split("&")[0].split("=")[0], s_Post.split("&")[0]
							.split("=")[1]));
			Log.e("test", s_Post.split("&")[0].split("=")[0]);
			Log.e("test", s_Post.split("&")[0].split("=")[1]);
			params.add(new BasicNameValuePair(
					s_Post.split("&")[1].split("=")[0], s_Post.split("&")[1]
							.split("=")[1]));
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			httppost.setEntity(ent);
		}

		HttpResponse response = httpclient.execute(httppost);
		CompleteContet = EntityUtils.toString(response.getEntity());
		return CompleteContet;

	} // ClientPOST()

	//
	// End Method : ClientPOST
	// //////////////////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////////
	// Method : ClientGET
	// Check : Correct
	//
	public static String ClientGET(String s_URL, String s_Cookie)
			throws Exception {
		Log.e("ClientGET", "entet it");
		// Get
		String CompleteContet = ""; // 處理完的字串
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(s_URL);
		httpget.setHeader("Cookie", s_Cookie);
		// Execute
		HttpResponse response = httpclient.execute(httpget);

		CompleteContet = EntityUtils.toString(response.getEntity());
		// Log.e("ClientGET",CompleteContet);
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			if (header.getName().contains("Set-Cookie")
					&& header.getValue().contains("JSESSIONID"))
				CompleteContet = header.getValue();
		}
		//

		return CompleteContet;

	} // ClientGET()

	//
	// End Method : ClientGET
	// //////////////////////////////////////////////////////////////////////////////

	public static String md5(String str) {
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] barr = md.digest(str.getBytes()); // 將 byte 陣列加密
			StringBuffer sb = new StringBuffer(); // 將 byte 陣列轉成 16 進制
			for (int i = 0; i < barr.length; i++) {
				sb.append(byte2Hex(barr[i]));
			}
			String hex = sb.toString();
			md5 = hex;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
	}

	public static String byte2Hex(byte b) {
		String[] h = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
				"b", "c", "d", "e", "f" };
		int i = b;
		if (i < 0) {
			i += 256;
		}
		return h[i / 16] + h[i % 16];
	}

	// //////////////////////////////////////////////////////////////////////////////
	// Method : LoadData
	void LoadData() {
		SQLiteDatabase db = MainActivity.dbhelper.getWritableDatabase();
		MainActivity.dbhelper.onUpgrade(db, 1, 1);

		ContentValues values = new ContentValues();
		int nowAt = 2;
		Element row = rows.get(nowAt);
		String s_row = row.text();
		Log.e("tst", s_row);
		String[] str = s_row.split(" ");
		String selectType = new String();
		int baseNum = 0; // 檢查第一個是不是類別，是的話 = 1

		baseNum = 1;
		do { // 第一次基本知能OK
			String insert[] = { "", "", "", "", "", "", "" }; // NEEDNAME,
																// NEEDCREDIT,
																// SEMESTER,
																// DONENAME,
																// DONECREDIT,
																// SCORE, TYPE
			if (str.length - baseNum == 4) { // 未修過
				insert[0] = str[1 + baseNum];
				insert[1] = str[2 + baseNum];
				insert[2] = str[3 + baseNum];
				insert[6] = "not";
			} // if
			else if (str.length - baseNum == 10) { // 已修過
				insert[0] = str[1 + baseNum];
				insert[1] = str[2 + baseNum];
				insert[2] = str[3 + baseNum];
				insert[3] = str[5 + baseNum];
				insert[4] = str[8 + baseNum];
				insert[5] = str[9 + baseNum];
				insert[6] = "done";
			} // else if
			else if (str.length - baseNum == 9) { // 正在修
				insert[0] = str[1 + baseNum];
				insert[1] = str[2 + baseNum];
				insert[2] = str[3 + baseNum];
				insert[3] = str[5 + baseNum];
				insert[4] = str[8 + baseNum];
				insert[6] = "doing";
			} // else if
			else if (str.length - baseNum == 7) { // 已修其他體育
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[5] = str[6 + baseNum];
				insert[6] = "doneother";
			} // else if
			else if (str.length - baseNum == 6) { // 正在修之其他體育
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[6] = "doingother";
			} // else if

			values.put(SimpleEntity.NEEDNAME, insert[0]);
			values.put(SimpleEntity.NEEDCREDIT, insert[1]);
			values.put(SimpleEntity.SEMESTER, insert[2]);
			values.put(SimpleEntity.DONENAME, insert[3]);
			values.put(SimpleEntity.DONECREDIT, insert[4]);
			values.put(SimpleEntity.SCORE, insert[5]);
			values.put(SimpleEntity.TYPE, insert[6]);
			db.insert(SimpleEntity.TABLE_NAME, null, values);

			values = new ContentValues();
			nowAt++;
			row = rows.get(nowAt);
			s_row = row.text();
			str = s_row.split(" ");
			baseNum = 0;
		} while (!str[0].equals("基本知能"));

		Log.e("tst", str[0]);
		values = new ContentValues();
		values.put(TotalEntity.TOTALTYPE, str[0] + "(不含體育)");
		values.put(TotalEntity.NEED, str[2]);
		values.put(TotalEntity.DONE, str[3]);
		db.insert(TotalEntity.TABLE_NAME, null, values);

		nowAt++;
		row = rows.get(nowAt);
		s_row = row.text();
		str = s_row.split(" ");

		selectType = "sky";
		baseNum = 1; // 天類
		values = new ContentValues();
		while (!str[0].equals("通識基礎必修")) {
			String insert[] = { "", "", "", "", "", "", "", "" }; // NEEDNAME,
																	// NEEDCREDIT,
																	// SEMESTER,
																	// DONENAME,
																	// DONECREDIT,
																	// SCORE,
																	// TYPE,
																	// CATEGORY
			if (str[0].equals("人類") || str[0].equals("物類")
					|| str[0].equals("我類")) {
				if (str[0].equals("人類"))
					selectType = "humanity"; // 人類
				if (str[0].equals("物類"))
					selectType = "mateiral"; // 物類
				if (str[0].equals("我類"))
					selectType = "self"; // 我類
				baseNum = 1;
			} // if

			if (str.length - baseNum == 3) { // 未修過
				insert[0] = str[1 + baseNum];
				insert[6] = "not";
				insert[7] = selectType;
			} // if
			else if (str.length - baseNum == 9) { // 已修過
				insert[0] = str[1 + baseNum];
				insert[2] = str[2 + baseNum];
				insert[3] = str[4 + baseNum];
				insert[4] = str[7 + baseNum];
				insert[5] = str[8 + baseNum];
				insert[6] = "done";
				insert[7] = selectType;
			} // else if
			else if (str.length - baseNum == 8) { // 正在修
				insert[0] = str[1 + baseNum];
				insert[2] = str[2 + baseNum];
				insert[3] = str[4 + baseNum];
				insert[4] = str[7 + baseNum];
				insert[6] = "doing";
				insert[7] = selectType;
			} // else if

			values.put(GeneralObligatoryEntity.NEEDNAME, insert[0]);
			values.put(GeneralObligatoryEntity.NEEDCREDIT, insert[1]);
			values.put(GeneralObligatoryEntity.SEMESTER, insert[2]);
			values.put(GeneralObligatoryEntity.DONENAME, insert[3]);
			values.put(GeneralObligatoryEntity.DONECREDIT, insert[4]);
			values.put(GeneralObligatoryEntity.SCORE, insert[5]);
			values.put(GeneralObligatoryEntity.TYPE, insert[6]);
			values.put(GeneralObligatoryEntity.CATEGORY, insert[7]);
			db.insert(GeneralObligatoryEntity.TABLE_NAME, null, values);
			values = new ContentValues();
			nowAt++;
			row = rows.get(nowAt);
			s_row = row.text();
			str = s_row.split(" ");
			baseNum = 0;
		} // while

		Log.e("tst", str[0]);

		values = new ContentValues();
		values.put(TotalEntity.TOTALTYPE, str[0]);
		values.put(TotalEntity.NEED, str[1]);
		values.put(TotalEntity.DONE, str[2]);
		db.insert(TotalEntity.TABLE_NAME, null, values);

		nowAt++;
		row = rows.get(nowAt);
		s_row = row.text();
		str = s_row.split(" ");
		baseNum = 0;
		values = new ContentValues();

		while (!str[0].equals("通識延伸選修：各類至少修2學分,修滿14學分")) {
			String insert[] = { "", "", "", "", "", "", "", "" }; // NEEDNAME,
																	// NEEDCREDIT,
																	// SEMESTER,
																	// DONENAME,
																	// DONECREDIT,
																	// SCORE,
																	// TYPE,
																	// CATEGORY
			if (str[0].equals("天學") || str[0].equals("人學")
					|| str[0].equals("物學") || str[0].equals("我學")) {
				if (str[0].equals("天學"))
					selectType = "sky"; // 延伸天類
				if (str[0].equals("人學"))
					selectType = "humanity"; // 延伸人類
				if (str[0].equals("物學"))
					selectType = "mateiral"; // 延伸物類
				if (str[0].equals("我學"))
					selectType = "self"; // 延伸我類
				baseNum = 1;
			} // if

			if (str.length - baseNum == 7) { // 已修過
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[5] = str[6 + baseNum];
				insert[6] = "done";
				insert[7] = selectType;
			} // if
			else if (str.length - baseNum == 6) { // 正在修
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[6] = "doing";
				insert[7] = selectType;
			} // else if

			values.put(GeneralElectiveEntity.NEEDNAME, insert[0]);
			values.put(GeneralElectiveEntity.NEEDCREDIT, insert[1]);
			values.put(GeneralElectiveEntity.SEMESTER, insert[2]);
			values.put(GeneralElectiveEntity.DONENAME, insert[3]);
			values.put(GeneralElectiveEntity.DONECREDIT, insert[4]);
			values.put(GeneralElectiveEntity.SCORE, insert[5]);
			values.put(GeneralElectiveEntity.TYPE, insert[6]);
			values.put(GeneralElectiveEntity.CATEGORY, insert[7]);
			db.insert(GeneralElectiveEntity.TABLE_NAME, null, values);
			values = new ContentValues();
			nowAt++;
			row = rows.get(nowAt);
			s_row = row.text();
			str = s_row.split(" ");
			baseNum = 0;
		} // while

		values = new ContentValues();
		values.put(TotalEntity.TOTALTYPE, "通識延伸選修(各類至少2)"); // str[0]
		values.put(TotalEntity.NEED, str[1]);
		values.put(TotalEntity.DONE, str[2]);
		db.insert(TotalEntity.TABLE_NAME, null, values);

		nowAt++;
		row = rows.get(nowAt);
		s_row = row.text();
		str = s_row.split(" ");

		baseNum = 1; // 學系必修
		values = new ContentValues();
		while (!str[0].equals("以上必修類別各課程學分數相加")) {
			String insert[] = { "", "", "", "", "", "", "" }; // NEEDNAME,
																// NEEDCREDIT,
																// SEMESTER,
																// DONENAME,
																// DONECREDIT,
																// SCORE, TYPE
			if (str.length - baseNum == 4) { // 未修過
				insert[0] = str[1 + baseNum];
				insert[1] = str[2 + baseNum];
				insert[2] = str[3 + baseNum];
				insert[6] = "not";
			} // if
			else if (str.length - baseNum == 10) { // 已修過
				insert[0] = str[1 + baseNum];
				insert[1] = str[2 + baseNum];
				insert[2] = str[3 + baseNum];
				insert[3] = str[5 + baseNum];
				insert[4] = str[8 + baseNum];
				insert[5] = str[9 + baseNum];
				insert[6] = "done";
			} // else if
			else if (str.length - baseNum == 9) { // 正在修
				insert[0] = str[1 + baseNum];
				insert[1] = str[2 + baseNum];
				insert[2] = str[3 + baseNum];
				insert[3] = str[5 + baseNum];
				insert[4] = str[8 + baseNum];
				insert[6] = "doing";
			} // else if
			else if (str.length - baseNum == 7) { // 已修其他
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[5] = str[6 + baseNum];
				insert[6] = "doneother";
			} // else if
			else if (str.length - baseNum == 6) { // 正在修之其他
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[6] = "doingother";
			} // else if

			values.put(DepartmentObligatoryEntity.NEEDNAME, insert[0]);
			values.put(DepartmentObligatoryEntity.NEEDCREDIT, insert[1]);
			values.put(DepartmentObligatoryEntity.SEMESTER, insert[2]);
			values.put(DepartmentObligatoryEntity.DONENAME, insert[3]);
			values.put(DepartmentObligatoryEntity.DONECREDIT, insert[4]);
			values.put(DepartmentObligatoryEntity.SCORE, insert[5]);
			values.put(DepartmentObligatoryEntity.TYPE, insert[6]);
			db.insert(DepartmentObligatoryEntity.TABLE_NAME, null, values);

			values = new ContentValues();
			nowAt++;
			row = rows.get(nowAt);
			s_row = row.text();
			str = s_row.split(" ");
			baseNum = 0;
		} // while

		values = new ContentValues();
		values.put(TotalEntity.TOTALTYPE, "學系必修"); // str[0]
		values.put(TotalEntity.NEED, str[1]);
		values.put(TotalEntity.DONE, str[2]);
		db.insert(TotalEntity.TABLE_NAME, null, values);

		nowAt++;
		row = rows.get(nowAt);
		s_row = row.text();
		str = s_row.split(" ");

		baseNum = 1; // 學系選修
		values = new ContentValues();
		do { // 第一次學系選修OK
			String insert[] = { "", "", "", "", "", "", "" }; // NEEDNAME,
																// NEEDCREDIT,
																// SEMESTER,
																// DONENAME,
																// DONECREDIT,
																// SCORE, TYPE
			if (str.length - baseNum == 7) { // 已修過
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[5] = str[6 + baseNum];
				insert[6] = "done";
			} // if
			else if (str.length - baseNum == 6) { // 正在修
				insert[2] = str[0 + baseNum];
				insert[3] = str[2 + baseNum];
				insert[4] = str[5 + baseNum];
				insert[6] = "doing";
			} // else if

			values.put(DepartmentElectiveEntity.NEEDNAME, insert[0]);
			values.put(DepartmentElectiveEntity.NEEDCREDIT, insert[1]);
			values.put(DepartmentElectiveEntity.SEMESTER, insert[2]);
			values.put(DepartmentElectiveEntity.DONENAME, insert[3]);
			values.put(DepartmentElectiveEntity.DONECREDIT, insert[4]);
			values.put(DepartmentElectiveEntity.SCORE, insert[5]);
			values.put(DepartmentElectiveEntity.TYPE, insert[6]);
			db.insert(DepartmentElectiveEntity.TABLE_NAME, null, values);
			values = new ContentValues();
			nowAt++;
			row = rows.get(nowAt);
			s_row = row.text();
			str = s_row.split(" ");
			baseNum = 0;
		} while (!str[0].equals("學系選修"));

		values = new ContentValues();
		Log.e("tst", str[0]);
		values.put(TotalEntity.TOTALTYPE, str[0]);
		values.put(TotalEntity.NEED, str[1]);
		values.put(TotalEntity.DONE, str[2]);
		db.insert(TotalEntity.TABLE_NAME, null, values);

		nowAt++;
		row = rows.get(nowAt);
		s_row = row.text();
		str = s_row.split(" ");

		values.put(TotalEntity.TOTALTYPE, str[0]);
		values.put(TotalEntity.NEED, str[1]);
		values.put(TotalEntity.DONE, str[3]);
		db.insert(TotalEntity.TABLE_NAME, null, values);

		System.out.println("[*]----Done LoadData------");
	} // LoadData()

}
