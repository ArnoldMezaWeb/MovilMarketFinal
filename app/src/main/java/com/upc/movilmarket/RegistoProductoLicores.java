package com.upc.movilmarket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.upc.movilmarket.entidades.Productos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class RegistoProductoLicores extends AppCompatActivity {
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    EditText txtNombre, txtcategoria,txtcosto;
    Button btnRegistrar,btnseleccionar,btncamara;
    ImageView foto;
    StorageReference storageReference;
    ProgressDialog cargando;

    Bitmap thumb_bitmap = null;
    String rutaImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo_producto_licores);
        inicializarFirebase();
        asignarReferencias();
    }

    private  void inicializarFirebase(){

        FirebaseApp.initializeApp(this);
        fbDatabase = FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();


    }

    private void asignarReferencias(){

        txtNombre = findViewById(R.id.txtnombreproveedor);
        txtcategoria = findViewById(R.id.txtdistritoproveedor);
        txtcosto = findViewById(R.id.txtrucproveedor);
        btnRegistrar = findViewById(R.id.btnregistrarproveedor);
        foto = findViewById(R.id.imgproducto);
        btnseleccionar = findViewById(R.id.btnseleccionar);
        btncamara = findViewById(R.id.btn_tomarfoto);

        btncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrircamara();

            }
        });



        btnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                capturardatos();


            }



        });

        storageReference = FirebaseStorage.getInstance().getReference().child("Licores");
        cargando = new ProgressDialog(this);

        btnseleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(RegistoProductoLicores.this);

            }

        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            Uri imagenuri = CropImage.getPickImageResultUri(this,data);

            CropImage.activity(imagenuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640, 480)
                    .setAspectRatio(2, 1).start(RegistoProductoLicores.this);


        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode ==RESULT_OK){

                Uri resultUri = result.getUri();
                File url = new File(resultUri.getPath());

                Picasso.with(this).load(url).into(foto);

                //Comprimir Datos

                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxHeight(480)
                            .setMaxWidth(640)
                            .setQuality(90)
                            .compressToBitmap(url);
                }catch (IOException e){

                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 90 ,byteArrayOutputStream);
                final  byte [] thumb_byte = byteArrayOutputStream.toByteArray();


                btnRegistrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder ventana = new AlertDialog.Builder(RegistoProductoLicores.this);
                        ventana.setTitle("Mensaje Informativo");
                        ventana.setMessage("Se registro Correctamente");
                        ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RegistoProductoLicores.this, LicoresActivity.class);
                                startActivity(intent);
                            }
                        });
                        ventana.create().show();


                    /*Productos p = new Productos();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setCategoria(categoria);
                    p.setCosto(costo);
                  */

                        //dbReference.child("Producto").child(p.getId()).setValue(p);


                        StorageReference  ref = storageReference.child("foto.jpg");
                        UploadTask uploadTask = ref.putBytes(thumb_byte);

                        //subir imagen a storage
                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()){
                                    throw Objects.requireNonNull(task.getException());

                                }
                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String nombre = txtNombre.getText().toString();
                                String categoria = txtcategoria.getText().toString();
                                Integer costo = Integer.parseInt(txtcosto.getText().toString());
                                Uri  downloaduri = task.getResult();

                                Productos p = new Productos();
                                p.setId(UUID.randomUUID().toString());
                                p.setNombre(nombre);
                                p.setCategoria(categoria);
                                p.setCosto(costo);
                                p.setFoto(downloaduri.toString());
                                dbReference.child("Licores").child(p.getId()).setValue(p);
                                //dbReference.push().child("Producto").setValue(nombre,downloaduri.toString());
                                //Productos gal = new Productos(nombre,categoria,costo);
                                cargando.dismiss();


                            }
                        });


                    }


                });

            }


        }

    }


    private boolean capturardatos(){


        String Nombre = txtNombre.getText().toString();
        String Catergoria = txtcategoria.getText().toString();





        boolean valida = true;
        if (Nombre.equals("")) {
            txtNombre.setError("Nombrees obligatorio");
            valida = false;
        }
        if (Catergoria.equals("")) {
            txtcategoria.setError("Categoria es obligatorio");
            valida = false;
        }

        try {


            int costo = Integer.parseInt(txtcosto.getText().toString() + "");

            return valida;

        }catch(
                NumberFormatException nfe)

        {

            txtcosto.setError("Costo es obligatorio");

        }
        return false;

    }

    private File crearImagen() throws IOException {

        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);

        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }
    private void abrircamara() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



        File imagenARCHIVO = null;

        try {
            imagenARCHIVO = crearImagen();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

        if (imagenARCHIVO != null) {

            Uri imagen= FileProvider.getUriForFile(this, "com.upc.movilmarket.fileprovider", imagenARCHIVO);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagen);
            startActivityForResult(intent, 1);

        }

    }

}