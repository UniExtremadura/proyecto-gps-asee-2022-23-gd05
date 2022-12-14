package es.unex.dcadmin.command;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.dcadmin.MainActivity;
import es.unex.dcadmin.R;
import es.unex.dcadmin.discord.discordApiManager;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.ViewHolder> {
    private List<Command> mItems = new ArrayList<Command>();
    private Context applicationContext;

    public interface OnItemClickListener {
        void onItemClick(Command item);     //Type of the element to be returned
    }

    public interface OnDeleteClickListener{ //Interfaz del listener de borrar
        void onDeleteClick(Command item);
    }

    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener; //Listener para borrar

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommandAdapter(OnItemClickListener listener, OnDeleteClickListener deleteListener, Context applicationContext) {//El segundo parametro es para el listener de borrar

        this.listener = listener;
        this.deleteListener = deleteListener; //Listener de borrar
        this.applicationContext = applicationContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,//Parent es la vista padre de este elemento, en este caso la recyclerview, es el que contiene este elemento
                                         int viewType) {
        //A partir de un layout, le metemos los datos a la vista(lo mismo que hacíamos con los fragments, crear una vista)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.command_item,parent,false);//LayoutInflater solo se puede crear con from. Esto mete los datos en el layout todo_item

        return new ViewHolder(v, applicationContext);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener, deleteListener); //Este último parametro es para el listener de borrar
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Command item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        for(Command c:mItems){//Para Ejecutar comando, destruye todos los comandos
            discordApiManager.destruir(c.getTrigger_text());
        }

        discordApiManager.destruir("!");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("default");
        editor.commit();


        mItems.clear();
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {

        return mItems.get(pos);

    }

    public void load(List<Command> items){

        mItems.clear();
        mItems = items;

        notifyDataSetChanged();

    }

    public void update(Command item){
        int pos = -1;
        boolean found = false;
        for(int i = 0;i<mItems.size() && !found; i++){
            if(mItems.get(i).getId() == item.getId()){
                pos = i;
                found = true;
            }
        }

        if(pos != -1)
            mItems.set(pos,item);

        notifyDataSetChanged();

    }

    public void delete(Command item){
        int pos = mItems.indexOf(item);
        mItems.remove(item);
        discordApiManager.destruir(item.getTrigger_text());//De ejecutar comando

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = prefs.edit();

        if(prefs.getLong("default", -1) == item.getId())
        {
            discordApiManager.destruir("!");
            editor.remove("default");
            editor.commit();
        }

        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,mItems.size());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView deleteButton, isDefaultIcon;

        private Context applicationContext;

        public ViewHolder(View itemView, Context applicationContext) {//itemVIew es la vista que contiene a todos los elementos
            super(itemView);

            name = itemView.findViewById(R.id.commandName);
            deleteButton = itemView.findViewById(R.id.deleteCommand);
            isDefaultIcon = itemView.findViewById(R.id.isDefaultIcon);

            this.applicationContext = applicationContext;

        }

        public void bind(final Command toDoItem, final OnItemClickListener listener, final OnDeleteClickListener deleteListener) {
            //Vamos a vincular las vistas con los datos de toDoItem
            name.setText(toDoItem.getName());

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            long defaultCommandId = prefs.getLong("default", -1);

            isDefaultIcon.setVisibility(toDoItem.getId() == defaultCommandId ? View.VISIBLE : View.GONE);
            deleteButton.setOnClickListener(new View.OnClickListener() { //Listener de borrar
                @Override
                public void onClick(View v) {
                    deleteListener.onDeleteClick(toDoItem);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(toDoItem);
                }
            });
        }
    }

}