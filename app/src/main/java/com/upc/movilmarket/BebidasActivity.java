package com.upc.movilmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class BebidasActivity extends AppCompatActivity {
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        navigationView =findViewById(R.id.btnnavigation);
        navigationView.setSelectedItemId(R.id.nav_bebidas);
        navigationView = findViewById(R.id.btnnavigation);
        navigationView.setItemIconTintList(null);



        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())

                {
                    case R.id.nav_abarrotes:
                        startActivity(new Intent(getApplicationContext(),AbarrotesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_licores:
                        startActivity(new Intent(getApplicationContext(),LicoresActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_bebidas:

                        return true;


                    case R.id.nav_limpieza:
                        startActivity(new Intent(getApplicationContext(),LimpiezaActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);



        return true;
    }



    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_salir:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(BebidasActivity.this, InicioActivity.class));
                return true;
            case R.id.nav_agregar:

                startActivity(new Intent(BebidasActivity.this, RegistroProducto.class));

                return true;
        }
        return super.onOptionsItemSelected(item);


    }
}