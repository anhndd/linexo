package vn.edu.hcmut.linexo.presentation.view.room;

public class RoomItem {
    int id;
    String url_host;
    String url_opponent;
    boolean isPrivate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_host() {
        return url_host;
    }

    public void setUrl_host(String url_host) {
        this.url_host = url_host;
    }

    public String getUrl_opponent() {
        return url_opponent;
    }

    public void setUrl_opponent(String url_opponent) {
        this.url_opponent = url_opponent;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public RoomItem(int id, String url_host, String url_opponent, boolean isPrivate) {
        this.id = id;
        this.url_host = url_host;
        this.url_opponent = url_opponent;
        this.isPrivate = isPrivate;
    }
    public RoomItem(int id, String url_host, String url_opponent) {
        this.id = id;
        this.url_host = url_host;
        this.url_opponent = url_opponent;
    }
}
