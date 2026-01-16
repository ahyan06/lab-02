package com.example.listcity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addBtn;
    Button deleteBtn;
    int selectedPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        addBtn = findViewById(R.id.btn_add_city);
        deleteBtn = findViewById(R.id.btn_delete_city);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow", "Sydney",
                "Berlin", "Vienna", "Tokyo", "Beijing",
                "Osaka", "New Dheli"
        };

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Selectnig a city
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
            }
        });

        // add city
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = new EditText(MainActivity.this);
                input.setHint("City name");

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add City")
                        .setView(input)
                        .setPositiveButton("CONFIRM", (dialog, which) -> {
                            String city = input.getText().toString();
                            dataList.add(city);
                            cityAdapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
        });

        // delete city that was selected
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos != -1) {
                    dataList.remove(selectedPos);
                    selectedPos = -1;
                    cityAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
