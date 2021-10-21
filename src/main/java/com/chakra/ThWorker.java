package com.chakra;

import java.net.Socket;
import java.time.LocalDateTime;

import log.LogDetail;
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
        var inputStream = socket.getInputStream();
        var outputStream = socket.getOutputStream();
        try {
            socket.close();

        } catch (Exception e) {
            ChakraError.E0009();
        }
    }

}
