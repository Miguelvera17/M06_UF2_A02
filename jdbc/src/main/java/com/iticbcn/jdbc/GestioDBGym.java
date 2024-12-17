package com.iticbcn.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.w3c.dom.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

public class GestioDBGym {
//Com veurem, aquesta booleana controla si volem sortir de l'aplicació.
    static boolean sortirapp = false;
    static boolean DispOptions = true;
        
        public static void main(String[] args) {
    
            try (BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in))) {
    
                try {
                    // Carregar propietats des de l'arxiu
                    Properties properties = new Properties();
                    try (InputStream input = GestioDBGym.class.getClassLoader().getResourceAsStream("config.properties")) {
                    //try (FileInputStream input = new FileInputStream(configFilePath)) {
                        properties.load(input);
        
                        // Obtenir les credencials com a part del fitxer de propietats
                        String dbUrl = properties.getProperty("db.url");
                        String dbUser = properties.getProperty("db.username");
                        String dbPassword = properties.getProperty("db.password");
        
                        // Conectar amb MariaDB
                        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                            System.out.println("Conexión exitosa");
        
                            String File_create_script = "db_scripts/DB_Schema_Gym.sql" ;
        
                            InputStream input_sch = GestioDBGym.class.getClassLoader().getResourceAsStream(File_create_script);
        
                            CRUDGym crudgym = new CRUDGym();
                            //Aquí farem la creació de la database i de les taules, a més d'inserir dades
                            crudgym.CreateDatabase(connection,input_sch);
                            while (sortirapp == false) {
                                MenuOptions(br1,crudgym,connection);
                            }

                    } catch (Exception e) {
                        System.err.println("Error al conectar: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("Error al cargar fichero de propiedades: " + e.getMessage());
                }
            } finally {}
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void MenuOptions(BufferedReader br, CRUDGym crudbgym, Connection connection) 
    throws NumberFormatException, IOException, SQLException, InterruptedException {

        Terminal terminal = TerminalBuilder.builder().system(true).build();

        String message = "";
        message = "==================";
        printScreen(terminal, message);

        message = "CONSULTA BD Gym";
        printScreen(terminal, message);

        message = "==================";
        printScreen(terminal, message);


        message = "OPCIONES";
        printScreen(terminal, message);

        message = "1. CARGAR TABLAS";
        printScreen(terminal, message);

        message = "2. INGRESAR UN NUEVO REGISTRO";
        printScreen(terminal, message);

        message = "3. CONSULTAR DATOS ORDENADOS";
        printScreen(terminal, message);

        message = "4. MOSTRAR EN FORMATO DOM";
        printScreen(terminal, message);

        message = "5. BUSCAR REGISTRO";
        printScreen(terminal, message);

        message = "6. MODIFICAR REGISTRO";
        printScreen(terminal, message);
        
        message = "7. BORRAR REGISTRO";
        printScreen(terminal, message);

        message = "8. SALIR";
        printScreen(terminal, message);

        message = "Introduce la opcion >> ";
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }

        int opcio = Integer.parseInt(br.readLine());

        switch(opcio) {
            case 1:
                String File_data_script = "db_scripts/DB_Data_Gym.sql" ;
    
                InputStream input_data = GestioDBGym.class.getClassLoader().getResourceAsStream(File_data_script);

                if (crudbgym.CreateDatabase(connection,input_data) == true) {
                    System.out.println("Registros duplicados");
                } else {
                    System.out.println("Registros ingresados con éxito");
                }

                break;
            case 2:
                MenuInsert(br,crudbgym,connection);
                break;
            case 3:
                MenuSelect(br,crudbgym,connection);
                break;
            case 4:
                MostrarDOM(br,crudbgym,connection);
                break;
            case 5:
                MenuSelectAltres(br, crudbgym, connection);
                break;
            case 6:
                MenuUpdate(br, crudbgym, connection);
                break;
            case 7:
                MenuSelectAltres(br, crudbgym, connection);
                break;
            case 8:
                System.out.println("Adios!!");
                sortirapp = true;
                break;
            default:
                System.out.print("Opción no válida");
                MenuOptions(br,crudbgym,connection);
        }
    
    }

    private static void printScreen(Terminal terminal, String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }
        System.out.println();
    }

    public static void MenuInsert(BufferedReader br, CRUDGym crudbgym,Connection connection) 
    throws SQLException, NumberFormatException, IOException {

        boolean insertMore = true;

        while (insertMore) {

            boolean dadaValida = true;

            System.out.println("\nIntroduce los datos de la nueva persona");

            String DNI = "";

            while (dadaValida) {
                System.out.print("Introduce DNI >> ");

                try {

                    DNI = br.readLine();

                    if (DNI.length() != 9) {
                        System.out.println("DNI no válido");

                    } else {
                        dadaValida = false;
                    }
                    
                } catch (Exception e) {
                    System.out.println("Formato DNI no válido");
                }
            }

            System.out.print("Introduce el nombre de la persona >> ");
            String nombre = br.readLine();

            System.out.print("Introduce el teléfono de la persona >> ");
            String telefono = br.readLine();

            dadaValida = true;

            Persona person = new Persona(DNI, nombre, telefono);

            crudbgym.InsertPerson(connection, "PERSONA", person);

            System.out.println("Quieres agregar otra persona?");

            if (!br.readLine().matches("[sS]")) {
                insertMore = false;
            }

        }
                            
    }

    public static void MenuSelect(BufferedReader br, CRUDGym crudbgym,Connection connection) 
    throws SQLException, NumberFormatException, IOException {

        int opcio = 0;

        while (DispOptions) {

            System.out.println("De que tabla desea mostrar los registros?");
            System.out.println("1. Personas");

            System.out.print("Introduce la opción >> ");

            opcio = Integer.parseInt(br.readLine());

            switch(opcio) {
                case 1:
                    crudbgym.ReadAllDatabase(connection, "persona");
                    break;
                case 2:
                    DispOptions = false;
                    break;
                default:
                    System.out.print("Opción no vàlida");
            }
                
            if (DispOptions) {
                System.out.println("Quiere hacer otra consulta? (S o N): ");
                String opcioB = br.readLine();
        
                if (opcioB.equalsIgnoreCase("n")){
                    System.out.println("Volviendo al menú...");
                    DispOptions = false;
                    break;
                } 
            }
        }
    }

    public static void MenuSelectAltres(BufferedReader br, CRUDGym crudbgym,Connection connection) 
    throws SQLException, NumberFormatException, IOException {

        int opcio = 0;

        while (DispOptions) {

            System.out.println("Qué consulta desea hacer?");
            System.out.println("1. Buscar persona por id");
            System.out.println("2. Buscar persona por nombre(LIKE)");
            System.out.println("3. Salir");

            System.out.print("Introdueix l'opció tot seguit >> ");

            opcio = Integer.parseInt(br.readLine());

            switch(opcio) {
                case 1:
                    System.out.print("Introduce el id de la persona >> ");
                    int id = Integer.parseInt(br.readLine());
                    crudbgym.BuscaID(connection, id);
                    break;
                case 2:
                    System.out.print("Introduce el nombre de la persona >> ");
                    String nombre = br.readLine();
                    crudbgym.BuscaLIKE(connection, nombre);
                    break;
                case 3:
                    break;
                default:
                    System.out.print("Opción no válida");
                    MenuSelectAltres(br, crudbgym, connection);
            }

        }

    }

    public static void MostrarDOM(BufferedReader br, CRUDGym crudbgym, Connection connection) 
        throws SQLException, NumberFormatException, IOException {
    
        BufferedReader sbr = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nIndicate the name of the XML file (without extension):");
        System.out.print("\n----> ");
        String name = sbr.readLine();
        System.out.print("\n");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Personas", null);
            document.setXmlVersion("1.0");

            Element rootElement = document.getDocumentElement();

            String query = "SELECT id, DNI, nombre, telefono FROM Persona";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Element persona = document.createElement("Persona");

                Element id = document.createElement("ID");
                id.appendChild(document.createTextNode(String.valueOf(resultSet.getInt("id"))));
                persona.appendChild(id);

                Element dni = document.createElement("DNI");
                dni.appendChild(document.createTextNode(resultSet.getString("DNI")));
                persona.appendChild(dni);

                Element nombre = document.createElement("Nombre");
                nombre.appendChild(document.createTextNode(resultSet.getString("nombre")));
                persona.appendChild(nombre);

                Element telefono = document.createElement("Telefono");
                telefono.appendChild(document.createTextNode(resultSet.getString("telefono")));
                persona.appendChild(telefono);

                rootElement.appendChild(persona);
            }

            resultSet.close();
            statement.close();

            String fileName = name + ".xml";
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(fileName));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");

            transformer.transform(source, result);

            System.out.println("XML generado: " + fileName);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void MenuUpdate(BufferedReader br, CRUDGym crudbgym, Connection connection) 
        throws SQLException, NumberFormatException, IOException {

            int opcio = 0;

        while (DispOptions) {

            System.out.println("Qué campo desea actualizar?");
            System.out.println("1. Nombre");
            System.out.println("2. Telefono");
            System.out.println("3. Salir");

            System.out.print("Introduce la opcion >> ");

            opcio = Integer.parseInt(br.readLine());

            switch(opcio) {
                case 1:
                    System.out.print("Introduce el nuevo nombre >> ");
                    int id = Integer.parseInt(br.readLine());
                    crudbgym.update(connection,nombre, id, );
                    break;
                case 2:
                    System.out.print("Introduce el nuevo telefono >> ");
                    String nombre = br.readLine();
                    crudbgym.update(connection, nombre);
                    break;
                case 3:
                    break;
                default:
                    System.out.print("Opción no válida");
                    MenuSelectAltres(br, crudbgym, connection);
            }

        }
    }
}
