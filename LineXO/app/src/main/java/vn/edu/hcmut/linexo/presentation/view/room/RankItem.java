package vn.edu.hcmut.linexo.presentation.view.room;

public class RankItem {
    String Uid;
    String userName;
    String userScore;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public RankItem(String uid, String userName, long userScore) {
        Uid = uid;
        this.userName = userName;
        if(userScore/1000 >= 10)
            this.userScore = (userScore/1000)+"k$";
        else this.userScore = userScore+"$";
    }
}
