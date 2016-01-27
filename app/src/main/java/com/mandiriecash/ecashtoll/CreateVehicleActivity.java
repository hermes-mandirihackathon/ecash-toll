package com.mandiriecash.ecashtoll;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * A login screen that offers login via email/password.
 */
public class CreateVehicleActivity extends AppCompatActivity {
    static final int SELECT_PICTURE = 1;

    Button selectPictureBtn;
    ImageView imageView;

    String selectedImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);

        selectPictureBtn = (Button) findViewById(R.id.selectPictureBtn);
        imageView = (ImageView) findViewById(R.id.imageView);

        selectPictureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Pilih gambar kendaraan"), SELECT_PICTURE);

            }
        });

        findViewById(R.id.addVehicleBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //add backbutton
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                selectedImagePath = getImagePath(selectedImageUri);
                imageView.setImageURI(selectedImageUri);
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getImagePath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }
}

