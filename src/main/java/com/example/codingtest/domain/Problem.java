package com.example.codingtest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
public class Problem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private String name;
    private String tier;
    private String tag;
    private String url;

    public void createProblem(Integer number, String name, String tier, String tag, String url)
    {
        this.number = number;
        this.name = name;
        this.tier = tier;
        this.tag = tag;
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "문제 번호 = " + number + "\n"
                + "문제 이름 = " + name + "\n"
                + "문제 난이도 = " + tier + "\n"
                + "문제 분류 = " + tag + "\n"
                + "문제 링크 = " + url + "\n========";
    }
}
