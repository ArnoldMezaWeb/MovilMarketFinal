package com.upc.movilmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class InicioActivity extends AppCompatActivity {
    Button btnregistrar,btniniciar;
    EditText txtcorreo,txtconstraseña;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        asignarreferencias();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void asignarreferencias()

    {
        txtcorreo = findViewById(R.id.txtusuarioinicio);
        txtconstraseña = findViewById(R.id.txtpassinicio);
        btniniciar = findViewById(R.id.btniniciar);
        btnregistrar = findViewById(R.id.btnregistrar);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent( InicioActivity.this, RegistroActivity.class);
              startActivity(intent);
          }
      });

        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtcorreo.getText().toString();
                String contraseña = txtconstraseña.getText().toString();

                if(TextUtils.isEmpty(correo)){
                    Toast.makeText(InicioActivity.this,"Porfavor ingrese Correo",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(contraseña)){
                    Toast.makeText(InicioActivity.this,"Porfavor ingrese contraseña",Toast.LENGTH_LONG).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(correo,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String tipoUsuario= "administrador@hotmail.com";
                        if(task.isSuccessful()){

                            Intent intent = new Intent( InicioActivity.this,AbarrotesActivity.class);
                            intent.putExtra("usuarioIngresado", correo);
                            startActivity(intent);



                       }else{
                            //display some message here
                            AlertDialog.Builder ventana = new AlertDialog.Builder(InicioActivity.this);
                            ventana.setTitle("Mensaje Informativo");
                            ventana.setMessage("Correo o Contraseña incorrecta");
                            ventana.setPositiveButton("Aceptar",null);
                            ventana.create().show();
                        }
                    }








                });


            }






       });
    }

}