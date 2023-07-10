package com.example.myshoesstore.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myshoesstore.LoginActivity;
import com.example.myshoesstore.R;
import com.example.myshoesstore.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {

    EditText edtUserName, edtUserEmail, edtUserPhone, edtUserLocation;
    Button btnUpdateProfile, btnSignout;

    FirebaseAuth auth;
    FirebaseDatabase database;
    String password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        edtUserName = root.findViewById(R.id.editTextUserName);
        edtUserEmail = root.findViewById(R.id.editTextUserEmail);
        edtUserPhone = root.findViewById(R.id.editTextUserPhone);
        edtUserLocation = root.findViewById(R.id.editTextUserAddress);
        btnUpdateProfile = root.findViewById(R.id.buttonUpdateProfile);
        btnSignout = root.findViewById(R.id.buttonSignOut);
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                edtUserName.setText(user.getName());
                edtUserEmail.setText(user.getEmail());
                edtUserPhone.setText(user.getPhoneNumber());
                edtUserLocation.setText(user.getAddress());
                password = user.getPassword();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

       return root;
   }

    private void updateProfile() {
        User user = new User();

        String name = edtUserName.getText().toString().trim();
        String email = edtUserEmail.getText().toString().trim();
        String phone = edtUserPhone.getText().toString().trim();
        String location = edtUserLocation.getText().toString().trim();

        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setAddress(location);
        user.setPassword(password);

        // Update the user's profile in the database
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    ;


}