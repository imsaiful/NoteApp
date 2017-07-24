package com.app3.imsaiful.noteapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class PostActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private EditText fTitle,fDesc;
    private Button fButton;
    private Uri imageUri=null;
    private static final int GALLERY_REQUEST=1;
    private StorageReference mStorage;
    private DatabaseReference myData;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mStorage= FirebaseStorage.getInstance().getReference();
        fTitle=(EditText)findViewById(R.id.addtitle);
        fDesc=(EditText)findViewById(R.id.adddesc);
        fButton=(Button)findViewById(R.id.sbtn);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        myData= FirebaseDatabase.getInstance().getReference().child("Note");

        progressBar=new ProgressDialog(this);
        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent gallery=new Intent(Intent.ACTION_GET_CONTENT);
                        gallery.setType("image/*");
                        startActivityForResult(gallery,GALLERY_REQUEST);

                    }
                }
        );
        fButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    startPostImage();
                    }
                }
        );

    }
    public void startPostImage()

    {
        progressBar.setMessage("Posting");
        progressBar.show();
        final String title=fTitle.getText().toString().trim();
        final String desc=fDesc.getText().toString().trim();
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && imageUri!=null)

        {
            StorageReference filepath=mStorage.child("Blog_Images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                            Uri dowanloafUrl=taskSnapshot.getDownloadUrl();
                            DatabaseReference newNote=myData.push();
                            newNote.child("Title").setValue(title);
                            newNote.child("Description").setValue(desc);
                            newNote.child("Image").setValue(imageUri.toString());
                            progressBar.dismiss();
                            startActivity(new Intent(PostActivity.this,MainActivity.class));
                        }
                    }
            );


        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==GALLERY_REQUEST  && resultCode==RESULT_OK)
        {
            imageUri=data.getData();
            imageButton.setImageURI(imageUri);
            String x=imageUri.toString();
            Toast.makeText(getApplicationContext(),x,Toast.LENGTH_LONG).show();
        }
    }
}
