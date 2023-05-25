package com.english.quizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.english.quizza.databinding.FragmentInviteBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class InviteFragment extends Fragment {


    public InviteFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentInviteBinding binding;
    FirebaseFirestore database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInviteBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();

        binding.invitefrnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Share demo");
                    String shareMessage = "https://play.google.com/store/apps/details?="+BuildConfig.APPLICATION_ID+"\n\n";
                    intent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(intent,"share by"));
                } catch (Exception e) {
                    // Toast.makeText(InviteFragment.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }
}