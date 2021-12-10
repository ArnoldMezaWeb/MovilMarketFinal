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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.movilmarket.entidades.Licores;
import com.upc.movilmarket.entidades.Proveedor;

import java.util.ArrayList;
import java.util.List;

public class Proveedores extends AppCompatActivity {

    RecyclerView rvProveedores;
    AdaptadorPersonalizadoProveedor adaptador;
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    private List<Proveedor> listaproveedores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedores);
        inicializarFirebase();
        listraDatos();
        asignarReferencias();
    }

    private  void listraDatos(){
        dbReference.child("Proveedores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaproveedores.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    Proveedor l = item.getValue(Proveedor.class);
                    listaproveedores.add(l);
                }
                adaptador = new AdaptadorPersonalizadoProveedor(Proveedores.this, listaproveedores);
                rvProveedores.setAdapter(adaptador);
                rvProveedores.setLayoutManager(new LinearLayoutManager(Proveedores.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void asignarReferencias(){

        rvProveedores = findViewById(R.id.rvproveedores);

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
                startActivity(new Intent(Proveedores.this, InicioActivity.class));
                return true;

            case R.id.nav_mapa:
                startActivity(new Intent(Proveedores.this, Maps.class));
            case R.id.nav_editprov:
                startActivity(new Intent(Proveedores.this, Proveedores.class));

                return true;

            case R.id.nav_prov:
                startActivity(new Intent(Proveedores.this, AgregarProveedores.class));
                return true;
            case R.id.nav_menu:
                startActivity(new Intent(Proveedores.this, AbarrotesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);


    }
}