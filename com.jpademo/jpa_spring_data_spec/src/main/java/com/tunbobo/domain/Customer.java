package com.tunbobo.domain;

import javax.persistence.*;


@Entity //声明实体类
@Table(name = "cst_customer") //配置实体类和表的映射关系, name : 表的名称

public class Customer {

    @Id //声明主键的配置
    @GeneratedValue( //配置主键的生成策略
//            strategy = GenerationType.SEQUENCE // -> sequence: 删除了表中row之后，新建的row id不是从1开始，而是从之前开始计数
            strategy = GenerationType.AUTO
    )
    @Column(name = "cust_id") //属性和数据库表字段的映射
    private Long custId;

    @Column(name = "cust_name")
    private String name;

    @Column(name = "cust_phone")
    private String custPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", name='" + name + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }
}

