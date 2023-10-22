package com.example.midterm;

/**
 * Lớp này đại diện cho đối tượng người chơi trong ứng dụng. Một người chơi có các thuộc tính như ID, tên đăng nhập, mật khẩu, tổng điểm và số lần chơi.
 */
public class Player {
    private int id;
    private String username;
    private String password;
    private int totalScore;
    private int totalPlay;
    private String phone;

    /**
     * Khởi tạo một đối tượng người chơi với tổng số điểm và số lần chơi ban đầu là 0.
     */
    public Player() {
        this.totalPlay = 0;
        this.totalScore = 0;
    }

    /**
     * Khởi tạo một đối tượng người chơi với thông tin cụ thể.
     *
     * @param id         ID của người chơi.
     * @param username   Tên đăng nhập của người chơi.
     * @param password   Mật khẩu của người chơi.
     * @param totalScore Tổng số điểm của người chơi.
     * @param totalPlay  Số lần chơi của người chơi.
     */
    public Player(int id, String username, String password, int totalScore, int totalPlay, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.totalScore = totalScore;
        this.totalPlay = totalPlay;
        this.phone = phone;
    }

    /**
     * Lấy ID của người chơi.
     *
     * @return ID của người chơi.
     */
    public int getId() {
        return id;
    }

    /**
     * Đặt ID của người chơi.
     *
     * @param id ID mới.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Lấy tên đăng nhập của người chơi.
     *
     * @return Tên đăng nhập của người chơi.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Đặt tên đăng nhập của người chơi.
     *
     * @param username Tên đăng nhập mới.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Lấy mật khẩu của người chơi.
     *
     * @return Mật khẩu của người chơi.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Đặt mật khẩu của người chơi.
     *
     * @param password Mật khẩu mới.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Lấy tổng số điểm của người chơi.
     *
     * @return Tổng số điểm của người chơi.
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Đặt tổng số điểm của người chơi.
     *
     * @param totalScore Tổng số điểm mới.
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Lấy số lần chơi của người chơi.
     *
     * @return Số lần chơi của người chơi.
     */
    public int getTotalPlay() {
        return totalPlay;
    }

    /**
     * Đặt số lần chơi của người chơi.
     *
     * @param totalPlay Số lần chơi mới.
     */
    public void setTotalPlay(int totalPlay) {
        this.totalPlay = totalPlay;
    }

    /**
     * Lấy số điện thoại của người chơi.
     *
     * @return Số điện thoại của người chơi.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Đặt số điện thoại của người chơi.
     *
     * @param phone Số điện thoại mới
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}

