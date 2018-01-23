package edu.northwestern.socialmedia.hestondissertationtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        }
        catch (Exception e) {
            // WOW I AM A GOOD DEVELOPER
        }
    }
}
