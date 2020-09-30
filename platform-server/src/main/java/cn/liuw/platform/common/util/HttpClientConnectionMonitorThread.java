package cn.liuw.platform.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * Description: 使用管理器，管理HTTP连接池 无效链接定期清理功能
 */
public class HttpClientConnectionMonitorThread extends Thread {

    protected final Log log = LogFactory.getLog(getClass());

    private final HttpClientConnectionManager connManager;

    private volatile boolean shutdown;

    public HttpClientConnectionMonitorThread(HttpClientConnectionManager connManager) {
        super();
        this.setName("http-connection-monitor");
        this.setDaemon(true); // 设置守护线程， 当JVM停止时，会自动结束。
        this.connManager = connManager;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                // log.info("开始");
                synchronized (this) {
                    // 每5秒清理一次。
                    wait(10000);
                    // log.info("处理");
                    // Close expired connections
                    connManager.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    connManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    log.info("http-connection-monitor:清理httpclient线程池");
                    // log.info("处理结束");
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

    /**
     * 方法描述: 停止 管理器 清理无效链接  (该方法当前暂时关闭)
     */
    @Deprecated
    public void shutDownMonitor() {
        synchronized (this) {
            shutdown = true;
            notifyAll();
        }
    }

}