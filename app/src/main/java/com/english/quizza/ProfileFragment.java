package com.english.quizza;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.english.quizza.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
   /* FirebaseDatabase.getInstance().getReference("users")
        .keepSynced(true);
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build();
FirebaseFirestore.getInstance().setFirestoreSettings(settings);*/

    TextView nameebox, emaillbox;
    Button update;
    CircleImageView profileimage;
    FirebaseFirestore database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FragmentProfileBinding binding;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        auth.getCurrentUser().getUid();
        FirebaseUser user = auth.getCurrentUser();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        binding.nameebox.setText(String.valueOf(user.getName()));
                        binding.emaillbox.setText(String.valueOf(user.getEmail()));
                        // binding.editimage.setOnClickListener();
                    }
                });

        View v = binding.getRoot();
        // nameebox = binding.nameebox;
        // profileimage = binding.profileimage;
        //emaillbox = binding.emaillbox;
        binding.editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null) {
            Uri sFile = data.getData();
            // Get the reference to the storage location where the image will be stored
            final StorageReference reference = storage.getReference().child("profile")
                    .child(FirebaseAuth.getInstance().getUid());

// Upload the image to the storage location
            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the download URL of the uploaded image
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String profile = uri.toString();
                            // Update profile picture in the database
                            database.collection("users").document(FirebaseAuth.getInstance().getUid())
                                    .update("profile", profile)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(), "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                                            // Set the updated profile picture in the CircleImageView
                                            binding.profileimage.setImageURI(sFile);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Failed to update profile picture: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                }
            });

        }

    }
    @Override
   /* public void onStart() {
        super.onStart();
        // Load the profile image from Firebase Storage
        StorageReference storageReference = storage.getReference().child("profile").child(auth.getCurrentUser().getUid());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(binding.profileimage);
            }
        });
    }*/

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Load the profile image from Firebase Storage
        if (isAdded()) {
            StorageReference storageReference = storage.getReference().child("profile").child(auth.getCurrentUser().getUid());
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext()).load(uri).into(binding.profileimage);
                }
            });
        }
    }

}
