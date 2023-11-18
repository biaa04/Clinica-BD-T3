package com.example.clinicabdt3.Model.Database;

public class DatabaseFactory {

    public static DatabaseSQLite getDatabase(String nome){

        if (nome.equals("clinicabd")){
            System.out.println("Database Factory");
            return new DatabaseSQLite();
        }else {
            return null;
        }

    }
}
