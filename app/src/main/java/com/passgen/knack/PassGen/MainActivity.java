package com.passgen.knack.PassGen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    EditText ShowingPasswordText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);

        ShowingPasswordText = (EditText)findViewById(R.id.ShowingPasswordText);
        ShowingPasswordText.setText(new GeneratePass().Generate());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemSettings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            case R.id.itemHelp:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Обработчик нажатия на все кнопки
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ShowMyPasswords:
                /*
                getWindow().setExitTransition(new Explode());
                Intent intent = new Intent(this, ChildActivity.class);
                startActivity(intent,
                        ActivityOptions
                                .makeSceneTransitionAnimation(this).toBundle());*/
                Intent intent = new Intent(this, ChildActivity.class);
                startActivity(intent);
                break;
            case R.id.GeneratePassButton:
                ShowingPasswordText.setText(new GeneratePass().Generate());
                break;
            case R.id.SavePassword:
                new AddToDatabaseForm().show(getSupportFragmentManager(), "login");
                break;
            case R.id.DeleteAllPasswords:
                new DialogDeleteTable().show(getSupportFragmentManager(), "key");
                break;
            default:
                break;
        }
    }
}

