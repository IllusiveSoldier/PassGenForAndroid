package com.passgen.knack.PassGen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.EditText;


public class AddToDatabaseForm extends android.support.v4.app.DialogFragment
                               implements DialogInterface.OnClickListener
{
    private View form = null;

    EditText ShowingPasswordText;
    EditText Resource;
    EditText Login;
    EditText Password;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        form = getActivity().getLayoutInflater().inflate(R.layout.save_form, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ShowingPasswordText = (EditText) getActivity().findViewById(R.id.ShowingPasswordText);
        Resource = (EditText) form.findViewById(R.id.Resource);
        Login = (EditText) form.findViewById(R.id.Login);
        Password = (EditText) form.findViewById(R.id.Password);

        Password.setText(ShowingPasswordText.getText().toString());

        return(builder.setTitle("Введите данные:").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        // Если поля для пароля или ресурса пусты - выводим мессэдж о ошибке
        if ((Password.getText().toString().equalsIgnoreCase(""))
                || (Resource.getText().toString().equalsIgnoreCase(""))
                || (Login.getText().toString().equalsIgnoreCase("")))
            new ShowMessage(getContext()).ShowToast("Заполните все поля!");
        // Иначе добавляем запись в БД
        else
        {
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        // Передаём в конструктор строки с ресурса, логина и паса
                        new DB(getContext()).AddValueToDatabase(Resource.getText().toString(),
                                                                Login.getText().toString(),
                                                                Password.getText().toString());
                    }
                    catch (Exception ex)
                    {
                        new ShowMessage(getContext()).ShowToast(ex.getMessage());
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();

            int notificationId = 001;

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getContext())
                            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                            .setContentTitle("PassGen")
                            .setContentText("Пароль добавлен в базу данных");

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getContext());

            notificationManager.notify(notificationId, notificationBuilder.build());
        }
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
