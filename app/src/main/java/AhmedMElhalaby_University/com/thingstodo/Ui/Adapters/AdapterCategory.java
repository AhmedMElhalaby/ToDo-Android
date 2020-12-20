package AhmedMElhalaby_University.com.thingstodo.Ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import AhmedMElhalaby_University.com.thingstodo.CallBack.OnItemClickListener;
import AhmedMElhalaby_University.com.thingstodo.Medules.Category;
import AhmedMElhalaby_University.com.thingstodo.R;


public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CustomView> {
    Context context;
    List<Category> mylist;
    private OnItemClickListener listener;
    int Layout;
    public static int ItemSelect = -1 ;


    public class CustomView extends RecyclerView.ViewHolder {
        TextView row_name , text_count;
        CardView card_view ;

        public CustomView(View v) {
            super(v);
            row_name = v.findViewById(R.id.text_name);
            text_count = v.findViewById(R.id.text_count);
            card_view = v.findViewById(R.id.card_view);
        }

    }

    public AdapterCategory() {
    }

    public AdapterCategory(Context context, List<Category> mylist, int Layout, OnItemClickListener listener) {
        this.context = context;
        this.mylist = mylist;
        this.listener = listener;
        this.Layout = Layout;
    }

    @Override
    public CustomView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(Layout, parent, false);
        CustomView viewholder = new CustomView(view);
        return viewholder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final CustomView holder, final int position) {
        holder.row_name.setText(mylist.get(position).getName() + "");
        holder.text_count.setText(  " 0 "+context.getResources().getString(R.string.task));

            if(ItemSelect == position){
                holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }else{
                holder.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.offwhite));
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view, position);
            }
        });


    }

    public void filterList(List<Category> spinnerItemList) {
        this.mylist = spinnerItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }


}

