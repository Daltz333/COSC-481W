package edu.emich.tilere;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<Result_Items_Manager> pageResults;

    public RecyclerAdapter(ArrayList<Result_Items_Manager> pageResults) {
        this.pageResults = pageResults;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text1;

        public MyViewHolder(final View view) {
            super(view);
            text1 = view.findViewById(R.id.recycler1);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String this_text1 = pageResults.get(position).getResult();
        holder.text1.setText(this_text1);
    }

    @Override
    public int getItemCount() {
        return pageResults.size();
    }
}
