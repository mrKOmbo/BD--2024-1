package io.github.cruzemilio.traductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    EditText editEmpleadoText, editPassText;
    Button buttonMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmpleadoText = (EditText) findViewById(R.id.textEmailAddress);
        editPassText = (EditText) findViewById(R.id.textPassword);
        buttonMain = (Button) findViewById(R.id.textLogin);
        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread tr= new Thread(){
                    @Override
                    public void run() {
                        final String res=enviarPost(editEmpleadoText.getText().toString(),editPassText.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r=objJSON(res);
                                if(r>0){
                                    Intent i=new Intent(getApplicationContext(), Principal.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(),"Usuario o Pas Inconrrectos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                };
                tr.start();
            }
        });
    }
    public void openFacebookPage(View view) {
        String facebookUrl = "https://www.facebook.com/tupagina";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(intent);
    }
    public void openGithubPage(View view) {
        String githubUrl = "https://github.com/mrKOmbo/BD--2024-1";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
        startActivity(intent);
    }
    public void openTwitterPage(View view) {
        String twitterUrl = "https://www.twitter.com";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
        startActivity(intent);
    }
    public void openPage(View view) {
        String Url = "https://www.infordisa.com/soc/contrasenas-top-10-mas-usadas-2023/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
        startActivity(intent);
    }
    public String enviarPost(String cor, String pas){
        String parametros="cor="+cor+"&pas="+pas;
        HttpsURLConnection conection=null;
        String respuesta="";
        try{
            URL url=new URL("https://bdf1.campanita.xyz/index.php");
            conection = (HttpsURLConnection) url.openConnection();
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));

            conection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(conection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream=new Scanner(conection.getInputStream());
            while(inStream.hasNextLine())
                respuesta+=(inStream.nextLine());
        }catch(Exception e){}
        Log.d("hola",respuesta.toString());
        return respuesta.toString();
    }

    public int objJSON(String rspta){
        int res=0;
        try{
            JSONArray json=new JSONArray(rspta);
            if(json.length()>0)
                res=1;
        }catch(Exception e){}
        return res;
    }
}