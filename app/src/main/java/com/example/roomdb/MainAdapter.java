package com.example.roomdb;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainData> mainDataList;
    private Activity context;
    private RoomDB db;

    public MainAdapter(List<MainData> mainDataList, Activity context) {
        this.mainDataList = mainDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainData data = mainDataList.get(position);
        db = RoomDB.getInstance(context);

        holder.textView.setText(data.getText());

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainData d = mainDataList.get(holder.getAdapterPosition());
                //get id
                int sID = d.getID();
                //get text

                String sText = d.getText();

                //create dialog
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                //assign varibale
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button bt_update = dialog.findViewById(R.id.bt_update);

                editText.setText(sText);

                bt_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String uText = editText.getText().toString().trim();
                        db.mainDao().update(sID,uText);
                        //notify when changed
                        mainDataList.clear();
                        mainDataList.addAll(db.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainData d = mainDataList.get(holder.getAdapterPosition());
                // delete text from db
                 db.mainDao().delete(d);

                 int postition = holder.getAdapterPosition();
                 mainDataList.remove(postition);
                 notifyItemRemoved(postition);
                 notifyItemRangeChanged(postition,mainDataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView btn_edit,btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            btn_edit = itemView.findViewById(R.id.bt_edit);
            btn_delete = itemView.findViewById(R.id.bt_delete);
        }
    }
}

