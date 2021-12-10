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
import com.upc.movilmarket.entidades.Licores;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPersonalizadoLicores extends RecyclerView.Adapter<AdaptadorPersonalizadoLicores.vistaHolder>{

    private Context context;
    private List<Licores>listaLicores = new ArrayList<>();


    public AdaptadorPersonalizadoLicores(Context c, List<Licores> lista){
       this.context = c;
        this.listaLicores = lista;

    }


    @NonNull
    @Override
    public AdaptadorPersonalizadoLicores.vistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.fila,parent, false);

        return new vistaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizadoLicores.vistaHolder holder, int position) {

      holder.filanombre.setText(listaLicores.get(position).getNombre()+"");
      holder.filacosto.setText(listaLicores.get(position).getCosto()+"");
       // holder.filacategoria.setText(listaProducto.get(position).getCategoria()+"");
       Glide.with(context).load(listaLicores.get(position).getFoto()).into(holder.imgproducto);
    }



    @Override
    public int getItemCount() {
        return listaLicores.size();
    }

    public class vistaHolder extends RecyclerView.ViewHolder{

        TextView filanombre,filacosto,filacategoria,cantidad;
        ImageView imgproducto;
        ImageView mas, menos;
        int total = 1;
        Button add;
        public vistaHolder(@NonNull View itemView) {
            super(itemView);
            filanombre = itemView.findViewById(R.id.cardnombreproveedor);
            filacosto = itemView.findViewById(R.id.carddistritoproveedor);
            //filacategoria = itemView.findViewById(R.id.txtcategoria);
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
