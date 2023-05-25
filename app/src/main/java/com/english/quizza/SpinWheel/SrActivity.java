package com.english.quizza.SpinWheel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.english.quizza.BuildConfig;
import com.english.quizza.MainActivity;
import com.english.quizza.R;
import com.english.quizza.User;
import com.english.quizza.databinding.ActivitySrBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SrActivity extends AppCompatActivity {
    ActivitySrBinding binding;
    Button exit, share;
    TextView earnedcoins, luckyscore;
     User user;
    private char[] Cash;
    int NO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int Cash = getIntent().getIntExtra("Cash", 0);
        long no = Cash * NO;
        exit = (Button)findViewById(R.id.exit);
        earnedcoins = findViewById(R.id.earnedcoins);
       // luckyscore = findViewById(R.id.luckyscore);
        FirebaseFirestore database;


        database = FirebaseFirestore.getInstance();
        database
                .collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                binding.earnedcoins.setText(String.valueOf(user.getCoins()));

//               binding.luckyscore.setText(Cash);
            }
        });

    //   binding.luckyscore.setText(String.valueOf(Cash));

     //   binding.luckyscore.setText(Cash);

        exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SrActivity.this, MainActivity.class));
            }
        });
        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT,"share demo");
                intent.setType("text/plain");
                String shareMessage = "https://play.google.com/store/apps/details?="+ BuildConfig.APPLICATION_ID+"\n\n";
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent,"share by"));

            }
        });
    }

}