package edu.emich.thp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.emich.thp.models.GroutItem;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<GroutItem> pageResults;

    public RecyclerAdapter(ArrayList<GroutItem> pageResults) {
        this.pageResults = pageResults;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView brand_name;
        private TextView brand_code;
        private TextView grout_name;
        private TextView color_hex;
        private TextView product_link;

        public MyViewHolder(final View view) {
            super(view);
            brand_name = view.findViewById(R.id.brand);
            brand_code = view.findViewById(R.id.color);
            grout_name = view.findViewById(R.id.grout);
            color_hex = view.findViewById(R.id.hex);
            product_link = view.findViewById(R.id.link);
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
        String this_text = pageResults.get(position).getBrandName();
        holder.brand_name.setText(this_text);

        this_text = pageResults.get(position).getBrandCode();
        holder.brand_code.setText(this_text);

        this_text = pageResults.get(position).getGroutName();
        holder.grout_name.setText(this_text);


        int temp = pageResults.get(position).getColorHex();

        // Red
        String temp2 = Integer.toString(temp);
        if(temp2.length() < 9) {
            temp2 = "0" + temp2;
        }
        String red = Integer.toHexString(Integer.parseInt(temp2.substring(0, 3)));
        if(red.length() < 2) {
            red = "0" + red;
        }

        // Green
        temp2 = Integer.toString(temp);
        if(temp2.length() < 9) {
            temp2 = "0" + temp2;
        }
        String green = Integer.toHexString(Integer.parseInt(temp2.substring(3, 6)));
        if(green.length() < 2) {
            green = "0" + red;
        }

        // Blue
        temp2 = Integer.toString(temp);
        if(temp2.length() < 9) {
            temp2 = "0" + temp2;
        }
        String blue = Integer.toHexString(Integer.parseInt(temp2.substring(6, 9)));
        if(blue.length() < 2) {
            blue = "0" + red;
        }

        this_text = red + green + blue;
        holder.color_hex.setText(this_text);

        this_text = pageResults.get(position).getProductLink();
        holder.product_link.setText(this_text);
    }

    @Override
    public int getItemCount() {
        return pageResults.size();
    }
}