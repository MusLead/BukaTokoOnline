package com.lazday.bukatokoonline.data.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("data")
    public List<Data> products;


    public List<Data> getProducts() {
        return products;
    }


    public class Data {


        public void setId(int id) {
            this.id = id;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @SerializedName("id")
        private int id;
        @SerializedName("product")
        private String product;
        @SerializedName("price")
        private int price;
        @SerializedName("stock")
        private int stock;
        @SerializedName("description")
        private String description;
        @SerializedName("image")
        private String image;

        public int getId() {
            return id;
        }

        public String getProduct() {
            return product;
        }

        public int getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }


    }
}
