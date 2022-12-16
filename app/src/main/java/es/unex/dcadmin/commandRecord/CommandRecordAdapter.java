package es.unex.dcadmin.commandRecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.dcadmin.R;

public class CommandRecordAdapter extends RecyclerView.Adapter<CommandRecordAdapter.ViewHolder> {
    private List<CommandRecord> mItems = new ArrayList<CommandRecord>();

    public interface OnItemClickListener {
        void onItemClick(CommandRecord item);     //Type of the element to be returned
    }

    public interface OnDeleteClickListener{ //Interfaz del listener de borrar
        void onDeleteClick(CommandRecord item);
    }

    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener; //Listener para borrar

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommandRecordAdapter(OnItemClickListener listener, OnDeleteClickListener deleteListener) {//El segundo parametro es para el listener de borrar

        this.listener = listener;
        this.deleteListener = deleteListener; //Listener de borrar
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.command_record_item,parent,false);

        return new ViewHolder(v);
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

    public void add(CommandRecord item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {

        return mItems.get(pos);

    }

    public void load(List<CommandRecord> items){

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public void update(CommandRecord item){
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

    public void delete(CommandRecord item){
        int pos = mItems.indexOf(item);
        mItems.remove(item);

        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,mItems.size());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView user;
        private TextView numExecutions;

        public ViewHolder(View itemView) {//itemVIew es la vista que contiene a todos los elementos
            super(itemView);

            name = itemView.findViewById(R.id.commandName);
            user = itemView.findViewById(R.id.command_user);
            numExecutions = itemView.findViewById(R.id.command_num_executions);
        }

        public void bind(final CommandRecord toDoItem, final OnItemClickListener listener, final OnDeleteClickListener deleteListener) { //Este ultimo parametro es para el listener de borrar
            //Vamos a vincular las vistas con los datos de toDoItem
            name.setText(toDoItem.getName());

            user.setText(toDoItem.getUserId());

            numExecutions.setText(String.valueOf(toDoItem.getNumExecutions()));

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(toDoItem);
                }
            });
        }
    }

}

