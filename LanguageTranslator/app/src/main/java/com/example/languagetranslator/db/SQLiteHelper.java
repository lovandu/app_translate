package com.example.languagetranslator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.languagetranslator.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="words.db";
    private static int DATABASE_VERSION=1;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE items("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "srcWord TEXT, resWord TEXT, date TEXT)";
        db.execSQL(sql);//chay lenh sql
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    //get all item, oder by date descending

    public List<Item> getAll(){
        List<Item> list=new ArrayList<>();
        SQLiteDatabase st=getReadableDatabase();
        String order="date DESC";
        Cursor rs=st.query("items",null, null, null, null, null,order);
        while(rs!=null && rs.moveToNext()) {
            int id=rs.getInt(0);
            String srcWord=rs.getString(1);
            String resWord=rs.getString(2);
            String date=rs.getString(3);


            list.add(new Item(id,srcWord,resWord,date));
        }
        return list;
    }
//
//    //them item
    public long addItem(Item i){
        ContentValues values=new ContentValues();
        values.put("srcWord",i.getSrcWord());
        values.put("resWord",i.getResWord());
        values.put("date",i.getDate());
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.insert("items",null, values);
    }
    public List<Item> getByKey(String key){
        List<Item> list=new ArrayList<>();
        String whereClause="srcWord like ?";
        String order="date DESC";
        String[] whereAgrs={"%"+key+"%"};
        SQLiteDatabase st=getReadableDatabase();
        Cursor rs=st.query("items",null,whereClause,whereAgrs,null, null,order);
        while (rs!=null && rs.moveToNext()){
            int id=rs.getInt(0);
            String srcWord=rs.getString(1);
            String resWord=rs.getString(2);
            String date=rs.getString(3);
            list.add(new Item(id,srcWord,resWord,date));
        }
        return list;
    }
    public List<Item> getByRand(){
        List<Item> list=new ArrayList<>();
        String order="RANDOM()";
        String limit="20";
        SQLiteDatabase st=getReadableDatabase();
        Cursor rs=st.query("items",null,null,null,null, null,order,limit);
        while (rs!=null && rs.moveToNext()){
            int id=rs.getInt(0);
            String srcWord=rs.getString(1);
            String resWord=rs.getString(2);
            String date=rs.getString(3);
            list.add(new Item(id,srcWord,resWord,date));
        }
        return list;
    }
//    public int delete(String id){
//        String whereClause="resWord= ?";
//        String[] whereAgrs={id};
//        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
//        return sqLiteDatabase.delete("items",whereClause,whereAgrs);
//
//    }
//    //lay cac Item theo date
////    public List<Item> getByDate(String date){
////        List<Item> list=new ArrayList<>();
////        String whereClause="date like ?";
////        String[] whereAgrs={date};
////        SQLiteDatabase st=getReadableDatabase();
////        Cursor rs=st.query("items",null,whereClause,whereAgrs,null, null,null);
////        while (rs!=null && rs.moveToNext()){
////            int id=rs.getInt(0);
////            String title=rs.getString(1);
////            String category=rs.getString(2);
////            String price=rs.getString(3);
////            list.add(new Item(id, title, category, price, date));
////        }
////        return list;
////    }
////
//    //Update
//    public int update(Item i){
//        ContentValues values=new ContentValues();
//        values.put("name",i.getName());
//        values.put("author",i.getAuthor());
//        values.put("nhaXB",i.getNhaXB());
//        values.put("ngayXB",i.getNgayXB());
//        values.put("price",i.getPrice());
//        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
//        String whereClause="id= ?";
//        String[] whereAgrs={Integer.toString(i.getId())};
//        return sqLiteDatabase.update("items", values,whereClause,whereAgrs);
//    }
//    //xoa

//    //
////    //tim kiem by key

//    //    //tim kiem by status
//    public List<Item> getByStatus(String key){
//        List<Item> list=new ArrayList<>();
//        String whereClause="nhaXB like ?";
//        String order="price DESC";
//        String[] whereAgrs={key};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.query("items",null,whereClause,whereAgrs,null, null,order);
//        while (rs!=null && rs.moveToNext()){
//            int id=rs.getInt(0);
//            String name=rs.getString(1);
//            String content=rs.getString(2);
//            String date=rs.getString(3);
//            String status=rs.getString(4);
//            int price=rs.getInt(5);
//
//            list.add(new Item(id,name,content,date,status,price));
//        }
//        return list;
//    }
//    //
//    //tim kiem by date
//    public List<Item> getByDateFromTo(int from, int to) {
//        List<Item> list = new ArrayList<>();
//        String whereClause = "price BETWEEN ? AND ?";
//        String[] whereAgrs = {""+from+"", ""+to+""};
//        SQLiteDatabase st = getReadableDatabase();
//        Cursor rs = st.query("items", null, whereClause, whereAgrs, null, null, null);
//        while (rs != null && rs.moveToNext()) {
//            int id=rs.getInt(0);
//            String name=rs.getString(1);
//            String content=rs.getString(2);
//            String date=rs.getString(3);
//            String status=rs.getString(4);
//            int price=rs.getInt(5);
//
//            list.add(new Item(id,name,content,date,status,price));
//        }
//        return list;
//    }
}
