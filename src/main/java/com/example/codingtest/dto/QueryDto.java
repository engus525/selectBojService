package com.example.codingtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class QueryDto
{
    private List<String> handles = new ArrayList<>();
    private String difficulty;
}
