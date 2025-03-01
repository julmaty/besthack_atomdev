package com.example.besthack_atomdev.dto;

import java.util.List;

public class LotListRequest {
    private String searchString; // Строка поиска
    private List<Integer> oilBaseCodes; // Фильтр по кодам нефтебаз
    private List<Integer> fuelTypeCodes; // Фильтр по кодам топлива
    private int page; // Номер страницы (начинается с 0)

    // Геттеры и сеттеры
    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Integer> getOilBaseCodes() {
        return oilBaseCodes;
    }

    public void setOilBaseCodes(List<Integer> oilBaseCodes) {
        this.oilBaseCodes = oilBaseCodes;
    }

    public List<Integer> getFuelTypeCodes() {
        return fuelTypeCodes;
    }

    public void setFuelTypeCodes(List<Integer> fuelTypeCodes) {
        this.fuelTypeCodes = fuelTypeCodes;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
