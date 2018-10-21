package com.team17.bikeworld.crawl.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;

public class BaseCrawler {


    protected BufferedReader getBufferedReaderForURL(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    private URLConnection getConnection(String url) throws IOException {
        URL urlObj = new URL(url);
        URLConnection con = urlObj.openConnection();
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        return con;
    }

    protected BufferedReader getBufferedReaderFromUrl(String url) throws IOException {
        URLConnection con = getConnection(url);
        InputStream is = con.getInputStream();
        return new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }

    public String getRawDocument(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderFromUrl(url);
            String line;
            String document = "";
            while ((line = reader.readLine()) != null) {
                document += line.trim();
            }
            return document;
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public String getDocumentBody(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderFromUrl(url);
            String line = "";
            String document = "";
            boolean isStart = false;
            boolean isEnd = false;
            while (!isEnd && (line = reader.readLine()) != null) {
                document += line.trim();
            }
            document = document.replaceAll("^.*<body", "<body");
            int pos = document.lastIndexOf("</body>");
            document = document.substring(0, pos) + "</body>";

            document = document.replaceAll("<script[^>]*>.*?<\\/script>", "");
            document = document.replaceAll("&#8211;", "-");
            document = document.replaceAll("&.{0,7}?;", "");

            StringBuilder builder = new StringBuilder();
            int lastplace = 0;
            for (int i = 0; i < document.length(); i++) {
                if (document.charAt(i) == '=') {
                    char charRight = document.charAt(i + 1);
                    if (('a' <= charRight && charRight <= 'z') || ('A' <= charRight && charRight <= 'Z') || ('0' <= charRight && charRight <= '9')) {
                        boolean found = false;
                        boolean isSpace = false;
                        int k = 2;
                        while (!found) {
                            char charRightNext = document.charAt(i + k);
                            if (charRightNext == ' ') {
                                found = true;
                                isSpace = true;
                            }
                            if (charRightNext == '\'' || charRightNext == '\"') {
                                found = true;
                            }
                            k++;
                        }
                        if (isSpace) {
                            int j = 1;
                            StringBuilder bareStringBuilder = new StringBuilder();
                            do {
                                bareStringBuilder.append(charRight);
                                j++;
                                charRight = document.charAt(i + j);
                            } while (('a' <= charRight && charRight <= 'z') || ('A' <= charRight && charRight <= 'Z') || ('0' <= charRight && charRight <= '9'));
                            builder.append(document.substring(lastplace, i)).append("=\"").append(bareStringBuilder.toString()).append("\" ");
                            lastplace = i + j;
                            i = i + j;
                        }
                    }
                }
            }
            document = builder.append(document.substring(lastplace, document.length())).toString();

            return document;
//            return stAXParserForCategories(document); unfix
        } catch (IOException ex) {
            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    protected XMLEventReader parseStringToXMLEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }
}
