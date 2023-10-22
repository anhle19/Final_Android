package com.example.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Lớp này đại diện cho màn hình kiểm tra và đặt lại mật khẩu của ứng dụng. Người dùng cần nhập tên đăng nhập và số điện thoại
 * để kiểm tra tính hợp lệ trước khi đặt lại mật khẩu.
 */
public class CheckResetActivity extends AppCompatActivity {

    private Button btnExit, btnConfirm;
    private EditText edtUsername, edtPhone;
    private DAO dao;

    /**
     * Được gọi khi hoạt động được tạo lần đầu. Phương thức này khởi tạo giao diện người dùng và xử lý sự kiện kiểm tra và đặt lại mật khẩu.
     *
     * @param savedInstanceState Trạng thái đã lưu trước đó của hoạt động (nếu có).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reset);
        dao = new DAO(CheckResetActivity.this);
        implementEvents();
    }

    /**
     * Ánh xạ các ID của phần tử giao diện và thiết lập sự kiện cho các nút thoát và xác nhận.
     */
    private void implementEvents() {
        btnExit = findViewById(R.id.btn_checkRS_exit);
        btnConfirm = findViewById(R.id.btn_checkRS_confirm);
        edtUsername = findViewById(R.id.edt_checkRS_username);
        edtPhone = findViewById(R.id.edt_checkRS_phone);

        // Xử lý sự kiện thoát khỏi màn hình kiểm tra và đặt lại mật khẩu
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckResetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện xác nhận và kiểm tra thông tin đặt lại mật khẩu
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String phone = edtPhone.getText().toString();

                if (username.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(CheckResetActivity.this, R.string.insert_complete_msg, Toast.LENGTH_SHORT).show();
                } else if (!Validation.isPhone(phone)) {
                    Toast.makeText(CheckResetActivity.this, R.string.phone_validate_msg, Toast.LENGTH_SHORT).show();
                } else if (!dao.checkResetPassword(username, phone)) {
                    Toast.makeText(CheckResetActivity.this, R.string.username_phone_not_founded, Toast.LENGTH_SHORT).show();
                } else {
                    int playerId = dao.getPlayerId(username);
                    if (playerId > -1) {
                        Intent intent = new Intent(CheckResetActivity.this, ResetPassActivity.class);
                        intent.putExtra("playerId", playerId);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}