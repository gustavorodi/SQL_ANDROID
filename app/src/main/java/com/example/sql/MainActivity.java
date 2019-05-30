package com.example.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Rating;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextNota;
    private TextView textView;
    private Button enviar;
    private Button listar;
    public SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextNota = findViewById(R.id.editTextNota);
        textView = findViewById(R.id.textView);
        enviar = findViewById(R.id.enviar);
        listar = findViewById(R.id.pegar);



        database = openOrCreateDatabase(
                "banquinho",MODE_PRIVATE, null
        );

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor cursor = database.rawQuery("SELECT NAME, RATING FROM MOVIES ", null);
                    int indexName = cursor.getColumnIndex("NAME");
                    int indexRating = cursor.getColumnIndex("RATING");
                    while (cursor.moveToNext()) {
                        Log.e("NAME:", cursor.getString(indexName));
                        Log.e("RATING:", cursor.getString(indexRating));

                        textView.setText(cursor.getString(indexName),cursor.getString(indexRating));

                    }
                } catch (Exception e) {

                }
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String nomeFilmes = editTextNome.getText().toString();
                    Integer notaFilmes = Integer.valueOf(editTextNota.getText().toString());

                    String sql = "INSERT INTO MOVIES(NAME,RATING) VALUES ('" + nomeFilmes + "', " + notaFilmes + ")";

                    database.execSQL("CREATE TABLE IF NOT EXISTS MOVIES(NAME VARCHAR(80), RATING INT(10))");
                    database.execSQL(sql);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
