package com.example.midterm;

/**
 * Lớp này đại diện cho thông tin điểm số của một người chơi trong một vòng chơi cụ thể.
 * Một đối tượng Score chứa ID của điểm số, ID của người chơi, điểm số trong vòng chơi và ID của vòng chơi.
 */
public class Score {
    private int id;
    private int playerId;
    private int gameScore;
    private int gameId;

    /**
     * Khởi tạo một đối tượng Score mới mà không có dữ liệu ban đầu.
     */
    public Score() {
    }

    /**
     * Khởi tạo một đối tượng Score mới với thông tin cơ bản.
     *
     * @param playerId  ID của người chơi liên quan đến điểm số.
     * @param gameScore Điểm số trong vòng chơi.
     * @param gameId    ID của vòng chơi.
     */
    public Score(int playerId, int gameScore, int gameId) {
        this.playerId = playerId;
        this.gameScore = gameScore;
        this.gameId = gameId;
    }

    /**
     * Khởi tạo một đối tượng Score với thông tin đầy đủ, bao gồm cả ID của điểm số.
     *
     * @param id        ID của điểm số.
     * @param playerId  ID của người chơi liên quan đến điểm số.
     * @param gameScore Điểm số trong vòng chơi.
     * @param gameId    ID của vòng chơi.
     */
    public Score(int id, int playerId, int gameScore, int gameId) {
        this.id = id;
        this.playerId = playerId;
        this.gameScore = gameScore;
        this.gameId = gameId;
    }

    /**
     * Lấy ID của điểm số.
     *
     * @return ID của điểm số.
     */
    public int getId() {
        return id;
    }

    /**
     * Đặt ID của điểm số.
     *
     * @param id ID mới.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Lấy ID của người chơi liên quan đến điểm số.
     *
     * @return ID của người chơi.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Đặt ID của người chơi liên quan đến điểm số.
     *
     * @param playerId ID mới của người chơi.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Lấy điểm số trong vòng chơi.
     *
     * @return Điểm số trong vòng chơi.
     */
    public int getGameScore() {
        return gameScore;
    }

    /**
     * Đặt điểm số trong vòng chơi.
     *
     * @param gameScore Điểm số mới trong vòng chơi.
     */
    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    /**
     * Lấy ID của vòng chơi.
     *
     * @return ID của vòng chơi.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Đặt ID của vòng chơi.
     *
     * @param gameId ID mới của vòng chơi.
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}

