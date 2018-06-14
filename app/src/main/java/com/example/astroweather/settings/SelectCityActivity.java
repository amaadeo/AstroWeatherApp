package com.example.astroweather.settings;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.astro.R;
import com.example.astroweather.MainActivity;
import com.example.astroweather.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class SelectCityActivity extends AppCompatActivity {

    private EditText cityEditText;
    private ImageView addImage;
    private DatabaseHelper databaseHelper;
    private Cursor data;
    private List<String> cityList;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private PopupMenu popupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        initElements();
        displayCityList();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = listView.getItemAtPosition(position).toString();
                Intent myIntent = new Intent(SelectCityActivity.this, MainActivity.class);
                myIntent.putExtra("cityName", cityName); //Optional parameters
                startActivity(myIntent);
            }
        });

    }

    private void initElements() {
        cityEditText = findViewById(R.id.cityEditText);
        addImage = findViewById(R.id.addImage);
        listView = findViewById(R.id.cityListView);
        cityList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        if (view.getId() == R.id.cityListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;
            menu.setHeaderTitle(cityList.get(info.position));
            String[] menuItem = getResources().getStringArray(R.array.menu);
            menu.add(Menu.NONE, 0, 0, menuItem[0]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String listItemName = cityList.get(info.position);
        if (databaseHelper.deleteData(listItemName)) {
            displayCityList();
        }
        return true;
    }

    private void displayCityList() {
        data = databaseHelper.getListContents();
        cityList.clear();
        while (data.moveToNext()) {
            cityList.add(data.getString(1));
        }
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_row, cityList);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);
    }

    public void clickAddButton(View view) {
        String cityName = cityEditText.getText().toString();
        if (cityEditText.length() != 0) {
            if (!cityList.contains(cityName)) {
                addData(cityName);
                cityEditText.setText("");
                displayCityList();
            } else {
                Toast.makeText(SelectCityActivity.this, "This city already exists!", LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SelectCityActivity.this, "City name cannot be empty!", LENGTH_SHORT).show();
        }
    }

    public void addData(String record) {
        boolean result = databaseHelper.insertData(record);

        if (result) {
            Toast.makeText(SelectCityActivity.this, "Successfull!", LENGTH_SHORT).show();
        } else {
            Toast.makeText(SelectCityActivity.this, "Something gone wrong!", LENGTH_SHORT).show();
        }
    }
}
