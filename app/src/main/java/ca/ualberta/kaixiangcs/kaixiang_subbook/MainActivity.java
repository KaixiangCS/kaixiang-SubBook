package ca.ualberta.kaixiangcs.kaixiang_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @Author Kaixiang Zhang
 * @version 1.0
 * @see inputActivity
 * @see ca.ualberta.kaixiangcs.kaixiang_subbook.ShowDetailActivity
 * This fuction is mainly implemented by lonelyTwitter from Lab and build ur first object from https://developer.android.com/training/basics/firstapp/starting-activity.html
 */

/**
 * this part is based on lonelyTwitter to initialize
 * 1.the file name which use to save and load the data by gson
 * 2.the ListView which used to print all the sub in the interface
 * 3.arraylist and arraylist.adapter,which the first one is use to store to sub-object and the sec is used for set the element of the arraylist to listView.
 */
public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "subscription.sav";
    private ListView oldList;
    private ArrayList<Subscription> subscriptionList;
    private ArrayAdapter<Subscription> adapter;
    private float theSum = 0 ;
    private Subscription sub1;
    private String name;
    private String date;
    private float charge;
    private String comment;



    //private ArrayList<Subscription> subscriptionList = new ArrayList<Subscription>();

    /**
     * this part is again based on lonelyTwitter to initialize the oldList(ListView) and the counter(theSum)which used to keep tracking about the sum of the monthly charge
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oldList = (ListView) findViewById(R.id.oldlist);

        TextView textView = (TextView) findViewById(R.id.sum);
        textView.setText(String.valueOf(theSum));
        /**
         * this part is for edit or delete the existing sub by clicking the items on the screem.
         */
        oldList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // your arrtlist ot arry to remove item

                Subscription sub1 = subscriptionList.get(position);
                subscriptionList.remove(position);
                adapter.notifyDataSetChanged();
                saveInFile();
                theSum = 0;
                for(int i = 0 ; i < subscriptionList.size(); i++){
                    theSum = theSum + subscriptionList.get(i).getCharge();
                    TextView textView = (TextView) findViewById(R.id.sum);
                    textView.setText(String.valueOf(theSum));}
                name = sub1.getName();
                date = sub1.getDate();
                charge = sub1.getCharge();
                comment = sub1.getComment();
                Intent intent = new Intent(getApplicationContext(), ShowDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("string1", name);
                bundle.putString("string2", date);
                bundle.putString("string3", comment);
                bundle.putFloat("float1", charge);
                intent.putExtras(bundle);


                /*System.out.println("------------------------------------------------------");
                System.out.println(name);
                System.out.println(date);
                System.out.println(bundle);
                System.out.println("------------------------------------------------------");*/

                startActivityForResult(intent, 0);
                //System.out.println("------------------------------------------------------");
                //System.out.println(id);
                //System.out.println("------------------------------------------------------");

        }
        });

    }

    /**
     * get userinput by click the add button!
     * and u can clear all the sub by click clearAlllll.
     * @param view
     */
    public void toTheNext(View view) {
        Intent intent = new Intent(this, inputActivity.class);
        startActivityForResult(intent, 0);
    }
    public void clearAll(View view) {
        subscriptionList.clear();
        adapter.notifyDataSetChanged();
        saveInFile();
        theSum = 0;
        TextView textView = (TextView) findViewById(R.id.sum);
        textView.setText(String.valueOf(theSum));
    }

    /**
     * this part is for receiving information from other activities, use RESULT_OK(FROM lonelyTwitter to set up the gate)
     * and assign these value properly and combine them into the Sub-obj!
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ShowDetailActivity.RESULT_OK){
            super.onActivityResult(requestCode, resultCode, data);
            Bundle bundle = data.getExtras();
            String name = bundle.getString("string1");
            String year = bundle.getString("string2");
            String comment = bundle.getString("string3");
            float float1 = bundle.getFloat("float1");
            Subscription subscript = new Subscription(name, year, float1, comment);
            //System.out.println("------------------------------------------------------");
            //System.out.println(subscript);
            subscriptionList.add(subscript);
            adapter.notifyDataSetChanged();
            //System.out.println(subscriptionList);
            //System.out.println("------------------------------------------------------");
            saveInFile();
            for(int i = 0 ; i < subscriptionList.size(); i++){
                theSum = theSum + subscriptionList.get(i).getCharge();
                TextView textView = (TextView) findViewById(R.id.sum);
                textView.setText(String.valueOf(theSum));
            }

        }
        else if(resultCode == ShowDetailActivity.RESULT_OK) {
            subscriptionList.add(sub1);
            adapter.notifyDataSetChanged();
            saveInFile();



        }
        else {
            for (int i = 0; i < subscriptionList.size(); i++) {
                theSum = theSum + subscriptionList.get(i).getCharge();
                TextView textView = (TextView) findViewById(R.id.sum);
                textView.setText(String.valueOf(theSum));
            }
        }
    }

    /**
     * the rest functions are all coming from lonelyTwitter which from the lab,
     * onStart is used to active whenever the function is called
     * and the load / save file use the Gson to keep the data wont lose when we closed the app.
     */
    @Override
    protected void onStart() {

        // TODO Auto-generated method stub
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        //System.out.println("------------------------------------------------------");
        //System.out.println("lol");
        //System.out.println("------------------------------------------------------");

        loadFromFile();
        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item,R.id.textView, subscriptionList);
        oldList.setAdapter(adapter);



    }
       private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subscriptionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionList = new ArrayList<Subscription>();
        }

    }

    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subscriptionList, out);
            //System.out.println("------------------------------------------------------");
            //System.out.println("dota");
           // System.out.println("------------------------------------------------------");
            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


}

