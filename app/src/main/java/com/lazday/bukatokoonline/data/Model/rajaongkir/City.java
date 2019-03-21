package com.lazday.bukatokoonline.data.Model.rajaongkir;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {

    @SerializedName("rajaongkir")
    private RajaOngkir rajaOngkir;

    public RajaOngkir getRajaOngkir() {
        return rajaOngkir;
    }

    public void setRajaOngkir(RajaOngkir rajaOngkir) {
        this.rajaOngkir = rajaOngkir;
    }

    public class RajaOngkir{
        @SerializedName("results")
        private List<Results> results;

        public List<Results> getResults() {
            return results;
        }

        public void setResults(List<Results> results) {
            this.results = results;
        }

        public class Results {
            @SerializedName("city_id")
            private String city_id;
            @SerializedName("city_name")
            private String city_name;

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }
        }
    }
}
