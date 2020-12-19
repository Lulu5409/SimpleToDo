package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> giftitems;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=findViewById(R.id.btnAdd);
        etItem=findViewById(R.id.etItem);
        rvItems=findViewById(R.id.rvItems);

          loadItems();
//        giftitems.add("Socks");
//        giftitems.add("Lipsticks");
//        giftitems.add("Pants");

        ItemsAdapter.OnLongClickListener onLongClickListener=new ItemsAdapter.OnLongClickListener(){
            @Override
            public void OnItemLongClicked(int position) {
                //delete the item from the model
                giftitems.remove(position);
                // notify the Adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Gift item was removed",Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };


        itemsAdapter=new ItemsAdapter(giftitems,onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem=etItem.getText().toString();
                // Add item to the model
                giftitems.add(todoItem);
                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(giftitems.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Gift item was added",Toast.LENGTH_SHORT).show();
                saveItems();

            }
        });

    }
    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");

    }
    // this function will load items by reading memory line of the data file
    private void loadItems(){
        try {
            giftitems = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }catch (IOException e){
            Log.e("MainActivity","Error reading items",e);
            giftitems=new ArrayList<>();
        }
    }
    // This function saves items by writing them into data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),giftitems);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items",e);
        }
    }
}