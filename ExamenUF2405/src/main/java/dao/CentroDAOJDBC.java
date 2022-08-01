package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Centro;
import utilidades.ConexionBD;

public class CentroDAOJDBC implements CentroDAO {
	
	private ConexionBD conexion;

	public CentroDAOJDBC() {
		conexion = new ConexionBD();
	}
	
	@Override
	public List<Centro> getCentro() {
		List<Centro> lista = new ArrayList<>();
		Connection con = conexion.getConexion();
		Statement consulta =null;
		ResultSet rs = null;
		
		try {
			consulta = con.createStatement();
			rs = consulta.executeQuery("select * from centros");
			while(rs.next()) {
				
				
				int cod_centro = rs.getInt("cod_centro");
				String nombre = rs.getString("nombre");
				String direccion = rs.getString("direccion");
				
				Centro ce = new Centro(cod_centro,nombre,direccion);	
				
				lista.add(ce);
			}
		
		} catch (SQLException ce) {
			ce.printStackTrace();
		} finally {
			try {
				rs.close();
				consulta.close();
				conexion.desconectar();
			} catch (SQLException ce) {
				ce.printStackTrace();
			}
		}
		return lista;
	}
	
	@Override
	public int eliminarCentro(int cod_centro) {
		Connection con = conexion.getConexion();
		PreparedStatement consulta =null;
		int num=0;
		try {
			consulta = con.prepareStatement(
					"delete from centros where cod_centro= ?");
			
			consulta.setInt(1, cod_centro);
			
			num = consulta.executeUpdate();
			System.out.println("Centro borrado correctamente");

		
		} catch (SQLException ce) {
			System.out.println("Error borrando el centro "+cod_centro);
			ce.printStackTrace();
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException ce) {
				ce.printStackTrace();
			}
		}
		return num;
	}

	@Override
	public Centro getCentro(int cod_centro) {
		// TODO Esbozo de método generado automáticamente
		return null;
	}

	@Override
	public int insertarCentro(Centro centro) {
		// TODO Esbozo de método generado automáticamente
		return 0;
	}

	@Override
	public int actualizarCentro(Centro centro) {
		// TODO Esbozo de método generado automáticamente
		return 0;
	}

}
