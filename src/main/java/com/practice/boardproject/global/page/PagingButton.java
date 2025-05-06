package com.practice.boardproject.global.page;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingButton {

    private int currentPage;
    private int startPage;
    private int endPage;

}
