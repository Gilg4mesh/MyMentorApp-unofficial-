package com.example.mediaproject_v3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.mediaprojectv3.db.GeneralObligatoryEntity;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class General_O extends Fragment {

	private View v;

	private ListView listView;
	private List<String> listString_T2 ;
	private List<String> listString_D ;
	private List<String> listString_C ;
	private static List<String> listString1 ;
	private static List<String> listString2 ;
	private static List<String> listString3 ;
	private static List<String> listString1_D ;
	private static List<String> listString2_D ;
	private static List<String> listString3_D ;
	private static List<String> listString1_C ;
	private static List<String> listString2_C ;
	private static List<String> listString3_C ;

	private ViewPager viewPage;
	private static MyPageAdapter adapter;
	private ArrayList<View> views;
	private String[] titles = { "未修過", "已修過", "正在修" };
	private baseAdapter[] p = new baseAdapter[3] ;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { // TODO Auto-generated method stub

		v = inflater.inflate(R.layout.general_o, container, false);
		
		viewPage = (ViewPager) v.findViewById(R.id.viewpage1);
		views = new ArrayList<View>();
		int len = titles.length;
        getCursor() ;

		// 設置 ViewPager 顯示的頁面內容
		LayoutInflater inflaterP = getActivity().getLayoutInflater();
		View view = null;
		listString_T2 = listString1;
		listString_D = listString1_D;
		listString_C = listString1_C;
		for (int i = 0; i < len; i++) {
			view = inflaterP.inflate(R.layout.cpager, null);
			listView = (ListView) view.findViewById(R.id.preferenses2);
			p[i] = new baseAdapter();
			listView.setAdapter(p[i]);
			listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

	            @Override
	            public void onItemClick( AdapterView<?> arg0, View arg1, int position, long id ) {
	            	dialog( position );
	            }
	            }) ;
			views.add(view);
		}

		// 建立一個 PagerAdapter
		adapter = new MyPageAdapter(titles, views);
		viewPage.setAdapter(adapter);

		// 設定你的 PagerSlidingTabStrip
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
		tabs.setShouldExpand(true);
		// 將你的 ViewPager 傳遞給 PagerSlidingTabStrip
		tabs.setViewPager(viewPage);
		tabs.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch( arg0 ) {
				case 0 :
					listString_T2 = listString1;
					listString_D = listString1_D;
					listString_C = listString1_C;
					p[0].notifyDataSetChanged();
					break;
					
				case 1 :
					listString_T2 = listString2;
					listString_D = listString2_D;
					listString_C = listString2_C;
					p[1].notifyDataSetChanged();
					break;
					
				case 2 :
					listString_T2 = listString3;
					listString_D = listString3_D;
					listString_C = listString3_C;
					p[2].notifyDataSetChanged();
					break;
				
				}
				
			}}) ;
		// 以下為選擇性的
		// 如果你需要設置 OnPageChangeListener，
		// 請用 tabs.setOnPageChangeListener(...)
		// 而不是 viewPage.setOnPageChangeListener(...)
		// tabs.setOnPageChangeListener(mPageChangeListener);

		return v;

	}
	
	
	private static void getCursor() {
        listString1 = new ArrayList<String>();
        listString2 = new ArrayList<String>();
        listString3 = new ArrayList<String>();
        listString1_D = new ArrayList<String>();
        listString2_D = new ArrayList<String>();
        listString3_D = new ArrayList<String>();
        listString1_C = new ArrayList<String>();
        listString2_C = new ArrayList<String>();
        listString3_C = new ArrayList<String>();
        SQLiteDatabase db = MainActivity.dbhelper.getReadableDatabase();
		String[] columns = { GeneralObligatoryEntity.NEEDNAME, GeneralObligatoryEntity.NEEDCREDIT, GeneralObligatoryEntity.SEMESTER, GeneralObligatoryEntity.DONENAME, GeneralObligatoryEntity.DONECREDIT, GeneralObligatoryEntity.SCORE, GeneralObligatoryEntity.TYPE, GeneralObligatoryEntity.CATEGORY };

		Cursor cursor = db.query(GeneralObligatoryEntity.TABLE_NAME, columns, null, null,
				null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if ( cursor.getString(6).equals("not") ) {
				listString1.add(cursor.getString(0));
				listString1_D.add("天類需宗哲、人哲，其他類各一，詳細功能之後再做 :3");
				listString1_C.add(cursor.getString(7)) ;
			} // if
			else if ( cursor.getString(6).equals("done") ) {
				listString2.add(cursor.getString(3));
				listString2_D.add("學期："+cursor.getString(2)+" 學分："+cursor.getString(4)+" 分數："+cursor.getString(5));
				listString2_C.add(cursor.getString(7)) ;
			} // else if
			else if ( cursor.getString(6).equals("doing") ) {
				listString3.add(cursor.getString(0));
				listString3_D.add("學期："+cursor.getString(2));
				listString3_C.add(cursor.getString(7)) ;
			} // else if
			cursor.moveToNext();
		}
	}
	
	
	public static void add() {
		getCursor() ;
		adapter.notifyDataSetChanged();
	}
	
	
	
	static AlertDialog showdialog = null;

	void dialog( int position ) {
		LayoutInflater.from(getActivity());
		android.app.AlertDialog.Builder longinDialog = new AlertDialog.Builder(getActivity());
		longinDialog.setTitle( listString_D.get(position) );
		longinDialog.create();
		showdialog = longinDialog.create(); // 把AlertDialog.Builder接到AlertDialog裡，才可以使用isShowing、dismiss
		showdialog.show();

	} // dialog()
	
	
	
	private class MyPageAdapter extends PagerAdapter{
		String[] titles;
		List<View> views;
 
		public MyPageAdapter(String[] titles, List<View> views){
			this.titles = titles;
			this.views = views;
		}
		
		@Override
		public int getCount(){
			return views == null ? 0 : views.size();
		}
 
		/**
		* 必要
		* PagerSlidingTabStrip 透過它來取得顯示在 Tabs 上的標題
		*/
		@Override
		public CharSequence getPageTitle(int position){
			return titles[position] ;
		}
 
		@Override
		public Object instantiateItem(ViewGroup container, int position){
			container.addView(views.get(position));
			return views.get(position);
		}
 
		@Override
		public void destroyItem(ViewGroup container, int position, Object object){
			container.removeView(views.get(position));
		}
 
		@Override
		public boolean isViewFromObject(View arg0, Object arg1){
			return arg0 == arg1;
		}
	}
	
	

	private class baseAdapter extends BaseAdapter {

		public baseAdapter() {
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listString_T2.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listString_T2.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			view = LinearLayout.inflate(getActivity(),
					R.layout.listview_content, null);

			int iconNum = 0 ;
			if ( listString_C.get(position).equals("sky") ) iconNum = R.drawable.s ;
			else if ( listString_C.get(position).equals("humanity") ) iconNum = R.drawable.h ;
			else if ( listString_C.get(position).equals("mateiral") ) iconNum = R.drawable.m ;
			else if ( listString_C.get(position).equals("self") ) iconNum = R.drawable.i ;
			
			// imageView
			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
			
			
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					iconNum);
			int newW = 100;
			int newH = (int) ((bmp.getHeight() / (float) bmp.getWidth()) * newW);
			Bitmap newBmp = Bitmap.createScaledBitmap(bmp, newW, newH, true);
			imageView.setImageBitmap(newBmp);

			// textView
			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText(listString_T2.get(position));

			return view;
		}
	};

}
