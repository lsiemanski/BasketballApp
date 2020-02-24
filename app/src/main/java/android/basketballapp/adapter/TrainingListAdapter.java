package android.basketballapp.adapter;

import android.basketballapp.R;
import android.basketballapp.TrainingSummaryActivity;
import android.basketballapp.entity.Training;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingViewHolder> {

    class TrainingViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTextView;
        private final TextView totalTextView;
        private final Button selectButton;
        private int trainingId;

        public TrainingViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.training_item_date);
            totalTextView = itemView.findViewById(R.id.training_item_total);
            selectButton = itemView.findViewById(R.id.select_button);

            selectButton.setOnClickListener(v -> {
                Intent trainingSummaryActivityIntent = new Intent(context, TrainingSummaryActivity.class);
                trainingSummaryActivityIntent.putExtra("trainingId", trainingId);
                trainingSummaryActivityIntent.putExtra("drillId", drillId);
                context.startActivity(trainingSummaryActivityIntent);
            });
        }
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.training_item, parent, false);
        return new TrainingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        if(trainings != null) {
            Training current = trainings.get(position);
            holder.dateTextView.setText(new SimpleDateFormat("dd-MM-yyyy").format(current.date));
            holder.totalTextView.setText(current.totalMakes + "/" + current.totalShots);
            holder.trainingId = current.trainingId;
        }
    }

    @Override
    public int getItemCount() {
        if(trainings != null)
            return trainings.size();
        return 0;
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<Training> trainings;
    private int drillId;

    public TrainingListAdapter(Context context, int drillId) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.drillId = drillId;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
        notifyDataSetChanged();
    }
}
