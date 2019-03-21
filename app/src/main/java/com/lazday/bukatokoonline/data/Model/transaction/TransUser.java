package com.lazday.bukatokoonline.data.Model.transaction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransUser {

    @SerializedName("data")
    private List<Data>  data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{

        @SerializedName("transaction_code")
        private String transaction_code;
        @SerializedName("user")
        private String user;
        @SerializedName("grandtotal")
        private String grandtotal;
        @SerializedName("resi_code")
        private String resi_code;
        @SerializedName("destination")
        private String destination;
        @SerializedName("ongkir")
        private String ongkir;
        @SerializedName("date_transaction")
        private String date_transaction;
        @SerializedName("status_transaction")
        private String status_transaction;

        public String getTransaction_code() {
            return transaction_code;
        }

        public void setTransaction_code(String transaction_code) {
            this.transaction_code = transaction_code;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getGrandtotal() {
            return grandtotal;
        }

        public void setGrandtotal(String grandtotal) {
            this.grandtotal = grandtotal;
        }

        public String getResi_code() {
            return resi_code;
        }

        public void setResi_code(String resi_code) {
            this.resi_code = resi_code;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getOngkir() {
            return ongkir;
        }

        public void setOngkir(String ongkir) {
            this.ongkir = ongkir;
        }

        public String getDate_transaction() {
            return date_transaction;
        }

        public void setDate_transaction(String date_transaction) {
            this.date_transaction = date_transaction;
        }

        public String getStatus_transaction() {
            return status_transaction;
        }

        public void setStatus_transaction(String status_transaction) {
            this.status_transaction = status_transaction;
        }
    }
}
