package com.team17.bikeworld.crawl.crawler;

import com.team17.bikeworld.crawl.dict.CateObj;
import com.team17.bikeworld.entity.*;
import com.team17.bikeworld.repositories.*;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

public class BaseCrawler {

    protected final CrawlRepository crawlRepository;
    protected final CategoryRepository categoryRepository;
    protected final CrawlProductImageRepository crawlProductImageRepository;
    protected final CrawlSiteRepository crawlSiteRepository;
    protected final CrawlStatusRepository crawlStatusRepository;
    protected final BrandRepository brandRepository;
    protected CrawlStatus statPending;
    protected CrawlSite site;
    protected Brand brandDefault;
    protected PrintWriter outCrw;
    protected PrintWriter outImg;

    protected final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BaseCrawler.class);

    public BaseCrawler(CrawlRepository crawlRepository, CategoryRepository categoryRepository, CrawlProductImageRepository crawlProductImageRepository, CrawlSiteRepository crawlSiteRepository, CrawlStatusRepository crawlStatusRepository, BrandRepository brandRepository, String siteName) {
        this.crawlRepository = crawlRepository;
        this.categoryRepository = categoryRepository;
        this.crawlProductImageRepository = crawlProductImageRepository;
        this.crawlSiteRepository = crawlSiteRepository;
        this.crawlStatusRepository = crawlStatusRepository;
        this.statPending = crawlStatusRepository.findByName("NEW").get();
        this.brandRepository = brandRepository;
        this.site = getSite(siteName);
        this.brandDefault = brandRepository.findById(1).get();
    }

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
                            }
                            while (('a' <= charRight && charRight <= 'z') || ('A' <= charRight && charRight <= 'Z') || ('0' <= charRight && charRight <= '9'));
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


    protected Category getCate(CateObj cateObj) {
        Optional<Category> optionalCategory = categoryRepository.findByName(cateObj.getMeaning());
        Category category;
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            category = new Category();
            category.setName(cateObj.getMeaning());
            category = categoryRepository.save(category);
        }
        return category;
    }


    protected CrawlSite getSite(String name) {
        Optional<CrawlSite> crawlSite = crawlSiteRepository.findByName(name);
        if (crawlSite.isPresent()) {
            return crawlSite.get();
        } else {
            return null;
        }
    }


    protected CrawlProduct saveNewCrawlProduct(String name, CrawlSite siteId, String link, String price, Category category, String img) {
        String hash = getHash(name, link, price);
        Optional<CrawlProduct> hashedProduct = crawlRepository.findByHash(hash);
        if (hashedProduct.isPresent()) {
<<<<<<< HEAD
            return hashedProduct.get();
        }else{
            CrawlProduct crawlProduct = new CrawlProduct();
            crawlProduct.setStatus(statPending);
            crawlProduct.setHash(hash);
            crawlProduct.setSiteId(siteId);
            crawlProduct.setCategoryId(category);
            crawlProduct.setPrice(price);
            crawlProduct.setUrl(link);
            crawlProduct.setName(name);
            crawlProduct.setDescription("New Product");
            crawlProduct.setBrandId(brandDefault);
            crawlProduct = crawlRepository.save(crawlProduct);

//            crawlRepository.addCrawlProduct(name,link,category,site,price,statPending,"NEW PRODUCT", hash);
//            CrawlProduct crawlProduct =  crawlRepository.findByHash(hash).get();


            CrawlProductImage crawlProductImage = new CrawlProductImage();
            crawlProductImage.setImageLink(img);
            crawlProductImage.setCrawlProductid(crawlProduct);
            crawlProductImageRepository.save(crawlProductImage);
            return crawlProduct;
            return null;
        } else {
            outCrw.println("INSERT INTO `bikeworld`.`crawlproduct` (`name`, `url`, `category_id`, `brand_id`, `site_id`, `price`, `status`, `desc`, `hash`) VALUES ('" + name + "', '" + link + "', '" + category.getId() + "', null, '" + siteId.getId() + "', '" + price + "', '1', null, '" + hash + "');");
            outImg.println("INSERT INTO `bikeworld`.`crawlproductimage` (`imageLink`, `crawlProduct_id`) VALUES ('" + img + "', (SELECT `id` FROM `bikeworld`.`crawlproduct` WHERE `hash` = '" + hash + "'));");
//            System.out.println("INSERT INTO `bikeworld`.`crawlproduct` (`name`, `url`, `category_id`, `brand_id`, `site_id`, `price`, `status`, `desc`, `hash`) VALUES ('" + name + "', '" + link + "', '" + category.getId() + "', null, '" + siteId.getId() + "', '" + price + "', '1', null, '" + hash + "');");
//            System.out.println("INSERT INTO `bikeworld`.`crawlproductimage` (`imageLink`, `crawlProduct_id`) VALUES ('" + img + "', SELECT `id` FROM `bikeworld`.`crawlproduct` WHERE `hash` = '" + hash + "');");
        }
        return null;
    }

