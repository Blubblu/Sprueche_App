package vhbandroidprogrammierung.de.spruecheapp.RecyclerViewStuff;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vhbandroidprogrammierung.de.spruecheapp.Activities.MainActivity;
import vhbandroidprogrammierung.de.spruecheapp.R;
import vhbandroidprogrammierung.de.spruecheapp.Saying;

public class ViewHolderSaying extends ViewHolder implements View.OnClickListener {

    private static final String TAG = "ViewHolderReminder";
    private CardView cardView;
    private TextView tv_saying, tv_author, tv_category;
    private ImageView iv_favorite, iv_user_saying;
    private Context context;
    private View view;

    public ViewHolderSaying(Context context, View view) {
        super(view);

        this.view = view;
        this.context = context;

        cardView = (CardView) this.view.findViewById(R.id.cardview);

        tv_saying = (TextView) this.view.findViewById(R.id.tv_saying);
        tv_author = (TextView) this.view.findViewById(R.id.tv_author);
        tv_category = (TextView) this.view.findViewById(R.id.tv_category);

        iv_favorite = (ImageView) this.view.findViewById(R.id.iv_star);
        iv_user_saying = (ImageView) this.view.findViewById(R.id.iv_user_saying);

        iv_favorite.setOnClickListener(this);;
    }

    /**
     * Providing the content for the Views from the Saying Items
     *
     * @param item ListItem to ask for content
     */
    public void bindType(ListItemSaying item) {

        String saying = ((Saying) item).getSaying();
        String author = ((Saying) item).getAuthor();
        String category = ((Saying) item).getCategory();

        boolean isFavorite = ((Saying) item).isFavorite();
        boolean isUserSaying = ((Saying) item).isUserSaying();

        favIonColorChange(isFavorite);
        userIconChange(isUserSaying);

        tv_saying.setText(saying);
        tv_author.setText(author);
        tv_category.setText(category);

        if (isFavorite) {
            //TODO/ Filled star Image
        }
    }

    /**
     * Wenn der Spruch ein eigener ist, das Icon einblenden, ansonsten vestecken
     */
    private void userIconChange(boolean isUserSaying) {
        if (!isUserSaying) {
            iv_user_saying.setVisibility(View.GONE);
        } else {
            iv_user_saying.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Wenn der Spruch ein Favorit ist, ist das Icon orange, ansonsten grau und ohne Füllung
     */
    private void favIonColorChange(boolean isFavorite) {

        if (!isFavorite) {
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_star_outline_grey600_36dp, null);
            iv_favorite.setImageDrawable(drawable);
        } else {
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_star_white_36dp, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int color = ContextCompat.getColor(context, R.color.orange);
                drawable.setTint(color);
            }
            iv_favorite.setImageDrawable(drawable);
        }

    }


    /**
     * Clicking on the anywhere triggers a dialog to change title and reminder.
     * Clicking the switch activates or deactivates the card.
     *
     * @param v The View that has been clicked
     */
    @Override
    public void onClick(View v) {

        int apaterPosition = getAdapterPosition();

        switch (v.getId()) {

            case R.id.iv_star:
                callHome(apaterPosition, cardView);
                break;

            default:
                break;
        }
    }

    /**
     * Calling MainActivity from onClick
     *
     * @param pos
     * @param cv
     */
    public void callHome(int pos, CardView cv) {
        ((MainActivity) context).favStarHasBeenClicked(pos, cv);
    }
}