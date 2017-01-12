package ke.co.buildbrand.samawati;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

public class LoginActivity extends AppCompatActivity {

    private DigitsAuthButton phoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {

            @Override
            public void success(DigitsSession session, String phoneNumber) {
                SessionRecorder.recordSessionActive("Login: digits account active", session);
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                Answers.getInstance().logLogin(new LoginEvent().putMethod("Digits").putSuccess(true));
               startMainactivity();

            }


            @Override
            public void failure(DigitsException e) {
                Answers.getInstance().logLogin(new LoginEvent().putMethod("Digits").putSuccess(false));
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_twitter_digits_fail),
                        Toast.LENGTH_SHORT).show();
                Crashlytics.logException(e);

            }
        });


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        twitterButton.onActivityResult(requestCode, resultCode, data);
//    }

    private void startMainactivity() {
        final Intent themeChooserIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(themeChooserIntent);
    }

}


