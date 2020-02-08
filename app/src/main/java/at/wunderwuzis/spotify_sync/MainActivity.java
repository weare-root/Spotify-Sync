package at.wunderwuzis.spotify_sync;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "8369c33ef7bf451296f570cc22880286";
    private static final String REDIRECT_URI = "spotifysync://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    private Button btn_submit;

    private Button btn_login;
    private Button btn_register;

    private TextView tv_username;
    private EditText et_password;


    private Map<String, String> users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //<editor-fold desc="Swap Click">
        SwapClick sc = new SwapClick();
        btn_login.setOnClickListener(sc);
        btn_register.setOnClickListener(sc);
        //</editor-fold>

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = (String) tv_username.getText();
                Editable password = et_password.getText();
                login(username,password);
            }
        });

    }

    public boolean login(String username, Editable password) throws Exception{
        if(users.containsKey(username)) {
            if( password.equals(users.get(username)) ) {
                return true;
            }
            else {
                return false;
            }
        }
        return new Exception();
    }

    public void init(){
        btn_submit = findViewById(R.id.btn_submit);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        tv_username = findViewById(R.id.tv_username);
        et_password = findViewById(R.id.et_password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {

            ConnectionParams connectionParams =
                    new ConnectionParams.Builder(CLIENT_ID)
                            .setRedirectUri(REDIRECT_URI)
                            .showAuthView(true)
                            .build();
            SpotifyAppRemote.connect(this, connectionParams,
                    new Connector.ConnectionListener() {
                        @Override
                        public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                            mSpotifyAppRemote = spotifyAppRemote;
                            Log.d("MainActivity", "Connected! Yay!");

                            // Now you can start interacting with App Remote
                            connected();
                        }
                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.e("MainActivity", throwable.getMessage(), throwable);
                            Log.e("main", "neger");
                            // Something went wrong when attempting to connect! Handle errors here
                        }
                    });
        } catch(Exception e) {
            Log.e("main", "onstart");
        }
    }
    private void connected() {
        try {
            //https://open.spotify.com/album/24hHNi75352fzhqiorank2?si=WuU611FgRx-hU-uL-IKaGg
            //spotify:playlist:4uSQULxDEPuyc26RDiX9sb
            mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:4uSQULxDEPuyc26RDiX9sb");

        } catch(Exception e) {
            Log.e("main", "neger");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private class SwapClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if( v.getId() == btn_login.getId() ) {

            }
            else {

            }
    }
}
