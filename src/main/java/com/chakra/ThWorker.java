package com.chakra;

import java.net.Socket;

import com.chakra.abnormality.ChakraError;

public class ThWorker implements Runnable {
    private Socket socket;

    public ThWorker(Socket sock) {
        socket = sock;
    }

    /**
     * Analyze request and send response
     */
    @Override
    public void run() {
        // TODO:
        try {
            socket.close();

        } catch (Exception e) {
            ChakraError.E0009();
        }
    }

}
