package com.chakra.abnormality;

public class ChakraError {
    /**
     * E0001: Invalid port number
     * 
     * @param inv invalid port number
     * @param def default port number
     */
    public static void E0001(int inv, int def) {
        System.out.printf("[ERROR 0001]Invalid port number '%d', using default port %d.\n", inv, def);
    }

    /**
     * E0001: Invalid port number
     * 
     * @param inv invalid port string
     * @param def default port number
     */
    public static void E0001(String inv, int def) {
        System.out.printf("[ERROR 0001]Invalid port number '%s', using default port %d.\n", inv, def);
    }

    /**
     * E0002: Invalid thread limit
     * 
     * @param inv invalid thread limit
     * @param def default thread limit
     */
    public static void E0002(int inv, int def) {
        System.out.printf("[ERROR 0002]Invalid thread limit '%d', using default limit %d.\n", inv, def);
    }

    /**
     * E0002: Invalid thread limit
     * 
     * @param inv invalid thread limit string
     * @param def default thread limit
     */
    public static void E0002(String inv, int def) {
        System.out.printf("[ERROR 0002]Invalid thread limit '%s', using default limit %d.\n", inv, def);
    }

    /**
     * E0003: Invalid launch parameter
     * 
     * @param arg invalid arg string
     */
    public static void E0003(String arg) {
        System.out.printf("[ERROR 0003]Invalid parameter '%s' is ignored.\n", arg);
    }

    /**
     * E0004: Invalid webroot path
     * 
     * @param inv invalid path
     * @param def default path
     */
    public static void E0004(String inv, String def) {
        System.out.printf("[ERROR 0004]Invalid webroot \"%s\", using default path \"%s\".\n", inv, def);
    }

    /**
     * E0005: Designated path not accessible
     * 
     * @param inv unaccessible path
     * @param def default path
     */
    public static void E0005(String inv, String def) {
        System.out.printf("[ERROR 0005]webroot \"%s\" denied access, using default path \"%s\".\n", inv, def);
    }

    /**
     * E0006: Unable to open server socket
     * 
     * @param port designated port
     */
    public static void E0006(int port) {
        System.out.printf("[ERROR 0006]Failed to open server socket on port %d.\n", port);
    }

    /**
     * E0007: Unable to close server socket
     */
    public static void E0007() {
        System.out.println("[ERROR 0007]Failed to close server socket.");
    }

    /**
     * E0008: Unable to establish connection with client
     */
    public static void E0008() {
        System.out.println("[ERROR 0008]Failed to establish connection with client.");
    }

    /**
     * E0009: Unable to close client socket
     */
    public static void E0009(){
        System.out.println("[ERROR 0009]Failed to close client socket.");
    }
}
