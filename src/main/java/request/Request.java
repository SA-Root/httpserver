package request;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private boolean err = false;
    private String method;
    private String resource;
    private final Map<String, String> headers;
    private String postData;
    private String UserAgent;

    public String getUserAgent() {
        return UserAgent;
    }

    private Request() {
        headers = new HashMap<>();
    }

    public boolean isErr() {
        return err;
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

    public String getPostData() {
        return postData;
    }

    public String get(String key) {
        if (!headers.containsKey(key)) {
            return "";
        }
        return headers.get(key);
    }

    public static Request parseFromInputStream(InputStream inputStream) throws IOException {
        Request request = new Request();

        byte[] buf = new byte[2048];

        int cnt = inputStream.read(buf);

        int i;

        if (cnt > 0) {
            String requestInfo = new String(buf).replaceAll("\0+$", "");
            // System.out.println(requestInfo);

            List<String> lines = requestInfo.lines().collect(Collectors.toList());

            String[] tmp = lines.get(0).split(" ");

            request.method = tmp[0];
            request.resource = tmp[1];
            request.UserAgent = lines.get(6).substring(12);
            for (i = 1; i < lines.size(); i++) {
                if (lines.get(i).length() != 0) {
                    String[] pair = lines.get(i).split(":", 2);
                    request.headers.put(pair[0], pair[1].trim());
                } else
                    break;
            }

            try {
                request.postData = lines.get(i + 1);
            } catch (Exception e) {
                
            }

            return request;
        }

        request.err = true;
        return request;
    }

}
