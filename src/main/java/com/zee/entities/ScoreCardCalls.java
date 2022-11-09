package com.zee.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.zee.utils.TestLogger;

public class ScoreCardCalls {

    private String c2;
    private String c7;
    private String c8;
    private String cs_c7amp;

    public ScoreCardCalls(String c2, String c7, String c8){
        this.c2 = c2;
        this.c7 = c7;
        this.c8 = c8;
    }
    
    public ScoreCardCalls(String c2, String c7, String c8, String cs_c7amp){
        this.c2 = c2;
        this.c7 = c7;
        this.c8 = c8;
        this.cs_c7amp = cs_c7amp;
    }

    public String getC2() {
        return c2;
    }

    public String getC7() {
        return decode(c7);
    }

    public String getC8() {
        return decode(c8);
    }
    
    public String getC7AMP() {
        return decode(cs_c7amp);
    }
    
    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            TestLogger.getInstance().error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "ScoreCardCalls{" +
                "c2='" + c2 + '\'' +
                ", c7='" + c7 + '\'' +
                ", c8='" + c8 + '\'' +
                '}';
    }
}
