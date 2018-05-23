package transactionsystem;

public class UserSession {
    protected Integer sid;
    protected String log;
    protected Integer owner;
    protected UserInfo ownerInfo;

    public UserSession(Integer uid, Integer sid, UserInfo userInfo) {
        this.sid = sid;
        owner = uid;
        ownerInfo = userInfo;
        log = "";
    }

    public Integer getId() {
        return sid;
    }

    public Integer getOwner() {
        return owner;
    }

    public String getLog() {
        return log;
    }

    public void openSession() {
    }

    public void log(String l) {
        log += l + "\n";
    }

    public void closeSession() {
    }

    public UserInfo getOwnerInfo() {
        return ownerInfo;
    }
}
