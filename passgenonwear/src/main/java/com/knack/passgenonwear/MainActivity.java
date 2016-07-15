package com.knack.passgenonwear;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    Button button;
    EditText editText;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_activity_main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        button.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                v.vibrate(50);
                SaveToClipboard(editText.getText().toString());
                return false;
            }
        });
    }

    public void onClick(View view) {
        editText.setText(new GeneratePass().Generate());
    }

    // Сохранение текста в буфере обмена
    public void SaveToClipboard(String value) {
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", value);
        clipboard.setPrimaryClip(clip);

        new ShowMessage(getApplicationContext()).ShowToast("Значение " + value + "было скопировано в буфер обмена.");
    }
}
