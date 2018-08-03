package com.qs.game.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import qs.game.constant.StrConst;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)//链式的操作方式
@ApiModel(description= "用户实体类，用户基本信息")
public class User implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名，不允许重复，要求应用层不允许重复",required = true)
    private String username;

    @ApiModelProperty(value = "用户密码",required = true)
    private String password;

    @ApiModelProperty(value = "用户状态")
    private Byte status;

    @ApiModelProperty(value = "用户注册时的ip地址")
    private String regLoginIp;

    @ApiModelProperty(value = "最后登陆的ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "最后登陆的时间")
    @JsonFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS, timezone = StrConst.GMT8)
    @DateTimeFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS)
    private Date lastLoginTime;

    @ApiModelProperty(value = "用户创建日期，系统时间")
    @JsonFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS, timezone = StrConst.GMT8)
    @DateTimeFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    @ApiModelProperty(value = "最后修改日期，系统时间")
    @JsonFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS, timezone = StrConst.GMT8)
    @DateTimeFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS)
    private Date updateTime;

    @ApiModelProperty(value = "删除状态")
    private Boolean delStatus;

    /*private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRegLoginIp() {
        return regLoginIp;
    }

    public void setRegLoginIp(String regLoginIp) {
        this.regLoginIp = regLoginIp == null ? null : regLoginIp.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Boolean delStatus) {
        this.delStatus = delStatus;
    }*/
}