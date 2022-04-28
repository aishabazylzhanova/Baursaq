package com.baursaq.baursaq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddActivity extends AppCompatActivity {
    private EditText recipeName;
    private EditText recipeContent;
    private ImageView recipeImage;
    private Button add_recipe;
    private Uri ImageUri;
    private static final int GALLERYPICK=1;
    private String rName, rDescription, saveCurrentDate, saveCurrentTime, RecipeRandomKey, downloadedUri;
    private StorageReference rImage;
    private DatabaseReference rDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        recipeName=findViewById(R.id.recipeName);
        recipeContent=findViewById(R.id.recipeContent);
        recipeImage=findViewById(R.id.recipeImage);
        add_recipe=findViewById(R.id.button_add_recipe);
        rImage = FirebaseStorage.getInstance().getReference().child("Image");
        rDatabase= FirebaseDatabase.getInstance().getReference().child("Recipes");
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateRecipeData();
            }
        });
    }

    private void ValidateRecipeData() {
        rName=recipeName.getText().toString();
        rDescription=recipeContent.getText().toString();
        if (ImageUri == null){
            Toast.makeText(this, "Добавьте изображение", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(rName)){
            Toast.makeText(this, "Добавьте название", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(rDescription)){
            Toast.makeText(this, "Добавьте описание", Toast.LENGTH_SHORT).show();
        }
        else{
            StoreRecipe();
        }

    }

    private void StoreRecipe() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        RecipeRandomKey=saveCurrentDate+saveCurrentTime;
        StorageReference filePath = rImage.child(ImageUri.getLastPathSegment() + RecipeRandomKey+ ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddActivity.this, "Image added", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadedUri=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
                            SaveRecipeInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveRecipeInfoToDatabase() {
        HashMap<String, Object> recipeMap = new HashMap<>();
        recipeMap.put("rId", RecipeRandomKey);
        recipeMap.put("date", saveCurrentDate);
        recipeMap.put("time", saveCurrentTime);
        recipeMap.put("image", downloadedUri);
        recipeMap.put("title", rName);
        recipeMap.put("description", rDescription);
        rDatabase.child(RecipeRandomKey).updateChildren(recipeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddActivity.this, "Recipe Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void OpenGallery() {
        Intent GalleryIntent = new Intent();
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent, GALLERYPICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERYPICK && resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            recipeImage.setImageURI(ImageUri);
        }
    }
}