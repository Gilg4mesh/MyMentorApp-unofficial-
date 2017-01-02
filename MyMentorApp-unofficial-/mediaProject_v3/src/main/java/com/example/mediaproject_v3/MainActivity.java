package com.example.mediaproject_v3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.provider.Settings.Secure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.mediaprojectv3.db.DBHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	private List<String> departmentID, departmentName;
	private SharedPreferences preferences;
	private Editor editor;
	private int sec ;
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String android_id ;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	public static DBHelper dbhelper;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbhelper = new DBHelper(this);
		setContentView(R.layout.activity_main);
		android_id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
		deptCourse();
		// =======================首次頁面用=======================
		preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
		// 判断是不是首次登录，
		if (preferences.getBoolean("firststart", true)) {
			addShortcut() ;
			editor = preferences.edit();
			// 将登录标志位设置为false，下次登录时不在显示首次登录界面
			editor.putBoolean("firststart", false);
			editor.commit();
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, Start.class);
			startActivity(intent);
		}
		// =======================首次頁面用=======================

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void onSectionAttached(int number) {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		final DatabaseReference CoursePreview = database.getReference("關注類別");
		Date date = new Date();

		sec = number ;
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			CoursePreview.child(android_id).child(sdf.format(date)).setValue(mTitle);
			Total total = new Total();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, total).commit();
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			CoursePreview.child(android_id).child(sdf.format(date)).setValue(mTitle);
			Simples simple = new Simples();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, simple).commit();
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			CoursePreview.child(android_id).child(sdf.format(date)).setValue(mTitle);
			General_O general_o = new General_O();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, general_o).commit();
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			CoursePreview.child(android_id).child(sdf.format(date)).setValue(mTitle);
			General_E general_e = new General_E();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, general_e).commit();
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			CoursePreview.child(android_id).child(sdf.format(date)).setValue(mTitle);
			Department_O department_o = new Department_O();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, department_o).commit();
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
			CoursePreview.child(android_id).child(sdf.format(date)).setValue(mTitle);
			Department_E department_e = new Department_E();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, department_e).commit();
			break;
		}
	}

	
	

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			new AlertDialog.Builder(MainActivity.this)
					.setItems(departmentName.toArray(new String[departmentName.size()]), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String id = departmentID.get(which);

							FirebaseDatabase database = FirebaseDatabase.getInstance();
							final DatabaseReference CoursePreview = database.getReference("大綱");
							Date date = new Date();
							CoursePreview.child(android_id).child(sdf.format(date)).setValue(departmentName.get(which));
							Uri uri=Uri.parse("http://cmap.cycu.edu.tw:8080/MyMentor/deptCourse.do?type=3&deptCode=" + id + "&pageName=%E6%95%99%E5%AD%B8%E7%89%B9%E8%89%B2%E8%88%87%E8%AA%B2%E7%A8%8B%E8%A6%8F%E5%8A%83");
							Intent i=new Intent(Intent.ACTION_VIEW,uri);
							startActivity(i);
						}
					})
					.show();

			return true;
		}

		if (id == R.id.coursewiki) {
			FirebaseDatabase database = FirebaseDatabase.getInstance();
			final DatabaseReference Coursewiki = database.getReference("選課大全");
			Date date = new Date();
			Coursewiki.child(android_id).child(sdf.format(date)).setValue("");
			Uri uri=Uri.parse("https://coursewiki.clouder.today/");
			Intent i=new Intent(Intent.ACTION_VIEW,uri);
			startActivity(i);

			return true;
		}

		if (id == R.id.fanpage) {
			FirebaseDatabase database = FirebaseDatabase.getInstance();
			final DatabaseReference Fanpage = database.getReference("下載更新");
			Date date = new Date();
			Fanpage.child(android_id).child(sdf.format(date)).setValue("");
			Uri uri=Uri.parse("https://www.facebook.com/mymentorappunofficial/");
			Intent i=new Intent(Intent.ACTION_VIEW,uri);
			startActivity(i);

			return true;
		}



		if (item.getItemId() == R.id.action_example) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MyMentor.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public void deptCourse() { // 設定課程大綱
		departmentID = new ArrayList<>();
		departmentName = new ArrayList<>();
		departmentName.add("資工系");departmentID.add("4700B");
		departmentName.add("應用數學系");departmentID.add("3100B");
		departmentName.add("物理學系");departmentID.add("3200B");
		departmentName.add("化學系");departmentID.add("3300B");
		departmentName.add("心理學系");departmentID.add("3400B");
		departmentName.add("生物科技學系");departmentID.add("3500B");
		departmentName.add("化學工程學系");departmentID.add("4100B");
		departmentName.add("土木工程學系");departmentID.add("4200B");
		departmentName.add("機械工程學系");departmentID.add("4300B");
		departmentName.add("生物醫學工程學系");departmentID.add("4501B");
		departmentName.add("生物環境工程學系");departmentID.add("4900B");
		departmentName.add("企業管理學系");departmentID.add("5100B");
		departmentName.add("國際經營與貿易學系");departmentID.add("520AB");
		departmentName.add("會計學系");departmentID.add("5300B");
		departmentName.add("資訊管理學系");departmentID.add("5400B");
		departmentName.add("財務金融學系");departmentID.add("5700B");
		departmentName.add("建築學系");departmentID.add("6100B");
		departmentName.add("商業設計學系");departmentID.add("6300B");
		departmentName.add("室內設計學系");departmentID.add("6200B");
		departmentName.add("景觀學系");departmentID.add("6500B");
		departmentName.add("設計學士原住民專班");departmentID.add("6700B");
		departmentName.add("特殊教育學系");departmentID.add("8100B");
		departmentName.add("應用外國語文學系");departmentID.add("8200B");
		departmentName.add("應用華語文學系");departmentID.add("8700B");
		departmentName.add("工業與系統工程學系");departmentID.add("440AB");
		departmentName.add("電子工程學系");departmentID.add("4600B");
		departmentName.add("電機工程學系");departmentID.add("4800B");
		departmentName.add("電機資訊學院學士班");departmentID.add("A00AB");
		departmentName.add("財經法律學系");departmentID.add("5500B");
		departmentName.add("通識教育中心");departmentID.add("8300B");
	} // deptCourse()


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}


	
	
	private void addShortcut() {
		 Intent shortcutIntent = new Intent(getApplicationContext(),  
				 SplashScreen.class); // 啟動捷徑入口，一般用MainActivity，有使用其他入口則填入相對名稱，ex:有使用SplashScreen  
		 shortcutIntent.setAction(Intent.ACTION_MAIN);  
		 Intent addIntent = new Intent();  
		 addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // shortcutIntent送入  
		 addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,  
		   getString(R.string.app_name)); // 捷徑app名稱  
		 addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,  
		   Intent.ShortcutIconResource.fromContext(  
		     getApplicationContext(),// 捷徑app圖  
		     R.mipmap.ic_launcher));
		 addIntent.putExtra("duplicate", false); // 只創建一次  
		 addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // 安裝  
		 getApplicationContext().sendBroadcast(addIntent); // 送出廣播  
		} 
	
	
	@Override
	protected void onResume() {
		super.onResume();
		onSectionAttached( sec ) ;
		restoreActionBar() ;
		
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDatabase();
	}

	private void closeDatabase() {
		dbhelper.close();
	}
}
