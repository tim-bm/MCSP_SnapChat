package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.WindowManager;

public class AddUserFromContacts extends Activity {

    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
     };
    /** show user name **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /** show phone **/
    private static final int PHONES_NUMBER_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_from_contacts);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }
}
