package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import Servicios.AutentificacionServicio;

@WebServlet("/login/validarClub")
public class LoginClubControlador extends HttpServlet {
    private AutentificacionServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new AutentificacionServicio();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Leer el cuerpo de la solicitud como JSON
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            jsonBuffer.append(line);
        }

        // Convertir el JSON recibido en un objeto
        JSONObject json = new JSONObject(jsonBuffer.toString());
        String email = json.getString("email");
        String password = json.getString("password");

        // Validar credenciales del club
        boolean esClubValido = servicio.validarCredencialesClub(email, password);

        // Crear objeto JSON para la respuesta
        JSONObject responseJson = new JSONObject();

        if (esClubValido) {
            // Si el club es válido, se envía una respuesta de éxito
            responseJson.put("mensaje", "Club autenticado con éxito.");
        } else {
            // Si las credenciales no son válidas, se envía un mensaje de error
            responseJson.put("error", "Credenciales inválidas o error en el servidor.");
        }

        // Configurar la respuesta con el tipo de contenido JSON
        response.setContentType("application/json");
        response.getWriter().write(responseJson.toString());
    }

}
