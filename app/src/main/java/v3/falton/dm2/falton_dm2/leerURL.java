package v3.falton.dm2.falton_dm2;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class leerURL extends AsyncTask<String, Integer, String> {

    private TextView textViewToSet;

    public leerURL(TextView descriptionTextView){
        this.textViewToSet = descriptionTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            URL url = new URL(params[0]);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                //get lines
                result+=line;
            }
            in.close();


        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    protected void onProgressUpdate() {
        //called when the background task makes any progress
    }

    protected void onPreExecute() {
        //called before doInBackground() is started
    }

    @Override
    protected void onPostExecute(String result) {

        try {
            JSONObject json = new JSONObject(result);
            String tipo_consulta = json.getString("tipo");

            switch (tipo_consulta){
                case "usuario":
                    loginUsuario(json);
                    break;
            }

        } catch (Throwable t) {
        }
    }

    private void loginUsuario(JSONObject json) throws JSONException {

        JSONArray jArray = json.getJSONArray("fila");

        for(int i=0; i<jArray.length(); i++){
            JSONObject obj = jArray.getJSONObject(i);
            Usuario usuario = new Usuario();

            usuario.setId(Integer.parseInt(obj.getString("id")));
            usuario.setNombre(obj.getString("Nombre"));
            usuario.setEmail(obj.getString("Email"));
            usuario.setPass(obj.getString("Pass"));
            usuario.setTelefono(obj.getString("Telefono"));
            usuario.setFoto(obj.getString("Foto"));


            this.textViewToSet.setText(textViewToSet.getText()+ " || " + usuario.toString());
        }
    }
}