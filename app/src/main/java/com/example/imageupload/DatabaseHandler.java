package com.example.imageupload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME="imdb.db";
    private static int DATABASE_VERSION=1;
    private static String createTableQuery="create table imageInfo (imageName TEXT" + " ,image BLOB)";

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInByte;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{

            sqLiteDatabase.execSQL(createTableQuery);
            Toast.makeText(context, "Table created..", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void storeImage(ModelClass objectModelClass){
        try{

            SQLiteDatabase objectSqLiteDatabase=this.getWritableDatabase();
            Bitmap imageToStoreBitmap=objectModelClass.getImage();

            objectByteArrayOutputStream=new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInByte=objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues=new ContentValues();

            objectContentValues.put("imageName", objectModelClass.getImageName());
            objectContentValues.put("image", imageInByte);

            long check = objectSqLiteDatabase.insert("imageInfo", null, objectContentValues);
            if (check!=-1){
                Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show();
                objectSqLiteDatabase.close();
            } else {
                Toast.makeText(context, "Not inserted", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public ArrayList<ModelClass> getAllImagesData(){
        try{
            SQLiteDatabase objectSqliteDatabase=this.getReadableDatabase();
            ArrayList<ModelClass> objectModelClassList=new ArrayList<>();

            Cursor objectCursor=objectSqliteDatabase.rawQuery("select * from imageInfo", null);
            if (objectCursor.getCount()!=0){
                while (objectCursor.moveToNext()){
                    String nameOfImage = objectCursor.getString(0);
                    byte [] imageBytes=objectCursor.getBlob(1);

                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    objectModelClassList.add(new ModelClass(nameOfImage, objectBitmap));
                }
                return objectModelClassList;

            } else {
                Toast.makeText(context, "No data in database", Toast.LENGTH_SHORT).show();
                return null;
            }


        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
