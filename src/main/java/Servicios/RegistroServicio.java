package Servicios;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class RegistroServicio {
	public boolean registrarUsuario(String nicknameUsuario,String nombreUsuario,String dniUsuario,String telefonoUsuario, String emailUsuario, String passwordUsuario) {
        try {
            // URL de la API para registrar usuarios
            URL url = new URL("http://localhost:8081/api/registro/usuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            // Crear cuerpo de la solicitud con formato JSON
            String jsonInput = new JSONObject()
            					.put("nicknameUsuario",nicknameUsuario)
                                .put("nombreUsuario", nombreUsuario)
                                .put("dniUsuario", dniUsuario)
                                .put("telefonoUsuario", telefonoUsuario)
                                .put("emailUsuario", emailUsuario)
                                .put("passwordUsuario", passwordUsuario)
                                .toString();

            // Enviar el cuerpo de la solicitud
            try (OutputStream os = conexion.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            // Leer la respuesta de la API
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_CREATED) { // Código 201 Created
                return true;
            } else {
                System.out.println("Error al registrar usuario: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Error al registrar
    }
	
	public boolean registrarClub(String nombreClub,String sedeClub, String emailClub, String passwordClub) {
        try {
            // URL de la API para registrar usuarios
            URL url = new URL("http://localhost:8081/api/registro/club");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            // Crear cuerpo de la solicitud con formato JSON
            String jsonInput = new JSONObject()
                                .put("nombreClub", nombreClub)
                                .put("sedeClub", sedeClub)
                                .put("emailClub", emailClub)
                                .put("passwordClub", passwordClub)
                                .toString();

            // Enviar el cuerpo de la solicitud
            try (OutputStream os = conexion.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            // Leer la respuesta de la API
            int codigoRespuesta = conexion.getResponseCode();
            if (codigoRespuesta == HttpURLConnection.HTTP_CREATED) { // Código 201 Created
                return true;
            } else {
                System.out.println("Error al registrar usuario: " + codigoRespuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Error al registrar
    }
}