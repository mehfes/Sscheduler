package co.ubien.sscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<Event> events = new ArrayList<>();
    RelativeLayout[] days = new RelativeLayout[7];

    User user;
    Post post;

    private void displayUserCard(){
        ImageView avatar = findViewById(R.id.avatar_details);
        int avatarIndex = user.getAvatarIndex();
        avatar.setImageResource(findAvatar(avatarIndex));
        TextView scheduleText = findViewById(R.id.schedule_details);
        scheduleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        scheduleText.setGravity(Gravity.CENTER);
        scheduleText.setTextSize(dpToInt(8));
        scheduleText.setTextColor(getResources().getColor(R.color.lavender));
        TextView usernameText = findViewById(R.id.username_details);
        usernameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        usernameText.setGravity(Gravity.CENTER);
        usernameText.setTextSize(dpToInt(6));
        usernameText.setTextColor(getResources().getColor(R.color.lavender));
        scheduleText.setText(post.getTitle());
        usernameText.setText(user.getUsername());
    }

    private void displaySchedule(){
        for (int i = 0; i < events.size(); ++i) {
            Event e = events.get(i);
            Button temp = new Button(this);

            temp.setBackgroundColor(e.getColor());
            temp.setText(e.getName());
            int len = e.getName().length();
            int size = Math.min((int)(dpToInt(23) / (len * 1f)) , dpToInt(7));
            temp.setPadding(0,0,0,0);

            int width = RelativeLayout.LayoutParams.MATCH_PARENT;
            int height = (int)((e.getEndMins() - e.getStartMins())/60f * dpToInt(50));

            temp.setTextSize(size);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            int topMargin = (int)(e.getStartMins()/60f * dpToInt(50) + dpToInt(45));
            params.topMargin = topMargin;

            temp.setTypeface(Typeface.DEFAULT_BOLD);
            days[e.getDay()].addView(temp, params);
        }
    }

    private void addEvent(Event e){
        events.add(e);
    }

    private int dpToInt(int dp){
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics());
        return (int)px;
    }

    private void createDays(){
        days[0] = findViewById(R.id.rl_mondet);
        days[1] = findViewById(R.id.rl_tuedet);
        days[2] = findViewById(R.id.rl_weddet);
        days[3] = findViewById(R.id.rl_thudet);
        days[4] = findViewById(R.id.rl_fridet);
        days[5] = findViewById(R.id.rl_satdet);
        days[6] = findViewById(R.id.rl_sundet);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        createDays();

        Schedule schedule1 = new Schedule();
        schedule1.addEvent(new Event(30,60,"MATH", Color.RED,0));
        schedule1.addEvent(new Event(180,300,"CS", Color.GREEN,1));
        schedule1.addEvent(new Event(0,60,"FITNESS", Color.GRAY,2));
        schedule1.addEvent(new Event(180,300,"FRENCH", Color.CYAN,3));
        schedule1.addEvent(new Event(0,60,"MATH", Color.RED,3));
        schedule1.addEvent(new Event(0,60,"banyo", Color.RED,4));
        schedule1.addEvent(new Event(60,120,"xx", Color.GREEN,4));

        User u1 = new User("joshua",6);
        post = new Post("Fitness","",schedule1,u1);
        user = post.getUser();
        this.events = post.getSchedule().getEvents();
        displayUserCard();
        displaySchedule();
        displayLikeDislike();


        Button commentsButton = findViewById(R.id.commentsbutton_details);
        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, CommentActivity.class);
                startActivity(i);
            }
        });

    }

    private void displayLikeDislike(){
        LinearLayout outer = findViewById(R.id.outer_details);
        int w = LinearLayout.LayoutParams.MATCH_PARENT;
        int h = LinearLayout.LayoutParams.MATCH_PARENT;
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w,h);
        params.weight = 1;

        ImageView likeImage = new ImageView(this);
        ImageView dislikeImage = new ImageView(this);
        TextView likeCount = new TextView(this);
        TextView dislikeCount = new TextView(this);

        likeImage.setImageResource(R.drawable.baseline_thumb_up_24);
        dislikeImage.setImageResource(R.drawable.baseline_thumb_down_24);
        likeCount.setText(post.getLike() + "");
        dislikeCount.setText(post.getDisLike() + "");

        panel.addView(likeImage, params);
        panel.addView(likeCount, params);
        panel.addView(dislikeImage, params);
        panel.addView(dislikeCount, params);

        w = dpToInt(100);
        LinearLayout.LayoutParams outerparams = new LinearLayout.LayoutParams(w,h);
        outerparams.gravity = Gravity.CENTER;
        outer.addView(panel,outerparams);
    }

    public int findAvatar(int avatarIndex){
        if (avatarIndex == 1){
            return R.drawable.cat;

        }
        else if (avatarIndex == 2){
            return R.drawable.gamer;

        }
        else if (avatarIndex == 3){
            return R.drawable.woman1 ;
        }
        else if (avatarIndex == 4){
            return R.drawable.man1 ;

        }
        else if (avatarIndex == 5){
            return R.drawable.man2;

        }
        else if (avatarIndex == 6){
            return R.drawable.man3;

        }
        else if (avatarIndex == 7){
            return R.drawable.profile;

        }
        else if (avatarIndex == 8){
            return R.drawable.user;

        }
        else if (avatarIndex == 9){
            return R.drawable.woman;

        }
        return R.drawable.man;
    }

}