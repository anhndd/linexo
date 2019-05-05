package vn.edu.hcmut.linexo.presentation.view.room;

public class RankItem {
    String uid;
    String avatarURL;
    String userName;
    String userScore;
    String numberRank;

    public String getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserScore() {
        return userScore;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getNumberRank() {
        return numberRank;
    }

    public RankItem(String uid, String userName, long userScore, String avatarURL, String numberRank) {
        this.uid = uid;
        this.userName = userName;
        this.userScore = userScore+"$";
        this.avatarURL = avatarURL;
        this.numberRank = numberRank;
    }
}
