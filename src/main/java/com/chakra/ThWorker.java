package com.chakra;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import log.LogDetail;
import log.Log;
import request.Request;
import response.Response;
import com.chakra.abnormality.ChakraError;

public class ThWorker implements Runnable {
    private final Socket socket;

    public ThWorker(Socket sock) {
        socket = sock;
    }

    private LogDetail getLogDetail(Request request, Response response) {
        LogDetail LogDetail = new LogDetail();
        LogDetail.setIpAddress(socket.getInetAddress().toString().substring(1));
        LogDetail.setIdentity("");
        LogDetail.setLoginID("");
        LogDetail.setHitTime(LocalDateTime.now().toString());
        LogDetail.setRequestMethod(request.getMethod());
        LogDetail.setFilePath(request.getResource());
        LogDetail.setStatusCode(Integer.toString(response.getStatusCode()));
        LogDetail.setFileSize(Integer.toString(response.getContentSize()));
        LogDetail.setReferPage(request.get("Referer"));

        return LogDetail;
    }

    /**
     * Analyze request and send response
     */
    @Override
    public void run() {
        InputStream inputStream;
        OutputStream outputStream;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {

        }
        Request request;
        try {
            request = Request.parseFromInputStream(inputStream);
        } catch (Exception e) {

        }
        if (!request.isErr()) {
            Response response;
            if (request.getResource().endsWith("cgi")) {
                response = CGIResponse.response(request);
            } else {
                response = StaticResponse.response(request);
            }

            var logInfo = getLogDetail(request, response);
            Log.writeLog(logInfo, Bootstrap.cfg.webroot());

            try {
                if (request.getMethod().equals("HEAD")) {
                    outputStream.write(response.head().getBytes());
                } else {
                    outputStream.write(response.compact().getBytes());
                }
            } catch (Exception e) {

            }
        }
        try {
            socket.close();
        } catch (Exception e) {
            ChakraError.E0009();
        }
    }

}
