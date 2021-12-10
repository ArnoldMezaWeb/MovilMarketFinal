package com.upc.movilmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.upc.movilmarket.entidades.Licores;
import com.upc.movilmarket.entidades.Limpieza;

import java.util.ArrayList;
import java.util.List;

public class LimpiezaActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    Bundle datos;
    FloatingActionButton fltbtn;
    RecyclerView rvLimpieza;
    AdaptadorPersonalizadoLimpieza adaptador;
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    private TextView txtcontador;
    private Button btnsuma;
    private Button   btnmenos;
    private String nombre;
    private FirebaseAuth mFirebaseAuth;
    static final String STATE_USER = "user";
    private List<Limpieza> listaLimpieza = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limpieza);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_buy_bag3);
        navigationView =findViewById(R.id.btnnavigation);

        navigationView.setSelectedItemId(R.id.nav_limpieza);
        navigationView = findViewById(R.id.btnnavigation);
        navigationView.setItemIconTintList(null);
        inicializarFirebase();
        listraDatos();
        asignarReferencias();

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
                        startActivity(new Intent(getApplicationContext(),BebidasActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_limpieza:

                        return true;
                    case R.id.nav_menu:
                        startActivity(new Intent(LimpiezaActivity.this, AbarrotesActivity.class));
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
                startActivity(new Intent(LimpiezaActivity.this, InicioActivity.class));
                return true;
            case R.id.nav_agregar:

                startActivity(new Intent(LimpiezaActivity.this, RegistroProductoLimpieza.class));

                return true;

            case R.id.nav_mapa:
                startActivity(new Intent(LimpiezaActivity.this, Maps.class));

                return true;
            case R.id.nav_editprov:
                startActivity(new Intent(LimpiezaActivity.this, Proveedores.class));

                return true;

            case R.id.nav_prov:
                startActivity(new Intent(LimpiezaActivity.this, AgregarProveedores.class));
                return true;
        }
        return super.onOptionsItemSelected(item);


    }


    private  void listraDatos(){
        dbReference.child("Limpieza").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaLimpieza.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    Limpieza l = item.getValue(Limpieza.class);
                    listaLimpieza.add(l);
                }
                adaptador = new AdaptadorPersonalizadoLimpieza(LimpiezaActivity.this, listaLimpieza);
                rvLimpieza.setAdapter(adaptador);
                rvLimpieza.setLayoutManager(new LinearLayoutManager(LimpiezaActivity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void asignarReferencias(){

        rvLimpieza = findViewById(R.id.rvlimpieza);

    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        fbDatabase=FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();
    }
}