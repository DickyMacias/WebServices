package mx.edu.utch.tis51m.webservices;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class Query extends ListActivity {
    private ProgressDialog progressDialog=null;
    private JSONParser jsonParser = null; //Objeto conexion webservice
    private static String _url = null;
    ArrayList<HashMap<String,String>> record = null;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS= "products";
    private static final String TAG_NAME    = "name";
    private static final String TAG_PRICE   = "price";
    private static final String TAG_ID    = "pid";
    private static final String TAG_DESC    = "description";
    String numeros[] = new String[100];
    String nameee[]  = new String[100];
    String priceee[] = new String[100];
    String desccc[]  = new String[100];


    private JSONArray productos=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.query);

        SharedPreferences preferencias = getSharedPreferences(PREFS_URL,0);
        String laURL = preferencias.getString("siteIP","NohayURL");
        _url ="http://"+laURL+"/webservice/get_all_products.php";

        record = new ArrayList<HashMap<String, String>>(); //Se guardan registros aqui

        //Se hace la consulta a la base de datos ejecutando el hilo y rellenando el arraylist
        new consulta().execute();

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertita = new AlertDialog.Builder(Query.this);
                alertita.setMessage("¿Borrar o actualizar?").setCancelable(false)
                                    .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                           Intent intento = new Intent(getApplicationContext(),Actualizar.class);
                                            intento.putExtra("id",numeros[position]);
                                            intento.putExtra("name", nameee[position]);
                                            intento.putExtra("price",priceee[position]);
                                            intento.putExtra("desc",desccc[position]);
                                            startActivity(intento);
                                            Query.this.finish();

                                        }
                                    })
                                    .setNegativeButton("Borrar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intento = new Intent(getApplicationContext(),Borrar.class);
                                            intento.putExtra("id",numeros[position]);
                                            intento.putExtra("name", nameee[position]);
                                            intento.putExtra("price",priceee[position]);
                                            intento.putExtra("desc",desccc[position]);
                                            startActivity(intento);
                                            Query.this.finish();
                                        }
                                    });
                alertita.show();
            }
        });

    }


    class consulta extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            productos = new JSONArray();

            progressDialog = new ProgressDialog(Query.this);
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> Params = new ArrayList<NameValuePair>();
            //Almacena la consulta al webservice
            JSONObject json = null;
            jsonParser = new JSONParser();

            try{
                //Se realiza la conexion al web service
                json = jsonParser.makeHttpRequest(_url,"GET", Params);
                Log.e("Error",json.toString());


                int ok = json.getInt(TAG_SUCCESS);
                if(ok == 1){
                    productos = json.getJSONArray(TAG_PRODUCTS);

                    for(int i = 0; i<productos.length(); i++){
                        JSONObject c = productos.getJSONObject(i);
                        String id   = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String price= c.getString(TAG_PRICE);
                        String desc = c.getString(TAG_DESC);

                        numeros[i] = id;
                        nameee[i]  =name;
                        priceee[i] = price;
                        desccc[i]    = desc;

                        //Se pasa al hashmap
                        HashMap<String, String> map = new HashMap<>();
                        map.put(TAG_ID,id);
                        map.put(TAG_NAME,name);
                        map.put(TAG_PRICE, price);
                       //agregamos el map a la lista
                        record.add(map);

                    }//SE acaba el for
                    publishProgress();
                    return "ok";
                }

            }catch(JSONException e){
                e.fillInStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //llenamos la lista
            ListAdapter adapter = new SimpleAdapter(
                    Query.this,
                    record,
                    R.layout.renglon,
                    new String[]{TAG_ID,TAG_NAME, TAG_PRICE},
                    new int[]{R.id.textView7,R.id.textView2,R.id.textView3}
            );
            setListAdapter(adapter);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s.equals("ok"));
            Toast.makeText(Query.this, "Fine", Toast.LENGTH_SHORT).show();
        }
    }



}
