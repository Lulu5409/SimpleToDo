package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

// Responsible
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener{
        void OnItemLongClicked(int position);



    }

    List<String> items;
    OnLongClickListener longClickListener;
    public ItemsAdapter(List<String> items,OnLongClickListener longClickListener){
        this.items=items;
        this.longClickListener=longClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // use layout inflator to inflate a view
        View todoView= LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);

        return new ViewHolder(todoView);
    }

    // responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the item at the position
        String item=items.get(position);
        // bind the item into the specified view holder
        holder.bind(item);

    }

    // tells how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide each access to each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=itemView.findViewById(android.R.id.text1);

        }
        // update the view inside the view holder with the data of string item
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // notify the listener the position
                    longClickListener.OnItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