//    protected CrawlProduct saveNewCrawlProductOld2(String name, CrawlSite siteId, String link, String price, Category category, String img) {
//
//        String hash = getHash(name, link, price);
//
//        Optional<CrawlProduct> hashedProduct = crawlRepository.findByHash(hash);
//        if (hashedProduct.isPresent()) {
//            return hashedProduct.get();
//        } else {
//            CrawlProduct crawlProduct = new CrawlProduct();
//            crawlProduct.setStatus(statPending);
//            crawlProduct.setHash(hash);
//            crawlProduct.setSiteId(siteId);
//            crawlProduct.setCategoryId(category);
//            crawlProduct.setPrice(price);
//            crawlProduct.setUrl(link);
//            crawlProduct.setName(name);
//            crawlProduct.setDesc("New Product");
//            crawlProduct.setBrandId(brandDefault);
////            crawlProduct = crawlRepository.save(crawlProduct);
//
//
//            System.out.println("INSERT INTO `bikeworld`.`crawlproduct` (`name`, `url`, `category_id`, `brand_id`, `site_id`, `price`, `status`, `desc`, `hash`) VALUES ('" + name + "', '" + link + "', '" + category.getId() + "', null, '" + siteId.getId() + "', '" + price + "', '1', null, '" + hash + "');");
//
//
////            crawlRepository.addCrawlProduct(name,link,category,site,price,statPending,"NEW PRODUCT", hash);
////            CrawlProduct crawlProduct =  crawlRepository.findByHash(hash).get();
//
//
//            CrawlProductImage crawlProductImage = new CrawlProductImage();
//            crawlProductImage.setImageLink(img);
//            crawlProductImage.setCrawlProductid(crawlProduct);
//            crawlProductImageRepository.save(crawlProductImage);
//            return crawlProduct;
//        }
//
//    }

//    protected CrawlProduct saveNewCrawlProductOld(String name, CrawlSite site, String link, String price, Category category, String img) {
//        CrawlProduct crawlProduct = new CrawlProduct();
//        crawlProduct.setSiteId(site);
//        crawlProduct.setCategoryId(category);
//        crawlProduct.setPrice(price);
//        crawlProduct.setUrl(link);
//        crawlProduct.setName(name);
//        crawlProduct.setHash(getHash(name, link, price));
//        crawlProduct.setStatus(statPending);
//
//        crawlProduct = crawlRepository.save(crawlProduct);
//        CrawlProductImage crawlProductImage = new CrawlProductImage();
//        crawlProductImage.setImageLink(img);
//        crawlProductImage.setCrawlProductid(crawlProduct);
//        crawlProductImageRepository.save(crawlProductImage);
//        return crawlProduct;
//    }


    protected String getHash(String name, String link, String price) {
        String fullStr = name + link + price;
//        String encode = bCryptPasswordEncoder.encode(fullStr);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(fullStr.getBytes());
        byte[] digest = md.digest();
        String encode = DatatypeConverter.printHexBinary(digest).toUpperCase();
//        assertThat(myHash.equals(hash)).isTrue();
        return encode;
    }


}
