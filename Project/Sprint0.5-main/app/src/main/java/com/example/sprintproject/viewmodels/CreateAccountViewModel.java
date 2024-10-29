package com.example.sprintproject.viewmodels;


import com.example.sprintproject.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountViewModel {

    private DatabaseReference userDatabase;

    /**
     * Constructor for CreateAccountViewModel
     * @param username of the user
     * @param password of the user
     */
    public CreateAccountViewModel(String username, String password) {
        userDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User(username, password);
        userDatabase.child("users").child(username).setValue(user);
    }

}
