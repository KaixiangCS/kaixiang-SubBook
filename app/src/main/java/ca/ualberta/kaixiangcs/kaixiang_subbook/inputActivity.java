package ca.ualberta.kaixiangcs.kaixiang_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Kaixiang Zhang
 * @version 1.0
 * @see MainActivity
 * This time this function is kind like onEdit, since the first time if we call showDetail, the main will send a null bundle to the showDeetail and will return
 * error, so we initialize a new function to do the Add.
 */

public class inputActivity extends AppCompatActivity {
    private EditText name;
    private EditText date;
    private EditText charge;
    private EditText comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Button submitButton = (Button) findViewById(R.id.button2);

        name = (EditText) findViewById(R.id.editText);
        date = (EditText) findViewById(R.id.editText2);
        charge = (EditText) findViewById(R.id.editText3);
        comment = (EditText) findViewById(R.id.editText5);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String date1 = date.getText().toString();
                String charge1 = charge.getText().toString();
                float charge2 = Float.parseFloat(charge1);
                String comment1 = comment.getText().toString();
                if (name1.length()>0 && name1.length()<20 && date1.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")&& charge2!=0 &&comment1.length()>0 && comment1.length()<30){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("string1" , name1);
                    bundle.putString("string2" , date1);
                    bundle.putString("string3" , comment1);
                    bundle.putFloat("float1" , charge2);
                    intent.putExtras(bundle);
                    setResult(ShowDetailActivity.RESULT_OK,intent);
                    finish();}
                //adapter.notifyDataSetChanged();
                //saveInFile();
            }
        });
    }
}
