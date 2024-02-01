package com.example.myfinances;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private final static String DATABASE_NAME = "finances_db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "my_finances";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ACCOUNT_TYPE = "account_type";
    private static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    private static final String COLUMN_INITIAL_BALANCE = "initial_balance";
    private static final String COLUMN_CURRENT_BALANCE = "current_balance";
    private static final String COLUMN_INTEREST_RATE = "interest_rate";
    private static final String COLUMN_PAYMENT_AMOUNT = "payment_amount";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCOUNT_TYPE + " TEXT, " +
                COLUMN_ACCOUNT_NUMBER + " TEXT, " + COLUMN_INITIAL_BALANCE + " FLOAT, " +
                COLUMN_CURRENT_BALANCE + " FLOAT, " + COLUMN_INTEREST_RATE + " FLOAT, " +
                COLUMN_PAYMENT_AMOUNT + " FLOAT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addToDb(String type, String accNumber, float initBal, float curBal, float intRate, float
            payAmnt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ACCOUNT_TYPE, type);
        cv.put(COLUMN_ACCOUNT_NUMBER, accNumber);
        cv.put(COLUMN_INITIAL_BALANCE, initBal);
        cv.put(COLUMN_CURRENT_BALANCE, curBal);
        cv.put(COLUMN_INTEREST_RATE, intRate);
        cv.put(COLUMN_PAYMENT_AMOUNT, payAmnt);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "FAILED TO INSERT INTO DATABASE :(",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "DATA ADDED SUCCESSFULLY :)", Toast.LENGTH_SHORT).show();
        }
    }
}