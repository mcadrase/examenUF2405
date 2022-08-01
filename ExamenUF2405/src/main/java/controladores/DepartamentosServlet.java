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
import modelo.Departamento;

/**
 * Servlet implementation class LibrosServlet
 */
@WebServlet("/departamentos")
public class DepartamentosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartamentosServlet() {
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
		int codDepartamento = Integer.parseInt(request.getParameter("codDepartamento"));
		
		DepartamentoDAO dao = new DepartamentoDAOJDBC();
		Departamento dept = dao.getDepartamento(codDepartamento);
		request.setAttribute("dept", dept);
		CentroDAO daoCen= new CentroDAOJDBC();
		List<Centro> lista = daoCen.getCentro();
		request.setAttribute("listaCentros", lista);
		request.getRequestDispatcher("/departamentos/editar.jsp").forward(request, response);
	
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int codDepartamento = Integer.parseInt(request.getParameter("codDepartamento"));
		DepartamentoDAO dao = new DepartamentoDAOJDBC();
		dao.eliminarDepartamento(codDepartamento);
		mostrarListado(request, response);
	}

	private void mostrarFormulario(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
		CentroDAO daoCen= new CentroDAOJDBC();
		List<Centro> lista = daoCen.getCentro();
		request.setAttribute("listaCentros", lista);
		request.getRequestDispatcher("/departamentos/insertar.jsp").forward(request, response);
	}

	private void mostrarListado(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DepartamentoDAO dao = new DepartamentoDAOJDBC();
		List<Departamento> lista = dao.getDepartamentos();
		request.setAttribute("listaDepartamentos", lista);
		request.getRequestDispatcher("/departamentos/listado.jsp").forward(request, response);
		
		
		
		
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
		int codDepartamento = Integer.parseInt(request.getParameter("codDepartamento"));
		int codCentro = Integer.parseInt(request.getParameter("codCentro")); 
		String tipoDir = request.getParameter("tipoDir");
		int presupuesto = Integer.parseInt(request.getParameter("presupuesto")); 
		String nombre = request.getParameter("nombre");
		
		
		Departamento dept = new Departamento(codDepartamento, codCentro, tipoDir, presupuesto, nombre);
		
		
		DepartamentoDAO dao = new DepartamentoDAOJDBC();
		dao.actualizarDepartamento(dept);
		mostrarListado(request, response);
		
	}

	private void insertar(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int codDepartamento = Integer.parseInt(request.getParameter("codDepartamento"));
		int codCentro = Integer.parseInt(request.getParameter("codCentro")); 
		String tipoDir = request.getParameter("tipoDir");
		int presupuesto = Integer.parseInt(request.getParameter("presupuesto")); 
		String nombre = request.getParameter("nombre");
		
		
		Departamento dept = new Departamento(codDepartamento, codCentro, tipoDir, presupuesto, nombre);
		
		DepartamentoDAO dao = new DepartamentoDAOJDBC();
		dao.insertarDepartamento(dept);
		mostrarListado(request, response);
		
	}

}