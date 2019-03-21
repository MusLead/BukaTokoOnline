package com.lazday.bukatokoonline.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lazday.bukatokoonline.data.Model.rajaongkir.Cart;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bukaTokoDemoProject";
    private static final int DATABASE_VERSION = 1;

    private static SQLiteDatabase sqLiteDatabases;

    private static final String TABLE_NAME = "cart";
    private static final String CART_ID = "cart_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT = "product";
    private static final String IMAGE_URL = "image_url";
    private static final String PRICE = "price";
    private static final String CURR_DATE = "curr_date";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabases = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                        "NOT NULL, " + PRODUCT_ID + " INTEGER, " + PRODUCT + " TEXT, " + IMAGE_URL +
                        " TEXT, " + PRICE + " INTERGER, " +
                        CURR_DATE + "DATE DEFAULT CURRENT_DATE );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public Long addToCart(int product_id, String product, String image, int price){

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, product_id);
        contentValues.put(PRODUCT, product);
        contentValues.put(IMAGE_URL, image);
        contentValues.put(PRICE, price);
        return sqLiteDatabases.insert(TABLE_NAME, null,contentValues);

    }

    //TODO:APA BEDANYA INTEGER SAMA INT
    //video 42 (09.00)
    public Integer checkExist(int product_id){
        String sql = "SELECT " + PRODUCT_ID + " FROM " + TABLE_NAME + " WHERE " + PRODUCT_ID + "='" + String.valueOf(product_id) + "'";

        sqLiteDatabases =getReadableDatabase();
        Cursor cursor = sqLiteDatabases.rawQuery(sql, null);

        int count = cursor.getCount();
        return  count;
    }

    public List<Cart> myCart(){
        String sqlQuery ="SELECT * FROM " + TABLE_NAME + " ORDER BY " + CART_ID + " DESC";

        //TODO:BEDANYA LIST SAMA ARRAYLIST APA
        //VIDEO 45 (02:25)
        ArrayList<Cart> carts = new ArrayList<>();

        Cursor cursor = sqLiteDatabases.rawQuery(sqlQuery,null);
        cursor.moveToFirst();

        for(int i = 0; i< cursor.getCount();i++){
            cursor.moveToPosition(i);

            Cart cart = new Cart();
            cart.setCart_id(cursor.getInt(cursor.getColumnIndex( CART_ID )));
            cart.setProduct_id(cursor.getInt(cursor.getColumnIndex( PRODUCT_ID )));
            cart.setProduct(cursor.getString(cursor.getColumnIndex( PRODUCT )));
            cart.setImage(cursor.getString(cursor.getColumnIndex( IMAGE_URL )));
            cart.setPrice(cursor.getInt(cursor.getColumnIndex( PRICE )));
            //TODO:CEK IF IT STILL WORKING
//            cart.setCurr_date(cursor.getString(cursor.getColumnIndex( CURR_DATE )));

            Log.e("_logProductName", cursor.getString(cursor.getColumnIndex(PRODUCT)) );
            carts.add(cart);
        }

        return carts;
    }

    public void removeItem(String cart_id){
        sqLiteDatabases.delete(TABLE_NAME, CART_ID + "='" + cart_id + "'", null);
    }

    public void clearTable(){
        sqLiteDatabases.delete(TABLE_NAME, null, null);
    }
}
