package br.edu.iff.pooa20172.projeto_final;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener{
    Uri uri;
    int position;
    static MediaPlayer mp;
    ArrayList<File> mySongs;
    Thread th;

    SeekBar sb;
    Button pausa,anterior,proxima,adiantar,voltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        sb = (SeekBar) findViewById(R.id.seekBar2);

        pausa = (Button) findViewById(R.id.pausa);
        anterior = (Button) findViewById(R.id.anterior);
        anterior.setText("|<");
        proxima = (Button) findViewById(R.id.proxima);
        proxima.setText(">|");
        adiantar = (Button) findViewById(R.id.adiantar);
        adiantar.setText(">>");
        voltar = (Button) findViewById(R.id.voltar);
        voltar.setText("<<");

        pausa.setOnClickListener(this);
        anterior.setOnClickListener(this);
        proxima.setOnClickListener(this);
        adiantar.setOnClickListener(this);
        voltar.setOnClickListener(this);

        sb = (SeekBar) findViewById(R.id.seekBar2);
        th = new Thread(){
            @Override
            public void run() {
                int total_duration = mp.getDuration();
                int position_atual = 0;
                while (position_atual < total_duration) {
                    try {
                        sleep(300);
                        position_atual = mp.getCurrentPosition();
                        sb.setProgress(position_atual);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        if(mp!=null){
            mp.stop();
            mp.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("songlist");
        position = bundle.getInt("pos",0);

        uri = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(),uri);
        mp.start();
        sb.setMax(mp.getDuration());
        th.start();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.pausa:
                if(mp.isPlaying()){
                    pausa.setText(">");
                    mp.pause();
                }
                else{
                    mp.start();
                    pausa.setText("||");
                }
                break;

            case R.id.proxima:
                mp.stop();
                mp.release();
                position = (position+1)%mySongs.size();
                uri = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(),uri);
                mp.start();
                sb.setMax(mp.getDuration());
                break;

            case R.id.anterior:
                mp.stop();
                mp.release();
                position = (position-1<0) ? mySongs.size()-1: position-1;
                uri = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(),uri);
                mp.start();
                sb.setMax(mp.getDuration());
                break;

            case R.id.adiantar:
                mp.seekTo(mp.getCurrentPosition()+3000);
                break;

            case R.id.voltar:
                mp.seekTo(mp.getCurrentPosition()-3000);
                break;
        }
    }
}