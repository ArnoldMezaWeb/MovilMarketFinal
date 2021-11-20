package com.upc.movilmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {
    Button btnregistrar;
    EditText txt_usuario,txt_correo,txt_pass,txt_cel;



    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        firebaseAuth = FirebaseAuth.getInstance();
        asignarreferencias();


    }

    private void asignarreferencias(){

        txt_usuario = findViewById(R.id.txt_usuario);
        txt_correo = findViewById(R.id.txt_correo);
        txt_pass = findViewById(R.id.txt_pass);
        txt_cel = findViewById(R.id.txt_cel);

        btnregistrar = findViewById(R.id.btnregistro);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = txt_correo.getText().toString();
                String usuario = txt_usuario.getText().toString();
                String password = txt_pass.getText().toString();
               String celular = txt_cel.getText().toString();

                if(TextUtils.isEmpty(correo)  || TextUtils.isEmpty(password) || TextUtils.isEmpty(usuario) || TextUtils.isEmpty(celular)){

                    AlertDialog.Builder ventana = new AlertDialog.Builder(RegistroActivity.this);
                    ventana.setTitle("Mensaje Informativo");
                    ventana.setMessage("Por favor completar todos los campos");

                    ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    });
                            ventana.create().show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegistroActivity.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            return;
                        }
                    }
                });

            }

        });
    }

    private boolean capturardatos(){


        String usuario = txt_usuario.getText().toString();
        String correo = txt_correo.getText().toString();
        String pass = txt_pass.getText().toString();






        boolean valida = true;
        if (usuario.equals("")) {
            txt_usuario.setError("Nombrees obligatorio");
            valida = false;
        }
        if (correo.equals("")) {
            txt_correo.setError("Categoria es obligatorio");
            valida = false;
        }
        if (pass.equals("")) {
            txt_pass.setError("Categoria es obligatorio");
            valida = false;
        }

        try {


            int celular = Integer.parseInt(txt_cel.getText().toString() + "");

            return valida;

        }catch(
                NumberFormatException nfe)

        {

            txt_cel.setError("Costo es obligatorio");

        }
        return false;

    }


}