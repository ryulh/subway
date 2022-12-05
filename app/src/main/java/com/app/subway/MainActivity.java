package com.app.subway;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.subway.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    // Used to load the 'subway' library on application startup.
    static {
        System.loadLibrary("subway");
    }

    private static final String TAG = "Touch";
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    private Matrix savedMatrix2 = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;

    private static HorizontalScrollView Scroll_Horizontal = null;
    private static ScrollView Scroll_Vertical = null;
    protected static int currentX = 0;
    protected static int currentY = 0;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    private static Button fullscreen_btn;
    private static LinearLayout top_nev_bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView view = (ImageView) findViewById(R.id.imageView);
        view.setOnTouchListener(this);

        Scroll_Vertical = (ScrollView) findViewById(R.id.imageViewVerticalScroll);
        Scroll_Vertical.setOnTouchListener(this);
        Scroll_Horizontal = (HorizontalScrollView) findViewById(R.id.imageViewHorizontalScroll);
        Scroll_Horizontal.setOnTouchListener(this);

        fullscreen_btn = (Button) findViewById(R.id.fullscreen_btn);
        top_nev_bar = (LinearLayout)findViewById(R.id.top_nev_bar);

        fullscreen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(top_nev_bar.getVisibility() == view.GONE){
                    top_nev_bar.setVisibility(view.VISIBLE);
                    fullscreen_btn.setBackgroundResource(R.drawable.icon_fullscreen);
                }else {
                    top_nev_bar.setVisibility(view.GONE);
                    fullscreen_btn.setBackgroundResource(R.drawable.icon_minscreen);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                currentX = (int)event.getRawX();
                currentY = (int)event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x2 = (int)event.getRawX();
                int y2 = (int)event.getRawY();
                scrollBy(currentX-x2, currentY-y2);
                currentX = x2;
                currentY = y2;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                currentX = (int)event.getRawX();
                currentY = (int)event.getRawY();
                break;
        }
        currentX = (int)event.getRawX();
        currentY = (int)event.getRawY();

        return true; // indicate event was handled
    }

    public static void scrollBy(int x, int y)
    {
        Scroll_Horizontal.scrollBy(x, 0);
        Scroll_Vertical.scrollBy(0, y);
    }
}