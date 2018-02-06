package compindia.intext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DrawableActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    static int imageID;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        setTitle("Drawable Images");
        init();

    }

    private void init() {
        imageView1 = (ImageView) findViewById(R.id.img_1);
        imageView2 = (ImageView) findViewById(R.id.img_2);
        imageView3 = (ImageView) findViewById(R.id.img_3);
        imageView4 = (ImageView) findViewById(R.id.img_4);
        imageView5 = (ImageView) findViewById(R.id.img_5);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
    }

    private void setSelected(int id) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.MY_KEY, id);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onClick(View view) {
        int viewID = view.getId();
        switch (viewID) {
            case R.id.img_1:
                setSelected(R.drawable.image1);
                break;
            case R.id.img_2:
                setSelected(R.drawable.image2);
                break;
            case R.id.img_3:
                setSelected(R.drawable.image3);
                break;
            case R.id.img_4:
                setSelected(R.drawable.image4);
                break;
            case R.id.img_5:
                setSelected(R.drawable.image5);
                break;
            default:
                break;
        }
    }
}
