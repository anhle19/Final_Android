package com.example.midterm;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Class này đại diện cho main activity của ứng dụng
 * Nó xử lý tương tác của người dùng và điều khiển những tác vụ của ứng dụng
 * */
public class MainActivity extends AppCompatActivity {

    //Mảng chứa danh sách các background color
    private final String[] colorBG = {
       "#61FFC107", //Màu vàng
       "#DDDDDD", //Màu xám
       "#FFCCCC" //Màu hồng
    };
    //Index của background color hiện tại
    private int currentColorIndex = 0;
    private Button btnAddImage, btnChangeBG, btnFinish;
    private ImageView imgUploaded;
    private TextView txtPoint, txtUsername;

    private LinearLayout   layoutMain, layoutControl, layoutDelete, layout1, layout2, layout3, layout4
            , layout5, layout6, layout7, layout8, layout9;

    private ConstraintLayout layoutImage;
    //Ghi nhớ vị trí ban đầu khi khởi động của imageView
    private float initialX, initialY;

    //Lưu điểm của người chơi
    private int gamePoint = 0;
    private int gameId, playerId, totalScore, totalPlay;
    private String username;
    private boolean isEnable = true;
    public static final int GALLERY_REQ_CODE = 1000;
    private DAO dao;

    /**
     * Được gọi khi ứng dụng được khỏi tạo lần đầu tiên
     * Khởi tạo giao diện người dùng
     * Hiển thị số điểm của người chơi
     * Ghi nhớ vị trí ban đầu của imageview kéo thả của người dùng
     * Đặt các màu sắc cho các thành phần
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DAO(MainActivity.this);
        implementEvents();
        getData();
        updateGamePoint();
        rememberInitialPosition();
        setColorPlayLayout();
    }

    // Ngăn chặn hành vi back
    @Override
    public void onBackPressed() {
        Toast.makeText(this, R.string.failed_msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Lấy dữ liệu của player được gửi thông qua intent
     * */
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("username");
            playerId = bundle.getInt("playerId");
            totalScore = bundle.getInt("totalScore");
            totalPlay = bundle.getInt("totalPlay");
            gameId = totalPlay+1;
            txtUsername.setText("Username: "+username);
        }
    }

    /**
     * Lưu trạng thái hiện tại của Activity vào một Bundle để khôi phục
     * */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Lưu điểm của trò chơi
        outState.putInt("gamePoint", gamePoint);
        //Kiểm tra có hình ảnh đã tải lên và lưu vào bundle dưới dạng chuỗi
        Uri uri = (Uri) imgUploaded.getTag();
        if(uri != null) {
            String uriString = uri.toString();
            outState.putString("image", uriString);
        }
        //Kiểm tra nếu chỉ số màu hiện tại lớn hơn 0 thì lưu vào bundle
        if (currentColorIndex > 0) {
            outState.putInt("colorIndex", currentColorIndex);
        }
        //Truyền trạng thái button add image
        outState.putBoolean("isEnable", isEnable);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /**
         * Khôi phục trạng thái của một Activity từ Bundle nếu có
         * */
        if (savedInstanceState != null) {
            //Khôi phục điểm của người chơi
            gamePoint = savedInstanceState.getInt("gamePoint");

            //Kiểm tra nếu có hình ảnh đã lưu trong Bundle và hiển thị nó trên Image view
            if (savedInstanceState.getString("image") != null) {
                String imageUri = savedInstanceState.getString("image");
                imgUploaded.setImageURI(Uri.parse(imageUri));
            }

            //Cập nhật điểm của người chơi
            updateGamePoint();

            //Kiểm tra màu nền hiện tại lớn hơn 0 và cập nhật hiển thị màu nền cho ứng dụng
            if (savedInstanceState.getInt("colorIndex") > 0) {
                currentColorIndex = savedInstanceState.getInt("colorIndex");
                layoutMain.setBackgroundColor(Color.parseColor(colorBG[currentColorIndex]));
            }
            //Cập nhật trạng thái button add image
            isEnable = savedInstanceState.getBoolean("isEnable");
            btnAddImage.setEnabled(isEnable);
        }
    }

    /**
     * Hiển thị điểm của người chơi
     * */
    private void updateGamePoint() {
        txtPoint.setText(String.valueOf(gamePoint));
    }

    /**
     * Phương thức được gọi đến từ sự kiện onClick của button change background
     * Tự động tăng chỉ số của màu hiện tại trong mảng
     * Đặt màu sắc mới cho background của mainLayout
     * */
    private void changeBGColor() {
        currentColorIndex++;

        if (currentColorIndex >= colorBG.length) {
            currentColorIndex = 0;
        }

        String selectedColor = colorBG[currentColorIndex];
        layoutMain.setBackgroundColor(Color.parseColor(selectedColor));
    }

    /**
     * Khởi tạo các màu sắc ban đầu cho ứng dụng
     * Đặt màu cho mainLayout và các layout dùng để kéo thả
     * */
    private void setColorPlayLayout() {
        layoutMain.setBackgroundColor(Color.parseColor(colorBG[0]));
        layout1.setBackgroundColor(Color.GRAY);
        layout2.setBackgroundColor(Color.GRAY);
        layout3.setBackgroundColor(Color.GRAY);
        layout4.setBackgroundColor(Color.GRAY);
        layout5.setBackgroundColor(Color.GRAY);
        layout6.setBackgroundColor(Color.GRAY);
        layout7.setBackgroundColor(Color.GRAY);
        layout8.setBackgroundColor(Color.GRAY);
        layout9.setBackgroundColor(Color.GRAY);
    }

    //Lưu lại vị trí ban đầu của Imageview dùng để kéo thả
    private void rememberInitialPosition() {
        initialX = imgUploaded.getX();
        initialY = imgUploaded.getY();
    }

    /**
     * Ánh xạ các id của các view và view group mà ứng dụng cần tương tác
     * Gắn các sự kiện cho các view và view group
     * */
    private void implementEvents() {
        //Ánh xạ id
        btnAddImage = findViewById(R.id.btnAddImage);
        btnChangeBG = findViewById(R.id.btnChangeBG);
        btnFinish = findViewById(R.id.btnFinish);
        imgUploaded = findViewById(R.id.imgUploaded);
        layoutMain = findViewById(R.id.layoutMain);
        layoutControl = findViewById(R.id.layoutControl);
        layoutDelete = findViewById(R.id.layoutDelete);
        layoutImage = findViewById(R.id.layoutImage);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        layout6 = findViewById(R.id.layout6);
        layout7 = findViewById(R.id.layout7);
        layout8 = findViewById(R.id.layout8);
        layout9 = findViewById(R.id.layout9);
        txtPoint = findViewById(R.id.txtPoint);
        txtUsername = findViewById(R.id.txtUsername);

        //Gắn sự kiện
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
        btnChangeBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBGColor();
            }
        });
        /**
         * Lưu thông tin chơi của player
         * Lưu thông tin màn chơi
         * */
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = 0;
                setNewDataForPlayer();
                Score score = new Score(playerId, gamePoint, gameId);
                //Cập thông tin người chơi
                result += dao.updatePlayer(playerId, totalScore, totalPlay);
                //Lưu thông tin màn chơi
                result += dao.insertScore(score);
                if (result > 0) {
                    Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("playerId", playerId);
                    bundle.putInt("gamePoint", gamePoint);
                    bundle.putInt("gameId", gameId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.failed_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgUploaded.setOnTouchListener(new ImageTouchListener());
        layout1.setOnDragListener(new PlayDragListener());
        layout2.setOnDragListener(new PlayDragListener());
        layout3.setOnDragListener(new PlayDragListener());
        layout4.setOnDragListener(new PlayDragListener());
        layout5.setOnDragListener(new PlayDragListener());
        layout6.setOnDragListener(new PlayDragListener());
        layout7.setOnDragListener(new PlayDragListener());
        layout8.setOnDragListener(new PlayDragListener());
        layout9.setOnDragListener(new PlayDragListener());
        layoutDelete.setOnDragListener(new DeleteDragListener());
    }

    //Cập nhật thông tin điểm và tổng số lần chơi của player
    private void setNewDataForPlayer() {
        totalScore+=gamePoint;
        totalPlay++;
    }

    /**
     * Class này xử lý sự kiện kéo thả imageview của người dùng
     * Các ô layout này sẽ tự động thay đổi màu sắc background khi người dùng kéo hoặc thả view vào
     * Khi thả vào các ô thì điểm của người chơi sẽ tự động được cập nhật
     * */
    protected class PlayDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //Khi sự kiện kéo thả bắt đầu
                    view.setBackgroundColor(Color.GRAY);
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    //Khi được kéo vào vùng
                    view.setBackgroundColor(Color.GREEN);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    //Khi thoát ra khỏi vùng
                    view.setBackgroundColor(Color.GRAY);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    //Khi sự kiện kéo thả kết thúc
                    break;

                case DragEvent.ACTION_DROP:
                    //Khi sự kiện thả
                    //Cập nhật điểm của người chơi
                    gamePoint++;
                    updateGamePoint();
                    View tvState = (View) event.getLocalState();
                    ViewGroup tvParent = (ViewGroup) tvState.getParent();
                    tvParent.removeView(tvState);
                    LinearLayout container = (LinearLayout) view;
                    container.addView(tvState);
                    tvState.setX(event.getX() - tvState.getWidth()/2);
                    tvState.setY(event.getY() - tvState.getHeight()/2);
                    view.setVisibility(View.VISIBLE);
                    view.setBackgroundColor(Color.RED);
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    /**
     * Class này xử lý sự kiện kéo thả imageview của người dùng vào thùng rác
     * Sau khi kéo thì view sẽ trả về vị trí ban đầu khi khởi động ứng dụng và gỡ hình ảnh hiện tại
     * */
    protected class DeleteDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    //Khi được thả vào
                    View tvState = (View) event.getLocalState();
                    ViewGroup tvParent = (ViewGroup) tvState.getParent();
                    tvParent.removeView(tvState);
                    layoutImage.addView(tvState);
                    tvState.setX(initialX);
                    tvState.setY(initialY);
                    imgUploaded.setImageDrawable(null);
                    view.setVisibility(View.VISIBLE);
                    isEnable = true;
                    btnAddImage.setEnabled(isEnable);
                    gamePoint = 0;
                    updateGamePoint();
                    Toast.makeText(MainActivity.this, R.string.delete_msg, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    /**
     * Class này xử lý sự kiện khi imageview được chạm vào
     * Tự động tạo bóng cho view có hành động chạm vào
     * */
    protected class ImageTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (imgUploaded.getDrawable() != null) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imgUploaded);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                    v.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, R.string.image_msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mở thư viện ảnh của thiết bị để người dùng có thể chọn ảnh
     * Phương thức này tạo một itent để mở image picker và chọn ảnh để hiển thị bên trong Imageview
     * */
    protected void openImagePicker() {
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery, GALLERY_REQ_CODE);
    }

    /**
     * Xử lý kết quả của việc chọn ảnh từ thư viện của người dùng
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                imgUploaded.setImageURI(data.getData());
                imgUploaded.setTag(data.getData());
                //Vô hiệu hóa button sau khi đã chọn được hình ảnh
                isEnable = false;
                btnAddImage.setEnabled(isEnable);
            }
        }
    }
}