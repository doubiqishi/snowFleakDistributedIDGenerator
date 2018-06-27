package com.cn.hhly.dao;


import java.io.Serializable;
import java.util.Date;
/**
 * 案件 --> T_CASE
 */
public class Case implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    /** 标题 --> TITLE */
    private String title;

    /** 案件编号 --> CODE */
    private String code;

    /** 案件来由 --> REASON */
    private String reason;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}