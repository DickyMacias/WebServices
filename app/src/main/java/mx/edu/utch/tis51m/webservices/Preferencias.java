package mx.edu.utch.tis51m.webservices;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Preferencias extends AppCompatActivity {

    private EditText ip = null;
    static final String PREFS_URL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencias);

        ip = (EditText)findViewById(R.id.edtIp);
        SharedPreferences preferencias = getSharedPreferences(PREFS_URL,0);
        String laURL = preferencias.getString("siteIP","NohayURL");

        ip.setText(laURL);
    }



    public void Guardar(View view) {
        SharedPreferences preferencias = getSharedPreferences(PREFS_URL,0);

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("siteIP",ip.getText().toString());

        editor.putString("script_productos","create_product.php");
        editor.putString("script_obtener_prods","get_all_products.php");
        editor.putString("script_config","db_config.php");
        editor.putString("script_db_config","db_connect.php");
        editor.putString("script_delete","delete_product.php");
        editor.putString("script_obtener detail_prods","get_product_details.php");
        editor.putString("script_update_prods","update_product.php");

        editor.commit();
        finish();
    }
}
