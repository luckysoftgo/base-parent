package com.application.base.operapi.hbase.test;

import com.application.base.operapi.tool.hbase.annotation.HbaseField;
import com.application.base.operapi.tool.hbase.annotation.HbaseRowKey;
import com.application.base.operapi.tool.hbase.annotation.HbaseTable;

import java.util.List;
import java.util.Map;
import java.util.Set;


//定义表名和列簇名（目前只支持单个表对应一个列簇）
@HbaseTable(table = "hbase-demo",family = "column1")
public class HbaseDemo {
	
    @HbaseRowKey(desc = "rowKey")
    @HbaseField(desc = "userId")
    private Long userId;
    
    @HbaseField(desc = "userName")
    private String userName;
    
    @HbaseField(desc = "age")
    private Integer age;
    
    @HbaseField(desc = "scoresMap")
    private Map<String,Integer> scoresMap;
    
    @HbaseField(desc = "hobiesList")
    private List<String> hobiesList;
    
    @HbaseField(desc = "schooling")
    private Double schooling;
    
    @HbaseField(desc = "years")
    private Set<Integer> years;
    
    @HbaseField(desc = "end")
    private Boolean end;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Map<String, Integer> getScoresMap() {
        return scoresMap;
    }

    public void setScoresMap(Map<String, Integer> scoresMap) {
        this.scoresMap = scoresMap;
    }

    public List<String> getHobiesList() {
        return hobiesList;
    }

    public void setHobiesList(List<String> hobiesList) {
        this.hobiesList = hobiesList;
    }

    public Double getSchooling() {
        return schooling;
    }

    public void setSchooling(Double schooling) {
        this.schooling = schooling;
    }

    public Set<Integer> getYears() {
        return years;
    }

    public void setYears(Set<Integer> years) {
        this.years = years;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", scoresMap=" + scoresMap +
                ", hobiesList=" + hobiesList +
                ", schooling=" + schooling +
                ", years=" + years +
                ", end=" + end +
                '}';
    }
}
