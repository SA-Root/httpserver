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
import response.CGIResponse;
import response.static_response;
import com.chakra.abnormality.ChakraError;
import com.chakra.abnormality.ChakraWarning;

/**
 * ONE-TIME client request handler
 */
public class ThWorker implements Runnable {
    private final Socket socket;

    public ThWorker(Socket sock) {
        socket = sock;
    }

    /**
     * Clean-up method: close client socket
     */
    private void CloseSocket() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                System.out.printf("[INFO]Connection with %s closed.\n", socket.getInetAddress().toString());
            } catch (Exception e) {
                ChakraError.E0009();
            }
        }
    }

    /**
     * Acquire log info
     * 
     * @param request  See {@link request.Request}
     * @param response See {@link response.Response}
     * @return log structure
     */
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
        // hook socket stream
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            ChakraError.E0010();
            CloseSocket();
            return;
        }
        // parse request
        Request request = null;
        try {
            request = Request.parseFromInputStream(inputStream);
        } catch (Exception e) {
            ChakraError.E0011();
            CloseSocket();
            return;
        }
        // generate response
        if (!request.isErr()) {
            Response response = null;
            try {
                if (request.getResource().endsWith("cgi")) {
                    response = CGIResponse.response(request);
                } else {
                    response = static_response.response(request);
                }
            } catch (Exception e) {
                ChakraError.E0012();
                CloseSocket();
                return;
            }
            // write log
            var logInfo = getLogDetail(request, response);
            try {
                Log.writeLog(logInfo, Bootstrap.cfg.webroot());
            } catch (Exception e) {
                ChakraWarning.W0004();
            }
            // write response
            try {
                if (request.getMethod().equals("HEAD")) {
                    outputStream.write(response.head().getBytes());
                } else {
                    outputStream.write(response.compact().getBytes());
                }
            } catch (Exception e) {
                ChakraError.E0013();
                CloseSocket();
                return;
            }
        }
    }

}
