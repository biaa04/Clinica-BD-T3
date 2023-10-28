package com.example.clinicabdt3.Model.Database;

public class DatabaseFactory {

    public static DatabaseSQLite getDatabase(String nome){

        if (nome.equals("clinicabd")){
            System.out.println("111111");
            return new DatabaseSQLite();
        }else {
            return null;
        }

    }
}
