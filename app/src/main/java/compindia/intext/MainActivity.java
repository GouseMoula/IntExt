package compindia.intext;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int DRAWABLE_PIC_REQUEST = 26;
    final int CAMERA_PIC_REQUEST = 25;
    static final String MY_KEY = "imageid";
    CheckBox checkBox;
    static ImageView imageView;
    Button buttonCapture, buttonShow, buttonSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        checkBox = (CheckBox) findViewById(R.id.checkbox_);
        checkBox.setChecked(false);
        buttonCapture = (Button) findViewById(R.id.btn_Capture);
        buttonSelect = (Button) findViewById(R.id.btn_Select);
        buttonShow = (Button) findViewById(R.id.btn_Next);
        imageView = (ImageView) findViewById(R.id.img_Main_View);
        buttonShow.setOnClickListener(this);
        buttonSelect.setOnClickListener(this);
        buttonCapture.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Capture:
                startCapture();
                break;
            case R.id.btn_Select:
                selectFromDrawable();
                break;
            case R.id.btn_Next:
                openGallery();
                break;
            default:
                break;
        }
    }

    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void selectFromDrawable() {
        Intent intent = new Intent(MainActivity.this, DrawableActivity.class);
        startActivityForResult(intent, DRAWABLE_PIC_REQUEST);
    }

    private void openGallery() {
        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    private void saveImageFromCam(Intent data) {
        if (data.hasExtra("data")) {
            StorageClass storage = new StorageClass(this);
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (checkBox.isChecked()) {
                if (storage.getInternalFolderList().length < 5) {
                    imageView.setImageBitmap(bitmap);
                    storage.saveToInternal(bitmap, storage.getNoOfFilesInInternal() + 1);
                } else {
                    Toast.makeText(this, "Internal Storage Limit exceeded", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (storage.getNoOfFilesInExternal() < 5) {
                    imageView.setImageBitmap(bitmap);
                    storage.saveToExternal(bitmap, storage.getNoOfFilesInExternal() + 1);
                } else {
                    Toast.makeText(this, "External Storage Limit exceeded", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveImageFromDrawable(Intent data) {

        if (data.getExtras().containsKey(MY_KEY)) {
            int pathID = data.getExtras().getInt(MY_KEY);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), pathID);
            StorageClass storage = new StorageClass(this);
            if (checkBox.isChecked()) {
                if (storage.getNoOfFilesInInternal() < 5) {
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                    storage.saveToInternal(bitmap, storage.getNoOfFilesInInternal() + 1);
                } else {
                    Toast.makeText(this, "Internal Storage Limit exceeded", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (storage.getNoOfFilesInExternal() < 5) {
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                    storage.saveToExternal(bitmap, storage.getNoOfFilesInExternal() + 1);
                } else {
                    Toast.makeText(this, "External Storage Limit exceeded", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected static void removePreview() {
        imageView.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    saveImageFromCam(data);
                    break;
                case DRAWABLE_PIC_REQUEST:
                    saveImageFromDrawable(data);
                    break;
                default:
                    break;
            }


        }
    }
}
