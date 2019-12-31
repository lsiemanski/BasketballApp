package android.basketballapp.adapter;

import android.basketballapp.DrillActivity;
import android.basketballapp.R;
import android.basketballapp.entity.Category;
import android.basketballapp.entity.Drill;
import android.basketballapp.entity.DrillAndCategory;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.icu.util.ULocale;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DrillListAdapter extends RecyclerView.Adapter<DrillListAdapter.DrillViewHolder> {

    class DrillViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryNameView;
        private final TextView drillNameView;
        private final ImageView imageView;
        private final Button selectButton;
        private String htmlFile;
        private int drillId;

        private DrillViewHolder(View itemView) {
            super(itemView);
            categoryNameView = itemView.findViewById(R.id.category_name_tv);
            drillNameView = itemView.findViewById(R.id.name_tv);
            imageView = itemView.findViewById(R.id.category_picture);
            selectButton = itemView.findViewById(R.id.select_button);

            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!htmlFile.equals("")) {
                        Intent drillActivityIntent = new Intent(context, DrillActivity.class);
                        drillActivityIntent.putExtra("htmlFile", htmlFile);
                        drillActivityIntent.putExtra("drillName", drillNameView.getText());
                        drillActivityIntent.putExtra("drillId", drillId);
                        drillActivityIntent.putExtra("playerId", playerId);
                        context.startActivity(drillActivityIntent);
                    }
                }
            });
        }
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<DrillAndCategory> drills;
    private int playerId;

    public DrillListAdapter(Context context, int playerId) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) { return position % 2; }

    @NonNull
    @Override
    public DrillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        switch (viewType) {
            case 0:
                itemView = inflater.inflate(R.layout.drill_light_panel, parent, false);
                break;
            case 1:
                itemView = inflater.inflate(R.layout.drill_dark_panel, parent, false);
                break;
        }

        return new DrillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrillViewHolder holder, int position) {
        if(drills != null) {
            DrillAndCategory current = drills.get(position);
            holder.categoryNameView.setText(current.category.name);
            holder.drillNameView.setText(current.drill.name);
            holder.htmlFile = current.drill.htmlFile;
            holder.drillId = current.drill.drillId;

            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(current.category.image, "drawable", context.getPackageName());
            holder.imageView.setImageDrawable(resources.getDrawable(resourceId, context.getTheme()));
        }
    }

    public void setDrills(List<DrillAndCategory> drills) {
        this.drills = drills;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(drills != null)
            return drills.size();
        return 0;
    }
}
