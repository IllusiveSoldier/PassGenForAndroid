package com.passgen.knack.PassGen;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class ChoiceYesNo extends android.support.v4.app.DialogFragment
{
    int ID;

    ChoiceYesNo(int id)
    {
        ID = id;
    }

    String[] values = {"Да",
                       "Нет"};
    String title = "Удалить?";

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setItems(values, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                            case 0:
                                DB db = new DB(getContext());
                                db.DeleteValueInDatabase(ID);
                                new ShowMessage(getContext()).ShowToast("Запись удалена! \n" +
                                                                        "Потяните вниз для обновления!");
                                break;
                            case 1:
                                new ShowMessage(getContext()).ShowToast("Отмена!");
                                break;
                        }
                    }
                });
        return builder.create();
    }
}
