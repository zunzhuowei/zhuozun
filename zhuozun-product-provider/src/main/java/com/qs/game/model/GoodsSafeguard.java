package com.qs.game.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@Accessors(chain = true)//链式的操作方式
@ApiModel(description= "用户实体类，用户基本信息")
public class GoodsSafeguard implements Serializable {
    private Long id;

    private String safeguardName;

    private BigDecimal price;

    private Date gmtCreate;

    private Date gmtUpdate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSafeguardName() {
        return safeguardName;
    }

    public void setSafeguardName(String safeguardName) {
        this.safeguardName = safeguardName == null ? null : safeguardName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}