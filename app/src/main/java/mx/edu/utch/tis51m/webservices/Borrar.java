package mx.edu.utch.tis51m.webservices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static mx.edu.utch.tis51m.webservices.Preferencias.PREFS_URL;

public class Borrar extends AppCompatActivity {


    private EditText idd=null, namee= null, pricee = null, descc = null;
    private ProgressDialog progressDialog=null;
    private JSONParser jsonParser = null; //Objeto conexion webservice
    private static String _url = null;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID      = "pid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrar);

        SharedPreferences preferencias = getSharedPreferences(PREFS_URL,0);
        String laURL = preferencias.getString("siteIP","NohayURL");
        _url = "http://"+laURL+"/webservice/delete_product.php";

        jsonParser = new JSONParser();
        idd    = (EditText)findViewById(R.id.edtId);
        idd.setEnabled(false);
        namee  = (EditText)findViewById(R.id.edtName);
        namee.setEnabled(false);
        pricee = (EditText)findViewById(R.id.edtPrice);
        pricee.setEnabled(false);
        descc  = (EditText)findViewById(R.id.edtDescription);
        descc.setEnabled(false);

        Intent intento = getIntent();
        String id = intento.getStringExtra("id");
        String name = intento.getStringExtra("name");
        String price= intento.getStringExtra("price");
        String desc = intento.getStringExtra("desc");

        idd.setText(id);
        namee.setText(name);
        pricee.setText(price);
        descc.setText(desc);

    }

    public void Borrarle(View view) {
        String idd    = this.idd.getText().toString();
        new Borrar.agregarNuevo().execute(idd);
    }

    public void Cancelar(View view) {
        Borrar.this.finish();
    }


    class agregarNuevo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(Borrar.this);
            progressDialog.setMessage("Borrando producto");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                List<NameValuePair> Params = new ArrayList<NameValuePair>();
                Params.add(new BasicNameValuePair(TAG_ID,params[0]));

                JSONObject json = jsonParser.makeHttpRequest(_url,"POST",Params);

                int succ = json.getInt(TAG_SUCCESS);

                if(succ == 1){
                    Intent intento = new Intent(getApplicationContext(),Query.class);
                    startActivity(intento);
                    Borrar.this.finish();
                    return "ok";
                }else{
                    return null;
                }

            }catch(JSONException e){
                e.printStackTrace();


            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }



}
