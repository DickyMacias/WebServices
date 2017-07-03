package mx.edu.utch.tis51m.webservices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static mx.edu.utch.tis51m.webservices.Preferencias.PREFS_URL;

public class Agregar extends AppCompatActivity {

    private EditText name = null, price = null, desc = null;
    private JSONParser jsonParser = null;
    private ProgressDialog progressDialog;
    private static String _url = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS= "products";
    private static final String TAG_NAME    = "name";
    private static final String TAG_PRICE   = "price";
    private static final String TAG_DESC    = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        //Objeto de conexion al web service

        SharedPreferences preferencias = getSharedPreferences(PREFS_URL,0);
        String laURL = preferencias.getString("siteIP","NohayURL");
        _url = "http://"+laURL+"/webservice/create_product.php";
        jsonParser = new JSONParser();

        name  = (EditText)findViewById(R.id.autoNombre);
        price = (EditText)findViewById(R.id.autoPrecio);
        desc  = (EditText)findViewById(R.id.autoDes);

    }

    public void cancelar(View view) {
        Agregar.this.finish();
    }

    public void aceptar(View view) {
        String name  = this.name.getText().toString();
        String price = this.price.getText().toString();
        String desc  = this.desc.getText().toString();
        new agregarNuevo().execute(name,price,desc);
    }

    class agregarNuevo extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(Agregar.this);
            progressDialog.setMessage("Creando nuevo producto");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                List<NameValuePair>Params = new ArrayList<NameValuePair>();
                Params.add(new BasicNameValuePair(TAG_NAME,params[0]));
                Params.add(new BasicNameValuePair(TAG_PRICE,params[1]));
                Params.add(new BasicNameValuePair(TAG_DESC,params[2]));

                JSONObject json = jsonParser.makeHttpRequest(_url,"POST",Params);

                int succ = json.getInt(TAG_SUCCESS);

                if(succ == 1){
                    Intent intento = new Intent(getApplicationContext(),Query.class);
                    startActivity(intento);
                    Agregar.this.finish();
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
            if(s.equals("ok")){
                Toast.makeText(Agregar.this, "Dato agregado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Agregar.this, "Dato no agregado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
