package com.example.mediaproject_v3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mediaprojectv3.db.TotalEntity;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Total extends Fragment {
	private View v;
	private ListView listView;
	static baseAdapter adapter;

	// private List<String> listString = Arrays.asList("基本知能：6/6","通識基礎必修14/14", "通識延伸選修：12/14", "學系必修：84/99", "學系選修：12/18", "畢業所需：128/151");
	private static List<String> listString ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { // TODO Auto-generated method stub

		v = inflater.inflate(R.layout.total, container, false);
		
		System.out.println("-------Check------");
        listView = (ListView) v.findViewById( R.id.listView ) ;
        getCursor() ;
        adapter = new baseAdapter() ;
        listView.setAdapter( adapter ) ;
        
        
		return v;

	}
	
	private static void getCursor() {
		
        listString = new ArrayList<String>();
        SQLiteDatabase db = MainActivity.dbhelper.getReadableDatabase();
		String[] columns = { TotalEntity.TOTALTYPE, TotalEntity.DONE, TotalEntity.NEED };

		Cursor cursor = db.query(TotalEntity.TABLE_NAME, columns, null, null,
				null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			listString.add(cursor.getString(0)+cursor.getString(1)+"/"+cursor.getString(2));
			cursor.moveToNext();
		}
	}
	
	
	public static void add() {
		getCursor() ;
		
	}

	private class baseAdapter extends BaseAdapter {

		public baseAdapter() {
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listString.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listString.get(arg0);
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

			// imageView
			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
			
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.mipmap.ic_launcher);
			int newW = 100;
			int newH = (int) ((bmp.getHeight() / (float) bmp.getWidth()) * newW);
			Bitmap newBmp = Bitmap.createScaledBitmap(bmp, newW, newH, true);
			imageView.setImageBitmap(newBmp);

			// textView
			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText(listString.get(position));

			return view;
		}
	};

	

}
