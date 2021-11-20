package com.upc.movilmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
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
        public vistaHolder(@NonNull View itemView) {
            super(itemView);
            filanombre = itemView.findViewById(R.id.txtproducto);
            filacosto = itemView.findViewById(R.id.txtcosto);
            //filacategoria = itemView.findViewById(R.id.txtcategoria);
            imgproducto = itemView.findViewById(R.id.imgproducto);
        }
    }
}
