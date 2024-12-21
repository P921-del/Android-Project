package com.example.food_order_application.Helpers;

import static android.provider.CalendarContract.Reminders.query;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Domains.CustomarOrderHistory;
import com.example.food_order_application.Domains.Item;
import com.example.food_order_application.Domains.ItemInCart;
import com.example.food_order_application.Encryption_And_Decryption_Algorithm.AESCrypt;
import com.example.food_order_application.ExternalFunctionsOntheProject.External;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Food_Order_Application_Database extends SQLiteOpenHelper {
    byte[] imageInBytes;
    public static final String DB_NAME = "foodOrder_db";
    public static final int VERSION = 1;
    /**********************************************************************/
    public static final String CUSTOMAR_TB_NAME = "customars";
    public static final String CUSTOMAR_CLN_CUSTOMARID = "CustomarID";
    public static final String CUSTOMAR_CLN_NAME = "Name";
    public static final String CUSTOMAR_CLN_PASSWORD = "Password";
    public static final String CUSTOMAR_CLN_ADDRESS = "Address";
    public static final String CUSTOMAR_CLN_CONTACTNUMBER = "ContactNumber";
    public static final String CUSTOMAR_CLN_EMAIL = "Email";
    /**********************************************************************/
    public static final String ORDER_TB_NAME = "orders";
    public static final String ORDER_CLN_ORDERID = "OrderID";
    //public static final String ORDER_CLN_ORDERDATE ="OrderDate";
    public static final String ORDER_CLN_QUANTITY = "Quantity";
    public static final String ORDER_CLN_STATUS = "Status";
    public static final String ORDER_CLN_TOTALAMOUNT = "TotalAmount";
    public static final String ORDER_CLN_FK_CUSTOMARID = "CustomarID";
    /**********************************************************************/
    public static final String ORDERITEMS_TB_NAME = "OrderItems";
    public static final String ORDERITEMS_CLN_PK_ORDERITEMSID = "OrderItemsID";
    public static final String ORDERITEMS_CLN_FK_ORDERID = "OrderID";
    public static final String ORDERITEMS_CLN_FK_ITEMID = "ItemID";
    /**********************************************************************/
    public static final String ITEMS_TB_NAME = "items";
    public static final String ITEMS_CLN_PK_ITEMID = "ItemID";
    public static final String ITEMS_CLN_NAME = "Name";
    public static final String ITEMS_CLN_PICTURE = "Picture";
    public static final String ITEMS_CLN_DESCRIPTION = "Description";
    public static final String ITEMS_CLN_PRICE = "Price";
    public static final String ITEMS_CLN_STAR = "Star";
    public static final String ITEMS_CLN_TIME = "Time";
    public static final String ITEMS_CLN_CALORIES = "Calories";
    public static final String ITEMS_CLN_QUANTITY = "Quantity";
    public static final String ITEMS_CLN_FK_MENUITEMID = "MenuItemID";
    /**********************************************************************/
    public static final String MENUITEMS_TB_NAME = "menuItems";
    public static final String MENUITEMS_CLN_MENUITEMSID = "menuItemsID";
    public static final String MENUITEMS_CLN_NAME = "menuItemsName";
    /**********************************************************************/
    public static final String PAYMENT_TB_NAME = "payments";
    public static final String PAYMENT_CLN_PAYMENTID = "PaymentID";
    public static final String PAYMENT_CLN_CUSTOMARID = "CustomarID";
    public static final String PAYMENT_CLN_METHOD = "PaymentMethod";
    public static final String PAYMENT_CLN_AMOUNT = "PaymentAmount";
    /**********************************************************************/
    public Food_Order_Application_Database(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CUSTOMAR_TB_NAME + " ( " + CUSTOMAR_CLN_CUSTOMARID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CUSTOMAR_CLN_NAME + " TEXT NOT NULL UNIQUE , " + CUSTOMAR_CLN_PASSWORD + " TEXT NOT NULL , " + CUSTOMAR_CLN_ADDRESS + " TEXT , " + CUSTOMAR_CLN_CONTACTNUMBER + " REAL NOT NULL , " + CUSTOMAR_CLN_EMAIL + " TEXT NOT NULL );");
        db.execSQL("CREATE TABLE " + ITEMS_TB_NAME + " ( " + ITEMS_CLN_PK_ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ITEMS_CLN_NAME + " TEXT NOT NULL UNIQUE , " + ITEMS_CLN_PICTURE + " TEXT NOT NULL UNIQUE , " + ITEMS_CLN_STAR + " INTEGER NOT NULL , " + ITEMS_CLN_QUANTITY + " INTEGER , " + ITEMS_CLN_TIME + " REAL NOT NULL , " + ITEMS_CLN_CALORIES + " INTEGER NOT NULL , " + ITEMS_CLN_DESCRIPTION + " TEXT , " + ITEMS_CLN_PRICE + " REAL NOT NULL , " + ITEMS_CLN_FK_MENUITEMID + " INTEGER , FOREIGN KEY (" + ITEMS_CLN_FK_MENUITEMID + ") REFERENCES " + MENUITEMS_TB_NAME + "(" + MENUITEMS_CLN_MENUITEMSID + ") ON DELETE SET NULL ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE " + MENUITEMS_TB_NAME + " ( " + MENUITEMS_CLN_MENUITEMSID + " INTEGER PRIMARY KEY  , " + MENUITEMS_CLN_NAME + " TEXT NOT NULL UNIQUE);");
        db.execSQL("INSERT INTO "+ MENUITEMS_TB_NAME +" VALUES ( 1,'Pizza' )");
        db.execSQL("INSERT INTO "+ MENUITEMS_TB_NAME +" VALUES ( 2,'Burger' )");
        db.execSQL("INSERT INTO "+ MENUITEMS_TB_NAME +" VALUES ( 3,'Hotdog' )");
        db.execSQL("INSERT INTO "+ MENUITEMS_TB_NAME +" VALUES ( 4,'Drink' )");
        db.execSQL("INSERT INTO "+ MENUITEMS_TB_NAME +" VALUES ( 5,'Donut' )");
        db.execSQL("CREATE TABLE " + ORDER_TB_NAME + " (  " + ORDER_CLN_ORDERID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ORDER_CLN_QUANTITY + " INTEGER , " +ORDER_CLN_STATUS+" ,TEXT NOT NULL DEFAULT 'Pending' , "+ ORDER_CLN_TOTALAMOUNT + " REAL , " + ORDER_CLN_FK_CUSTOMARID + " INTEGER , FOREIGN KEY (" + ORDER_CLN_FK_CUSTOMARID + ") REFERENCES " + CUSTOMAR_TB_NAME + "(" + CUSTOMAR_CLN_CUSTOMARID + ") ON DELETE SET NULL ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE " + ORDERITEMS_TB_NAME + " (  " + ORDERITEMS_CLN_PK_ORDERITEMSID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ORDERITEMS_CLN_FK_ORDERID + " INTEGER , " + ORDERITEMS_CLN_FK_ITEMID + " INTEGER , FOREIGN KEY (" + ORDERITEMS_CLN_FK_ORDERID + ") REFERENCES " + ORDER_TB_NAME + "(" + ORDER_CLN_ORDERID + ") ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY ( " + ORDERITEMS_CLN_FK_ITEMID + ") REFERENCES  " + ITEMS_TB_NAME + "( " + ITEMS_CLN_PK_ITEMID + ") ON DELETE SET NULL ON UPDATE CASCADE);");
        db.execSQL("CREATE TABLE " + PAYMENT_TB_NAME + " ( " + PAYMENT_CLN_PAYMENTID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + PAYMENT_CLN_METHOD + " TEXT NOT NULL , " + PAYMENT_CLN_AMOUNT + " REAL NOT NULL , " + PAYMENT_CLN_CUSTOMARID + " INTEGER , FOREIGN KEY (" + PAYMENT_CLN_CUSTOMARID + ") REFERENCES " + CUSTOMAR_TB_NAME + "(" + CUSTOMAR_CLN_CUSTOMARID + ") ON DELETE SET NULL ON UPDATE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMAR_TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MENUITEMS_TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ORDERITEMS_TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PAYMENT_TB_NAME);
        onCreate(db);
    }

    /**********************************************************************/
    public Customar checkCredientials(String email, String password, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_EMAIL + " LIKE ?", new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {

            @SuppressLint("Range") String EncryptedPassword = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_PASSWORD));
            try {
                String DecryptedPassword = AESCrypt.decrypt(EncryptedPassword);
                if (DecryptedPassword.trim().equals(password)) {
                    @SuppressLint("Range") int CustomarID = cursor.getInt(cursor.getColumnIndex(CUSTOMAR_CLN_CUSTOMARID));
                    @SuppressLint("Range") String Name = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_NAME));
                    @SuppressLint("Range") String Address = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_ADDRESS));
                    @SuppressLint("Range") int ContactNumber = cursor.getInt(cursor.getColumnIndex(CUSTOMAR_CLN_CONTACTNUMBER));
                    @SuppressLint("Range") String Email = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_EMAIL));
                    @SuppressLint("Range") String Password = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_PASSWORD));
                    Customar customar = new Customar(CustomarID, Name, Address, ContactNumber, Email, Password);
                    return customar;
                }
            } catch (Exception e) {
                //handle error
                Toast.makeText(context, "Error in the system ,Please wait until fixed!", Toast.LENGTH_LONG).show();

            }
            cursor.close();
        }
        return null;
    }

    public boolean createCustomar(Customar customar) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMAR_CLN_NAME, customar.getName());
        contentValues.put(CUSTOMAR_CLN_PASSWORD, customar.getPassword());
        contentValues.put(CUSTOMAR_CLN_ADDRESS, customar.getAddress());
        contentValues.put(CUSTOMAR_CLN_CONTACTNUMBER, customar.getContactNumber());
        contentValues.put(CUSTOMAR_CLN_EMAIL, customar.getEmail());
        long result = db.insert(CUSTOMAR_TB_NAME, null, contentValues);
        return result != -1;
    }

    public boolean updateCustomar(Customar customar) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {customar.getCustomarID() + ""};
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMAR_CLN_NAME, customar.getName());
        contentValues.put(CUSTOMAR_CLN_PASSWORD, customar.getPassword());
        contentValues.put(CUSTOMAR_CLN_ADDRESS, customar.getAddress());
        contentValues.put(CUSTOMAR_CLN_CONTACTNUMBER, customar.getContactNumber());
        contentValues.put(CUSTOMAR_CLN_EMAIL, customar.getEmail());
        int result = db.update(CUSTOMAR_TB_NAME, contentValues, CUSTOMAR_CLN_CUSTOMARID+" =?", args);
        return result > 0;
    }

    public long getCustomarsCount() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, CUSTOMAR_TB_NAME);
    }

    public boolean deleteCustomar(Customar customar) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {String.valueOf(customar.getCustomarID())};
        int result = db.delete(CUSTOMAR_TB_NAME, CUSTOMAR_CLN_CUSTOMARID+" =?", args);
        return result > 0;
    }

    public Customar getCustomarByID(int customarID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_CUSTOMARID + " =?", new String[]{customarID + ""});
        if (cursor.getColumnCount() > 0 && cursor.moveToFirst())//he finds customar his name matches with this search
        {

            @SuppressLint("Range") String Name = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_NAME));
            @SuppressLint("Range") String Address = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_ADDRESS));
            @SuppressLint("Range") int ContactNumber = cursor.getInt(cursor.getColumnIndex(CUSTOMAR_CLN_CONTACTNUMBER));
            @SuppressLint("Range") String Email = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_EMAIL));
            @SuppressLint("Range") String Password = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_PASSWORD));
            Customar customar = new Customar(Name, Address, ContactNumber, Email, Password);
            cursor.close();
            return customar;
        }
        return null;
    }

    public ArrayList<Customar> getAllCustomars() {
        ArrayList<Customar> customars = new ArrayList<Customar>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int CustomarID = cursor.getInt(cursor.getColumnIndex(CUSTOMAR_CLN_CUSTOMARID));
                @SuppressLint("Range") String Name = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_NAME));
                @SuppressLint("Range") String Address = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_ADDRESS));
                @SuppressLint("Range") int ContactNumber = cursor.getInt(cursor.getColumnIndex(CUSTOMAR_CLN_CONTACTNUMBER));
                @SuppressLint("Range") String Email = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_EMAIL));
                @SuppressLint("Range") String Password = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_PASSWORD));
                Customar customar = new Customar(CustomarID, Name, Address, ContactNumber, Email, Password);
                customars.add(customar);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return customars;
    }

    public ArrayList<Customar> searchForCustomar(String search) {
        ArrayList<Customar> customars = new ArrayList<Customar>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_NAME + " LIKE ?", new String[]{"%" + search + "%"});
        if (cursor != null && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int CustomarID = cursor.getInt(cursor.getColumnIndex(CUSTOMAR_CLN_CUSTOMARID));
                @SuppressLint("Range") String Name = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_NAME));
                @SuppressLint("Range") String Address = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_ADDRESS));
                @SuppressLint("Range") int ContactNumber = cursor.getInt(cursor.getColumnIndex(CUSTOMAR_CLN_CONTACTNUMBER));
                @SuppressLint("Range") String Email = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_EMAIL));
                @SuppressLint("Range") String Password = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_PASSWORD));
                Customar customar = new Customar(CustomarID, Name, Address, ContactNumber, Email, Password);
                customars.add(customar);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return customars;
    }

    public boolean CheckCustomarNameUniqueForCreate(String Name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_NAME + " LIKE ?", new String[]{Name + "%"});
        if (cursor != null && cursor.moveToFirst())//he finds customar his name matches with this search
        {
            cursor.close();
            return false;
        } else {
            return true;
        }
    }

    public boolean CheckCustomarNameUniqueForUpdate(String Name, int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_CUSTOMARID + " =?", new String[]{id + ""});
        if (cursor1 != null && cursor1.moveToFirst())//he finds customar his name matches with this search
        {
            @SuppressLint("Range") String CustomarName = cursor1.getString(cursor1.getColumnIndex(CUSTOMAR_CLN_NAME));
            if (CustomarName.equals(Name))
                return true;
            cursor1.close();
        }

        Cursor cursor2 = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_CUSTOMARID + " !=? AND " + CUSTOMAR_CLN_NAME + " =?", new String[]{String.valueOf(id), Name});
        if (cursor2 != null && cursor2.moveToFirst()) {
            cursor2.close();
            return false;
        } else {
            return true;
        }

    }

    public boolean CheckCustomarEmailUniqueForCreate(String Email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_EMAIL + " LIKE ?", new String[]{Email + "%"});
        if (cursor != null && cursor.moveToFirst())//he finds customar his name matches with this search
        {
            cursor.close();
            return false;
        } else {
            return true;
        }
    }

    public boolean CheckCustomarEmailUniqueForUpdate(String Email, int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_CUSTOMARID + " =?", new String[]{id + ""});
        if (cursor1 != null && cursor1.moveToFirst())//he finds customar his name matches with this search
        {
            @SuppressLint("Range") String CustomarEmail = cursor1.getString(cursor1.getColumnIndex(CUSTOMAR_CLN_EMAIL));
            if (CustomarEmail.equals(Email))
                return true;
            cursor1.close();
        }

        Cursor cursor2 = db.rawQuery("SELECT * FROM " + CUSTOMAR_TB_NAME + " WHERE " + CUSTOMAR_CLN_CUSTOMARID + " !=? AND " + CUSTOMAR_CLN_EMAIL + " =?", new String[]{String.valueOf(id), Email});
        if (cursor2 != null && cursor2.moveToFirst()) {
            cursor2.close();
            return false;
        } else {
            return true;
        }

    }

    /**********************************************************************/
    public boolean addItem(Item item) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMS_CLN_NAME, item.getTitle());
        //how to save image inside android with java project
        ByteArrayOutputStream objectByteOutputStream = new ByteArrayOutputStream();
        item.getPicture().compress(Bitmap.CompressFormat.JPEG, 100, objectByteOutputStream);
        imageInBytes = objectByteOutputStream.toByteArray();
        contentValues.put(ITEMS_CLN_PICTURE, imageInBytes);
        contentValues.put(ITEMS_CLN_DESCRIPTION, item.getDescription());
        contentValues.put(ITEMS_CLN_PRICE, item.getPrice());
        contentValues.put(ITEMS_CLN_STAR, item.getStar());
        contentValues.put(ITEMS_CLN_TIME, item.getTime());
        contentValues.put(ITEMS_CLN_CALORIES, item.getCalories());
        contentValues.put(ITEMS_CLN_QUANTITY, item.getQuantity());
        contentValues.put(ITEMS_CLN_FK_MENUITEMID, item.getMenuItems_id_FK());
        long result = db.insert(ITEMS_TB_NAME, null, contentValues);
        return result != -1;
    }

    public boolean updateItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {item.getId() + ""};
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMS_CLN_NAME, item.getTitle());
        //how to save image inside android with java project
        contentValues.put(ITEMS_CLN_PICTURE, item.getTitle());
        contentValues.put(ITEMS_CLN_DESCRIPTION, item.getDescription());
        contentValues.put(ITEMS_CLN_PRICE, item.getPrice());
        contentValues.put(ITEMS_CLN_STAR, item.getStar());
        contentValues.put(ITEMS_CLN_TIME, item.getTime());
        contentValues.put(ITEMS_CLN_CALORIES, item.getCalories());
        contentValues.put(ITEMS_CLN_QUANTITY, item.getQuantity());
        contentValues.put(ITEMS_CLN_FK_MENUITEMID, item.getMenuItems_id_FK());
        int result = db.update(ITEMS_TB_NAME, contentValues, ITEMS_CLN_PK_ITEMID+" =?", args);
        return result > 0;
    }

    public long getItemsCount() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, ITEMS_TB_NAME);
    }

    public boolean deleteItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {String.valueOf(item.getId())};
        int result = db.delete(ITEMS_TB_NAME, ITEMS_CLN_PK_ITEMID+" =?", args);
        return result > 0;
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int itemID = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_PK_ITEMID));
                @SuppressLint("Range") String Title = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_NAME));
                //convert byte array saved in the database to bitmap
                @SuppressLint("Range") byte[] PictureArray = cursor.getBlob(cursor.getColumnIndex(ITEMS_CLN_PICTURE));
                Bitmap Picture = External.byteArrayToBimap(PictureArray);
                // end convert byte array saved in the database to bitmap
                @SuppressLint("Range") String Description = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_DESCRIPTION));
                @SuppressLint("Range") double Price = cursor.getDouble(cursor.getColumnIndex(ITEMS_CLN_PRICE));
                @SuppressLint("Range") int Star = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_STAR));
                @SuppressLint("Range") int Time = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_TIME));
                @SuppressLint("Range") int Calories = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_CALORIES));
                @SuppressLint("Range") int Quantity = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_QUANTITY));
                @SuppressLint("Range") int menuItems_id_FK = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_FK_MENUITEMID));
                Item item = new Item(itemID, Title, Picture, Description, Price, Star, Time, Calories, Quantity, menuItems_id_FK);
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    public Item getItemByID(int ItemID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TB_NAME + " WHERE " + ITEMS_CLN_PK_ITEMID + " =?", new String[]{ItemID + ""});
        if (cursor.getColumnCount() > 0 && cursor.moveToFirst())//he finds customar his name matches with this search
        {

            @SuppressLint("Range") String Title = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_NAME));
            //convert byte array saved in the database to bitmap
            @SuppressLint("Range") byte[] PictureArray = cursor.getBlob(cursor.getColumnIndex(ITEMS_CLN_PICTURE));
            Bitmap Picture = External.byteArrayToBimap(PictureArray);
            // end convert byte array saved in the database to bitmap
            @SuppressLint("Range") String Description = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_DESCRIPTION));
            @SuppressLint("Range") double Price = cursor.getDouble(cursor.getColumnIndex(ITEMS_CLN_PRICE));
            @SuppressLint("Range") int Star = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_STAR));
            @SuppressLint("Range") int Time = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_TIME));
            @SuppressLint("Range") int Calories = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_CALORIES));
            @SuppressLint("Range") int Quantity = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_QUANTITY));
            @SuppressLint("Range") int menuItems_id_FK = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_FK_MENUITEMID));
            Item item = new Item(Title, Picture, Description, Price, Star, Time, Calories, Quantity, menuItems_id_FK);
            cursor.close();
            return item;
        }
        return null;
    }

    public ArrayList<Item> searchForItem(String search) {
        ArrayList<Item> items = new ArrayList<Item>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TB_NAME + " WHERE " + ITEMS_CLN_NAME + " LIKE ?", new String[]{"%" + search + "%"});
        if (cursor != null && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int itemID = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_PK_ITEMID));
                @SuppressLint("Range") String Title = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_NAME));
                //convert byte array saved in the database to bitmap
                @SuppressLint("Range") byte[] PictureArray = cursor.getBlob(cursor.getColumnIndex(ITEMS_CLN_PICTURE));
                Bitmap Picture = External.byteArrayToBimap(PictureArray);
                // end convert byte array saved in the database to bitmap
                @SuppressLint("Range") String Description = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_DESCRIPTION));
                @SuppressLint("Range") double Price = cursor.getDouble(cursor.getColumnIndex(ITEMS_CLN_PRICE));
                @SuppressLint("Range") int Star = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_STAR));
                @SuppressLint("Range") int Time = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_TIME));
                @SuppressLint("Range") int Calories = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_CALORIES));
                @SuppressLint("Range") int Quantity = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_QUANTITY));
                @SuppressLint("Range") int menuItems_id_FK = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_FK_MENUITEMID));
                Item item = new Item(itemID, Title, Picture, Description, Price, Star, Time, Calories, Quantity, menuItems_id_FK);
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }

    public boolean CheckItemNameUniqueForCreate(String Name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TB_NAME + " WHERE " + ITEMS_CLN_NAME + " LIKE ?", new String[]{Name + "%"});
        if (cursor.getColumnCount() > 0 && cursor.moveToFirst())//he finds item its name matches with this search
        {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    public boolean CheckItemNameUniqueForUpdate(String Name, int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + ITEMS_TB_NAME + " WHERE " + ITEMS_CLN_PK_ITEMID + " =?", new String[]{id + ""});
        if (cursor1.getColumnCount() > 0 && cursor1.moveToFirst())//he finds customar his name matches with this search
        {
            @SuppressLint("Range") String ItemName = cursor1.getString(cursor1.getColumnIndex(ITEMS_CLN_NAME));
            if (ItemName.equals(Name)) {
                cursor1.close();
                return true;
            }

        }
        Cursor cursor2 = db.rawQuery("SELECT * FROM " + ITEMS_TB_NAME + " WHERE " + ITEMS_CLN_PK_ITEMID + " !=? AND " + ITEMS_CLN_NAME + " =?", new String[]{String.valueOf(id), Name});
        if (cursor2.getColumnCount() > 0 && cursor2.moveToFirst()) {
            cursor2.close();
            return false;
        } else {
            cursor2.close();
            return true;
        }

    }

    /****************************************************************************************/
    public boolean createOrder(int CustomarID, int ItemID, double TotalAmounts, int count) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(ORDER_CLN_FK_CUSTOMARID, CustomarID);
        contentValues1.put(ORDER_CLN_TOTALAMOUNT, TotalAmounts);
        contentValues1.put(ORDER_CLN_QUANTITY, count);
        long result1 = db.insert(ORDER_TB_NAME, null, contentValues1);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(ORDERITEMS_CLN_FK_ORDERID, contentValues1.getAsInteger(ORDER_CLN_ORDERID));
        contentValues2.put(ORDERITEMS_CLN_FK_ITEMID, ItemID);
        long result2 = db.insert(ORDERITEMS_TB_NAME, null, contentValues2);
        return (result1 != -1 && result2 != -1);
    }
    public boolean ChangeOrderStatus(int OrderID , String Status) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {OrderID + ""};
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_CLN_STATUS, Status);
        int result = db.update(ORDER_TB_NAME, contentValues, ORDER_CLN_ORDERID+" =?", args);
        return result > 0;
    }

    public ArrayList<ItemInCart> getAllItemsInCart(int custoamrID) {
        ArrayList<ItemInCart> items = new ArrayList<ItemInCart>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("SELECT " + ORDER_CLN_QUANTITY + ", " + ORDER_CLN_ORDERID + ", " + ORDER_CLN_TOTALAMOUNT + ", " + ITEMS_CLN_NAME + ", " + ITEMS_CLN_PK_ITEMID + ", " + ITEMS_CLN_PICTURE + ", " + ITEMS_CLN_PRICE + " FROM " + ORDER_TB_NAME + " JOIN " + ORDERITEMS_TB_NAME + " ON " + ORDER_CLN_ORDERID + " = " + ORDERITEMS_CLN_FK_ORDERID + "  JOIN " + ITEMS_TB_NAME + " ON " + ORDERITEMS_CLN_FK_ITEMID + " = " + ITEMS_CLN_PK_ITEMID + " WHERE " + ORDER_CLN_FK_CUSTOMARID + " =?", null, null, new String[]{"" + custoamrID}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int OrderID = cursor.getInt(cursor.getColumnIndex(ORDER_CLN_ORDERID));
                @SuppressLint("Range") int itemID = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_PK_ITEMID));
                @SuppressLint("Range") int CustomarID = cursor.getInt(cursor.getColumnIndex(ORDER_CLN_FK_CUSTOMARID));
                @SuppressLint("Range") String Title = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_NAME));
                //convert byte array saved in the database to bitmap
                @SuppressLint("Range") byte[] PictureArray = cursor.getBlob(cursor.getColumnIndex(ITEMS_CLN_PICTURE));
                Bitmap Picture = External.byteArrayToBimap(PictureArray);
                // end convert byte array saved in the database to bitmap
                @SuppressLint("Range") double Price = cursor.getDouble(cursor.getColumnIndex(ITEMS_CLN_PRICE));
                @SuppressLint("Range") int QuantityInCart = cursor.getInt(cursor.getColumnIndex(ORDER_CLN_QUANTITY));
                @SuppressLint("Range") double TotalPriceOfItemType = cursor.getDouble(cursor.getColumnIndex(ORDER_CLN_TOTALAMOUNT));
                ItemInCart item = new ItemInCart(itemID, OrderID, Title, Picture, QuantityInCart, Price, TotalPriceOfItemType, CustomarID);
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            return  items;

        }
        return null;
    }

    public boolean changeQuantityOfOrderByID(int OrderID, int quantity, double price) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {OrderID + ""};
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_CLN_QUANTITY, quantity);
        double total = quantity * price;
        contentValues.put(ORDER_CLN_TOTALAMOUNT, total);
        int result = db.update(ORDER_TB_NAME, contentValues, "id=?", args);
        return result > 0;
    }

    public ArrayList<CustomarOrderHistory> getAllHistoryOrders() {
        ArrayList<CustomarOrderHistory> items = new ArrayList<CustomarOrderHistory>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("SELECT " + CUSTOMAR_CLN_NAME + ", " + ORDER_CLN_QUANTITY + ", " + ORDER_CLN_ORDERID + ", " + ORDER_CLN_TOTALAMOUNT + ", " + ITEMS_CLN_NAME + ", " + ITEMS_CLN_PK_ITEMID + ", " + ITEMS_CLN_PICTURE + ", " + ITEMS_CLN_PRICE + " FROM " + CUSTOMAR_TB_NAME + " JOIN " + ORDER_TB_NAME + " ON " + ORDER_CLN_FK_CUSTOMARID + " = " + CUSTOMAR_CLN_CUSTOMARID + " JOIN " + ORDERITEMS_TB_NAME + " ON " + ORDER_CLN_ORDERID + " = " + ORDERITEMS_CLN_FK_ORDERID + "  JOIN " + ITEMS_TB_NAME + " ON " + ORDERITEMS_CLN_FK_ITEMID + " = " + ITEMS_CLN_PK_ITEMID , null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int OrderID = cursor.getInt(cursor.getColumnIndex(ORDER_CLN_ORDERID));
                @SuppressLint("Range") int ItemID = cursor.getInt(cursor.getColumnIndex(ITEMS_CLN_PK_ITEMID));
                @SuppressLint("Range") String CustomarName = cursor.getString(cursor.getColumnIndex(CUSTOMAR_CLN_NAME));
                @SuppressLint("Range") int CountOrders = cursor.getInt(cursor.getColumnIndex(ORDER_CLN_QUANTITY));
                @SuppressLint("Range") double TotalPriceForThisOrder = cursor.getDouble(cursor.getColumnIndex(ORDER_CLN_TOTALAMOUNT));
                @SuppressLint("Range") String FoodName = cursor.getString(cursor.getColumnIndex(ITEMS_CLN_NAME));
                @SuppressLint("Range") double Price = cursor.getDouble(cursor.getColumnIndex(ITEMS_CLN_PRICE));
                CustomarOrderHistory order = new CustomarOrderHistory(CustomarName , FoodName , Price , CountOrders , TotalPriceForThisOrder , "Pending" , ItemID ,OrderID);
                items.add(order);
            } while (cursor.moveToNext());
            cursor.close();
            return  items;
        }
        return null;
    }
    public double getTotalPriceForAllOrdersInTheSystem(){
        SQLiteDatabase db = getReadableDatabase();
        double total= 0.00;
        Cursor cursor = db.rawQuery("SELECT "+ORDER_CLN_TOTALAMOUNT+" FROM "+ ORDER_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") double TotalPriceForThisOrder = cursor.getDouble(cursor.getColumnIndex(ORDER_CLN_TOTALAMOUNT));
               total += TotalPriceForThisOrder;
            } while (cursor.moveToNext());
            cursor.close();
            return  total;
        }
        return 0.00;
    }
    public boolean PayByPayPal(double Amount , int CustomarID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PAYMENT_CLN_AMOUNT , Amount);
        contentValues.put(PAYMENT_CLN_METHOD , "PayPal");
        contentValues.put(PAYMENT_CLN_CUSTOMARID , CustomarID);
        long result = db.insert(PAYMENT_TB_NAME, null, contentValues);
        return result != -1;
    }
}
