package log;

public class LogDetail {
    private String ipAddress; // 发出请求的主机ip地址
    private String loginID; // 访问者login ID
    private String hitTime; // 日期和时间
    private String requestMethod; // 请求方法
    private String filePath; // 请求文件的位置和名字
    private String statusCode; // HTTP状态码
    private String fileSize; // 请求文件的大小
    private String UA;

    // 获取并初始化成员变量

    public void setUA(String uA) {
        UA = uA;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public void setHitTime(String hitTime) {
        this.hitTime = hitTime;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    // 213.60.233.243 - - [25/May/2018:00:17:09 +1200] "GET /internet/index.html
    // HTTP/1.1" 200 6792 "http://www.mediacollege.com/video/streaming/http.html"
    // "Mozilla/5.0 (X11; U; Linux i686; es-ES; rv:1.6) Gecko/20040413 Debian/1.6-5"
    @Override
    public String toString() {
        String logString = "";
        // if (!checkEmpty())
        {
            logString += this.ipAddress + " - - ";
            logString += "[" + this.hitTime + "] ";
            logString += "\"" + this.requestMethod;
            logString += " " + this.filePath + "\"";
            logString += " " + this.loginID;
            logString += " " + this.statusCode;
            logString += " " + this.fileSize;
            logString += " \"" + this.filePath + "\"";
            logString += " " + this.UA + "\n";

        }
        return logString;
    }
}