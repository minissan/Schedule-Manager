package com.example.nissan.schedulemanager.expert;
/**
 * Created by nissan on 12/26/2017.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nissan.schedulemanager.admin.AdminDashBoard;
import com.example.nissan.schedulemanager.R;
import com.example.nissan.schedulemanager.guest.GuestDashBoard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExpertLoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private TextView mAdmin;
    private TextView mGuest;
    //private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expert_activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        mSignInButton = findViewById(R.id.button_sign_in);
        //mSignUpButton = findViewById(R.id.button_sign_up);

        // Click listeners
        mSignInButton.setOnClickListener(this);
        //mSignUpButton.setOnClickListener(this);


    }

  /*  @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    } */

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            AlertBox();
                             /* Toast.makeText(ExpertLoginActivity.this, "Sign In Failed, Plese check net",
                                    Toast.LENGTH_SHORT).show(); */
                        }
                    }
                });
    }

   /* private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    } */

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        if (username.contains("admin"))
        {
            startActivity(new Intent(ExpertLoginActivity.this, AdminDashBoard.class));
        }
        else if (username.contains("guest"))
        {
            startActivity(new Intent(ExpertLoginActivity.this, GuestDashBoard.class));
        }
        // Write new user
       // writeNewUser(user.getUid(), username, user.getEmail());
        else {
            // Go to MainActivity
            startActivity(new Intent(ExpertLoginActivity.this, ExpertDashBoard.class));
        }
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
       /* if (mEmailField.getText().toString().contains("admin@sm.com") || mEmailField.getText().toString().contains("guest@sm.com")){
            //Toast.makeText(ExpertLoginActivity.this,"Access Denied",Toast.LENGTH_LONG).show();
            AlertBox();
            mEmailField.setError("Access Denied!");
            result = false;
        }
        else {
            mEmailField.setError(null);
        } */
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    /*
    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]
    */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_sign_in) {
            signIn();
        }/* else if (i == R.id.button_sign_up) {
            signUp();
        } */
    }
    // Alert Box
    public void AlertBox(){
        //Alert Box
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpertLoginActivity.this, R.style.AlertDialogStyle);
        builder.setTitle("Sign In Failed!");
        builder.setMessage("Please check Email/Password or Internet connection.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}