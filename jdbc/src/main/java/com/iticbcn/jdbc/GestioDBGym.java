package com.iticbcn.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
                            System.out.println("Conexió exitosa");
        
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
                    System.err.println("Error al carregar fitxer de propietats: " + e.getMessage());
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


        message = "OPCIONS";
        printScreen(terminal, message);

        message = "1. CARREGAR TAULA";
        printScreen(terminal, message);

        message = "2. CONSULTAR TOTES LES DADES";
        printScreen(terminal, message);

        message = "3. ALTRES CONSULTES";
        printScreen(terminal, message);

        message = "4. INSERIR NOU REGISTRE";
        printScreen(terminal, message);

        message = "9. SORTIR";
        printScreen(terminal, message);


        message = "Introdueix l'opcio tot seguit >> ";
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
                    System.out.println("Registres duplicats");
                } else {
                    System.out.println("Registres inserits amb éxit");
                }

                break;
            case 2:
                //Preguntem de quina taula volem mostrar
                MenuSelect(br,crudbgym,connection);
                break;

            case 3:
                MenuSelectAltres(br,crudbgym,connection);
                break;

            case 4:
                MenuInsert(br,crudbgym,connection);
                break;

            case 9:
                //sortim
                System.out.println("Adéu!!");
                sortirapp = true;
                break;
            default:
                System.out.print("Opcio no vàlida");
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

    public static void MenuSelect(BufferedReader br, CRUDGym crudbgym,Connection connection) 
    throws SQLException, NumberFormatException, IOException {

        int opcio = 0;

        while (DispOptions) {

            System.out.println("De quina taula vols mostrar els seus registres?");
            System.out.println("1. Personas");

            System.out.print("Introdueix l'opció tot seguit >> ");

            opcio = Integer.parseInt(br.readLine());

            switch(opcio) {
                case 1:
                    crudbgym.ReadAllDatabase(connection, "PERSONAS");
                    break;
                case 2:
                    DispOptions = false;
                    break;
                default:
                    System.out.print("Opcio no vàlida");
            }
                
            if (DispOptions) {
                System.out.println("Vols fer altra consulta? (S o N): ");
                String opcioB = br.readLine();
        
                if (opcioB.equalsIgnoreCase("n")){
                    System.out.println("No, no marxis si us plau!");
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

            System.out.println("Quina consulta vols fer?");
            System.out.println("1. Departament per id");
            System.out.println("2. Rang de salaris d'empleats");

            System.out.print("Introdueix l'opció tot seguit >> ");

            opcio = Integer.parseInt(br.readLine());

            switch(opcio) {
                case 1:
                    System.out.println("Introdueix la id del departament >> ");
                    int idDept = Integer.parseInt(br.readLine());
                    crudbgym.ReadDepartamentsId(connection, "DEPARTMENTS", idDept);
                    break;
            }

        }

    }

    public static void MenuInsert(BufferedReader br, CRUDGym crudbgym,Connection connection) 
    throws SQLException, NumberFormatException, IOException {

        boolean insertMore = true;

        while (insertMore) {

            boolean dadaValida = true;

            System.out.println("Introdueix els detalls del nou persona");

            int ID = 0;

            while (dadaValida) {
                System.out.print("Quina és la id (PK) de l'persona? >> ");

                try {

                    ID = Integer.parseInt(br.readLine());

                    if (ID <= 0) {
                        System.out.println("Format numèric no vàlid");

                    } else {
                        dadaValida = false;
                    }
                    
                } catch (Exception e) {
                    System.out.println("Format numèric no vàlid");
                }

            }
            
            String DNI = "";

            while (dadaValida) {
                System.out.print("Introdueix DNI >> ");

                try {

                    DNI = br.readLine();

                    if (DNI.length() != 9) {
                        System.out.println("DNI no vàlid");

                    } else {
                        dadaValida = false;
                    }
                    
                } catch (Exception e) {
                    System.out.println("Format de data no vàlid");
                }
            }

            System.out.print("Introdueix el nom de l'persona >> ");
            String name = br.readLine();

            System.out.print("Introdueix el telèfon de l'persona >> ");
            String phone = br.readLine();

            dadaValida = true;

            Persona person = new Persona(ID, DNI, name, phone);

            crudbgym.InsertEmployee(connection, "PERSONA", person);

            System.out.println("Vols afegir un altre persona?");

            if (!br.readLine().matches("[sS]")) {
                insertMore = false;
            }

        }
                            
        }

}
