package com.app3.imsaiful.noteapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    private RecyclerView noteRecyclerview;
    private DatabaseReference myDataBase;
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener nAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteRecyclerview=(RecyclerView) findViewById(R.id.note_list);
        noteRecyclerview.setHasFixedSize(true);
        noteRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myDataBase=FirebaseDatabase.getInstance().getReference().child("Note");
        nAuth= FirebaseAuth.getInstance();
        nAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));

                }

            }
        };

    }

    @Override
    protected void onStart() {
        nAuth.addAuthStateListener(nAuthListener);
        super.onStart();
        FirebaseRecyclerAdapter<Note,NoteViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Note, NoteViewHolder>(
                        Note.class,
                        R.layout.note_row,
                        NoteViewHolder.class,
                        myDataBase

                ) {
                    @Override
                    protected void populateViewHolder(NoteViewHolder viewHolder, Note model, int position) {
                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setDesc(model.getDescription());
                    viewHolder.setImage(getApplicationContext(),model.getImage());
                    }
                };
                noteRecyclerview.setAdapter(firebaseRecyclerAdapter);


    }
    public static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        View myView;
        public NoteViewHolder(View itemView) {
            super(itemView);
            myView=itemView;
        }
        public void setTitle(String title)
        {
            TextView note_Tile=(TextView)myView.findViewById(R.id.noteTitle);
            note_Tile.setText(title);
        }
        public void setDesc(String desc)
        {
            TextView note_Desc=(TextView)myView.findViewById(R.id.noteDesc);
            note_Desc.setText(desc);
        }
        public void setImage(Context ctx,String image)
        {
            ImageView note_img=(ImageView)myView.findViewById(R.id.noteImage);
            Picasso.with(ctx).load(image).into(note_img);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add)
        {
            startActivity(new Intent(MainActivity.this,PostActivity.class));

        }
        if(item.getItemId()==R.id.action_logOut)
        {
            signOut();

        }


        return super.onOptionsItemSelected(item);
    }
    public void signOut()
    {
        nAuth.signOut();
    }
}
