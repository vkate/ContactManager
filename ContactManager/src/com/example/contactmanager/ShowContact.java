package com.example.contactmanager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactmanager.DeleteConfirmation.DeleteDialogListener;


/**
 * @author Vamsi Katepalli NetId: Vxk142730
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * start date: 10/27/2014
 * 
 * This method is used to show contact and user can edit/delete from here.
 */

public class ShowContact extends Activity implements DeleteDialogListener{

	TextView firstName;
	TextView lastName;
	TextView phoneNumber;
	TextView emailAddres;
	int saveMode;
	String mainActivityFileDir;
	User currentUser;
	public final static String MAIN_CONTEXT = "com.example.myfirstapp.MAIN_CONTEXT";
	public final static String FIRST_NAME = "com.example.myfirstapp.FIRST_NAME";
	public final static String POSITION = "com.example.myfirstapp.POSITION";
	
	
	public String getMainActivityFileDir() {
		return mainActivityFileDir;
	}

	public void setMainActivityFileDir(String mainActivityFileDir) {
		this.mainActivityFileDir = mainActivityFileDir;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Oncreate override.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_contact);
		Intent intent = getIntent();
	    String firstNameText = intent.getStringExtra(MainActivity.FIRST_NAME);
	    mainActivityFileDir = intent.getStringExtra(MainActivity.MAIN_CONTEXT);
	    String position = intent.getStringExtra(MainActivity.POSITION);
	    
	    saveMode=0;
	    firstName = ((TextView) findViewById(R.id.firstNameShow));
	    lastName = (TextView) findViewById(R.id.lastNameShow);
	    phoneNumber = (TextView) findViewById(R.id.phoneNumberShow);
	    emailAddres = (TextView) findViewById(R.id.emailAddressShow);
	    if(firstNameText!=null){
	    	saveMode=1;
	    	currentUser = getUser(firstNameText,position);
			firstName.setText(currentUser.getFirstName());
			lastName.setText(currentUser.getLastName());
			phoneNumber.setText(currentUser.getPhoneNumber().toString());
			emailAddres.setText(currentUser.getEmailAddress());
	    }
	}

	/**
	 * @param firstNameText
	 * @return User
	 * get user object
	 */
	private User getUser(String firstNameText,String position) {
		FileManager fileManager = new FileManager();
		return fileManager.getRecord(mainActivityFileDir, new User(firstNameText),position);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_contact, menu);
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
		}else if(id == R.id.edit_contact){
        	Intent intent = new Intent(this, ModifyContact.class);
        	intent.putExtra(MAIN_CONTEXT, ShowContact.this.mainActivityFileDir);
        	intent.putExtra(FIRST_NAME, firstName.getText().toString()); 
        	intent.putExtra(POSITION, getIntent().getStringExtra(MainActivity.POSITION)); 
        	startActivity(intent);
        }else if(id == R.id.delete_contact){
        	deleteContact();
        }
		return super.onOptionsItemSelected(item);
	}
	
	//saveMode
	/**
	 * save user
	 */
	public void saveContact(View view){
		try{
			FileManager fileManager = new FileManager();
			if(saveMode==0){
				if(firstName.getText().toString()==null || firstName.getText().toString().equals("")){
					Toast.makeText(this, "Please enter first name of the contact", Toast.LENGTH_SHORT).show();
					return;
				}
				 
                if(firstName.getText().toString().contains(";") || lastName.getText().toString().contains(";") || emailAddres.getText().toString().contains(";")|| 
                		phoneNumber.getText().toString().contains(";")){
                	Toast.makeText(this, "Please do not add ; in input", Toast.LENGTH_SHORT).show();
                	return;
                }
                	
				
				fileManager.AddUser(new User(firstName.getText().toString().trim(),lastName.getText().toString(),phoneNumber.getText().toString(),emailAddres.getText().toString(),0),mainActivityFileDir);
				}
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        	finish();
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
	}
	/**
	 * delete contact
	 */
	public void deleteContact(){
		try{
			DeleteConfirmation confirmation = new DeleteConfirmation();
			FragmentManager fm = getFragmentManager();
			confirmation.show(fm, "Delete_confirm");
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	  /* (non-Javadoc)
	 * @see com.example.contactmanager.DeleteConfirmation.DeleteDialogListener#onFinishEditDialog(java.lang.String)
	 * call back function
	 */
	@Override
	    public void onFinishEditDialog(String inputText) {
		  Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
	    }
	
}
