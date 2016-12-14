package com.example.mediaproject_v3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

import com.example.mediaprojectv3.db.DBHelper;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	private SharedPreferences preferences;
	private Editor editor;
	private int sec ;

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
		sec = number ;
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			Total total = new Total();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, total).commit();
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			Simples simple = new Simples();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, simple).commit();
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			General_O general_o = new General_O();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, general_o).commit();
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			General_E general_e = new General_E();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, general_e).commit();
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			Department_O department_o = new Department_O();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, department_o).commit();
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
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
