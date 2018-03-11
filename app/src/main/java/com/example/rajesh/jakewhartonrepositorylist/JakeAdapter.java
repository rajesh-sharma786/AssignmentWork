package com.example.rajesh.jakewhartonrepositorylist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class JakeAdapter extends RealmRecyclerViewAdapter<JakeWhartonDataEntity, JakeAdapter.JakeViewHolder> {

    Context _activityContext;
    int _number=1;

    public JakeAdapter(RealmResults<JakeWhartonDataEntity> jakeworks, Context _activityContext) {
        super(jakeworks, true);
        this._activityContext=_activityContext;
    }

    // create new views (invoked by the layout manager)
    @Override
    public JakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        return new JakeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wharton_entity, parent, false));
    }

    @Override
    public void onBindViewHolder(JakeViewHolder holder, final int position) {
        // get the repository info
        final JakeWhartonDataEntity entity = getItem(position);
        int _pos=position;
        _pos=_pos+1;
        if((_pos== 12*_number))
        {
            ((JakeActivity)_activityContext).startIntentMethod((_number+1),15);
            _number+=1;

        }

        if(entity != null) {
            holder.bind(entity);
        }
    }

    public static class JakeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_books)
        CardView card;

        @BindView(R.id.text_title)
        TextView textTitle;

        @BindView(R.id.text_description)
        TextView textDescription;

        @BindView(R.id.text_language)
        TextView textLanguage;

        @BindView(R.id.text_stargazer)
        TextView textStargazer;

        @BindView(R.id.text_watcher)
        TextView textWatcher;

        @BindView(R.id.image_background)
        ImageView imageBackground;

        final Context context;


        public JakeViewHolder(View itemView) {
            // standard view holder pattern with ButterKnife view injection
            super(itemView);
            this.context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(final JakeWhartonDataEntity entity) {
            // cast the generic view holder to our specific one
            // set the title and the snippet

            textTitle.setText(entity.getName());
            textDescription.setText(entity.getDescription());

            if(entity.getLanguage().contentEquals("null"))
            {
                textLanguage.setVisibility(View.GONE);
            }
            else
            {
                textLanguage.setText(entity.getLanguage());
            }


            textStargazer.setText(String.valueOf(entity.getStargazers_count()));
            textWatcher.setText(String.valueOf(entity.getWatchers_count()));


        }
    }
}
