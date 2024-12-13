package com.example.food_order_application.ExternalFunctionsOntheProject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class External {
    public static Bitmap byteArrayToBimap (byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.length,null);
    }
}
