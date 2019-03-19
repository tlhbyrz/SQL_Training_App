package com.example.boyraztalha.sql_training_app;

import android.provider.BaseColumns;

public final class Database_Column_Names {

    public Database_Column_Names(){

    }

    public static final class ColumnNames implements BaseColumns{

        public static final String TABLE_NAME = "WorkersInfo";

        public static final String WORKER_NAME = "Name";

        public static final String WORKER_SALARY = "Salary";

    }

}
