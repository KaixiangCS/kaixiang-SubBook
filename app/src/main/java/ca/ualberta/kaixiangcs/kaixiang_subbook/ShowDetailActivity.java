package ca.ualberta.kaixiangcs.kaixiang_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class is mainly for display and edit the information
 * @author Kaixiang Zhang
 * @version 1.0
 * @see MainActivity
 */

public class ShowDetailActivity extends AppCompatActivity {
    private EditText name;
    private EditText date;
    private EditText charge;
    private EditText comment;

    /**
     * first onCreate we gonna get the information by using bundle to compact all the element of the Subscription and decompose one by one then assign them to
     * all 4 blank properly
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_del);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("string1");
        String year = bundle.getString("string2");
        String comment = bundle.getString("string3");
        float float1 = bundle.getFloat("float1");
        TextView textView1 = (TextView) findViewById(R.id.editText6);
        textView1.setText(String.valueOf(name));
        TextView textView2 = (TextView) findViewById(R.id.editText4);
        textView2.setText(String.valueOf(year));
        TextView textView3 = (TextView) findViewById(R.id.editText8);
        textView3.setText(String.valueOf(comment));
        TextView textView4 = (TextView) findViewById(R.id.editText7);
        textView4.setText(String.valueOf(float1));

    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            super.onActivityResult(requestCode, resultCode, data);
            Bundle bundle = data.getExtras();
            String name = bundle.getString("string1");
            String year = bundle.getString("string2");
            String comment = bundle.getString("string3");
            float float1 = bundle.getFloat("float1");
        System.out.println("------------------------------------------------------");
        System.out.println(name);
        System.out.println(year);
        System.out.println(comment);
        System.out.println("------------------------------------------------------");
            TextView textView1 = (TextView) findViewById(R.id.editText6);
            textView1.setText(String.valueOf(name));
            TextView textView2 = (TextView) findViewById(R.id.editText4);
            textView2.setText(String.valueOf(year));
            TextView textView3 = (TextView) findViewById(R.id.editText7);
            textView3.setText(String.valueOf(comment));
            TextView textView4 = (TextView) findViewById(R.id.editText8);
            textView4.setText(String.valueOf(float1));
    }*/

    /**
     * here we wont give a return function since u can just do nothing then press edit
     * or u can just type delete to delete this row
     * or u can implement whatever by urself and click the edit button.
     * @param view
     */
    public void toTheBack(View view) {
        Intent returnIntent = new Intent();
        setResult(ShowDetailActivity.RESULT_OK, returnIntent);
        finish();
    }
    public void toDelete(View view) {
        finish();
    }

    /**
     * here toEdit uses bundle again, but this time we gonna submit bundle all the element of Subscription togather then use proper code
     * to push back to the mainActivity, since we ask for return from the main and we have the code this will be really ez.
     * @param view
     */
    public void toEdit(View view) {
        name = (EditText) findViewById(R.id.editText6);
        date = (EditText) findViewById(R.id.editText4);
        charge = (EditText) findViewById(R.id.editText7);
        comment = (EditText) findViewById(R.id.editText8);
        String name1 = name.getText().toString();
        String date1 = date.getText().toString();
        String charge1 = charge.getText().toString();
        float charge2 = Float.parseFloat(charge1);
        String comment1 = comment.getText().toString();
        if (name1.length()>0 && name1.length()<20 && date1.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")&& charge2!=0){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("string1" , name1);
                    bundle.putString("string2" , date1);
                    bundle.putString("string3" , comment1);
                    bundle.putFloat("float1" , charge2);
                    intent.putExtras(bundle);
                    setResult(ShowDetailActivity.RESULT_OK,intent);
                    finish();}
    }
}
