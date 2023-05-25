package com.english.quizza;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends Fragment {
    // Declare variables for UI elements and Firebase reference
    private EditText userNameEditText, userEmailEditText, userMsgEditText;
    private Button sendBtn;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        // Initialize UI elements
        userNameEditText = view.findViewById(R.id.user_name);
        userEmailEditText = view.findViewById(R.id.user_email);
        userMsgEditText = view.findViewById(R.id.user_msg);
        sendBtn = view.findViewById(R.id.send_btn);

        // Initialize Firebase reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("feedback");

        // Set OnClickListener for Send button
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String userName = userNameEditText.getText().toString();
                String userEmail = userEmailEditText.getText().toString();
                String userMsg = userMsgEditText.getText().toString();

                // Validate user input
                if (TextUtils.isEmpty(userName)) {
                    userNameEditText.setError("Please enter your name");
                    userNameEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(userEmail)) {
                    userEmailEditText.setError("Please enter your email");
                    userEmailEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(userMsg)) {
                    userMsgEditText.setError("Please enter your message");
                    userMsgEditText.requestFocus();
                    return;
                }

                // Create a new Feedback object
                Feedback feedback = new Feedback(userName, userEmail, userMsg);

                // Save the Feedback object to Firebase database
                mDatabase.push().setValue(feedback);

                // Clear the input fields
                userNameEditText.setText("");
                userEmailEditText.setText("");
                userMsgEditText.setText("");

                // Show a success message to the user
                Toast.makeText(getContext(), "Feedback sent successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}