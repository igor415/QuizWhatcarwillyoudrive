package com.varivoda.igor.quiz_whatcarwillyoudrive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.varivoda.igor.quiz_whatcarwillyoudrive.FirestoreDataObjects.Opcija;

public class HomeAdapter extends FirestoreRecyclerAdapter<Opcija, HomeAdapter.HomeViewHolder> {
    private onItemClickListener listener;

    public HomeAdapter(@NonNull FirestoreRecyclerOptions<Opcija> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull Opcija model) {
        holder.naziv.setText(model.getNaziv());
        Picasso.get().load(model.getUrl()).centerInside().fit().into(holder.slika);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,null,false);
        return new HomeViewHolder(v);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView naziv;
        ImageView slika;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            naziv = itemView.findViewById(R.id.naziv);
            slika = itemView.findViewById(R.id.slika);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION && listener!= null){
                    listener.onItemClick(getSnapshots().getSnapshot(pos),pos);
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
