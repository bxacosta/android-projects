package ec.edu.uce.vista;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ec.edu.uce.R;
import ec.edu.uce.modelo.Vehiculo;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder> implements Filterable {

    private List<Vehiculo> vehiculos;
    private List<Vehiculo> vehiculosFiltrados;
    private Context context;
    private ItemClickListener itemClickListener;

    public VehiculoAdapter(List<Vehiculo> vehiculos) {

        this.vehiculos = vehiculos;
        this.vehiculosFiltrados = vehiculos;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_list_vehiculo, viewGroup, false);

        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int i) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        NumberFormat nf = new DecimalFormat("###,##0.00");

        holder.placa.setText(vehiculos.get(i).getPlaca());
        holder.marca.setText(vehiculos.get(i).getMarca());
        holder.fabricado.setText(df.format(vehiculos.get(i).getFechaFabricacion()));
        holder.costo.setText("USD " + nf.format(vehiculos.get(i).getCosto()));
        holder.matriculado.setText(vehiculos.get(i).getMatriculado() ? "Si" : "No");
        holder.color.setText(vehiculos.get(i).getColor());
        System.out.println("BYTES:\n" + vehiculos.get(i).getFoto());
        holder.foto.setImageBitmap(vehiculos.get(i).getFoto());
        holder.estado.setText(vehiculos.get(i).getEstado()? "Si" : "No");
        holder.tipo.setText(vehiculos.get(i).getTipo());

        Vehiculo vehiculo = vehiculosFiltrados.get(i);
    }

    @Override
    public int getItemCount() {
        return vehiculosFiltrados.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public Vehiculo obtenerVehiculo(int posision) {
        return vehiculos.get(posision);
    }

    public void eliminarVehiculo(int posision) {
        Vehiculo eliminado = vehiculos.remove(posision);
        notifyItemRemoved(posision);
    }

    public void restaurarVehiculo(Vehiculo vehiculo, int posision) {
        vehiculos.add(posision, vehiculo);
        notifyItemInserted(posision);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    vehiculosFiltrados = vehiculos;
                } else {
                    List<Vehiculo> filteredList = new ArrayList<>();
                    for (Vehiculo row : vehiculos) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPlaca().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    vehiculosFiltrados = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = vehiculosFiltrados;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                vehiculosFiltrados = (ArrayList<Vehiculo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class VehiculoViewHolder extends RecyclerView.ViewHolder {
        public TextView placa;
        public TextView marca;
        public TextView fabricado;
        public TextView costo;
        public TextView matriculado;
        public TextView color;
        public CircleImageView foto;
        public TextView estado;
        public TextView tipo;

        public Button opciones;

        public VehiculoViewHolder(View itemView) {
            super(itemView);

            placa = itemView.findViewById(R.id.tvPlaca);
            marca = itemView.findViewById(R.id.tvMarca);
            fabricado = itemView.findViewById(R.id.tvFabricado);
            costo = itemView.findViewById(R.id.tvCosto);
            matriculado = itemView.findViewById(R.id.tvMatriculado);
            color = itemView.findViewById(R.id.tvColor);
            foto = itemView.findViewById(R.id.civFoto);
            estado = itemView.findViewById(R.id.tvEstado);
            tipo = itemView.findViewById(R.id.tvTipo);
            opciones = itemView.findViewById(R.id.btnOpciones);


            opciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}
