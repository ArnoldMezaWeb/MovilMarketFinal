package com.upc.movilmarket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.upc.movilmarket.entidades.Pedidos;
import com.upc.movilmarket.entidades.Proveedor;

import java.util.HashMap;
import java.util.UUID;

public class AgregarProveedores extends AppCompatActivity {
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    EditText txtNombre, txtdistrito,txtruc,txtdireccion;
    Button btnRegistrar;
    StorageReference storageReference;
    ProgressDialog cargando;
    boolean registrar = true;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_proveedores);
        inicializarFirebase();
        txtNombre = findViewById(R.id.txtnombreproveedor);
        txtdistrito = findViewById(R.id.txtdistritoproveedor);
        txtruc = findViewById(R.id.txtrucproveedor);
        txtdireccion = findViewById(R.id.txtdireccionproveedor);
        btnRegistrar=findViewById(R.id.btnregistrarproveedor);
        verificar();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (registrar==true){

                        registrar();
                }else {

                    actualizar();
                }



            }
        });
    }

    private void verificar() {

        if (getIntent().hasExtra("id")){

            registrar=false;

            id =getIntent().getStringExtra("id");

            txtNombre.setText(getIntent().getStringExtra("nombre"));
            txtdistrito.setText(getIntent().getStringExtra("distrito"));
            txtruc.setText(getIntent().getStringExtra("ruc"));
            txtdireccion.setText(getIntent().getStringExtra("direccion"));


        }else {
            registrar=true;
        }
    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
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
                startActivity(new Intent(AgregarProveedores.this, InicioActivity.class));
                return true;

            case R.id.nav_mapa:
                startActivity(new Intent(AgregarProveedores.this, Maps.class));
            case R.id.nav_editprov:
                startActivity(new Intent(AgregarProveedores.this, Proveedores.class));

                return true;

            case R.id.nav_prov:
                startActivity(new Intent(AgregarProveedores.this, AgregarProveedores.class));
                return true;
            case R.id.nav_menu:
                startActivity(new Intent(AgregarProveedores.this, AbarrotesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);


    }

    public void  registrar(){

        AlertDialog.Builder ventana = new AlertDialog.Builder(AgregarProveedores.this);
        ventana.setTitle("Mensaje Informativo");
        ventana.setMessage("Proveedor Registrado");
        ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AgregarProveedores.this, Proveedores.class);
                startActivity(intent);
            }
        });
        ventana.create().show();

        String nombres = txtNombre.getText().toString();
        String Distritos = txtdistrito.getText().toString();
        String Ruc = txtruc.getText().toString();
        String Direcciones = txtdireccion.getText().toString();



        Proveedor p = new Proveedor();
        p.setId(UUID.randomUUID().toString());
        p.setNombre(nombres);
        p.setDistrito(Distritos);
        p.setRuc(Ruc);
        p.setDireccion(Direcciones);


        dbReference.child("Proveedores").child(p.getId()).setValue(p);

    }

    private void actualizar(){

        String nombres = txtNombre.getText().toString();
        String Distritos = txtdistrito.getText().toString();
        String Ruc = txtruc.getText().toString();
        String Direcciones = txtdireccion.getText().toString();
        HashMap map = new HashMap();
        map.put("nombre",nombres);
        map.put("distrito", Distritos);
        map.put("ruc", Ruc);
        map.put("direccion", Direcciones);

        dbReference.child("Proveedores").child(id).updateChildren(map);
                Toast.makeText(this,"Se Actualizo Proveedor",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AgregarProveedores.this, Proveedores.class);
        startActivity(intent);

    }

}