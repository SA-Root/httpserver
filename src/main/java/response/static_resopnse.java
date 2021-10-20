package response;

import request.Request;
// import builtin.SpecialPages;
import java.io.*;

import java.io.File;
import java.io.FileInputStream;

public class static_resopnse {
    public static Response response(Request request) throws IOException {
        String resource_name=request.getResource();
        Response response=new Response();
        //下面开始对response进行填充
        if(resource_name.equals("/"))//默认页面传入参数0即index.html
        {
            // response.setContent(SpecialPages.getPage(0));
            response.setStatusCode(200);
        }
        else {
            String address="webroot/"+resource_name;//根据资源名进行搜索
            File file=new File(address);
            if(file.exists())//文件存在则使用file读取并设定状态码
            {
                String encoding = "UTF-8";
                Long filelength = file.length();
                byte[] filecontent = new byte[filelength.intValue()];
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
               response.setContent(new String(filecontent, encoding)) ;
                response.setStatusCode(200);
            }
            else//不存在则返回404页面
            {
                String encoding = "UTF-8";
                File file1=new File("webroot/builtin/404.html");
                Long filelength = file1.length();
                byte[] filecontent = new byte[filelength.intValue()];
                FileInputStream in = new FileInputStream(file1);
                in.read(filecontent);
                in.close();
                response.setContent(new String(filecontent, encoding)) ;
                response.setStatusCode(404);
            }
        }
        response.setContentType("text/html");
        return response;
    }

}
