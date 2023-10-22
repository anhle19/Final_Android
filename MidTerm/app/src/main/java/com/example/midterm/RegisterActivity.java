package com.example.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Lớp này đại diện cho màn hình đăng ký tài khoản của ứng dụng. Người dùng có thể nhập thông tin tên đăng nhập và mật khẩu để tạo tài khoản mới.
 */
public class RegisterActivity extends AppCompatActivity {

    private Button btnExit, btnReg;
    private EditText edtUsername, edtPassword, edtConfirm, edtPhone;
    private DAO dao;

    /**
     * Được gọi khi hoạt động được tạo lần đầu. Phương thức này khởi tạo giao diện người dùng và xử lý sự kiện đăng ký tài khoản.
     *
     * @param savedInstanceState Trạng thái đã lưu trước đó của hoạt động (nếu có).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dao = new DAO(RegisterActivity.this);
        implementEvents();

        // Xử lý sự kiện thoát khỏi màn hình đăng ký
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện đăng ký tài khoản
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean result = false;
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirm = edtConfirm.getText().toString();
                String phone = edtPhone.getText().toString();
                if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, R.string.insert_complete_msg, Toast.LENGTH_SHORT).show();
                } else if (!Validation.isUsername(username)) {
                    Toast.makeText(RegisterActivity.this, R.string.username_validate_msg, Toast.LENGTH_SHORT).show();
                } else if (!Validation.isPassword(password)) {
                    Toast.makeText(RegisterActivity.this, R.string.password_validate_msg, Toast.LENGTH_SHORT).show();
                } else if (!Validation.isPhone(phone)) {
                    Toast.makeText(RegisterActivity.this, R.string.phone_validate_msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.checkUsername(username)) {
                        Toast.makeText(RegisterActivity.this, R.string.username_existed_msg, Toast.LENGTH_SHORT).show();
                    } else if (dao.checkPhone(phone)) {
                        Toast.makeText(RegisterActivity.this, R.string.phone_used_msg, Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(confirm)) {
                        Toast.makeText(RegisterActivity.this, R.string.confirm_msg, Toast.LENGTH_SHORT).show();
                    } else {
                        // Truyền dữ liệu cho player để thêm vào CSDL
                        Player player = new Player();
                        player.setUsername(username);
                        // Mã hóa mật khẩu bằng BCrypt
                        player.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
                        player.setPhone(phone);
                        result = dao.insertPlayer(player);

                        // Hiển thị thông báo trạng thái của sự kiện
                        if (result) {
                            Toast.makeText(RegisterActivity.this, R.string.reg_success_msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            clearInsert();
                            Toast.makeText(RegisterActivity.this, R.string.failed_msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    /**
     * Xóa dữ liệu đã nhập trong các trường đăng ký.
     */
    private void clearInsert() {
        edtUsername.setText("");
        edtPassword.setText("");
        edtConfirm.setText("");
        edtPhone.setText("");
    }

    /**
     * Ánh xạ các ID của phần tử giao diện.
     */
    private void implementEvents() {
        btnExit = findViewById(R.id.btn_reg_exit);
        btnReg = findViewById(R.id.btn_reg_reg);
        edtUsername = findViewById(R.id.edt_reg_username);
        edtPassword = findViewById(R.id.edt_reg_password);
        edtConfirm = findViewById(R.id.edt_reg_confirm);
        edtPhone = findViewById(R.id.edt_reg_phone);
    }
}
