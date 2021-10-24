package response;

import request.Request;
import java.io.*;

import java.io.File;
import java.io.FileInputStream;

public class static_response {
    public static Response response(Request request) throws IOException {
        String resource_name = request.getResource();
        Response response = new Response();
        if (resource_name.equals("/")) {
            String encoding = "UTF-8";
            File file1 = new File("webroot/index.html");
            Long filelength = file1.length();
            byte[] filecontent = new byte[filelength.intValue()];
            FileInputStream in = new FileInputStream(file1);
            in.read(filecontent);
            in.close();
            response.setContent(new String(filecontent, encoding));
            response.setStatusCode(200);
        } else {
            String address = "webroot/" + resource_name;
            File file = new File(address);
            if (file.exists()) {
                String encoding = "UTF-8";
                Long filelength = file.length();
                byte[] filecontent = new byte[filelength.intValue()];
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
                response.setContent(new String(filecontent, encoding));
                response.setStatusCode(200);
            } else {
                String encoding = "UTF-8";
                File file1 = new File("webroot/404.html");
                Long filelength = file1.length();
                byte[] filecontent = new byte[filelength.intValue()];
                FileInputStream in = new FileInputStream(file1);
                in.read(filecontent);
                in.close();
                response.setContent(new String(filecontent, encoding));
                response.setStatusCode(404);
            }
        }
        response.setContentType("text/html");
        return response;
    }

}
