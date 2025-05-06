package com.practice.boardproject.global.page;

import org.springframework.data.domain.Page;

public class Pagenation {

    public static PagingButton getPagingButtonInfo(Page page) {

        int currentPage = page.getNumber() + 1;
        int defaultButtonCount = 10; // 기본 버튼 수

        int startPage = (int) (Math.ceil((double) currentPage / defaultButtonCount) - 1) * defaultButtonCount + 1;
        int endPage = startPage + defaultButtonCount - 1;

        // 실제 총 페이지 수보다 끝 페이지가 클 경우, 끝 페이지를 총 페이지 수로 조정한다.
        if (page.getTotalPages() < endPage) {
            endPage = page.getTotalPages();
        }

        // 페이지가 없을 때도 시작 페이지와 끝 페이지가 1이 되도록한다.
        if (page.getTotalPages() == 0 && endPage == 0) {
            endPage = startPage;
        }

        return new PagingButton(currentPage, startPage, endPage);
    }
}
