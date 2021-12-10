package com.upc.movilmarket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.Person;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upc.movilmarket.entidades.Limpieza;
import com.upc.movilmarket.entidades.Productos;
import com.upc.movilmarket.entidades.Proveedor;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AdaptadorPersonalizadoProveedor extends RecyclerView.Adapter<AdaptadorPersonalizadoProveedor.vistaHolder>{

    private Context context;
    private List<Proveedor>listaProveedores = new ArrayList<>();
    String id;
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;




    public AdaptadorPersonalizadoProveedor(Context c, List<Proveedor> lista){
        this.context = c;
        this.listaProveedores = lista;
        inicializarFirebase();

    }


    @NonNull
    @Override
    public AdaptadorPersonalizadoProveedor.vistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.filaproveedores,parent, false);


        return new vistaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizadoProveedor.vistaHolder holder, int position) {
      holder.filanombre.setText(listaProveedores.get(position).getNombre()+"");
      holder.filadistrito.setText(listaProveedores.get(position).getDistrito()+"");
      holder.filaruc.setText(listaProveedores.get(position).getRuc()+"");
        holder.filadireccion.setText(listaProveedores.get(position).getDireccion()+"");
        holder.filaeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, AgregarProveedores.class);
                intent.putExtra("id",listaProveedores.get(position).getId());
                intent.putExtra("nombre",listaProveedores.get(position).getNombre());
                intent.putExtra("distrito",listaProveedores.get(position).getDistrito());
                intent.putExtra("ruc",listaProveedores.get(position).getRuc());
                intent.putExtra("direccion",listaProveedores.get(position).getDireccion());

                context.startActivity(intent);


            }
        });


        holder.filaeliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }



    @Override
    public int getItemCount() {
        return listaProveedores.size();
    }

    public class vistaHolder extends RecyclerView.ViewHolder{

        TextView filanombre,filadistrito,filaruc,filadireccion;
        ImageButton filaeditar,filaeliminar;
        int total = 1;
        Button add;
        String id;
        public vistaHolder(@NonNull View itemView) {
            super(itemView);
            filanombre = itemView.findViewById(R.id.cardnombreproveedor);
            filadistrito = itemView.findViewById(R.id.carddistritoproveedor);
            filaruc = itemView.findViewById(R.id.cardrucproveedor);
            filadireccion = itemView.findViewById(R.id.carddireccionproveedor);
            filaeditar= itemView.findViewById(R.id.btnedit);
            filaeliminar=itemView.findViewById(R.id.btndelete);




        }


    }
    private void inicializarFirebase() {

        FirebaseApp.initializeApp(context);
        fbDatabase = FirebaseDatabase.getInstance();
        dbReference = fbDatabase.getReference();


    }

}
