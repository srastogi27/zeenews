package com.zee.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.zee.utils.TestLogger;

public class GoogleAnalyticCalls {

    private String t;
    private String dt;
    private String dl;
    private String tid;
    private String en;


    public GoogleAnalyticCalls(String t, String dt, String dl, String tid, String en){
        this.t = t;
        this.dt = dt;
        this.dl = dl;
        this.tid = tid;
        this.en = en;
    }

    public GoogleAnalyticCalls(String t, String dt, String dl, String en){
        this.t = t;
        this.dt = dt;
        this.dl = dl;
        this.en = en;
    }
    
    public GoogleAnalyticCalls(String t, String dt, String dl){
        this.t = t;
        this.dt = dt;
        this.dl = dl;
    }
    
    public GoogleAnalyticCalls(String dt, String dl){
        this.dt = dt;
        this.dl = dl;
    }

    public String getT() {
        return t;
    }

    public String getDt() {
        return decode(dt);
    }

    public String getDl() {
        return decode(dl);
    }

    public String getTid() {
        return tid;
    }

    public String getEn(){
        return en;
    }
    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            TestLogger.getInstance().error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
