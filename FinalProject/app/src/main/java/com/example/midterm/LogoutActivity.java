package com.example.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Lớp này đại diện cho màn hình đăng xuất và hiển thị thông tin sau khi người dùng kết thúc một vòng chơi.
 * Người dùng có thể đăng xuất, chơi lại hoặc thay đổi mật khẩu sau khi kết thúc vòng chơi.
 */
public class LogoutActivity extends AppCompatActivity {

    private Button btnLogout, btnPlayAgain, btnChangePass;
    private TextView txtPreScore, txtNewScore;
    private int playerId, gameId, newScore, preScore, recordScore;
    private DAO dao;

    /**
     * Được gọi khi hoạt động được tạo. Phương thức này khởi tạo giao diện người dùng, truy xuất dữ liệu từ intent gửi kèm, và hiển thị thông tin sau khi kết thúc vòng chơi.
     *
     * @param savedInstanceState Trạng thái đã lưu trước đó của hoạt động (nếu có).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        dao = new DAO(LogoutActivity.this);
        getData();
        implementEvents();
        setData();
    }

    /**
     * Hiển thị các thông tin của Activity như điểm số mới, điểm số trước đó và thông báo nếu điểm số mới là kỷ lục.
     */
    private void setData() {
        int preGameId = gameId - 1;
        txtNewScore.setText("New score: " + newScore);
        preScore = dao.getScore(preGameId, playerId);
        txtPreScore.setText("Previous score: " + preScore);
        recordScore = dao.getRecordScore(playerId);

        if (newScore == recordScore) {
            Toast.makeText(LogoutActivity.this, R.string.new_record_msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Lấy dữ liệu được truyền vào khi khởi động Activity, bao gồm thông tin người chơi, vòng chơi, và điểm số mới.
     */
    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            playerId = bundle.getInt("playerId");
            gameId = bundle.getInt("gameId");
            newScore = bundle.getInt("gamePoint");
        }
    }

    /**
     * Khởi tạo các phần tử giao diện và gán chúng cho các biến trong lớp. Đồng thời, thiết lập xử lý sự kiện cho các nút.
     */
    private void implementEvents() {
        btnLogout = findViewById(R.id.btn_logout_logout);
        btnPlayAgain = findViewById(R.id.btn_logout_playAgain);
        btnChangePass = findViewById(R.id.btn_logout_changePass);
        txtPreScore = findViewById(R.id.txtPreScore);
        txtNewScore = findViewById(R.id.txtNewScore);

        // Xử lý sự kiện đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(LogoutActivity.this, R.string.logout_msg, Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện chơi lại
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoutActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                Player player = dao.getPlayerById(playerId);
                bundle.putInt("playerId", player.getId());
                bundle.putString("username", player.getUsername());
                bundle.putInt("totalScore", player.getTotalScore());
                bundle.putInt("totalPlay", player.getTotalPlay());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện thay đổi mật khẩu
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoutActivity.this, ChangePassActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("playerId", playerId);
                bundle.putInt("gameId", gameId);
                bundle.putInt("gameScore", newScore);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
