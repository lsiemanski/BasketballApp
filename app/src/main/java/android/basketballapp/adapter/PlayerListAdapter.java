package android.basketballapp.adapter;

import android.basketballapp.R;
import android.basketballapp.entity.Player;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView positionView;
        private final TextView heightView;
        private final ImageView imageView;

        private PlayerViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_tv);
            positionView = itemView.findViewById(R.id.position_tv);
            heightView = itemView.findViewById(R.id.height_tv);
            imageView = itemView.findViewById(R.id.profile_picture);
        }
    }

    private final LayoutInflater inflater;
    private final Context context;
    private List<Player> players;

    public PlayerListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        switch(viewType) {
            case 0:
                itemView = inflater.inflate(R.layout.player_light_panel, parent, false);
                break;
            case 1:
                itemView = inflater.inflate(R.layout.player_dark_panel, parent, false);
                break;
        }
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        if(players != null) {
            Player current = players.get(position);
            holder.nameView.setText(current.getName() + " " + current.getSurname());
            holder.positionView.setText(current.getPosition().getDescription());
            holder.heightView.setText(current.getHeight() + "cm");

            if(current.getImage() != null) {
                ContextWrapper cw = new ContextWrapper(context);
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File mypath = new File(directory, current.getImage());

                holder.imageView.setImageDrawable(Drawable.createFromPath(mypath.toString()));
            } else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.profile, context.getTheme()));
            }



        } else {

        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(players != null)
            return players.size();
        return 0;
    }
}
