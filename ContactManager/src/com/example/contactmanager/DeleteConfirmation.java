package com.example.contactmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * @author Vamsi Katepalli Vxk142730
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * start date: 10/27/2014
 * 
 * DeleteConfirmation class is used to show the confirmation dialog.
 * Delete operations are included in this file.
 */

public class DeleteConfirmation extends DialogFragment implements OnEditorActionListener{
	
	/**
	 * This interface is implemented by activity for callback function. 
	 *
	 */
	public interface DeleteDialogListener {
        void onFinishEditDialog(String inputText);
    }
	
    /* (non-Javadoc)
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     * onCreateDialog contains Delete activities and confirmation message.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.check_delete)
               .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
	                	FileManager fileManager = new FileManager();
	                	ShowContact activity = (ShowContact) getActivity();
	           			fileManager.DeleteUser(activity.getCurrentUser(),activity.getMainActivityFileDir(),activity.getIntent().getStringExtra(MainActivity.POSITION));
	           			Intent intent = new Intent(activity, MainActivity.class);
	           			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	           			DeleteConfirmation.this.dismiss();
	           			startActivity(intent);
	           			
	           			activity.finish();
                   }
               })
               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   DeleteConfirmation.this.dismiss();
                   }
               });
        
        return builder.create();
    }
    
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
        	DeleteDialogListener activity = (DeleteDialogListener) getActivity();
            activity.onFinishEditDialog("true");
            this.dismiss();
            return true;
        }
        return false;
    }
}
