package com.example.galleonff.model;

public class Retailer {
    String retailerName, retailerDmsCode, retailerAddress, retailerLat, retailerLon, retailerVisitCount;
    public Retailer( String retailerName, String retailerDmsCode, String retailerAddress, String retailerLat, String retailerLon, String retailerVisitCount){
        this.retailerName = retailerName;
        this.retailerDmsCode = retailerDmsCode;
        this.retailerAddress = retailerAddress;
        this.retailerLat = retailerLat;
        this.retailerLon = retailerLon;
        this.retailerVisitCount = retailerVisitCount;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }

    public String getRetailerDmsCode() {
        return retailerDmsCode;
    }

    public void setRetailerDmsCode(String retailerDmsCode) {
        this.retailerDmsCode = retailerDmsCode;
    }

    public String getRetailerLat() {
        return retailerLat;
    }


    public String getRetailerLon() {
        return retailerLon;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public String getRetailerVisitCount() {
        return retailerVisitCount;
    }

    public void setRetailerLat(String retailerLat) {
        this.retailerLat = retailerLat;
    }

    public void setRetailerLon(String retailerLon) {
        this.retailerLon = retailerLon;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public void setRetailerVisitCount(String retailerVisitCount) {
        this.retailerVisitCount = retailerVisitCount;
    }

}

