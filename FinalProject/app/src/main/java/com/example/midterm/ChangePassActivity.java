package com.example.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class này dùng để hiển thị giao diện thay đổi mật khẩu
 * Cho phép người dùng thay đổi mật khẩu
 * Dừng thay đổi mật khẩu để quay về activity trước
 * */
public class ChangePassActivity extends AppCompatActivity {

    private int playerId, gameId, gameScore;
    private Button btnExit, btnChange;
    private EditText edtPass, edtNewPass, edtConfirm;
    private DAO dao;

    /**
     * Hiển thị giao diện của activity
     * Khởi tạo đối tượng DAO
     * lấy các dữ liệu cần thiết
     * Ánh xạ và gắn các sự kiện
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        dao = new DAO(ChangePassActivity.this);
        getData();
        implementEvents();
    }

    private void implementEvents() {
        //Ánh xạ id
        btnExit = findViewById(R.id.btn_changePass_exit);
        btnChange = findViewById(R.id.btn_changePass_change);
        edtPass = findViewById(R.id.edt_changePass_password);
        edtNewPass = findViewById(R.id.edt_changePass_newPass);
        edtConfirm = findViewById(R.id.edt_changePass_confirm);

        //Sự kiện dừng thay đổi mật khẩu
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassActivity.this, LogoutActivity.class);
                Bundle bundle = new Bundle();
                //Gửi theo các thông tin cần cho logout activity
                bundle.putInt("playerId", playerId);
                bundle.putInt("gameId", gameId);
                bundle.putInt("gamePoint", gameScore);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Sự kiện thay đổi mật khẩu
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = edtPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String confirm = edtConfirm.getText().toString();
                int result = 0;
                /**
                 * Kiểm tra nhập đầy đủ các trường
                 * Validate các thông tin nhập vào
                 * Thay đổi mật khẩu và quay trở lại màn hình đăng nhập
                 * */
                if (pass.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(ChangePassActivity.this, R.string.insert_complete_msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (!dao.checkPassword(playerId, pass)) {
                        Toast.makeText(ChangePassActivity.this, R.string.wrong_password_msg, Toast.LENGTH_SHORT).show();
                    } else if (Validation.isPassword(newPass) == false) {
                        Toast.makeText(ChangePassActivity.this, R.string.password_validate_msg, Toast.LENGTH_SHORT).show();
                    } else if (newPass.equals(pass)) {
                        Toast.makeText(ChangePassActivity.this, R.string.password_existed, Toast.LENGTH_SHORT).show();
                    } else if (!newPass.equals(confirm)) {
                        Toast.makeText(ChangePassActivity.this, R.string.confirm_msg, Toast.LENGTH_SHORT).show();
                    } else {
                        result = dao.updatePlayerPassword(playerId, newPass);
                        if (result > 0) {
                            Toast.makeText(ChangePassActivity.this, R.string.changePass_success_msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePassActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ChangePassActivity.this, R.string.failed_msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    //Lấy dữ liệu được gửi từ intent
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        playerId = bundle.getInt("playerId");
        gameId = bundle.getInt("gameId");
        gameScore = bundle.getInt("gameScore");
    }
}