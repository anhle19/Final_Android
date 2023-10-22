package com.example.midterm;

/**
 * Lớp này cung cấp các phương thức để kiểm tra tính hợp lệ của tên đăng nhập và mật khẩu theo các quy tắc nhất định.
 */
public class Validation {
    /**
     * Mẫu chuỗi cho tên đăng nhập: Tên đăng nhập chỉ chứa ký tự thường, chữ số, dấu gạch dưới hoặc dấu gạch ngang,
     * và có độ dài từ 6 đến 12 ký tự.
     */
    public static final String usernamePattern = "[a-z0-9_-]{6,12}$";

    /**
     * Mẫu chuỗi cho mật khẩu: Mật khẩu chỉ chứa ký tự thường, chữ số, dấu gạch dưới hoặc dấu gạch ngang,
     * và có độ dài từ 8 đến 20 ký tự.
     */
    public static final String passwordPattern = "[a-z0-9_-]{8,20}$";

    /**
     * Mẫu chuỗi cho số điện thoại: bắt đầu bằng 0 các ký tự còn lại là số từ 0-9
     * Có độ dài là 9 số
     * */
    public static final String phonePattern = "[0-9]{10}$";

    /**
     * Kiểm tra tính hợp lệ của tên đăng nhập.
     *
     * @param username Tên đăng nhập cần kiểm tra.
     * @return true nếu tên đăng nhập hợp lệ, ngược lại false.
     */
    public static boolean isUsername(String username) {
        return username.matches(usernamePattern);
    }

    /**
     * Kiểm tra tính hợp lệ của mật khẩu.
     *
     * @param password Mật khẩu cần kiểm tra.
     * @return true nếu mật khẩu hợp lệ, ngược lại false.
     */
    public static boolean isPassword(String password) {
        return password.matches(passwordPattern);
    }

    /**
     * Kiểm tra tính hợp lệ của số điện thoại.
     *
     * @param phone Số điện thoại cần kiểm tra.
     * @return true nếu Số điện thoại, ngược lại false.
     */
    public static boolean isPhone(String phone) {
        return phone.matches(phonePattern);
    }
}

