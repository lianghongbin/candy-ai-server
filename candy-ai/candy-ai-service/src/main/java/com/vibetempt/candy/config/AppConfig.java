package com.vibetempt.candy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用配置属性类
 * 统一管理服务器地址、端口等配置
 * 
 * @author candy
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用版本
     */
    private String version;

    /**
     * 服务器配置
     */
    private Server server = new Server();

    /**
     * 基础URL
     */
    private String baseUrl;

    public static class Server {
        private String protocol;
        private String host;
        private Integer port;
        private String contextPath;

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getContextPath() {
            return contextPath;
        }

        public void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 构建完整的回调URL
     * 
     * @param path 路径
     * @return 完整的URL
     */
    public String buildCallbackUrl(String path) {
        // 避免双斜杠问题
        if (baseUrl.endsWith("/") && path.startsWith("/")) {
            return baseUrl + path.substring(1);
        }
        return baseUrl + path;
    }
} 