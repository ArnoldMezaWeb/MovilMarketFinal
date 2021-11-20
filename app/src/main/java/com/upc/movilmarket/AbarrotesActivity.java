package com.upc.movilmarket;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.movilmarket.entidades.Productos;

import java.util.ArrayList;
import java.util.List;

public class AbarrotesActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    Bundle datos;
    FloatingActionButton fltbtn;
    RecyclerView rvProductos;
    AdaptadorPersonalizado adaptador;
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    private TextView txtcontador;
     private   Button btnsuma;
     private Button   btnmenos;



    private String nombre;
    private FirebaseAuth mFirebaseAuth;
    static final String STATE_USER = "user";
    private List<Productos>listaProductos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abarrotes);
        inicializarFirebase();
        listraDatos();
        asignarReferencias();
        navigationView = findViewById(R.id.btnnavigation);
        navigationView.setItemIconTintList(null);


        if (savedInstanceState != null) {
             nombre = savedInstanceState.getString("Nombre");



        }





        datos = getIntent().getExtras();

       /* if (datos !=null){
            String tipoUsuario = datos.getString("usuarioIngresado");
            if(tipoUsuario.equals("administrador@hotmail.com")){
                // Toast.makeText(MainActivity.this, "CORRECTO", Toast.LENGTH_SHORT).show();
                fltbtn.setVisibility(View.VISIBLE);


            }else {

            }

        }else {

        }*/






        navigationView = findViewById(R.id.btnnavigation);
        navigationView.setSelectedItemId(R.id.nav_abarrotes);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_abarrotes:

                        return true;
                    case R.id.nav_licores:
                        startActivity(new Intent(getApplicationContext(), LicoresActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_bebidas:
                        startActivity(new Intent(getApplicationContext(), BebidasActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_limpieza:
                        startActivity(new Intent(getApplicationContext(), LimpiezaActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }

        });

    }

    private  void listraDatos(){
        dbReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    Productos p = item.getValue(Productos.class);
                    listaProductos.add(p);
                }
                adaptador = new AdaptadorPersonalizado(AbarrotesActivity.this, listaProductos);
                rvProductos.setAdapter(adaptador);
                rvProductos.setLayoutManager(new LinearLayoutManager(AbarrotesActivity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void asignarReferencias(){

        rvProductos = findViewById(R.id.rvproductos);
        fltbtn = findViewById(R.id.btnadd);
        fltbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbarrotesActivity.this, RegistroProducto.class);
                startActivity(intent);
            }
        });
    }

     private void inicializarFirebase(){
         FirebaseApp.initializeApp(this);
         fbDatabase=FirebaseDatabase.getInstance();
         dbReference = fbDatabase.getReference();
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
                startActivity(new Intent(AbarrotesActivity.this, InicioActivity.class));
                return true;
            case R.id.nav_agregar:

                startActivity(new Intent(AbarrotesActivity.this, RegistroProducto.class));

                return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("usuarioIngresado", String.valueOf(datos));

        super.onSaveInstanceState(outState);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



}