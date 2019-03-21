package com.lazday.bukatokoonline.data.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cost {

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

        public class Results{
            @SerializedName("code")
            private String code;
            @SerializedName("name")
            private String name;

            @SerializedName("costs")
            public List<Costs> costs;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<Costs> getCosts() {
                return costs;
            }

            public void setCosts(List<Costs> costs) {
                this.costs = costs;
            }

            public class Costs {

                @SerializedName("service")
                private String service;
                @SerializedName("description")
                private String description;

                @SerializedName("cost")
                private List<Data> cost;

                public String getService() {
                    return service;
                }

                public void setService(String service) {
                    this.service = service;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<Data> getCost() {
                    return cost;
                }

                public void setCost(List<Data> cost) {
                    this.cost = cost;
                }

                public class Data{
                    @SerializedName("value")
                    private int value;
                    @SerializedName("etd")
                    private String etd;

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getEtd() {
                        return etd;
                    }

                    public void setEtd(String etd) {
                        this.etd = etd;
                    }
                }
            }
        }
    }
}