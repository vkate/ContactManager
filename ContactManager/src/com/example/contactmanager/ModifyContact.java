package com.example.contactmanager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactmanager.DeleteConfirmation.DeleteDialogListener;



/**
 * @author Vamsi Katepalli NetID: Vxk142730
 * This class is written as part of User Interface Assignment taught  by 
 * Dr. John Cole.
 * start date: 10/27/2014
 * 
 * ModifyContact is the activity to modify the user comtact.
 * All the fields used in add form can be edited. 
 */
public class ModifyContact extends Activity  implements DeleteDialogListener{

	EditText firstName;
	EditText lastName;
	EditText phoneNumber;
	EditText emailAddres;
	String mainActivityFileDir;
	User currentUser;
	
	public EditText getFirstName() {
		return firstName;
	}

	public void setFirstName(EditText firstName) {
		this.firstName = firstName;
	}

	public EditText getLastName() {
		return lastName;
	}

	public void setLastName(EditText lastName) {
		this.lastName = lastName;
	}

	public EditText getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(EditText phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EditText getEmailAddres() {
		return emailAddres;
	}

	public void setEmailAddres(EditText emailAddres) {
		this.emailAddres = emailAddres;
	}

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
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_contact);
		Intent intent = getIntent();
	    String firstNameText = intent.getStringExtra(MainActivity.FIRST_NAME);
	    //String lastNameText = intent.getStringExtra(MainActivity.LAST_NAME);
	    firstName = ((EditText) findViewById(R.id.firstName));
	    lastName = (EditText) findViewById(R.id.lastname);
	    phoneNumber = (EditText) findViewById(R.id.phoneNumber);
	    emailAddres = (EditText) findViewById(R.id.emailAddress);
	    mainActivityFileDir = intent.getStringExtra(MainActivity.MAIN_CONTEXT);
	    String position = intent.getStringExtra(MainActivity.POSITION);
	    if(firstNameText!=null){
	    	currentUser = getUser(firstNameText,position);
			firstName.setText(currentUser.getFirstName());
			lastName.setText(currentUser.getLastName());
			phoneNumber.setText(currentUser.getPhoneNumber().toString());
			emailAddres.setText(currentUser.getEmailAddress());
	    }
	    firstName.requestFocus();
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
		getMenuInflater().inflate(R.menu.add_contact, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Save the contact after modifying the details.
	 */
	public void saveContact(View view){
		try{
			if(firstName.getText().toString()==null || firstName.getText().toString().equals("")){
				Toast.makeText(this, "Please enter first name of the contact", Toast.LENGTH_SHORT).show();
				return;
			}
			 
            if(firstName.getText().toString().contains(";") || lastName.getText().toString().contains(";") || emailAddres.getText().toString().contains(";")|| 
            		phoneNumber.getText().toString().contains(";")){
            	Toast.makeText(this, "Please do not add ; in input", Toast.LENGTH_SHORT).show();
            	return;
            }
			FileManager fileManager = new FileManager();
			String position = getIntent().getStringExtra(MainActivity.POSITION);
			int result = fileManager.ModifyUser(new User(firstName.getText().toString().replaceAll("\\s",""),lastName.getText().toString(),phoneNumber.getText().toString(),emailAddres.getText().toString(),0),currentUser,mainActivityFileDir,position);
			
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	startActivity(intent);
        	Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
			finish();
        	
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	/**
	 * Delete Contact but show dialog.
	 */
	public void deleteContact(View view){
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
	 * Call back to show message
	 */
	@Override
	    public void onFinishEditDialog(String inputText) {
		  Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
	    }
	
}
