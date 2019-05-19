package com.example.snapchatpassport;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;
import com.snapchat.kit.sdk.creative.exceptions.SnapMediaSizeException;
import com.snapchat.kit.sdk.creative.exceptions.SnapStickerSizeException;
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory;
import com.snapchat.kit.sdk.creative.media.SnapPhotoFile;
import com.snapchat.kit.sdk.creative.media.SnapSticker;
import com.snapchat.kit.sdk.creative.models.SnapPhotoContent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.EasyPermissions;

public class SnapActivity extends AppCompatActivity {

    private static final String STICKER_NAME = "enghandTerritorySticker";
    private File mStickerFile;

    SnapCreativeKitApi snapCreativeKitApi;
    boolean photoPicker;

    public ImageView mImageView;

    private static final int WRITE_STORAGE_PERM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap);

        mStickerFile = new File(getCacheDir(), STICKER_NAME);
        mImageView = findViewById(R.id.imageView);

        if (!mStickerFile.exists()) {
            try (InputStream inputStream = getAssets().open("enghandTerritorySticker.png")) {
                copyFile(inputStream, mStickerFile);
            } catch (IOException e) {
                Toast.makeText(this, "Failed to copy sticker asset", Toast.LENGTH_SHORT).show();
            }
        }

        EasyImage.configuration(this)
                .setAllowMultiplePickInGallery(false);

        snapCreativeKitApi = SnapCreative.getApi(this);

        photoPicker = false;
        Button sendButton = findViewById(R.id.sndPhotoButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPhotoSend();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (photoPicker) {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    photoPicker = false;
                    //Some error handling
                }

                @Override
                public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                    //Handle the images
                    photoPicker = false;
                    onPhotosReturned(imagesFiles);
                }
            });
        }
    }

    private void onPhotosReturned(List<File> imageFiles) {
        Log.d("photo Returned", "Photo returned");
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(this);

        if (imageFiles.size() == 1) {
            SnapPhotoFile photoFile;
            try {
                photoFile = snapMediaFactory.getSnapPhotoFromFile(imageFiles.get(0));
            } catch (SnapMediaSizeException e) {
                e.printStackTrace();
                return;
            }
            SnapPhotoContent snapPhotoContent = new SnapPhotoContent(photoFile);

            SnapSticker snapSticker = checkLocationS();
            checkLocationT(snapPhotoContent);

            snapPhotoContent.setSnapSticker(snapSticker);

            snapCreativeKitApi.send(snapPhotoContent);
            Log.d("Snapchat", "Photo sent to snapchat");
        }
    }

    private SnapSticker checkLocationS(){
        //if..... within certain range of coordinates

        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(this);
        SnapSticker snapSticker = null;
        try {
            snapSticker = snapMediaFactory.getSnapStickerFromFile(mStickerFile);
        } catch (SnapStickerSizeException e) {
            e.printStackTrace();
            return null;
        }

        snapSticker.setHeight(300);
        snapSticker.setWidth(300);
        snapSticker.setPosX(0.75f);
        snapSticker.setPosY(0.25f);
        snapSticker.setRotationDegreesClockwise(0f);

        return  snapSticker;
    }

    private void checkLocationT(SnapPhotoContent snapPhotoContent){
        //if..... within certain range of coordinates
        snapPhotoContent.setCaptionText("Welcome to team enghand's corner!");
    }

    private void startPhotoSend() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //has permissions
            EasyImage.openGallery(SnapActivity.this, 0);
            photoPicker = true;
        } else {
            //does not have permissions
            EasyPermissions.requestPermissions(this, getString(R.string.app_permission_text), WRITE_STORAGE_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private static void copyFile(InputStream inputStream, File file) throws IOException {
        byte[] buffer = new byte[1024];
        int length;

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        }
    }
}
