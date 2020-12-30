package com.nikhilverma360.arclassroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Profile extends AppCompatActivity {
    TextView name, mail;

    ImageView imageView, logout;
    FloatingActionButton floatingActionButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MyClassesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        floatingActionButton= findViewById(R.id.floting_createclass);
        imageView = findViewById(R.id.profilepic);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),JoinClassroom.class);
                startActivity(intent);
            }
        });

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            name.setText("Hi! " +signInAccount.getDisplayName());
            mail.setText("Mail: "+signInAccount.getEmail());
            //imageView.setImageURI();
            Picasso.get().load(signInAccount.getPhotoUrl()).into(imageView);
            CollectionReference myClassesRef = db.collection("students").document(Objects.requireNonNull(signInAccount.getId())).collection("classes");

            setUpRecyclerView(myClassesRef);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setUpRecyclerView(CollectionReference myClassesRef) {

        Query query = myClassesRef;
        FirestoreRecyclerOptions<MyClasses> options = new FirestoreRecyclerOptions.Builder<MyClasses>()
                .setQuery(query, MyClasses.class)
                .build();
        adapter = new MyClassesAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerclassview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        adapter.setOnItemClickListener(new MyClassesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                MyClasses classes = documentSnapshot.toObject(MyClasses.class);
                String classcode = documentSnapshot.getId();

                Intent i = new Intent(getApplicationContext(), ARZone.class);
                i.putExtra("classname", classes.getClassname());
                i.putExtra("section", classes.getSection());
                i.putExtra("room", classes.getRoom());
                i.putExtra("subject", classes.getSubject());
                i.putExtra("classcode", classcode);
                startActivity(i);
                Toast.makeText(Profile.this, "Position: " + position + " classcode: " + classes.getClassname(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}