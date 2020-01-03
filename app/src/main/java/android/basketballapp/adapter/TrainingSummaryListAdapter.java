package android.basketballapp.adapter;

import android.basketballapp.R;
import android.basketballapp.entity.ShotAndSpot;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrainingSummaryListAdapter extends RecyclerView.Adapter<TrainingSummaryListAdapter.TrainingSummaryListViewHolder> {

    class TrainingSummaryListViewHolder extends RecyclerView.ViewHolder {
        private final TextView positionTextView;
        private final TextView resultTextView;
        private final TextView spotTextView;
        private final TextView totalTextView;

        public TrainingSummaryListViewHolder(@NonNull View itemView) {
            super(itemView);
            positionTextView = itemView.findViewById(R.id.shot_item_position);
            resultTextView = itemView.findViewById(R.id.shot_item_result);
            spotTextView = itemView.findViewById(R.id.shot_item_spot);
            totalTextView = itemView.findViewById(R.id.shot_item_total);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingSummaryListViewHolder holder, int position) {
        if(shots != null) {
            ShotAndSpot current = shots.get(position);
            holder.positionTextView.setText(current.spot.description);
            holder.resultTextView.setText(current.shot.isMade ? "MAKE" : "MISS");
            holder.resultTextView.setTextColor(current.shot.isMade ? context.getColor(R.color.green_spot_dark) : context.getColor(R.color.red_spot_dark));
            holder.spotTextView.setText(current.shot.madeFromSpot + "/" + current.shot.takenFromSpot);
            holder.totalTextView.setText(current.shot.madeTotal + "/" + current.shot.takenTotal);
        }
    }

    @NonNull
    @Override
    public TrainingSummaryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.shot_item, parent, false);
        return new TrainingSummaryListViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return shots.size();
    }

    private final LayoutInflater inflater;
    private List<ShotAndSpot> shots;
    private Context context;

    public TrainingSummaryListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        shots = new ArrayList<>();
    }

    public void setShots(List<ShotAndSpot> shots) {
        this.shots = shots;
        notifyDataSetChanged();
    }

    public void addShot(ShotAndSpot shot) {
        shots.add(0, shot);
        notifyDataSetChanged();
    }
}
