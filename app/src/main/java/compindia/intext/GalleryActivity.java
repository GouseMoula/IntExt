package compindia.intext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static compindia.intext.R.*;

public class GalleryActivity extends AppCompatActivity {
    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;
    static StorageClass storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_gallery);
        setTitle("Gallery");
        init();
        setImgs();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        if (item.getItemId() == id.menu_clear_all) {
            if (storage.getInternalFolderList().length + storage.getExternalFolderList().length <= 0) {

                Toast.makeText(this, "No Images Found", Toast.LENGTH_SHORT).show();

            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GalleryActivity.this, "All Images cleared", Toast.LENGTH_SHORT).show();

                        setImgs();
                    }
                }, 1000);
                MainActivity.removePreview();
                storage.clearAllImages();
            }
        }
        return true;
    }

    void init() {
        img1 = (ImageView) findViewById(id.img_gal_1);
        img2 = (ImageView) findViewById(id.img_gal_2);
        img3 = (ImageView) findViewById(id.img_gal_3);
        img4 = (ImageView) findViewById(id.img_gal_4);
        img5 = (ImageView) findViewById(id.img_gal_5);
        img6 = (ImageView) findViewById(id.img_gal_6);
        img7 = (ImageView) findViewById(id.img_gal_7);
        img8 = (ImageView) findViewById(id.img_gal_8);
        img9 = (ImageView) findViewById(id.img_gal_9);
        img10 = (ImageView) findViewById(id.img_gal_10);
        storage = new StorageClass(this);
    }

    void setInternalImages() {
        ImageView[] views = {img1, img2, img3, img4, img5};
        for (int i = 0; i < storage.getInternalFolderList().length; i++) {
            if (storage.getInternalFolderList()[i].exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(storage.getInternalFolderList()[i].getAbsolutePath());
                if (i < 5) {
                    views[i].setVisibility(View.VISIBLE);
                    views[i].setImageBitmap(myBitmap);
                }
            }
        }
    }

    void setExternalImages() {
        ImageView[] views = {img6, img7, img8, img9, img10};
        for (int i = 0; i < storage.getExternalFolderList().length; i++) {
            if (storage.getExternalFolderList()[i].exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(storage.getExternalFolderList()[i].getAbsolutePath());
                if (i < 5) {
                    views[i].setVisibility(View.VISIBLE);
                    views[i].setImageBitmap(myBitmap);
                }
            }
        }
    }

    void setImgs() {
        storage = new StorageClass(this);
        ImageView[] views = {img1, img2, img3, img4, img5, img6, img7, img8, img9, img10};
        int c = 0;
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.INVISIBLE);
        }
        setInternalImages();
        setExternalImages();
    }
}
