package cn.liuw.platform.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * @author hok
 */
public abstract class XmlUtil {

    protected static final Log log = LogFactory.getLog(cn.liuw.platform.common.util.XmlUtil.class);

    public static Document getDocument(String xmlPath) {
        SAXReader redaer = new SAXReader();
        Document document = null;
        try {
            InputStream in = cn.liuw.platform.common.util.XmlUtil.class.getClassLoader().getResourceAsStream(xmlPath);
            document = redaer.read(in);

        } catch (Exception e) {
            log.error(e.getMessage());
            // throw new UnexpectedException(EnumerableErrorCodes.CONFIGURATION_ERROR, e.getMessage(), e);
        }
        return document;
    }

    public static boolean isGzipInRequest(HttpServletRequest request) {
        String header = request.getHeader("Accept-Encoding");
        return (header != null) && (header.indexOf("gzip") >= 0);
    }

    public static String serialize(Object object) throws IOException {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);

        return xStream.toXML(object);
    }

    //获取序列化xml
    public static String serializeXml(Object object) throws IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += serialize(object);
        return xml;
    }

    public static void writeXmlToResponse(HttpServletResponse response,
                                          String encoding, String xml, boolean addXmlHeader, boolean gzip, boolean noCache,
                                          String contentType) throws IOException {
        response.setContentType(contentType + ";charset=" + encoding);
        if (noCache) {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
        }

        if (addXmlHeader) {
            xml = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>" + xml;
        }

        if (gzip) {
            response.addHeader("Content-Encoding", "gzip");
            GZIPOutputStream out = null;
            InputStream in = null;
            try {
                out = new GZIPOutputStream(response.getOutputStream());
                in = new ByteArrayInputStream(xml.getBytes());
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                if (in != null)
                    in.close();
                if (out != null) {
                    out.finish();
                    out.close();
                }
            }
        } else {
            response.setContentLength(xml.getBytes(encoding).length);
            PrintWriter out = response.getWriter();
            out.print(xml);
            out.flush();
            out.close();
        }
    }

    /**
     * 将Bean转换为XML
     *
     * @param clazzMap 别名-类名映射Map
     * @param bean     要转换为xml的bean对象
     * @return XML字符串
     */
    public static String bean2xml(Map<String, Class> clazzMap, Object bean) {
        XStream xstream = new XStream();
        for (Iterator it = clazzMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Class> m = (Map.Entry<String, Class>) it.next();
            xstream.alias(m.getKey(), m.getValue());
        }
        String xml = xstream.toXML(bean);
        return xml;
    }

    /**
     * 将XML转换为Bean
     *
     * @param clazzMap 别名-类名映射Map
     * @param xml      要转换为bean对象的xml字符串
     * @return Java Bean对象
     */
    public static Object xml2Bean(String key, Class cla, String xml) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cla);
        xstream.alias(key, cla);
        Object bean = xstream.fromXML(xml);
        return bean;
    }

    /**
     * 获取XStream对象
     *
     * @param clazzMap 别名-类名映射Map
     * @return XStream对象
     */
    public static XStream getXStreamObject(Map<String, Class> clazzMap) {
        XStream xstream = new XStream();
        for (Iterator it = clazzMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Class> m = (Map.Entry<String, Class>) it.next();
            xstream.alias(m.getKey(), m.getValue());
        }
        return xstream;
    }

    public static String toXml(Object obj) {
        XStream xstream = new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释
        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性

//        xstream.omitField(SxUser.class, "id");
//        xstream.omitField(SxUser.class, "oldBosCode");
//        xstream.omitField(SxUser.class, "createtime");
//        xstream.omitField(SxUser.class, "updatetime");
//        xstream.omitField(SxUser.class, "userValidity");
//        xstream.omitField(SxUser.class, "ip");
//        xstream.omitField(SxUser.class, "isBlacklist");
        
        xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }

}
