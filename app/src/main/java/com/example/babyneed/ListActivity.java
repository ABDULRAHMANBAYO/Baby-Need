package com.example.babyneed;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.babyneed.data.DatabaseHandler;
import com.example.babyneed.model.BabyItem;
import com.example.babyneed.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<BabyItem> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerview);
        fab  = findViewById(R.id.fab);

        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList= new ArrayList<>();

        //Get items from db
        itemList = databaseHandler.getAllBabyItems();
        for(BabyItem item:itemList)
        {
            Log.d("Activity","onCreete:"+item.getItemName());
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }


        });


    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass view so snackbar would be attached to a view
                if (!babyItem.getText().toString().isEmpty()
                        && !itemColor.getText().toString().isEmpty()
                        && !itemQuantity.getText().toString().isEmpty()
                        && !itemSize.getText().toString().isEmpty()
                ) {
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Please make sure all fields are filled correctly", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        babyItem = view.findViewById(R.id.babyItem);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        itemColor = view.findViewById(R.id.itemColor);
        itemSize = view.findViewById(R.id.itemSize);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveItem(View view) {
        //Create baby item object
        BabyItem item = new BabyItem();

        //Get values from EditTextView
        String newBabyItem = babyItem.getText().toString().trim();
        String newItemColor = itemColor.getText().toString().trim();
        int newItemQuantity = Integer.parseInt(itemQuantity.getText().toString().trim());
        int newItemSize = Integer.parseInt(itemSize.getText().toString().trim());
        //Assign values to baby item object
        item.setItemName(newBabyItem);
        item.setItemColor(newItemColor);
        item.setItemQuantity(newItemQuantity);
        item.setItemSize(newItemSize);

        //Save item to database
        databaseHandler.addBabyItem(item);

        //Display snackbar after save
        Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //code to be run
                alertDialog
            .dismiss();
                // Move to detail screen
                startActivity(new Intent(ListActivity.this,ListActivity.class));
                finish();

            }

        },1200);
    }
}
