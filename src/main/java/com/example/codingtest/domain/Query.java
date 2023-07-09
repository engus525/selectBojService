package com.example.codingtest.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Query
{
    private String tier;
    private String tag;
    private List<String> handles = new ArrayList<>();
    public void setHandles(List<String> handles)
    {
        handles.removeIf(String::isBlank);
        this.handles = handles;
    }

    private StringBuilder query = new StringBuilder();

    public StringBuilder getQuery()
    {
        query.setLength(0);
        query.append("*").append(tier);
        query.append("-%23arbitrary_precision");//임의 정밀도/큰 수 연산 제거
        query.append("%26%23").append(tag);//&#
        for (String handle : handles)
            query.append("-s%40").append(handle);//-s@

        return query;
    }

}
