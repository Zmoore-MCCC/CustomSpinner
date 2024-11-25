package com.example.customspinner;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    TextView foodSel;
    ArrayList<String> foods;

    //all variables below are used for the dialog (popup)
    //they are found in the dialog so can only be accessed
    //once the dialog is create
    Dialog popup;
    EditText popup_search;
    ListView popup_listView;
    ArrayAdapter<String> popup_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        foodSel = findViewById(R.id.tv_selection);

        foods = new ArrayList<>();

        //add foods to the arraylist
        foods.add("Apple");
        foods.add("Artichoke");
        foods.add("Anchovies");
        foods.add("Almonds");
        foods.add("Asparagus");
        foods.add("Basil");
        foods.add("Bacon");
        foods.add("Blueberries");
        foods.add("Banana");
        foods.add("Bell Pepper");
        foods.add("Beet");


        clickListenerTextView();

    }

    private void clickListenerTextView()
    {
        //when the user clicks on the textview we want to show them the new window (popup)
        //this will show the options that we want them to select from
        //we will put all of this information into a dialog
        foodSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //initialize dialog
                popup = new Dialog(MainActivity.this);

                //set up the custom dialog
                //we will use the spinner_data that we created
                //remember that contained an edittext and listview
                //we will use the edittext to receive input from the user
                //we will use the listview to display all of the different types of food
                popup.setContentView(R.layout.spinner_data);

                //set the size of the popup
                popup.getWindow().setLayout(650,800);

                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                popup.show();

                //initialize the gui elements
                //we have to do this here because they are part of the dialog
                //not part of main
                //due to this we have to find the view in the popup
                popup_search = popup.findViewById(R.id.et_search);
                popup_listView = popup.findViewById(R.id.lv_foods);

                //create an adapter for the listview
                //we do not need a custom adapter because we are only displaying text
                //we will give the adapter the array called foods
                popup_adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, foods);

                //set adapter
                popup_listView.setAdapter(popup_adapter);

                //used to listed to the user as they key in information and
                //change the results that shown in the listview
                popupEditTextChangeListener();

                //determine which item was selected from our listview
                popupClickListener();
            }
        });
    }

    private void popupEditTextChangeListener()
    {
        //as the user is typing into our edittext we will actively change
        //what values are shown in our list based off what the user has entered
        popup_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //change what is shown in our listview
                //we will filter the adapter (adapter is using the food array)
                //to only show foods that contain the CharSequence charSequence
                popup_adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void popupClickListener()
    {
        popup_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //when item is selected from the listview in the dialog
                //set selected item on mainactivity
                foodSel.setText(popup_adapter.getItem(i));;

                //remove the popup
                popup.dismiss();
            }
        });
    }
}