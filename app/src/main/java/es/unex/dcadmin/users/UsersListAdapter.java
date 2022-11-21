package es.unex.dcadmin.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.dcadmin.R;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {
    private List<Member> mItems = new ArrayList<Member>();

    public interface OnItemClickListener {
        void onItemClick(Member item);
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public UsersListAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UsersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new UsersListAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Member item) {
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

    public void load(List<Member> items){
        mItems.clear();
        mItems = items;
        notifyDataSetChanged();
    }

    public void update(Member item){
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

    public void delete(Member item){
        int pos = mItems.indexOf(item);
        mItems.remove(item);

        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,mItems.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView server;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            server = itemView.findViewById(R.id.serverName);
        }

        public void bind(final Member toDoItem, final OnItemClickListener listener) {
            name.setText(toDoItem.getName());
            server.setText(toDoItem.getServer());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(toDoItem);
                }
            });
        }
    }

}
