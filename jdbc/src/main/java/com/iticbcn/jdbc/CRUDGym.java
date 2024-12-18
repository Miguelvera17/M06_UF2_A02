package com.iticbcn.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDGym {
    
    public boolean CreateDatabase(Connection connection, InputStream input) 
    throws IOException, ConnectException, SQLException {

        boolean dupRecord = false;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            StringBuilder sqlStatement = new StringBuilder();
            String line;

            try (Statement statement = connection.createStatement()) {
                while ((line = br.readLine()) != null) {
                // Ignorar comentaris i línies buides
                    line = line.trim();
                        
                    if (line.isEmpty() || line.startsWith("--") || line.startsWith("//") || line.startsWith("/*")) {
                            continue;
                    }

                    // Acumular la línea al buffer
                    sqlStatement.append(line);

                    // el caràcter ";" es considera terminació de sentència SQL
                    if (line.endsWith(";")) {
                    // Eliminar el ";" i executar la instrucción
                        String sql = sqlStatement.toString().replace(";", "").trim();
                        statement.execute(sql);

                        // Reiniciar el buffer para la siguiente instrucción
                        sqlStatement.setLength(0);
                    }
                }
            } catch (SQLException sqle) {
                if (!sqle.getMessage().contains("Duplicate entry")) {
                    System.err.println(sqle.getMessage());
                } else {
                    dupRecord = true;
                    br.readLine();
                }
            }
        }

        return dupRecord;
    }

    public void InsertPerson(Connection connection, String TableName, Persona person) 
    throws ConnectException, SQLException {

        String query = "INSERT INTO " + TableName 
                    + " (DNI, nombre, telefono)"
                    + " VALUES (?,?,?)";

        try (PreparedStatement prepstat = connection.prepareStatement(query)) {

            prepstat.setString(1, person.getDNI());
            prepstat.setString(2, person.getName());
            prepstat.setString(3, person.getPhone());

            prepstat.executeUpdate();

            System.out.println("Persona añadidad con éxito");
        
        } catch (SQLException sqle) {
            if (!sqle.getMessage().contains("Duplicate entry")) {
                System.err.println(sqle.getMessage());
            } else {
                System.out.println("Registros duplicados");
            }
        }
    }

    public void ReadAllDatabase(Connection connection, String TableName) throws ConnectException, SQLException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try (Statement statement = connection.createStatement()) {
            int n = 0;
            int m = 10;
            while(true) {
                String query = "SELECT * FROM " + TableName +  " ORDER BY ID LIMIT " + m + " OFFSET " + n + ";";
                ResultSet rset = statement.executeQuery(query);
                int colNum = getColumnNames(rset);
                if (colNum > 0) {
                    recorrerRegistres(rset,colNum);
                }            
                System.out.println("Mostrar más?: [Si] [No]"); 
                System.out.print(">>> ");
                if (br.readLine().matches("[sS]")) {
                    //n++;
                    m++;
                } else {
                    break;
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public void searchID(Connection connection, int id) 
    throws ConnectException, SQLException {

        String query = "SELECT * FROM persona WHERE id = ?";

        try (PreparedStatement prepstat = connection.prepareStatement(query)) {

            prepstat.setInt(1, id);
            ResultSet rset = prepstat.executeQuery();

            int colNum = getColumnNames(rset);

            if (colNum > 0) {

                recorrerRegistres(rset,colNum);

            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public void searchLike(Connection connection, String nombre) 
    throws ConnectException, SQLException {

        String query = "SELECT * FROM persona WHERE nombre lIKE ?";

        try (PreparedStatement prepstat = connection.prepareStatement(query)) {

            prepstat.setString(1, "%" + nombre + "%");
            ResultSet rset = prepstat.executeQuery();

            int colNum = getColumnNames(rset);

            if (colNum > 0) {

                recorrerRegistres(rset,colNum);

            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public void update(Connection connection, String campo, int id, String valor) 
    throws ConnectException, SQLException {

        String query = "UPDATE persona SET " + campo + " = " + "'" + valor  + "'" + " WHERE id = ?";

        try (PreparedStatement prepstat = connection.prepareStatement(query)) {

            prepstat.setInt(1, id);
            ResultSet rset = prepstat.executeQuery();

            int colNum = getColumnNames(rset);

            if (colNum > 0) {
                recorrerRegistres(rset,colNum);
            }
            int rowsUpdated = prepstat.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La actualización fue exitosa.");
            } else {
                System.out.println("Error, verifica los datos");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public void delete(Connection connection, int id) 
    throws ConnectException, SQLException {

        String query = "DELETE FROM persona WHERE id = ?";

        try (PreparedStatement prepstat = connection.prepareStatement(query)) {

            prepstat.setInt(1, id);
            ResultSet rset = prepstat.executeQuery();

            int colNum = getColumnNames(rset);

            if (colNum > 0) {
                recorrerRegistres(rset,colNum);
            }
            int rowsDeleted = prepstat.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Registro borrado con exito");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static int getColumnNames(ResultSet rs) throws SQLException {
        
        int numberOfColumns = 0;
        
        if (rs != null) {   
            ResultSetMetaData rsMetaData = rs.getMetaData();
            numberOfColumns = rsMetaData.getColumnCount();   
        
            for (int i = 1; i < numberOfColumns + 1; i++) {  
                String columnName = rsMetaData.getColumnName(i);
                System.out.print(columnName + ", ");
            }
        }
        
        System.out.println();

        return numberOfColumns;
        
    }

    public void recorrerRegistres(ResultSet rs, int ColNum) throws SQLException {

        while(rs.next()) {
            for(int i =0; i<ColNum; i++) {
                if(i+1 == ColNum) {
                    System.out.println(rs.getString(i+1));
                } else {
            
                System.out.print(rs.getString(i+1)+ ", ");
                }
            } 
        }
            
    }
        
}
