package com.example.babyneed.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.babyneed.R;
import com.example.babyneed.data.DatabaseHandler;
import com.example.babyneed.model.BabyItem;
import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
  
    private Context context;
    private  List<BabyItem>babyItemList;
    private AlertDialog.Builder builder;
    private  AlertDialog alertDialog;
    private LayoutInflater inflater;
    public RecyclerViewAdapter(Context context, List<BabyItem> babyItemList) {
        this.context = context;
        this.babyItemList= babyItemList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BabyItem babyItem = babyItemList.get(position);

        holder.itemName.setText(MessageFormat.format("Item:{0}", babyItem.getItemName()));
        holder.itemQuantity.setText(String.valueOf(MessageFormat.format("Quantity:{0}", babyItem.getItemQuantity())));
        holder.itemColor.setText(MessageFormat.format("Color:{0}", babyItem.getItemColor()));
        holder.itemSize.setText(MessageFormat.format("Size:{0}", String.valueOf(babyItem.getItemSize())));
        holder.itemDate.setText(MessageFormat.format("Added on:{0}", babyItem.getDateItemAdded()));
    }

    @Override
    public int getItemCount() {
        return babyItemList.size();
    }
     
    public class  ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView itemName;
        public TextView itemQuantity;
        public TextView itemColor;
        public TextView itemSize;
        public TextView itemDate;
        public Button editButton;
        public  Button deleteButton;

        public int id;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemColor = itemView.findViewById(R.id.item_color);
            itemSize = itemView.findViewById(R.id.item_size);
            itemDate = itemView.findViewById(R.id.item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position;
            switch (v.getId())
            {
                case R.id.editButton:
//                    editItem();
                    break;
                case R.id.deleteButton:
                   position = getAdapterPosition();
                   BabyItem item = babyItemList.get(position);

                   deleteItem(item.getId());

                    break;


            }

        }
        private  void  deleteItem(final int babyItemId)
        {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.confirmation_pop,null);

           Button noButton = v.findViewById(R.id.confirm_no_button);
          Button  yesButton = v.findViewById(R.id.confirm_yes_button);

            builder.setView(v);
            alertDialog = builder.create();
            alertDialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteBabyItem(babyItemId);
                    babyItemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    alertDialog.dismiss();

                }
            });



        }
//        private void editItem() {
//            DatabaseHandler db = new DatabaseHandler(context);
//            db.updateBabyItem();
//        }
    }


}
