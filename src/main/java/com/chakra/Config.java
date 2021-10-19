package com.chakra;

/**
 * Server configuration
 */
public record Config(int Port, int maxThreads, String webroot) {

}
