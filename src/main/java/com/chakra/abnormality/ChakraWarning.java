package com.chakra.abnormality;

public class ChakraWarning {
    /**
     * W0001: No designated port
     * 
     * @param arg default port number
     */
    public static void W0001(int arg) {
        System.out.printf("[WARNING 0001]No listening port specified, using default port %d.\n", arg);
    }

    /**
     * W0002: No designated thread limit
     * 
     * @param arg default thread limit
     */
    public static void W0002(int arg) {
        System.out.printf("[WARNING 0002]No thread limit specified, using default limit %d.\n", arg);
    }

    /**
     * W0003: No designated webroot path
     * 
     * @param arg default webroot path
     */
    public static void W0003(String arg) {
        System.out.printf("[WARNING 0003]No webroot specified, using default path \"%s\".\n", arg);
    }
}
