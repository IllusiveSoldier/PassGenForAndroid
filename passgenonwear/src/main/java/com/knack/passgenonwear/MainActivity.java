package com.knack.passgenonwear;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity
{

    Button button;
    EditText editText;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_activity_main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        button.setOnLongClickListener(new View.OnLongClickListener()
        {

            @Override
            public boolean onLongClick(View view)
            {
                v.vibrate(50);
                SaveToClipboard(editText.getText().toString());

                final String TAG = "SEND_MESSAGE";

                GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
                        {
                            @Override
                            public void onConnected(Bundle connectionHint)
                            {
                                Log.d(TAG, "onConnected: " + connectionHint);
                            }
                            @Override
                            public void onConnectionSuspended(int cause)
                            {
                                Log.d(TAG, "onConnectionSuspended: " + cause);
                            }
                        })
                        .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener()
                        {
                            @Override
                            public void onConnectionFailed(ConnectionResult result)
                            {
                                Log.d(TAG, "onConnectionFailed: " + result);
                            }
                        })
                        // Request access only to the Wearable API
                        .addApi(Wearable.API)
                        .build();
                mGoogleApiClient.connect();
                if (mGoogleApiClient.isConnected())
                {
                    int notificationId = 001;

                    NotificationCompat.Builder notificationBuilder =
                            new NotificationCompat.Builder(MainActivity.this)
                                    .setSmallIcon(R.drawable.open_on_phone)
                                    .setContentTitle("Мяу!")
                                    .setContentText("А кота кто кормить будет?");

                    NotificationManagerCompat notificationManager =
                            NotificationManagerCompat.from(MainActivity.this);

                    notificationManager.notify(notificationId, notificationBuilder.build());
                }

                return false;
            }
        });
    }

    public void onClick(View view)
    {
        editText.setText(new GeneratePass().Generate());
    }

    // Сохранение текста в буфере обмена
    public void SaveToClipboard(String value)
    {
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", value);
        clipboard.setPrimaryClip(clip);

        new ShowMessage(getApplicationContext()).ShowToast("Значение " + value + "было скопировано в буфер обмена.");
    }
}
