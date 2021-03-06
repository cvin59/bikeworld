package com.team17.bikeworld.crawl.crawler;

import com.team17.bikeworld.crawl.dict.CateDictObj;
import com.team17.bikeworld.crawl.dict.CateObj;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.CrawlProduct;
import com.team17.bikeworld.entity.CrawlSite;
import com.team17.bikeworld.entity.CrawlStatus;
import com.team17.bikeworld.repositories.*;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YnebikersCrawler extends BaseCrawler implements Runnable {

    public YnebikersCrawler(CrawlRepository crawlRepository, CategoryRepository categoryRepository, CrawlProductImageRepository crawlProductImageRepository, CrawlSiteRepository crawlSiteRepository, BrandRepository brandRepository, CrawlStatus crawlStatus) {
        super(crawlRepository, categoryRepository, crawlProductImageRepository, crawlSiteRepository, brandRepository, "ynebikers", crawlStatus);
    }

    public static final String baseLink = "https://ynebikers.com.my";
    private static boolean lock = false;
    public static Thread instance;
    private static int count;
    private CrawlSite site;

    public static boolean isLock() {
        return lock;
    }

    public static int getCount() {
        return count;
    }

    public NodeList getCates() throws IOException {


        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(baseLink + "/main/productcategory/3-accessories");
            String line = "";

            boolean isStart = false;
            boolean isEnd = false;

            String body = "";
            while (!isEnd && (line = reader.readLine()) != null) {
                if (line.contains("<div class=\"accordian\">")) {
                    isStart = true;
                }
                if (isStart) {
                    body += line.trim();
                    if (line.contains("</div>")) {
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
                    if (!link.contains("#") && cateObj != null) {
                        System.out.println("!!!! " + cateObj.getMeaning() + " - " + link);
                        getItemList(baseLink + link, cateObj);
                    }
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

//    public boolean getItemList2(String url, CateObj cate) {
//
//        Optional<Category> optionalCategory = categoryRepository.findByName(cate.getMeaning());
//        Category category;
//        if (optionalCategory.isPresent()) {
//            category = optionalCategory.get();
//        } else {
//            category = new Category();
//            category.setName(cate.getMeaning());
//            category = categoryRepository.save(category);
//        }
//        BufferedReader reader = null;
//        try {
//            reader = getBufferedReaderForURL(url);
//            String line = "";
//            String body = "";
//            boolean isStart = false;
//            boolean isEnd = false;
//            int divClose = 1;
//            while (!isEnd && (line = reader.readLine()) != null) {
//                if (line.contains("id=\"productcategory_view_product_list\"")) {
//                    isStart = true;
//                }
//                if (isStart) {
//                    body += line.trim();
//                    if (line.contains("</ul>")) {
//                        isEnd = true;
//                    }
//                }
//
//            }
//
////            System.out.println("");
////            System.out.println("doc-");
////            System.out.println(body);
////            System.out.println("-end doc");
////            System.out.println("");
//            boolean next = true;
//            int itemPoint = 0;
//            while (next) {
//                String startStr = "<a href=\"";
//                String endStr = "\">";
//                int startApos = body.indexOf(startStr, itemPoint) + startStr.length();
//                int endApos = body.indexOf(endStr, startApos);
//                if (itemPoint < startApos && startApos < endApos) {
//                    String link = body.substring(startApos, endApos).trim();
////                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
//                    itemPoint = endApos;
//
//                    startStr = "src=\"";
//                    endStr = "\">";
//                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
//                    endApos = body.indexOf(endStr, startApos);
//                    String img = body.substring(startApos, endApos).trim().toLowerCase();
////                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
//                    itemPoint = endApos;
//
//                    startStr = "<a href=\"" + link + "\">";
////                    System.out.println("startStr " + startStr);
//                    endStr = "</a>";
//                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
//                    endApos = body.indexOf(endStr, startApos);
//                    String name = body.substring(startApos, endApos).trim();
////                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
//                    itemPoint = endApos;
//
//                    startStr = "productcategory_view_product_price\">";
//                    endStr = "</div>";
//                    startApos = body.indexOf(startStr, itemPoint) + startStr.length();
//                    endApos = body.indexOf(endStr, startApos);
//                    String price = body.substring(startApos, endApos).trim();
////                    System.out.println(itemPoint + " - " + startApos + " - " + endApos);
//                    itemPoint = endApos;
//
//                    if (CateDictObj.checkName(cate, name.toLowerCase())) {
////                        System.out.println(itemPoint + " - " + startApos + " - " + endApos);
////                        System.out.println("");
////                        System.out.println("name  - " + name);
////                        System.out.println("link  - " + link);
////                        System.out.println("img   - " + img);
////                        System.out.println("price - " + price);
////                        System.out.println("");
//                        crawlRepository.addCrawlProduct(baseLink, category, name, link, price);
//                    }
//                } else {
//                    next = false;
//                }
//            }
//            return true;
//        } catch (IOException ex) {
//            Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return false;
//    }


    public boolean getItemList(String url, CateObj cateObj) {

        Category category = getCate(cateObj);
        BufferedReader reader = null;
        int page = 1;
        try {
            do {
                reader = getBufferedReaderForURL(url + "?page=" + page);
                String line = "";
                String body = "";
                boolean isStart = false;
                boolean isEnd = false;
                int divClose = 1;
                while (!isEnd && (line = reader.readLine()) != null) {
                    if (line.contains("id=\"productcategory_view_product_list\"")) {
                        isStart = true;
                    }
                    if (isStart) {
                        body += line.trim();
                        if (line.contains("</ul>")) {
                            isEnd = true;
                        }
                    }
                }
                int countInPage = 0;
                boolean next = true;
                int itemPoint = 0;
                while (next) {
                    String startStr = "<a href=\"";
                    String endStr = "\">";
                    int startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                    int endApos = body.indexOf(endStr, startApos);
                    if (itemPoint < startApos && startApos < endApos) {
                        String link = body.substring(startApos, endApos).trim();
                        itemPoint = endApos;

                        startStr = "src=\"";
                        endStr = "\">";
                        startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                        endApos = body.indexOf(endStr, startApos);
                        String img = body.substring(startApos, endApos).trim().toLowerCase();
                        itemPoint = endApos;

                        startStr = "<a href=\"" + link + "\">";
                        endStr = "</a>";
                        startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                        endApos = body.indexOf(endStr, startApos);
                        String name = body.substring(startApos, endApos).trim();
                        itemPoint = endApos;

                        startStr = "productcategory_view_product_price\">";
                        endStr = "</div>";
                        startApos = body.indexOf(startStr, itemPoint) + startStr.length();
                        endApos = body.indexOf(endStr, startApos);
                        String price = body.substring(startApos, endApos).trim();

                        itemPoint = endApos;

                        if (CateDictObj.checkName(cateObj, name.toLowerCase())) {
//                            crawlRepository.addCrawlProduct(baseLink, category, name, link, price);
                            CrawlProduct crawlProduct = saveNewCrawlProduct(name, site, link, price, category, img);
                            countInPage++;
                        }
                    } else {
                        next = false;
                    }
                }
                if (countInPage > 0) {
                    page++;
                } else {
                    page = -1;
                }

            } while (page > 0);
            return true;
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
        return false;
    }

    @Override
    public void run() {
        try {

            if (site == null) {
                site = getSite("ynebikers");
            }
            System.out.println(site.getLink());
            System.out.println();
            System.out.println(statPending.getId());
            System.out.println();


//            List<CrawlProductImage> imgBySite = crawlProductImageRepository.findAllBySite(baseLink);
//            crawlProductImageRepository.deleteAll(imgBySite);
//            List<CrawlProduct> allBySite = crawlRepository.findAllBySite(baseLink);
//            crawlRepository.deleteAll(allBySite);
            getCates();
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
