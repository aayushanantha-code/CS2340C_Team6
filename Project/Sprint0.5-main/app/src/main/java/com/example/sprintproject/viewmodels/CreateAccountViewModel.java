package com.example.sprintproject.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.sprintproject.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountViewModel {

    private DatabaseReference userDatabase;
    public CreateAccountViewModel(String username, String password) {
        userDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User(username, password);
        userDatabase.child("users").child(username).setValue(user);
    }

}
