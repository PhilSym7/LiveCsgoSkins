package com.boolin.csgoliveskins;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.lang.String;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Search("");
    }

    protected void fucntion(String query){

    }
    Handler handler = new Handler();
    public void Search(final String query) {

        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                //DO NETWORK CALL
                                            //DO NOT RUN WITH THIS LINK: http://steamcommunity.com/market/search?appid=730%s
                                            //Requests values thousands of times causing temporary ban
                String m4a4query = null;
                try {
                    m4a4query = URLEncoder.encode("M4A4%20%7C%20Desolate%20Space%20%28Field-Tested%29", "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url = "http://steamcommunity.com/market/listings/730/" + m4a4query;

                try {
                    Document document = Jsoup.parse(new URL(url), 5000);
                        Elements e = document.body().getElementsByClass("market_listing_row");
                        for (Element x : e) {
                        Element qty = x.getElementsByClass("market_listing_num_listings_qty").first();
                        Element normal_price = x.getElementsByClass("normal_price").first();
                        Element sale_price = x.getElementsByClass("sale_price").first();
                        Element name = x.getElementsByClass("market_listing_item_name").first();
                        Element img = x.getElementsByClass("market_listing_item_img").first();

                        String nameString = name.text();
                        String salePrice = sale_price.text();
                        final String normalPrice = normal_price.text();
                        String qtyString = qty.text();
                        String imgString = img.attr("src");

                        System.out.println(nameString);
                        System.out.println(salePrice);
                        System.out.println(qtyString);
                        System.out.println(normalPrice);
                        System.out.println(imgString);
                        System.out.println();

                        Search("");

                        handler.post(new Runnable(){
                            @Override
                            public void run() {
                                //BACK ON UI THREAD

                                TextView t = (TextView) findViewById(R.id.price_text);
                                t.setText(normalPrice);

                            }});


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}