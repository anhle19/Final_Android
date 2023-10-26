package com.example.midterm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Lớp này cho phép thao tác với cơ sở dữ liệu
 * Thao tác CRUD với các bảng trong cơ sở dữ liệu
 * */
public class DAO {

    //Tạo đối tượng SQLiteDatabase để thao tác trên CSDL
    SQLiteDatabase db;
    //Tạo đối tượng DBHelper
    DBHelper dbHelper;
    //Tạo đối tượng kiểu context
    Context context;
    public static final String tag = "DAO";

    public DAO(Context context) {
        //Khởi tạo đối tượng database
        dbHelper = new DBHelper(context);
        //Trả về đối tượng cho phép write db
        db = dbHelper.getWritableDatabase();
    }

    //Thêm một player mới
    public boolean insertPlayer(Player player) {
        try {
            String sql = "INSERT INTO " + DBHelper.TABLE_PLAYERS + "(username,password,totalScore,totalPlay,phone) " +
                    "VALUES ('" + player.getUsername() + "','" + player.getPassword() + "','" + player.getTotalScore() + "'," +
                    "'" + player.getTotalPlay() + "'," + "'" + player.getPhone() + "');";
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.d(tag, "Insert failed");
            return false;
        }
    }

    //Kiểm tra username có tồn tại hay không
    public boolean checkUsername(String username) {
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    //Kiểm tra password có tồn tại từ trước hay không
    public boolean checkPassword(int id, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE id=?", new String[]{id + ""});
        if (cursor != null && cursor.moveToFirst()) {
            String dbPass = cursor.getString(2);
            //So sánh mật khẩu đã được mã hóa
            if (BCrypt.checkpw(password, dbPass)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean checkResetPassword(String username, String phone) {
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE username=? AND phone=?", new String[]{username + "", phone + ""});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    //Kiểm tra số điện thoại có tồn tại trước đó hay không
    public boolean checkPhone(String phone) {
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE phone=?", new String[]{phone});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    //Kiểm tra đăng nhập
    public boolean checkPlayer(String username, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE username=?", new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            String dbPass = cursor.getString(2);
            //So sánh mật khẩu đã được mã hóa
            if (BCrypt.checkpw(password, dbPass)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Lấy thông tin player qua username
     * Trả về một đối tượng player
     */
    public Player getPlayerByUsername(String username) {
        Player player = new Player();
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE username=?", new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int totalScore = cursor.getInt(3);
            int totalPlay = cursor.getInt(4);
            player.setId(id);
            player.setUsername(username);
            player.setTotalScore(totalScore);
            player.setTotalPlay(totalPlay);
        }
        return player;
    }

    //Lấy ra player thông qua id
    public Player getPlayerById(int id) {
        Player player = new Player();
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE id=?", new String[]{id + ""});
        if (cursor != null && cursor.moveToFirst()) {
            String username = cursor.getString(1);
            int totalScore = cursor.getInt(3);
            int totalPlay = cursor.getInt(4);
            player.setId(id);
            player.setUsername(username);
            player.setTotalScore(totalScore);
            player.setTotalPlay(totalPlay);
        }
        return player;
    }

    //Lấy ra id của người chơi
    public int getPlayerId(String username) {
        int id = -1;
        Cursor cursor = db.rawQuery("SELECT * FROM Players WHERE username=?", new String[]{username});
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    //Cập nhật tổng số màn chơi và tổng số điểm của player
    public int updatePlayer(int id, int totalScore, int totalPlay) {
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_TOTAL_SCORE, totalScore);
            values.put(DBHelper.COLUMN_TOTAL_PLAY, totalPlay);
            return db.update(DBHelper.TABLE_PLAYERS, values, "id=?", new String[]{"" + id});
        } catch (Exception e) {
            Log.d(tag, "Update failed");
            return -1;
        }
    }

    //Cập nhật mật khẩu mới
    public int updatePlayerPassword(int id, String password) {
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_PASSWORD, BCrypt.hashpw(password, BCrypt.gensalt(12)));
            return db.update(DBHelper.TABLE_PLAYERS, values, "id=?", new String[]{"" + id});
        } catch (Exception e) {
            Log.d(tag, "Update failed");
            return -1;
        }
    }



    //Thêm một thông tin màn chơi mới
    public int insertScore(Score score) {
        try {
            String sql = "INSERT INTO " + DBHelper.TABLE_SCORES + "(playerId,gameScore,gameId) " +
                    "VALUES ('" + score.getPlayerId() + "','" + score.getGameScore() + "','" + score.getGameId() + "');";
            db.execSQL(sql);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    //Lấy điểm số của màn chơi thông qua gameId và playerId
    public int getScore(int gameId, int playerId) {
        int gameScore = 0;
        Cursor cursor = db.rawQuery("SELECT gameScore FROM Scores WHERE playerId=? AND gameId=?", new String[]{playerId + "", gameId + ""});
        if (cursor != null && cursor.moveToFirst()) {
            gameScore = cursor.getInt(0);
        }
        return gameScore;
    }

    //Lấy điểm số cao nhất trong các màng chơi
    public int getRecordScore(int playerId) {
        int recordScore = 0;
        Cursor cursor = db.rawQuery("SELECT MAX(gameScore) FROM Scores WHERE playerId=?", new String[]{playerId + ""});
        if (cursor != null && cursor.moveToFirst()) {
            recordScore = cursor.getInt(0);
        }
        return recordScore;
    }
}
