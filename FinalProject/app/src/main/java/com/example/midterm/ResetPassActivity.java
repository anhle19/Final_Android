package com.example.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Lớp này đại diện cho màn hình đặt lại mật khẩu của ứng dụng. Người dùng có thể nhập mật khẩu mới và xác nhận mật khẩu để đặt lại mật khẩu cho tài khoản của họ.
 */
public class ResetPassActivity extends AppCompatActivity {

    private Button btnExit, btnReset;
    private TextView edtPassword, edtConfirm;
    private int playerId;
    private DAO dao;

    /**
     * Được gọi khi hoạt động được tạo lần đầu. Phương thức này khởi tạo giao diện người dùng và xử lý sự kiện đặt lại mật khẩu.
     *
     * @param savedInstanceState Trạng thái đã lưu trước đó của hoạt động (nếu có).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        dao = new DAO(ResetPassActivity.this);
        getData();
        implementEvents();
    }

    /**
     * Lấy dữ liệu từ Intent gửi đến hoạt động, bao gồm ID của người chơi cần đặt lại mật khẩu.
     */
    private void getData() {
        playerId = getIntent().getIntExtra("playerId", -1);
    }

    /**
     * Thực hiện thao tác thoát khỏi màn hình đặt lại mật khẩu và quay trở lại màn hình đăng nhập.
     */
    private void exitAction() {
        Intent intent = new Intent(ResetPassActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Ánh xạ các ID của phần tử giao diện và thiết lập sự kiện cho các nút thoát và đặt lại mật khẩu.
     */
    private void implementEvents() {
        btnExit = findViewById(R.id.btn_resetPass_exit);
        btnReset = findViewById(R.id.btn_resetPass_reset);
        edtPassword = findViewById(R.id.edt_resetPass_password);
        edtConfirm = findViewById(R.id.edt_resetPass_confirm);

        // Xử lý sự kiện thoát khỏi màn hình đặt lại mật khẩu
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitAction();
            }
        });

        // Xử lý sự kiện đặt lại mật khẩu
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edtPassword.getText().toString();
                String confirm = edtConfirm.getText().toString();
                if (password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(ResetPassActivity.this, R.string.insert_complete_msg, Toast.LENGTH_SHORT).show();
                } else if (!Validation.isPassword(password)) {
                    Toast.makeText(ResetPassActivity.this, R.string.password_validate_msg, Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirm)) {
                    Toast.makeText(ResetPassActivity.this, R.string.confirm_msg, Toast.LENGTH_SHORT).show();
                } else {
                    int result = dao.updatePlayerPassword(playerId, password);
                    if (result > 0) {
                        Toast.makeText(ResetPassActivity.this, R.string.resetPass_success_msg, Toast.LENGTH_SHORT).show();
                        exitAction();
                    } else {
                        Toast.makeText(ResetPassActivity.this, R.string.failed_msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}