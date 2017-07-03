package mx.edu.utch.tis51m.webservices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static mx.edu.utch.tis51m.webservices.Preferencias.PREFS_URL;

public class Actualizar extends AppCompatActivity {

    private EditText idd=null, namee= null, pricee = null, descc = null;
    private ProgressDialog progressDialog=null;
    private JSONParser jsonParser = null; //Objeto conexion webservice
    private static String _url = null;

    ArrayList<HashMap<String,String>> record = null;

    private static final String TAG_PRODUCTS= "products";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID      = "pid";
    private static final String TAG_NAME    = "name";
    private static final String TAG_PRICE   = "price";
    private static final String TAG_DESC    = "description";

    private JSONArray productos=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar);

        SharedPreferences preferencias = getSharedPreferences(PREFS_URL,0);
        String laURL = preferencias.getString("siteIP","NohayURL");
        _url = "http://"+laURL+"/webservice/update_product.php";

        jsonParser = new JSONParser();
        idd    = (EditText)findViewById(R.id.edtId);
        idd.setEnabled(false);
        namee  = (EditText)findViewById(R.id.edtName);
        pricee = (EditText)findViewById(R.id.edtPrice);
        descc  = (EditText)findViewById(R.id.edtDescription);

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

    public void Actualizale(View view) {
        String idd    = this.idd.getText().toString();
        String namee  = this.namee.getText().toString();
        String pricee = this.pricee.getText().toString();
        String descc  = this.descc.getText().toString();
        new Actualizar.agregarNuevo().execute(idd,namee,pricee,descc);
    }

    public void Cancelale(View view) {
        Actualizar.this.finish();
    }


    class agregarNuevo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(Actualizar.this);
            progressDialog.setMessage("Actualizando producto");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        protected String doInBackground(String... params) {
            try{
                List<NameValuePair>Params = new ArrayList<NameValuePair>();
                Params.add(new BasicNameValuePair(TAG_ID,params[0]));
                Params.add(new BasicNameValuePair(TAG_NAME,params[1]));
                Params.add(new BasicNameValuePair(TAG_PRICE,params[2]));
                Params.add(new BasicNameValuePair(TAG_DESC,params[3]));

                JSONObject json = jsonParser.makeHttpRequest(_url,"POST",Params);

                int succ = json.getInt(TAG_SUCCESS);

                if(succ == 1){
                    Intent intento = new Intent(getApplicationContext(),Query.class);
                    startActivity(intento);
                    Actualizar.this.finish();
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
