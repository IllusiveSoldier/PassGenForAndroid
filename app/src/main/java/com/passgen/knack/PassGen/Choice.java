package com.passgen.knack.PassGen;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class Choice extends android.support.v4.app.DialogFragment
{
    String RESOURCE;
    String PASSWORD;
    int ID;
    String LOGIN;

    Choice(int id, String resource, String login, String password)
    {
        RESOURCE = resource;
        PASSWORD = password;
        ID = id;
        LOGIN = login;
    }

    // Варианты в дилоге
    String[] values = {"Скопировать ЛОГИН в буфер обмена",
                       "Скопировать ПАРОЛЬ в буфер обмена",
                       "Скопировать ЛОГИН в буфер и открыть браузер",
                       "Скопировать ПАРОЛЬ в буфер и открыть браузер",
                       "Удалить запись"};
    // Заголовок
    String title = "Что сделать?";

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setItems(values, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which) {
                            case 0:
                                SaveToClipboard(LOGIN);
                                break;
                            case 1:
                                SaveToClipboard(PASSWORD);
                                break;
                            case 2:
                                SaveToClipboard(LOGIN);
                                try
                                {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + RESOURCE)));
                                }
                                catch (Exception ex)
                                {
                                    new ShowMessage(getContext()).ShowToast("Ошибка перехода по адресу! \n"
                                            + "Ошибка: " + ex.getMessage());
                                }
                                break;
                            case 3:
                                SaveToClipboard(PASSWORD);
                                try
                                {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + RESOURCE)));
                                }
                                catch (Exception ex)
                                {
                                    new ShowMessage(getContext()).ShowToast("Ошибка перехода по адресу! \n"
                                            + "Ошибка: " + ex.getMessage());
                                }
                                break;
                            case 4:
                                new ChoiceYesNo(ID).show(getFragmentManager(), "login");
                                break;
                        }
                    }
                });
        return builder.create();
    }

    // Сохранение текста в буфере обмена
    public void SaveToClipboard(String value)
    {
        ClipboardManager clipboard = (ClipboardManager) getActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", value);
        clipboard.setPrimaryClip(clip);

        new ShowMessage(getContext()).ShowToast("Значение " + value + " было скопировано в буфер обмена.");
    }
}

