package com.example.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Lớp này đại diện cho màn hình đăng nhập của ứng dụng. Người dùng có thể nhập tên đăng nhập và mật khẩu để đăng nhập hoặc chuyển đến màn hình đăng ký để tạo tài khoản mới. Nếu quá trình đăng nhập thành công, người dùng sẽ được chuyển hướng đến màn hình chính.
 */
public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnReg;
    private EditText edtUsername, edtPassword;
    private TextView txtForgotPass;
    private DAO dao;

    /**
     * Được gọi khi hoạt động được tạo lần đầu. Phương thức này khởi tạo giao diện người dùng, thiết lập xử lý sự kiện cho nút đăng nhập và nút đăng ký, và xử lý thử đăng nhập.
     *
     * @param savedInstanceState Trạng thái đã lưu trước đó của hoạt động (nếu có).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dao = new DAO(LoginActivity.this);
        implementEvents();

        // Mở màn hình đăng ký
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý khi nút đăng nhập được nhấn
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.insert_complete_msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (!dao.checkPlayer(username, password)) {
                        Toast.makeText(LoginActivity.this, R.string.wrong_username_password_msg, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.login_success_msg, Toast.LENGTH_SHORT).show();
                        Player player = dao.getPlayerByUsername(username);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("playerId", player.getId());
                        bundle.putString("username", player.getUsername());
                        bundle.putInt("totalScore", player.getTotalScore());
                        bundle.putInt("totalPlay", player.getTotalPlay());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });

        txtForgotPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(LoginActivity.this, CheckResetActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    /**
     * Khởi tạo các phần tử giao diện và gán chúng cho các biến trong lớp.
     */
    private void implementEvents() {
        btnLogin = findViewById(R.id.btn_login_login);
        btnReg = findViewById(R.id.btn_login_reg);
        edtUsername = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);
        txtForgotPass = findViewById(R.id.txtForgotPass);
    }
}
