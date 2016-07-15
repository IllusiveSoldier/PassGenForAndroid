package com.passgen.knack.PassGen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class DialogDeleteTable extends android.support.v4.app.DialogFragment
        implements DialogInterface.OnClickListener
{
    View form = null;
    TextView Key;
    EditText CheckKey;
    SQLiteDatabase db;
    Vibrator v;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        form = getActivity().getLayoutInflater().inflate(R.layout.delete_table, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Key = (TextView) form.findViewById(R.id.Key);
        CheckKey = (EditText) form.findViewById(R.id.CheckKey);
        db = new DbHelper(getContext()).getWritableDatabase();

        v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        Key.setText(GenerateKey());

        return(builder.setTitle("").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        try
        {
            if (Key.getText().toString().equalsIgnoreCase(CheckKey.getText().toString()))
            {
                new DB(getContext()).DeleteAndRestoreTable(db);
                new ShowMessage(getContext()).ShowToast("Данные удалены!");
            }
            else
            {
                v.vibrate(300);
                new ShowMessage(getContext()).ShowToast("Несовпадение ключей!");
            }
        }
        catch (Exception ex)
        {
            new ShowMessage(getContext()).ShowToast(ex.getMessage());
        }
    }

    private String GenerateKey()
    {
        String resourceString = "1234567890";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= 4; i++)
        {
            sb.append(resourceString.charAt(rand.nextInt(resourceString.length())));
        }

        return sb.toString();
    }

    @Override
    public void onDismiss(DialogInterface unused)
    {
        super.onDismiss(unused);
    }

    @Override
    public void onCancel(DialogInterface unused)
    {
        super.onCancel(unused);
    }
}
