package com.upc.movilmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upc.movilmarket.entidades.Pedidos;
import com.upc.movilmarket.entidades.Productos;

import java.util.UUID;

public class DetallePedido extends AppCompatActivity {

    TextView nombre,costo,cantidad;
    Button registro;
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    EditText direccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        inicializarFirebase();
        nombre = findViewById(R.id.detallenombre);
        costo = findViewById(R.id.detallecosto);
        cantidad = findViewById(R.id.detallecantidad);
        registro = findViewById(R.id.registrardetalle);
        direccion = findViewById(R.id.txtDireccion);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ventana = new AlertDialog.Builder(DetallePedido.this);
                ventana.setTitle("Mensaje Informativo");
                ventana.setMessage("Pedido Confirmado");
                ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DetallePedido.this, AbarrotesActivity.class);
                        startActivity(intent);
                    }
                });
                ventana.create().show();

                    String nombres = nombre.getText().toString();
                    String Direcciones = direccion.getText().toString();
                    Integer cantidades =Integer.parseInt(cantidad.getText().toString());
                    Integer costos = Integer.parseInt(costo.getText().toString());


                    Pedidos p = new Pedidos();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombres);
                    p.setCosto(costos);
                    p.setCantidad(cantidades);
                    p.setDireccion(Direcciones);


                    dbReference.child("Pedido").child(p.getId()).setValue(p);




            }
        });


        String value1 = "";
        String value2 = "";
        String value3 = "";

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            value1 = extras.getString("nombre");
            value2 = extras.getString("costo");
            value3 = extras.getString("cantidad");


        }



        // y así los configuras
        nombre.setText(value1);
        costo.setText(value2);
        cantidad.setText(value3);
    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();


    }
}