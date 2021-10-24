package log;

public class LogDetail {
    private String ipAddress; // 发出请求的主机ip地址
    private String loginID; // 访问者login ID
    private String hitTime; // 日期和时间
    private String requestMethod; // 请求方法
    private String filePath; // 请求文件的位置和名字
    private String statusCode; // HTTP状态码
    private String fileSize; // 请求文件的大小
    private String referPage; // The web page which referred the hit
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

    // 检查是否有字符串值为空
    private boolean checkEmpty() {
        if (!this.ipAddress.isEmpty()) {
            System.out.println("Log error: ip address is empty!");
            return true;
        }  else if (!this.loginID.isEmpty()) {
            System.out.println("Log error: login ID is empty!");
            return true;
        } else if (!this.hitTime.isEmpty()) {
            System.out.println("Log error: hit time is empty!");
            return true;
        } else if (!this.requestMethod.isEmpty()) {
            System.out.println("Log error: request method is empty!");
            return true;
        } else if (!this.filePath.isEmpty()) {
            System.out.println("Log error: file path is empty!");
            return true;
        } else if (!this.statusCode.isEmpty()) {
            System.out.println("Log error: status code is empty!");
            return true;
        } else if (!this.fileSize.isEmpty()) {
            System.out.println("Log error: file size is empty!");
            return true;
        }
        return false;
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