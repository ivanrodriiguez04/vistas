package controladores;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Servicios.RegistroServicio;

@WebServlet("/registro/usuario")
public class RegistroUsuarioControlador extends HttpServlet {
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

			System.out.println("JSON recibido del cliente: " + jsonBuffer);

			// Convertir el JSON recibido en un objeto
			JSONObject json = new JSONObject(jsonBuffer.toString());
			String nicknameUsuario = json.optString("nicknameUsuario");
			String nombreUsuario = json.optString("nombreUsuario");
			String dniUsuario = json.optString("dniUsuario");
			String telefonoUsuario = json.optString("telefonoUsuario");
			String emailUsuario = json.optString("emailUsuario");
			String passwordUsuario = json.optString("passwordUsuario");

			// Validar los datos
			if (nicknameUsuario.isEmpty() || emailUsuario.isEmpty() || passwordUsuario.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\": \"Todos los campos son obligatorios.\"}");
				return;
			}

			// Enviar datos al servicio
			boolean exito = servicio.registrarUsuario(nicknameUsuario, nombreUsuario, dniUsuario, telefonoUsuario,
					emailUsuario, passwordUsuario);
			if (exito) {
				response.setStatus(HttpServletResponse.SC_CREATED);
				response.getWriter().write("{\"mensaje\": \"Usuario registrado exitosamente.\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				response.getWriter().write("{\"error\": \"Error al registrar usuario en el backend.\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"Error interno del servidor.\"}");
		}
	}
}
