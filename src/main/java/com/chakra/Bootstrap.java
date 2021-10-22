package com.chakra;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

import com.chakra.abnormality.ChakraError;
import com.chakra.abnormality.ChakraWarning;

/**
 * HTTP Server launcher
 * 
 * @apiNote Supported launch parameters: {@code /p:[int]} Port ID;
 *          {@code /th:[int]} Maximum threads; {@code /wr:[path]} Path of
 *          webroot
 * @author Chakra
 */
public class Bootstrap {
    public static Config cfg;
    private static String pwd;

    public static void main(String[] args) {
        InitializeArgs(args);
        Launch(SetupSocket(cfg.Port()));
    }

    /**
     * Launch the server
     * 
     * @param serverSocket opened server socket
     */
    private static void Launch(ServerSocket serverSocket) {
        var ThPool = Executors.newFixedThreadPool(cfg.maxThreads());
        var ClientSocketQueue = new ArrayBlockingQueue<Socket>(cfg.maxThreads(), true);
        while (true) {
            Socket csocket = null;
            try {
                csocket = serverSocket.accept();
            } 
            catch(SocketTimeoutException e){
                break;
            }
            catch (Exception e) {
                ChakraError.E0008();
                break;
            }
            if (ClientSocketQueue.remainingCapacity() == 0) {
                try {
                    var tmpSocket = ClientSocketQueue.poll();
                    //close client socket to terminate worker
                    tmpSocket.close();
                    System.out.printf("[INFO]Connection with %s closed.\n", tmpSocket.getInetAddress().toString());
                } catch (Exception e) {
                    ChakraError.E0009();
                }
            }
            try {
                ClientSocketQueue.add(csocket);
                ThPool.execute(new ThWorker(csocket));
                System.out.printf("[INFO]Connection with %s established.\n", csocket.getInetAddress().toString());
            } catch (Exception e) {
                ChakraError.E0008();
            }
        }
        try {
            serverSocket.close();
            System.out.println("[INFO]Server socket successfully closed.");
        } catch (Exception ce) {
            ChakraError.E0007();
        }
    }

    /**
     * Setup listening port on server
     * 
     * @param port listeing port
     * @return listening socket
     */
    private static ServerSocket SetupSocket(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            // serverSocket.setSoTimeout(1000);
            System.out.println("[INFO]Server socket successfully opened.");
        } catch (Exception e) {
            ChakraError.E0006(port);
            try {
                serverSocket.close();
                System.out.println("[INFO]Server socket successfully closed.");
            } catch (Exception ce) {
                ChakraError.E0007();
            }
            System.exit(0);
        }
        return serverSocket;
    }

    /**
     * Process CLI params
     * 
     * @param args CLI params
     */
    private static void InitializeArgs(String[] args) {
        var port = 8888;
        var maxThreads = 16;
        pwd = System.getProperty("user.dir") + "\\";
        var webroot = pwd + "webroot\\";
        for (var arg : args) {
            // param port id
            if (arg.startsWith("/p:")) {
                if (arg.length() > 3) {
                    var strPort = arg.substring(3);
                    try {
                        var tmpPort = Integer.parseInt(strPort);
                        if (tmpPort <= 0 || tmpPort > 65535) {
                            ChakraError.E0001(tmpPort, port);
                        } else {
                            port = tmpPort;
                        }
                    } catch (NumberFormatException e) {
                        ChakraError.E0001(strPort, port);
                    }
                } else {
                    ChakraWarning.W0001(port);
                }
            }
            // param max threads
            else if (arg.startsWith("/th:")) {
                if (arg.length() > 4) {
                    var strTh = arg.substring(4);
                    try {
                        var tmpTh = Integer.parseInt(strTh);
                        if (tmpTh <= 0) {
                            ChakraError.E0002(tmpTh, maxThreads);
                        } else {
                            maxThreads = tmpTh;
                        }
                    } catch (NumberFormatException e) {
                        ChakraError.E0002(strTh, maxThreads);
                    }
                } else {
                    ChakraWarning.W0002(maxThreads);
                }
            }
            // param webroot path
            else if (arg.startsWith("/wr:")) {
                if (arg.length() > 4) {
                    var strWr = arg.substring(4);
                    var tmpDir = new File(pwd + strWr);
                    try {
                        if (tmpDir.exists() && tmpDir.isDirectory()) {
                            webroot = tmpDir.getAbsolutePath();
                            if (!webroot.endsWith("\\")) {
                                webroot += "\\";
                            }
                        } else {
                            ChakraError.E0004(strWr, webroot);
                        }
                    } catch (SecurityException e) {
                        ChakraError.E0005(strWr, webroot);
                    }
                } else {
                    ChakraWarning.W0003(webroot);
                }
            }
            // param invalid
            else {
                ChakraError.E0003(arg);
            }
        }
        cfg = new Config(port, maxThreads, webroot);
        System.out.printf("[INFO]Maximum thread workers set to %d.\n", cfg.maxThreads());
        System.out.printf("[INFO]webroot is \"%s\".\n", cfg.webroot());
        System.out.printf("[INFO]Listening port is %d.\n", cfg.Port());
    }
}
