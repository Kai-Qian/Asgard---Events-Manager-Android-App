package com.brynhildr.asgard.userInterface.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.User;
import com.brynhildr.asgard.local.RegisterUserToRemote;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPhoneNumber;
    private EditText mUserName;
    private TextView directLogin;
    private RadioGroup genderGroup;
    private String gender;
    private ImageView mShowPicture;

    private Animation start;
    private Animation middle;
    private Animation last;
    private Drawable mPicture1;
    private Drawable mPicture2;
    private Drawable mPicture3;

    private String registerUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_register);

        mPasswordView = (EditText) findViewById(R.id.password_register);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mUserName = (EditText) findViewById(R.id.username_register);
        mUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register_name || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mPhoneNumber = (EditText) findViewById(R.id.phonenumber_register);
        mPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register_phonenumber || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailRegisterButton = (Button) findViewById(R.id.asgard_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        directLogin = (TextView)findViewById(R.id.directLogin);
        directLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                directLogin.setTextColor(Color.RED);
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        });
        genderGroup = (RadioGroup)this.findViewById(R.id.genderGroup);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                int radioButtonId = arg0.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) RegisterActivity.this.findViewById(radioButtonId);
                gender = rb.getText().toString();
            }
        });
        mShowPicture = (ImageView) findViewById(R.id.guide_picture);
        mPicture1 = getResources().getDrawable(R.drawable.back1);
        mPicture2 = getResources().getDrawable(R.drawable.back2);
        mPicture3 = getResources().getDrawable(R.drawable.back3);
        mShowPicture.setImageDrawable(mPicture1);
        start = AnimationUtils.loadAnimation(this, R.anim.registerpage1);
        middle = AnimationUtils.loadAnimation(this, R.anim.registerpage2);
        last = AnimationUtils.loadAnimation(this, R.anim.registerpage3);
        mShowPicture.startAnimation(start);
        start.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {

                mShowPicture.startAnimation(middle);
            }
        });
        middle.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mShowPicture.startAnimation(last);
            }
        });
        last.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (mShowPicture.getDrawable().equals(mPicture1)) {
                    mShowPicture.setImageDrawable(mPicture2);
                } else if (mShowPicture.getDrawable().equals(mPicture2)) {
                    mShowPicture.setImageDrawable(mPicture3);
                } else if (mShowPicture.getDrawable().equals(mPicture3)) {
                    mShowPicture.setImageDrawable(mPicture1);
                }
                mShowPicture.startAnimation(start);
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            String phoneNum = mPhoneNumber.getText().toString();
            String userName = mUserName.getText().toString();
            User user = new User(userName, email, phoneNum, password, gender);
            try {
                registerUserInfo = new RegisterUserToRemote().execute(user).get();
                System.out.println(registerUserInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (registerUserInfo.equals("OK")) {
                System.out.println("Register successfully.");
                finish();
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            } else {
                System.out.println("Register unsuccessfully.");
            }
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

