package controladores;

import java.io.IOException;
import java.util.List;

import dao.CentroDAO;
import dao.CentroDAOJDBC;
import dao.DepartamentoDAO;
import dao.DepartamentoDAOJDBC;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Centro;

/**
 * Servlet implementation class LibrosServlet
 */
@WebServlet("/centros")
public class CentrosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CentrosServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opcion = request.getParameter("opcion");
		if (opcion ==null || opcion.equals("listado")) {
			mostrarListado(request,response);
		} else if (opcion.equals("nuevo")) {
			mostrarFormulario(request,response);
		} else if (opcion.equals("eliminar")) {
			eliminar(request,response);
		} else if (opcion.equals("editar")) {
			editar(request,response);
		}
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int codCentro = Integer.parseInt(request.getParameter("codCentro"));
		CentroDAO dao = new CentroDAOJDBC();
		Centro centro = dao.getCentro(codCentro);
		request.setAttribute("centro", centro);
		request.getRequestDispatcher("/centros/editar.jsp").forward(request, response);
	
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int codCentro = Integer.parseInt(request.getParameter("codCentro"));
		CentroDAO dao = new CentroDAOJDBC();
		dao.eliminarCentro(codCentro);
		mostrarListado(request, response);
	}

	private void mostrarFormulario(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/centros/insertar.jsp").forward(request, response);
	}

	private void mostrarListado(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		CentroDAO dao = new CentroDAOJDBC();
		List<Centro> lista = dao.getCentro();
		request.setAttribute("listaCentros", lista);
		request.getRequestDispatcher("/centros/listado.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opcion = request.getParameter("opcion");
		if (opcion !=null && opcion.equals("insertar")) {
			insertar(request,response);
		} else if (opcion!=null && opcion.equals("editar")) {
			actualizar(request,response);
		}
	}

	private void actualizar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		int codCentro =Integer.parseInt(request.getParameter("codCentro"));
		String nombre = request.getParameter("nombre");
		String direccion = request.getParameter("direccion");
		
		Centro centro = new Centro(codCentro, nombre, direccion);
		
		CentroDAO dao = new CentroDAOJDBC();
		dao.actualizarCentro(centro);
		mostrarListado(request, response);
		
	}

	private void insertar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int codCentro =Integer.parseInt(request.getParameter("codCentro"));
		String nombre = request.getParameter("nombre");
		String direccion = request.getParameter("direccion");
		
		Centro centro = new Centro(codCentro, nombre, direccion);
		
		CentroDAO dao = new CentroDAOJDBC();
		dao.insertarCentro(centro);
		mostrarListado(request, response);
		
	}

}