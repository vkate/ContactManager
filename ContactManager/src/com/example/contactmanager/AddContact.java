package com.example.contactmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Vamsi Katepalli Vxk142730
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * start date: 10/27/2014
 * 
 * AddContact activity class is used to show the add form to the user.
 * User wil be able to add the contact by giving input in four fields.
 * Only first name is mandatory.
 *
 */
public class AddContact extends Activity {

	EditText firstName; // first name
	EditText lastName; //last name
	EditText phoneNumber; //phone number
	EditText emailAddres; //email address
	int saveMode; 
	String mainActivityFileDir; 
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * On Create method is overridden here and startup activity 
	 * are done here.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		Intent intent = getIntent();
	    String firstNameText = intent.getStringExtra(MainActivity.FIRST_NAME);
	    mainActivityFileDir = intent.getStringExtra(MainActivity.MAIN_CONTEXT);
	    saveMode=0;
	    firstName = ((EditText) findViewById(R.id.firstName));
	    lastName = (EditText) findViewById(R.id.lastname);
	    phoneNumber = (EditText) findViewById(R.id.phoneNumber);
	    emailAddres = (EditText) findViewById(R.id.emailAddress);
	    if(firstNameText!=null){
	    	saveMode=1;
		    User user = getUser(firstNameText);
			firstName.setText(user.getFirstName());
			lastName.setText(user.getLastName());
			phoneNumber.setText(user.getPhoneNumber().toString());
			emailAddres.setText(user.getEmailAddress());
	    }
	    // To focus on firstName by default.
	    firstName = ((EditText) findViewById(R.id.firstName));
	    firstName.requestFocus();
	}

	/**
	 * @param firstNameText
	 * @return User
	 * This method returns the user object based on first name.
	 */
	private User getUser(String firstNameText) {
		FileManager fileManager = new FileManager();
		return fileManager.getRecord(mainActivityFileDir, new User(firstNameText));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * This method adds menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 * This method is overridden for handling item selected.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * @param view
	 * This is the main method for save contact.
	 * File manager is called and verification checks are taken care.
	 */
	public void saveContact(View view){
		try{
			FileManager fileManager = new FileManager();
				if(firstName.getText().toString()==null || firstName.getText().toString().equals("")){
					Toast.makeText(this, "Please enter first name of the contact", Toast.LENGTH_SHORT).show();
					return;
				}
				 
                if(firstName.getText().toString().contains(";") || lastName.getText().toString().contains(";") || emailAddres.getText().toString().contains(";")|| 
                		phoneNumber.getText().toString().contains(";")){
                	Toast.makeText(this, "Please do not add ; in input", Toast.LENGTH_SHORT).show();
                	return;
                }
                	
				
				int result = fileManager.AddUser(new User(firstName.getText().toString().replaceAll("\\s",""),lastName.getText().toString(),phoneNumber.getText().toString(),emailAddres.getText().toString(),0),mainActivityFileDir);
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        	finish();
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
	}
	
}
