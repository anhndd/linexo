package vn.edu.hcmut.linexo.presentation.model;

public class Message {
    String id;
    String name;
    String avatarURL;
    String message;
    Long time;

    public Message(){}

    public Message(String id, String name, String avatarURL, String message, Long time) {
        this.id = id;
        this.name = name;
        this.avatarURL = avatarURL;
        this.message = message;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
