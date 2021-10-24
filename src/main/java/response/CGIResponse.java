package response;

import com.chakra.Bootstrap;
import request.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class CGIResponse {

    public static Response response(Request request) {
        String exe = Bootstrap.cfg.webroot() + "cgibin" + request.getResource();
        Response response = new Response();

        try {
            exe = exe.split("\\.")[0] + ".py";
            String result = executeCGI(exe, request.getPostData());
            System.out.println("return: " + result);

            // use Arrays.asList(str.split()) to replace
            // str.lines().collect(Collectors.toList()) in jdk11
            List<String> lines = Arrays.asList(result.split("\\r?\\n"));
            String contentType = lines.get(0).split(":")[1].trim();
            String content = String.join("\n", lines.subList(1, lines.size())); // concat content of result'row[1:]

            response.setContentType(contentType);
            response.setStatusCode(200);
            response.setContent(content);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(500);
            response.setContent("Internal Server Error");
        }
        return response;
    }

    private static String executeCGI(String cgi_file, String inputs) {
        System.out.println(cgi_file);
        System.out.println(inputs);
        inputs += "\n";

        try {
            // String cmd = file.replaceAll("/", "\\\\");
            System.out.println("cgi_file: " + cgi_file);
            Process process = Runtime.getRuntime().exec("python "+cgi_file);

            OutputStream outputStream = process.getOutputStream();
            outputStream.write(inputs.getBytes());
            outputStream.flush();
            outputStream.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.println("exit value: " + process.waitFor()); // wait the sub-process to terminate

            StringBuilder result = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                result.append(temp).append("\n");
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "666";
    }
}
