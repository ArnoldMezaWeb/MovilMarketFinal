package com.upc.movilmarket;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Bundle datos;
    FloatingActionButton fltbtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         navigationView =findViewById(R.id.btnnavigation);
         fltbtn = findViewById(R.id.btnadd);
         asignarReferencias();

        navigationView.setSelectedItemId(R.id.nav_abarrotes);

       navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               switch (item.getItemId())

               {
                   case R.id.nav_abarrotes:

                       return true;
                   case R.id.nav_licores:
                       startActivity(new Intent(getApplicationContext(),LicoresActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   case R.id.nav_bebidas:
                       startActivity(new Intent(getApplicationContext(),BebidasActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   case R.id.nav_limpieza:
                       startActivity(new Intent(getApplicationContext(),LimpiezaActivity.class));
                       overridePendingTransition(0, 0);
                       return true;

               }
               return false;
           }
       });


        datos = getIntent().getExtras();

        String tipoUsuario = datos.getString("usuarioIngresado");

        if(tipoUsuario.equals("administrador@hotmail.com")){
           // Toast.makeText(MainActivity.this, "CORRECTO", Toast.LENGTH_SHORT).show();
           fltbtn.setVisibility(View.VISIBLE);

        }else {

        }


    }

    private void asignarReferencias(){

        fltbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroProducto.class);
                startActivity(intent);
            }
        });
    }
}