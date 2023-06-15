package algonquin.cst2335.zhou0223;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import algonquin.cst2335.zhou0223.databinding.ActivityMainBinding;


/**
 * The main activity java class of the activity_main.xml
 * @author Xuemin
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
//    private MainViewModel model;

    /**
     * A function to check the password contains at least an uppercase, a lowercase,
     * a number and a special character.
     * @param pw The String object that we are checking
     * @return  Return true if the password is complex enough
     */
    private boolean checkPasswordComplexity(String pw){
        if(!pw.matches(".*[A-Z].*")){
            Toast.makeText(this, "Missing an UpperCase letter", Toast.LENGTH_SHORT);
            return false;
        }
        else if(!pw.matches(".*[a-z].*")){
            Toast.makeText(this, "Missing a LowerCase letter", Toast.LENGTH_SHORT);
            return false;
        }
        else if(!pw.matches(".*\\d.*")){
            Toast.makeText(this, "Missing a Number", Toast.LENGTH_SHORT);
            return false;
        }
        else if(!pw.matches(".*[#$%^&*!@?].*")){
            Toast.makeText(this, "Missing a Special Character (#$%^&*!@?)", Toast.LENGTH_SHORT);
            return false;
        }
        else {
            return true;
        }
    }

    //boolean isSpecialCharacter(char c){
       // switch (c)
        //{
           // case '#':
           // case'?':
           // case'*':
              //  return true;
          //  default:
               // return false;
      //  }
   // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view binding
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        /** This holds the text at the centre of the screen*/
        TextView tv = variableBinding.textView;
        /** This hold the input of the password from the user */
        EditText et = variableBinding.theEditText;
        /** This holds the login in button*/
        Button btn = variableBinding.button;

        //click button to show text from edittext to text view
        btn.setOnClickListener(click -> {
            String password = et.getText().toString();
            if(checkPasswordComplexity(password)){
                tv.setText("Your password is complex enough");
            }
            else{
                tv.setText("You shall not pass!");
            }
        });
    }
}

