package vn.edu.hcmut.linexo.presentation.model;

public class User {
    private String uid;
    private String email;
    private String avatar;
    private String name;
    private Integer score;
    private long time;

    public User() {
    }

    public User(String uid, String email, String avatar, String name, Integer score, long time) {
        this.uid = uid;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public User(String uid, String email, String avatar, String name, Integer score) {
        this.uid = uid;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.score = score;
        this.time = System.currentTimeMillis();
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
