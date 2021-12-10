package com.upc.movilmarket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.upc.movilmarket.entidades.Productos;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.vistaHolder>{

    private Context context;
    private List<Productos>listaProducto = new ArrayList<>();



    public AdaptadorPersonalizado(Context c, List<Productos> lista){
       this.context = c;
        this.listaProducto = lista;

    }


    @NonNull
    @Override
    public AdaptadorPersonalizado.vistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.fila,parent, false);


        return new vistaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizado.vistaHolder holder, int position) {

      holder.filanombre.setText(listaProducto.get(position).getNombre()+"");
      holder.filacosto.setText(listaProducto.get(position).getCosto()+"");

       // holder.filacategoria.setText(listaProducto.get(position).getCategoria()+"");
       Glide.with(context).load(listaProducto.get(position).getFoto()).into(holder.imgproducto);
    }



    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public class vistaHolder extends RecyclerView.ViewHolder{

        TextView filanombre,filacosto,filacategoria;
        ImageView imgproducto;
        TextView cantidad;
        ImageView mas, menos;
        int total = 1;
        Button add,filaeditar,filaeliminar;



        public vistaHolder(@NonNull View itemView) {
            super(itemView);

            filanombre = itemView.findViewById(R.id.cardnombreproveedor);
            filacosto = itemView.findViewById(R.id.carddistritoproveedor);
            filacategoria = itemView.findViewById(R.id.txtdistritoproveedor);
            imgproducto = itemView.findViewById(R.id.imgproducto);
            cantidad = itemView.findViewById(R.id.cantidadcarrito);
            mas = itemView.findViewById(R.id.btn_mas);
            menos =itemView.findViewById(R.id.btn_menos);
            add = itemView.findViewById(R.id.add_producto);


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetallePedido.class);
                    // envías los TextView de esta forma:
                    intent.putExtra("nombre", filanombre.getText());
                    intent.putExtra("costo", filacosto.getText());
                    intent.putExtra("cantidad", cantidad.getText());
                    v.getContext().startActivity(intent);

                }
            });




            mas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total<10){
                        total++;
                        cantidad.setText(String.valueOf(total));
                    }
                }
            });

            menos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total>1){
                        total--;
                        cantidad.setText(String.valueOf(total));
                    }
                }
            });



        }


    }


}
