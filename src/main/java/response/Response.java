package response;

public class Response {
    private int statusCode;//状态码
    private String content;//内容
    private String contentType;//设定内容类型

    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

    public int getContentSize() {
        return content.getBytes().length;
    }

    public String getPhrase(int code) {
        if (code == 200)
        {
            return "ok";
        }
        else if (code == 404)
        {
            return "not found";
        }

        return "unknowen code";
    }

    public String compact() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("HTTP/1.0 ").append(statusCode).append(getPhrase(statusCode)).append("\r\n");
        stringBuilder.append("Content-Type:").append(contentType).append("\r\n");
        stringBuilder.append("Content-Length:").append(getContentSize()).append("\r\n");
        stringBuilder.append("\r\n");

        stringBuilder.append(content);

        return new String(stringBuilder);
    }

    public String head() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("HTTP/1.0 ").append(statusCode).append(getPhrase(statusCode)).append("\r\n");
        stringBuilder.append("Content-Type:text/html\r\n");
        stringBuilder.append("Content-Length:").append(getContentSize()).append("\r\n");
        stringBuilder.append("\r\n");

        return new String(stringBuilder);
    }
}
