package com.passgen.knack.PassGen;

import android.content.Context;
import android.widget.Toast;

public class ShowMessage
{
    Context context;

    public ShowMessage(Context c)
    {
        context = c;
    }

    public void ShowToast(String message)
    {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
