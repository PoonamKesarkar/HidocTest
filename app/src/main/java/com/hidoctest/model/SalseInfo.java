package com.hidoctest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalseInfo implements Serializable {
    @SerializedName("country")
    String country;
    @SerializedName("saleability")
    String saleability;
    @SerializedName("isEbook")
    String isEbook;
    @SerializedName("listPrice")
    ListPrize listPrice;
    @SerializedName("retailPrice")
    RetailPrice retailPrice;
    @SerializedName("buyLink")
    String buyLink;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public String getIsEbook() {
        return isEbook;
    }

    public void setIsEbook(String isEbook) {
        this.isEbook = isEbook;
    }

    public ListPrize getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrize listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPrice getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public class ListPrize implements Serializable{
        @SerializedName("amount")
        double amount;
        @SerializedName("currencyCode")
        String currencyCode;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }
    }
    public class RetailPrice  implements Serializable {
        @SerializedName("amount")
        String amount;
        @SerializedName("currencyCode")
        String currencyCode;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }
    }
}
