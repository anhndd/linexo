package vn.edu.hcmut.linexo.presentation.model;

public class Message {
    int type;
    String id;
    String name;
    String avatarURL;
    String message;

    public Message(int type, String id, String name, String avatarURL, String message) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.avatarURL = avatarURL;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
