package fr.greta60.planeteappgreta60.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.greta60.planeteappgreta60.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.ClientInfoStatus;
import java.util.List;

import fr.greta60.planeteappgreta60.model.Planete;
import fr.greta60.planeteappgreta60.view.RecyclerPlaneteView;

//Creer une class de type RecyclerView.Adapter<RecyclerPlaneteView> qui contient
public class RecyclerPlaneteAdapter extends RecyclerView.Adapter<RecyclerPlaneteView>  {
    //variable pour stock la liste des planètes
    private List<Planete> list;
    //pour stocker la position d'élément cliqué
    private int clickedPosition = RecyclerView.NO_POSITION;
    //écouteur pour créer le menu contextuel
    private View.OnCreateContextMenuListener menuListener;
    //constructeur pour initialiser la liste des planetes => list
    public  RecyclerPlaneteAdapter(@NonNull List<Planete> planetes){
        super();
        list = planetes;
    }
    @NonNull
    @Override
    //Comment créer la view
    public RecyclerPlaneteView onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        //objet qui permet deTransformer une ressource (xml, png) en objet java
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        //Création d'un objet type view
        View view = li.inflate(R.layout.recycler_item, parent, false);
        //création d'un objet de type RecyclerView.ViewHolder => objet contenant la vue
        return new RecyclerPlaneteView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPlaneteView holder, final int position) {
        Planete p = list.get(position);
        holder.setItem(p);
        //ajouter un écouteur d'évènement sur chaque élément de la liste
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickedPosition(position);
            }
        });
        //holder.itemView.setOnClickListener((view)->setClickedPosition(position));//expression Lambda
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                setClickedPosition(position);
                return false;
            }
        });
        //SI la possition de l'élément cliqué est égal à sa position initiale, on change le font en LTGRAY si non TRANSPARENT
        holder.itemView.setBackgroundColor(getClickedPosition() == position ? Color.LTGRAY : Color.TRANSPARENT);
        //ajouter des menu contextuel
        holder.itemView.setOnCreateContextMenuListener(menuListener);
    }

    @Override
    public int getItemCount() {//nombre d'éléments de la liste
        return list.size();
    }

    public int getClickedPosition() {
        return clickedPosition;
    }

    public void setClickedPosition(int clickedPosition) {
        notifyItemChanged(this.clickedPosition);
        this.clickedPosition = clickedPosition;
        notifyItemChanged(clickedPosition);
    }

    public View.OnCreateContextMenuListener getMenuListener() {
        return menuListener;
    }

    public void setMenuListener(View.OnCreateContextMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public void addPlanete(Planete planete) {
        list.add(planete);
        notifyItemInserted(list.size()-1);
    }

    public void removePlanete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        //envoie une notification à la vue
        notifyDataSetChanged();
    }
}
