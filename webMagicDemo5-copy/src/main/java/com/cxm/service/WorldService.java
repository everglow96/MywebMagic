package com.cxm.service;

import com.cxm.pojo.Movie;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class WorldService implements PageProcessor {

    Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setCharset("UTF-8")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");



    public void process(Page page) {


        List<String> m_names = page.getHtml().xpath("//*[@id=\"content\"]/div/div[1]/ol/li/div/div[2]/div[1]/a/span[1]/text()").all();
        List<String> comments = page.getHtml().xpath("//*[@id=\"content\"]/div/div[1]/ol/li/div/div[2]/div[2]/p[2]/span/text()").all();
        List<String> scores = page.getHtml().xpath("//*[@id=\"content\"]/div/div[1]/ol/li/div/div[2]/div[2]/div/span[2]/text()").all();

        for (int i = 0; i < m_names.size(); i++) {
            String m_name = m_names.get(i);
            String comment = comments.get(i);
            double score = Double.parseDouble(scores.get(i));

            System.out.println(m_name);
            System.out.println(comment);
            System.out.println(score);

            Movie movie = new Movie();
            movie.setM_name(m_name);
            movie.setComment(comment);
            movie.setScore(score);

//             // 插入
//            MovieDao movieDao = new MovieDao();
//            movieDao.add(movie);
        }


    }

    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args){
        String url = "https://movie.douban.com/top250?start=";
        for (int i = 0; i < 10; i++) {
            Spider.create(new WorldService())
                    .addUrl(url + i*25)
                    .thread(5)
                    .run();
        }
    }

}
