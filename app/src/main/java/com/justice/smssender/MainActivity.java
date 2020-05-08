package com.justice.smssender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout contactsInput;
    private TextInputLayout messageInput;
    private Button sendBtn;
    private final int SMS_REQUEST=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendBtnTapped();

            }
        });
    }

    private void sendBtnTapped() {
        String contacts = contactsInput.getEditText().getText().toString().trim();
        String message = messageInput.getEditText().getText().toString().trim();

        if (contacts.isEmpty()) {
            contactsInput.setError("Field Cannot Be Empty");
            return;
        }
        if (message.isEmpty()) {
            messageInput.setError("Field Cannot Be Empty");
            return;
        }

        sendMessage(contacts, message);


    }

    private void sendMessage(String fullContacts, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},SMS_REQUEST);
        }

        SmsManager smsManager=SmsManager.getDefault();

        String[] contactsArray=fullContacts.split(",");

        for (String contact:contactsArray){

            smsManager.sendTextMessage(contact.trim(),null,message,null,null);

        }



    }

    private void initWidgets() {
        contactsInput = findViewById(R.id.contactInput);
        messageInput = findViewById(R.id.messageInput);
        sendBtn = findViewById(R.id.sendBtn);

    }
}
