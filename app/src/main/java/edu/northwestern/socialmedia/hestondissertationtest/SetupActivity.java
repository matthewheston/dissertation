package edu.northwestern.socialmedia.hestondissertationtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SetupActivity extends AppCompatActivity {
    String FILENAME = "id_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = { Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.INTERNET };


        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        Intent intent = new Intent(this, OutgoingSMSReceiver.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }

        FileInputStream fis;
        String idText = "";
        try {
            fis = openFileInput(FILENAME);
            byte[] input = new byte[fis.available()];
            while (fis.read(input) != -1) {}
            idText += new String(input);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        try {
            EditText editText = findViewById(R.id.idText);
            editText.setText(idText);
            if (!idText.isEmpty()) {
                Button btn = (Button) findViewById(R.id.button2);
                btn.setText("Saved");
                btn.setEnabled(false);
            }
        }
        catch (Exception e) {
            // lol
        }
    }

    public void saveID(View view) {
        EditText idTextView = findViewById(R.id.idText);
        String idText = idTextView.getText().toString();
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(idText.getBytes());
            Button btn = (Button) findViewById(R.id.button2);
            btn.setText("Saved");
            btn.setEnabled(false);
        }
        catch (Exception e) {
            // WOW I AM A GOOD DEVELOPER
        }
    }

    public void gotoMessageList(View view) {
        Intent intent = new Intent(this, MessageListActivity.class);
        startActivity(intent);
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}