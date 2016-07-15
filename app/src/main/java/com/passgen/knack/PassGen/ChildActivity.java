package com.passgen.knack.PassGen;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    // Ссылки на view
    public ListView listView;
    public DB mDbAdapter;
    public Cursor mCursor;
    public SimpleCursorAdapter mCursorAd;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        listView = (ListView) findViewById(R.id.listView);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,
                                           View view,
                                           int position,
                                           long id)
            {
                try
                {
                    v.vibrate(30);
                    TextView itemPassword = (TextView) view.findViewById(R.id.itemPassword);
                    TextView itemResource = (TextView) view.findViewById(R.id.itemResource);
                    TextView itemID = (TextView) view.findViewById(R.id.itemID);

                    new Choice(itemResource.getText().toString(),
                               itemPassword.getText().toString(),
                               Integer.parseInt(itemID.getText()
                                       .toString()))
                                       .show(getSupportFragmentManager(), "login");
                }
                catch (Exception ex)
                {
                    new ShowMessage(getApplicationContext()).ShowToast(ex.getMessage());
                }
                return false;
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        GetValueFromDatabase();
    }

    @Override
    public void onRefresh()
    {
        try
        {
            // начинаем показывать прогресс
            mSwipeRefreshLayout.setRefreshing(true);
            GetValueFromDatabase();
            mSwipeRefreshLayout.setRefreshing(false);
            new ShowMessage(getApplicationContext()).ShowToast("Данные обновлены");
        }
        catch (Exception ex)
        {
            new ShowMessage(getApplicationContext()).ShowToast("Ошибка обновления данных! \n " +
                                                               "Ошибка: " + ex.getMessage());
        }
    }

    // Возвращаем все строки из БД
    public void GetValueFromDatabase()
    {
        try
        {
            mDbAdapter = new DB(this);
            mCursor = mDbAdapter.getAllItems();

            String[] from = new String[] { DB.FeedEntry._ID,
                                           DB.FeedEntry.COLUMN_NAME_RESOURCE,
                                           DB.FeedEntry.COLUMN_NAME_PASSWORD};
            int[] to = new int[] { R.id.itemID, R.id.itemResource, R.id.itemPassword};

            mCursorAd = new SimpleCursorAdapter(this, R.layout.item, mCursor, from, to, 0);
            listView.setAdapter(mCursorAd);

            mDbAdapter.Close();
        }
        catch (Exception ex)
        {
            new ShowMessage(getApplication()).ShowToast(ex.getMessage());
        }
    }
}