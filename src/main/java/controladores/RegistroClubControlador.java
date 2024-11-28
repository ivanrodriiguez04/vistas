package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import Servicios.RegistroServicio;

@WebServlet("/registro/club")
public class RegistroClubControlador extends HttpServlet {
    private RegistroServicio servicio;

    @Override
    public void init() throws ServletException {
        this.servicio = new RegistroServicio();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Leer el cuerpo de la solicitud como JSON
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                jsonBuffer.append(line);
            }

            JSONObject json = new JSONObject(jsonBuffer.toString());
            String nombreClub = json.optString("nombreClub");
            String sedeClub = json.optString("sedeClub");
            String emailClub = json.optString("emailClub");
            String passwordClub = json.optString("passwordClub");

            // Validar los datos
            if (emailClub.isEmpty() || passwordClub.isEmpty() || nombreClub.isEmpty() || sedeClub.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Todos los campos son obligatorios.\"}");
                return;
            }

            // Llamar al servicio para registrar el club
            boolean exito = servicio.registrarClub(nombreClub, sedeClub, emailClub, passwordClub);
            if (exito) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"mensaje\": \"Usuario registrado exitosamente.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"error\": \"El correo electrónico ya está registrado.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error interno del servidor.\"}");
        }
    }
}
