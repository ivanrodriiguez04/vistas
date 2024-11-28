package Servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject; // Asegúrate de tener esta dependencia para manejar JSON

public class AutentificacionServicio {

    public String validarCredencialesUsuario(String email, String password) {
        try {
            // URL de la API
            URL url = new URL("http://localhost:8081/api/login/validarUsuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json"); // Usamos JSON como contenido
            conexion.setDoOutput(true);

            // Crear cuerpo de la solicitud con formato JSON
            String jsonInput = new JSONObject()
                                .put("email", email)
                                .put("password", password)
                                .toString();

            // Enviar el cuerpo de la solicitud
            try (OutputStream os = conexion.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            // Leer respuesta de la API
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) { // Código 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Obtener la respuesta (el rol del usuario)
                String responseString = response.toString();
                if ("admin".equals(responseString)) {
                    return "admin"; // El usuario es admin
                } else if ("usuario".equals(responseString)) {
                    return "usuario"; // El usuario es usuario
                }
            } else {
                System.out.println("Error en la conexión: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Error o credenciales inválidas
    }
    
    public boolean validarCredencialesClub(String email, String password) {
        try {
            // URL de la API del servlet de validación de club
            URL url = new URL("http://localhost:8081/api/login/validarClub");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json"); // Usamos JSON como contenido
            conexion.setDoOutput(true);

            // Crear cuerpo de la solicitud con formato JSON
            String jsonInput = new JSONObject()
                                .put("email", email)
                                .put("password", password)
                                .toString();

            // Enviar el cuerpo de la solicitud
            try (OutputStream os = conexion.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            // Leer respuesta de la API
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_OK) { // Código 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Verificar si la respuesta es "club"
                String responseString = response.toString().trim();
                if ("club".equals(responseString)) {
                    return true; // Las credenciales son válidas para un club
                }
            } else {
                System.out.println("Error en la conexión: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Credenciales inválidas o error en la conexión
    }

}
