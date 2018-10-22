package com.team17.bikeworld.crawl.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathExpressionException;

import com.team17.bikeworld.crawl.dict.CateDictObj;
import com.team17.bikeworld.crawl.dict.CateObj;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.repositories.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;

public class RevzillaCrawler extends BaseCrawler implements Runnable {

    public RevzillaCrawler(CrawlRepository crawlRepository, CategoryRepository categoryRepository, CrawlProductImageRepository crawlProductImageRepository) {
        super(crawlRepository, categoryRepository, crawlProductImageRepository);
    }

    public static final String baseLink = "https://www.revzilla.com";

    private static boolean lock = false;
    public static Thread instance;

    public NodeList getCates() throws IOException, XPathExpressionException {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(baseLink + "/motorcycle-parts");
            String line = "";

            boolean isStart = false;
            boolean isEnd = false;

            String body = "";
            while (!isEnd && (line = reader.readLine()) != null) {
                if (line.contains("\"category-links-content-block__list\"")) {
                    isStart = true;
                }
                if (isStart) {
                    body += line.trim();
                    if (line.contains("</ul>")) {
                        isEnd = true;
                    }
                }

            }
            body = body.replaceAll("&.{0,7}?;", "");
            boolean next = true;
            int itemPoint = 0;
            while (next) {
                String startStr = "href=\"";
                String endStr = "\"";
                int startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                int endApos = body.indexOf(endStr, startApos);
                if (itemPoint < startApos && startApos < endApos) {
                    String link = body.substring(startApos, endApos).trim();

                    itemPoint = endApos;

                    startStr = ">";
                    endStr = "</a>";
                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    endApos = body.indexOf(endStr, startApos);
                    String cateName = body.substring(startApos, endApos).trim().toLowerCase();

                    itemPoint = endApos;

                    CateObj cateObj = CateDictObj.checkCate(cateName);

                    getCates2s(getUrl(link), cateObj);

                } else {
                    next = false;
                }
            }
            return null;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private String getUrl(String link) {
        if (link.charAt(0) == '/') {
            return baseLink + link;
        } else {
            return baseLink + "/" + link;
        }
    }

    public NodeList getCates2s(String url, CateObj cateObj) throws IOException, XPathExpressionException {

        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";

            boolean isStart = false;
            boolean isEnd = false;

            String body = "";
            while (!isEnd && (line = reader.readLine()) != null) {
                if (line.contains("<body")) {
                    isStart = true;
                }
                if (isStart) {
                    body += line.trim();
                    if (line.contains("</body>")) {
                        isEnd = true;
                    }
                }
            }
            body = body.replaceAll("&.{0,7}?;", "");
            if (body.contains("Shop By Category")) {
                boolean next = true;
                int itemPoint = body.indexOf("Shop By Category");
                int endPoint = body.indexOf("</ul>", itemPoint);
                while (next) {
                    String startStr = "href=\"";
                    String endStr = "\"";
                    int startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    int endApos = body.indexOf(endStr, startApos);
                    if (itemPoint < startApos && startApos < endApos && startApos < endPoint) {
                        String link = body.substring(startApos, endApos).trim();
                        itemPoint = endApos;

                        startStr = ">";
                        endStr = "</a>";
                        startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                        endApos = body.indexOf(endStr, startApos);
                        String cateName = body.substring(startApos, endApos).trim().toLowerCase();
                        itemPoint = endApos;
                        if (cateObj != null) {
                            getItemList(getUrl(link), cateObj);
                        } else {
                            cateObj = CateDictObj.checkCate(cateName);
                            if (cateObj != null) {
                                getItemList(getUrl(link), cateObj);
                            }
                        }
                    } else {
                        next = false;
                    }
                }
            } else {
                if (cateObj != null) {
                    getItemList(url, cateObj);
                }
            }
        } catch (Exception ex) {

            System.out.println("e cate2");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }

    public boolean getItemList(String url, CateObj cate) {


        Category cateEntity = categoryRepository.getByName(cate.getMeaning());

        BufferedReader reader = null;
        try {

            reader = getBufferedReaderForURL(url);
            String line = "";
            String body = "";
            boolean isStart = false;
            boolean isEnd = false;
            int divClose = 1;
            while (!isEnd && (line = reader.readLine()) != null) {
                if (line.contains("product-index-results__product-tile-wrapper")) {
                    isStart = true;
                }
                if (isStart) {
                    body += line.trim();
                    if (line.contains("product-index-results__pagination")) {
                        isEnd = true;
                    }
                }

            }

//            System.out.println("");
//            System.out.println("doc-");
//            System.out.println(body);
//            System.out.println("-end doc");
//            System.out.println("");
            boolean next = true;
            int itemPoint = 0;
            while (next) {
                String startStr = "product-tile-wrapper";
                String endStr;
                int startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                int endApos;
                if (itemPoint < startApos) {
                    itemPoint = startApos;

                    startStr = "href=\"";
                    endStr = "\">";
                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    endApos = body.indexOf(endStr, startApos);
                    String link = body.substring(startApos, endApos).trim().toLowerCase();
//                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
                    itemPoint = endApos;

                    startStr = "itemprop=\"url\" title=\"";
                    endStr = "\">";
                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    endApos = body.indexOf(endStr, startApos);
                    String name = body.substring(startApos, endApos).trim().toLowerCase();
//                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
                    itemPoint = endApos;

                    startStr = "itemprop=\"brand\" content=\"";
//                    System.out.println("startStr " + startStr);
                    endStr = "\">";
                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    endApos = body.indexOf(endStr, startApos);
                    String brand = body.substring(startApos, endApos).trim();
//                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
                    itemPoint = endApos;

                    startStr = "itemprop=\"image\" content=\"";
                    endStr = "\">";
                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    endApos = body.indexOf(endStr, startApos);
                    String img = body.substring(startApos, endApos).trim();
//                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
                    itemPoint = endApos;

                    startStr = "itemprop=\"price\" content=\"";
                    endStr = "\"";
                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    endApos = body.indexOf(endStr, startApos);
                    String price = body.substring(startApos, endApos).trim();
//                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
                    itemPoint = endApos;

                    startStr = "itemprop=\"priceCurrency\" content=\"";
                    endStr = "\"";
                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    endApos = body.indexOf(endStr, startApos);
                    String priceUnit = body.substring(startApos, endApos).trim();
//                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
                    itemPoint = endApos;

//                    String desc = getItemDetail(baseLink + link);
//                    System.out.println("");
//                    System.out.println("name  - " + name);
//                    System.out.println("link  - " + link);
//                    System.out.println("img   - " + img);
//                    System.out.println("price - " + priceUnit + price);
//                    System.out.println("");
//                    productDao.replaceProduct(connection, baseLink, cate.getMeaning(), name, link, img, price);

                    crawlRepository.addCrawlProduct(baseLink, cateEntity, name, link, price);
                } else {
                    next = false;
                }
            }
            return true;
        } catch (Exception ex) {

            System.out.println("e list");
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
        return false;
    }




    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}
