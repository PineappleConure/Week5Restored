package algonquin.cst2335.wang0467;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity is the primary activity for this application.
 * It provides a user interface for password input and validation.
 * Through this interface, users can enter a password, which will then be
 * checked against certain complexity requirements. Feedback is provided
 * to the user based on the validity of the entered password.
 *
 * @author Linna Wang - 040755479
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This TextView displays feedback messages to the user about the password's complexity.
     */
    private TextView tv = null;

    /**
     * This TextView displays feedback messages to the user about the password's complexity.
     */
    private EditText et = null;

    /**
     * This Button triggers the password complexity check when clicked.
     */
    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.password_EditText);
        btn = findViewById(R.id.login_btn);

        btn.setOnClickListener( clk -> {
            String password = et.getText().toString();
            if (checkPasswordComplexity(password)) {
                tv.setText("Your password meets the requirements");
            } else {
                tv.setText("You shall not pass!");
            };
        });
    }

    /**
     * This function is to check the password complexity.
     *
     * @param pw The String object that we are checking
     * @return Returns true if the password meets the complexity requirements, else false
     */
    boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);

            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(this, "Your password does not have an upper case letter", Toast.LENGTH_LONG).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "Your password does not have a lower case letter", Toast.LENGTH_LONG).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "Your password does not have a number", Toast.LENGTH_LONG).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Your password does not have a special symbol", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * This function checks if the given character is a special character.
     *
     * @param c The character to check
     * @return Returns true if the character is a special character, else false
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}
