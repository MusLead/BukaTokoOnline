package com.lazday.bukatokoonline.data.Retrofit;


import com.lazday.bukatokoonline.data.Model.rajaongkir.City;
import com.lazday.bukatokoonline.data.Model.Cost;
import com.lazday.bukatokoonline.data.Model.Detail;
import com.lazday.bukatokoonline.data.Model.Product;
import com.lazday.bukatokoonline.data.Model.transaction.TransPost;
import com.lazday.bukatokoonline.data.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//Route::get('/users','Api\UserController@users');
//Route::get('user/{id}','Api\UserController@user');
//Route::get('logout/{id}','Api\UserController@logout');
//
//Route::post('auth/login','Api\UserController@login');
//Route::post('auth/register','Api\UserController@register');
//Route::post('auth/update/{iduser}','Api\UserController@updateUser');
//
//Route::get('/products','Api\ProductsController@products');
//Route::get('/product/{id}','Api\ProductsController@product');
//
//Route::post('/transaction','Api\TransactionController@store');
//Route::get('/transaction-user/{userId}/{status?}','Api\TransactionController@user');
//Route::get('/transaction/{code}','Api\TransactionController@byCode');
//
//Route::post('/upload/{code}','Api\TransactionController@upload');

    @GET("products")
    Call<Product> getProducts();

    @GET("product/{id}")
    Call<Detail> getDetail(@Path("id") int id);

    @FormUrlEncoded
    @POST("auth/login")
    Call<User> authLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/register")
    Call<User> authRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/update/{id}")
    Call<User> updateUser(
            @Path("id") String id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    //TODO:KNP KOK SKRG PAKAI BODY ?
    //jawaban: karena kita inginjawaban loop
    //tanya: tapi kenapa kok coat yg looping tidak pakai body ?
    @POST("transaction")
    Call<TransPost> insertTrans(@Body TransPost transPost);



    /*RAJAONGKIR*****************************/
    @GET("city")
    Call<City> getCites(@Query("key") String key);

    @FormUrlEncoded
    @POST("cost")
    Call<Cost> getCost(
            @Field("key") String key,
            @Field("origin") String origin,
            @Field("destination") String destination,
            @Field("weight") String weight,
            @Field("courier") String courier
    );


}
