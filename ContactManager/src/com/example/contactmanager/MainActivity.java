package com.example.contactmanager;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Vamsi Katepalli NetId = Vxk142730
 * 
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * start date: 10/27/2014
 * 
 * MainActivity activity class is used to show the user list
 * and is the main screen.
 *
 */
public class MainActivity extends Activity {
	
	public final static String FIRST_NAME = "com.example.myfirstapp.FIRST_NAME";
	public final static String LAST_NAME = "com.example.myfirstapp.LAST_NAME";
	public final static String MAIN_CONTEXT = "com.example.myfirstapp.MAIN_CONTEXT";
	public final static String POSITION = "com.example.myfirstapp.POSITION";
	
	List<User> userList = new ArrayList<User>();
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Oncreate method used to initialize tasks.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		populateListView();
		registerClickCallback();
	}

	/**
	 * populateListView method used to populate List by querying the file.
	 * It uses a custom adapter to show the list.
	 */
	private void populateListView() {
		FileManager fileManager = new FileManager();
		try {
			userList = fileManager.getAllRecords(this.getDir("MyFolder", Context.MODE_WORLD_READABLE).getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayAdapter<User> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.contactListView);
		list.setAdapter(adapter);
	}
	
	/**
	 * This is the Custom adapter for showing the list.
	 */
	private class MyListAdapter extends ArrayAdapter{

		public MyListAdapter() {
			super(MainActivity.this,R.layout.item_user,userList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if(itemView == null){
				itemView = getLayoutInflater().inflate(R.layout.item_user, parent,false);
			}
			User currentUser = userList.get(position);
			if(currentUser != null){
				//fill the view
				ImageView imageView = (ImageView)itemView.findViewById(R.id.user_icon);
				imageView.setImageResource(currentUser.getIconId());
				
				TextView firstNameTextView = (TextView) itemView.findViewById(R.id.firstName);
				firstNameTextView.setText(currentUser.getFirstName() + " "
						+ ""+ currentUser.getLastName());
				
				TextView phoneTextView = (TextView) itemView.findViewById(R.id.phoneNumber);
				phoneTextView.setText(currentUser.getPhoneNumber().toString());
			}
			
			return itemView;
		}
		
	}

	/**
	 * In this function we register a call back when item is clicked.
	 */
	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.contactListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				RelativeLayout relativeLayout = (RelativeLayout)view;
				
				Intent intent = new Intent(MainActivity.this, ShowContact.class);
				
				TextView firstNameText = (TextView) relativeLayout.getChildAt(1);
				String firstName = firstNameText.getText().toString().split(" ")[0];
				intent.putExtra(FIRST_NAME, firstName);
				intent.putExtra(POSITION, Integer.toString(position));
				intent.putExtra(MAIN_CONTEXT, MainActivity.this.getDir("MyFolder", Context.MODE_WORLD_READABLE).getAbsolutePath());
				startActivity(intent);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
 
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if(id == R.id.add_contact){
        	Intent intent = new Intent(this, AddContact.class);
        	intent.putExtra(MAIN_CONTEXT, MainActivity.this.getDir("MyFolder", Context.MODE_WORLD_READABLE).getAbsolutePath());
        	startActivity(intent);
        }
		return super.onOptionsItemSelected(item);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onNewIntent(android.content.Intent)
	 * Call back function to refresh the view.
	 */
	protected void onNewIntent(Intent intent) {
		  super.onNewIntent(intent);
		  setIntent(intent);
		  populateListView();
		  registerClickCallback();
	}
}
