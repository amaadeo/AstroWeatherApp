package com.example.astroweather.settings;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.astro.R;
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
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        cityEditText = findViewById(R.id.cityEditText);
        addImage = findViewById(R.id.addImage);
        listView = findViewById(R.id.cityListView);
        cityList = new ArrayList<>();

        databaseHelper = new DatabaseHelper(this);


        displayCityList();

    }

    private void displayCityList() {
        data = databaseHelper.getListContents();
        cityList.clear();
        while (data.moveToNext()) {
            cityList.add(data.getString(1));
        }
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_row, cityList);
        listView.setAdapter(arrayAdapter);
    }

    public void clickAddButton(View view) {
        if (cityEditText.length() != 0) {
            addData(cityEditText.getText().toString());
            cityEditText.setText("");

        } else {
            Toast.makeText(SelectCityActivity.this, "City name cannot be empty!", LENGTH_SHORT).show();
        }

        displayCityList();
    }

    private void addData(String record) {
        boolean result = databaseHelper.insertData(record);

        if (result) {
            Toast.makeText(SelectCityActivity.this, "Successfull!", LENGTH_SHORT).show();
        } else {
            Toast.makeText(SelectCityActivity.this, "Something gone wrong!", LENGTH_SHORT).show();
        }
    }
}
