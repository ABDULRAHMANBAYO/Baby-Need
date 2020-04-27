package com.example.babyneed;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.babyneed.data.DatabaseHandler;
import com.example.babyneed.model.BabyItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;
    private DatabaseHandler databaseHandler;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHandler = new DatabaseHandler(this);
        byPassMainActivity();


        //Check if item was saved
        List<BabyItem> items = databaseHandler.getAllBabyItems();
        for (BabyItem item : items) {
            Log.d("SavedItem", "Getall" + item.getDateItemAdded());
        }
       


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void byPassMainActivity() {
        if(databaseHandler.numberOfBabyItem()>0)
        {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

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
        dialog = builder.create();//Create dialog object
        dialog.show();
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
                dialog.dismiss();
                // Move to detail screen
                startActivity(new Intent(MainActivity.this,ListActivity.class));

            }

        },1200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
