package SelectTests;

import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.*;

import org.junit.Test;

import com.db_engine.DBApp;
import com.db_engine.DBAppException;
import com.db_engine.SQLTerm;
import com.db_engine.Tuple;

public class SelectTestCases {

    // @Test
    // public void selectWithoutIndexWithId() throws DBAppException, IOException,
    // ClassNotFoundException {
    // try {
    // DBApp dbApp = new DBApp();

    // dbApp.init();

    // String strTableName = "Student";

    // Hashtable<String, String> htblColNameType = new Hashtable<String, String>();

    // htblColNameType.put("id", "java.lang.Integer");

    // htblColNameType.put("name", "java.lang.String");

    // htblColNameType.put("gpa", "java.lang.Double");

    // dbApp.createTable(strTableName, "id", htblColNameType);

    // // insert 20 rows

    // for (int i = 0; i < 20; i++) {
    // Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>();
    // htblColNameValue.put("id", i);
    // htblColNameValue.put("name", "Student" + i);
    // htblColNameValue.put("gpa", 3.0 + i);
    // dbApp.insertIntoTable(strTableName, htblColNameValue);
    // }

    // // select all rows

    // SQLTerm[] arrSQLTerms = new SQLTerm[1];
    // String[] strarrOperators = new String[0];

    // arrSQLTerms[0] = new SQLTerm();
    // arrSQLTerms[0]._strTableName = strTableName;
    // arrSQLTerms[0]._strColumnName = "id";
    // arrSQLTerms[0]._strOperator = "=";
    // arrSQLTerms[0]._objValue = 5;

    // Iterator iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

    // for (int i = 0; i < 20; i++) {
    // if (i == 5) {
    // assert iterator.hasNext();
    // Tuple tuple = (Tuple) iterator.next();
    // assert tuple.getColumnValue("id").equals(5);
    // assert tuple.getColumnValue("name").equals("Student5");
    // assert tuple.getColumnValue("gpa").equals(3.0 + 5);
    // } else {
    // assert !iterator.hasNext();
    // }
    // }

    // } finally {
    // cleanUp();
    // }
    // }

    @Test
    public void invalidTableNameException() throws DBAppException, IOException {
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "WrongStudent";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 5;

            // select with invalid Table name
            // final SQLTerm[] arrSQLTerms = arrSQLTerms;
            // final String[] strarrOperators = strarrOperators;
            assertThrows(DBAppException.class, () -> { // select * from WrongStudent where id=5;
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });
        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidOperatorException() throws DBAppException, IOException {
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "==";
            arrSQLTerms[0]._objValue = 5;

            assertThrows(DBAppException.class, () -> { // select * from student where id==5;
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });
        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidDataTypeOnClustKeyException() throws DBAppException, IOException {
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            // select with invalid datatype on clust key
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = "hello";

            assertThrows(DBAppException.class, () -> { // select * from student where id="hello";
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });

        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidDataTypeOnNonClustKeyException() throws DBAppException, IOException {
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            // select with invalid datatype on non-clust key
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 5;

            assertThrows(DBAppException.class, () -> { // select * from student where name=5;
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });

        } finally {
            cleanUp();
        }
    }

    @Test
    public void EmptystrarrOperatorsException() throws DBAppException, IOException {
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            SQLTerm[] arrSQLTerms = new SQLTerm[2];
            String[] EmptystrarrOperators = new String[1];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 5;

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "name";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = "Student5";

            // select with strarrOperators empty
            assertThrows(DBAppException.class, () -> { // Select * from student where id=5 .... name="Student5"; (With
                                                       // empty strarrOperators)
                dbApp.selectFromTable(arrSQLTerms, EmptystrarrOperators);
            });

        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidArgOperatorException1() throws DBAppException, IOException { // Test 1: Base Case - 1 Operator
                                                                                    // (Arg operators : AND, OR, XOR)
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            SQLTerm[] arrSQLTerms = new SQLTerm[2];
            String[] strarrOperators = new String[1];

            // select with invalid strarrOperators Value (Test 1: Base Case - 1 Operator)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 1;

            strarrOperators[0] = "NOT";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 3.2;

            assertThrows(DBAppException.class, () -> { // Select * from student where id=1 NOT gpa=3.2;
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });

        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidArgOperatorException2() throws DBAppException, IOException { // Test 2: 2 Operators, One Valid,
                                                                                    // One Invalid (Arg operators : AND,
                                                                                    // OR, XOR)
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            SQLTerm[] arrSQLTerms = new SQLTerm[2];
            String[] strarrOperators = new String[3];

            // select with invalid strarrOperators Value (Test 2: 2 Operators, One Valid,
            // One Invalid)

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 1;

            strarrOperators[0] = "AND";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 3.2;

            strarrOperators[1] = "NOT";

            arrSQLTerms[2] = new SQLTerm();
            arrSQLTerms[2]._strTableName = "Student";
            arrSQLTerms[2]._strColumnName = "name";
            arrSQLTerms[2]._strOperator = "=";
            arrSQLTerms[2]._objValue = "Student1";

            assertThrows(DBAppException.class, () -> { // Select * from student where id=1 AND gpa=3.2 NOT
                                                       // name="Student1"
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });

        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidArgOperatorException3() throws DBAppException, IOException { // Test 3: Invalid Num of
                                                                                    // ArgOperators (Arg operators :
                                                                                    // AND, OR, XOR)
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            // select with invalid number of strarrOperators Value (Test 3)
            SQLTerm[] arrSQLTerms = new SQLTerm[3];
            String[] strarrOperators = new String[1];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 1;

            strarrOperators[0] = "AND";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 3.2;

            arrSQLTerms[2] = new SQLTerm();
            arrSQLTerms[2]._strTableName = "Student";
            arrSQLTerms[2]._strColumnName = "name";
            arrSQLTerms[2]._strOperator = "=";
            arrSQLTerms[2]._objValue = "Student1";

            assertThrows(DBAppException.class, () -> { // Select * from student where id =1 AND GPA = 3.2 ...name =
                                                       // Student1;
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });

        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidArgOperatorException4() throws DBAppException, IOException { // Test 4: Invalid Num of
                                                                                    // ArgOperators (Arg operators :
                                                                                    // AND, OR, XOR)
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20
            // select with invalid number of strarrOperators Value (Test 4)
            SQLTerm[] arrSQLTerms = new SQLTerm[3];
            String[] strarrOperators = new String[3];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 1;

            strarrOperators[0] = "AND";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 3.2;

            strarrOperators[1] = "AND";

            arrSQLTerms[2] = new SQLTerm();
            arrSQLTerms[2]._strTableName = "Student";
            arrSQLTerms[2]._strColumnName = "name";
            arrSQLTerms[2]._strOperator = "=";
            arrSQLTerms[2]._objValue = "Student1";

            strarrOperators[2] = "OR";

            assertThrows(DBAppException.class, () -> { // Select * from student where id=1 AND gpa=3.2 AND
                                                       // name="Student1" OR;
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });
        } finally {
            cleanUp();
        }
    }

    @Test
    public void invalidJoinException() throws DBAppException, IOException { // Joins are not allowed in our system
                                                                            // whatsoever
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20); // Initializes Student Table with Columns Id, name, GPA , with rows count n
                                            // = 20

            // select with joining (two different tables)
            SQLTerm[] arrSQLTerms = new SQLTerm[2];
            String[] strarrOperators = new String[1];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 1;

            strarrOperators[0] = "AND";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Instructor";
            arrSQLTerms[1]._strColumnName = "id";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 4;

            assertThrows(DBAppException.class, () -> { // Selecting from Student and from Instructor
                dbApp.selectFromTable(arrSQLTerms, strarrOperators);
            });
        } finally {
            cleanUp();
        }
    }

    @Test
    public void ExistingTuplesExactValues() throws DBAppException, ClassNotFoundException, IOException {
        // testing the base case: Searching for a tuple that exists using exact values.

        try {

            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20);

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            // Test with Clustering key
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 5;

            Iterator iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert tuple.getColumnValue("id").equals(5);
                assert tuple.getColumnValue("name").equals("Student5");
                assert tuple.getColumnValue("gpa").equals(3.0 + 5);
            }

            // Test with Non-clustering key (Test 1: String)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = "Student2";

            Iterator iterator2 = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

            while (iterator2.hasNext()) {
                Tuple tuple = (Tuple) iterator2.next();
                assert tuple.getColumnValue("id").equals(2);
                assert tuple.getColumnValue("name").equals("Student2");
                assert tuple.getColumnValue("gpa").equals(3.0 + 2);
            }

            // Test with Non-clustering key (Test 2: Double)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "gpa";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 3.6;

            Iterator iterator3 = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

            while (iterator3.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert tuple.getColumnValue("id").equals(6);
                assert tuple.getColumnValue("name").equals("Student6");
                assert tuple.getColumnValue("gpa").equals(3.0 + 6);
            }
        } finally {
            cleanUp();
        }

    }

    @Test
    public void ExistingTuplesRangedValues() throws DBAppException, ClassNotFoundException, IOException {
        // select tuples that exist in my table using ranged values
        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20);

            int count = 0;

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            // Test on Clustering Key (Test 1)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = 5;

            Iterator iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select *from Student where id <
                                                                                     // 5;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (int) tuple.getColumnValue("id") < 5;
                count++;
            }

            assert count == 5;

            count = 0;

            // Test on Clustering Key (Test 2)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "<=";
            arrSQLTerms[0]._objValue = 5;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators);// Select *from Student where id <= 5;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (int) tuple.getColumnValue("id") <= 5;
                count++;
            }

            assert count == 6;

            count = 0;

            // Test on Clustering Key (Test 3)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = 10;

            strarrOperators[0] = "AND";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "id";
            arrSQLTerms[1]._strOperator = ">";
            arrSQLTerms[1]._objValue = 5;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where id < 10 AND
                                                                            // id > 5;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (int) tuple.getColumnValue("id") < 10 && (int) tuple.getColumnValue("id") > 5;
                count++;
            }

            assert count == 4;

            count = 0;

            // Test on Clustering Key (Test 4) (Same as Test 3 but change order of range)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];

            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = ">";
            arrSQLTerms[0]._objValue = 5;

            strarrOperators[0] = "AND";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "id";
            arrSQLTerms[1]._strOperator = "<";
            arrSQLTerms[1]._objValue = 10;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where id > 5 AND
                                                                            // id < 10;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (int) tuple.getColumnValue("id") < 10 && (int) tuple.getColumnValue("id") > 5;
                count++;
            }

            assert count == 4;

            count = 0;

            // Test on Non-Clustering Key (Test 1 : Double)
            arrSQLTerms = new SQLTerm[1];
            strarrOperators = new String[0];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "gpa";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = 3.5;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where gpa < 3.5;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (double) tuple.getColumnValue("gpa") < 3.5;
                count++;
            }

            assert count == 4;

            count = 0;

            // Test on Non-Clustering Key (Test 2 : Double)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "gpa";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = 3.5;

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = ">";
            arrSQLTerms[1]._objValue = 3.9;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where gpa < 3.5 (4)
                                                                            // OR
                                                                            // id > 3.9 (10) --> 4+10 = 14 tuples;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (double) tuple.getColumnValue("gpa") < 3.5 || (int) tuple.getColumnValue("gpa") > 3.9;
                count++;
            }

            assert count == 14;

            count = 0;

            // Test on Non-Clustering Key (Test 3 : Double)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "gpa";
            arrSQLTerms[0]._strOperator = "<=";
            arrSQLTerms[0]._objValue = 3.5;

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = ">=";
            arrSQLTerms[1]._objValue = 3.9;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where gpa <= 3.5
                                                                            // (5)
                                                                            // OR
                                                                            // id >= 3.9 (11) --> 5+11 = 16 tuples;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (double) tuple.getColumnValue("gpa") <= 3.5 || (int) tuple.getColumnValue("gpa") >= 3.9;
                count++;
            }

            assert count == 16;

            count = 0;

            // Test on Non-Clustering Key (Test 4 : Double)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "gpa";
            arrSQLTerms[0]._strOperator = "<=";
            arrSQLTerms[0]._objValue = 3.5;

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = ">";
            arrSQLTerms[1]._objValue = 3.9;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where gpa <= 3.5
                                                                            // (5)
                                                                            // OR
                                                                            // id > 3.9 (10) --> 5+10 = 15 tuples;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (double) tuple.getColumnValue("gpa") <= 3.5 || (int) tuple.getColumnValue("gpa") > 3.9;
                count++;
            }

            assert count == 15;

            count = 0;

            // Test on Non-Clustering Key (Test 5 : Double)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "gpa";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = 3.5;

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "gpa";
            arrSQLTerms[1]._strOperator = ">=";
            arrSQLTerms[1]._objValue = 3.9;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where gpa < 3.5
                                                                            // (4)
                                                                            // OR
                                                                            // id >= 3.9 (11) --> 4+11 = 15 tuples;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (double) tuple.getColumnValue("gpa") < 3.5 || (int) tuple.getColumnValue("gpa") >= 3.9;
                count++;
            }

            assert count == 15;

            count = 0;

            // Test on Non-Clustering Key (Test 1 : String)
            arrSQLTerms = new SQLTerm[1];
            strarrOperators = new String[0];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "!=";
            arrSQLTerms[0]._objValue = "Student5";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name !=
                                                                            // "Student5";

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert !tuple.getColumnValue("name").equals("Student5");
                count++;
            }

            assert count == 19;

            count = 0;

            // Test on Non-Clustering Key (Test 2 : String)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[0];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = "Student5";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name <
                                                                            // "Student5";

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert ((String) tuple.getColumnValue("name")).compareTo("Student5") < 0;
                count++;
            }

            assert count == 5;

            count = 0;

            // Test on Non-Clustering Key (Test 3 : String)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[0];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "<=";
            arrSQLTerms[0]._objValue = "Student5";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name <=
                                                                            // "Student5";

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert ((String) tuple.getColumnValue("name")).compareTo("Student5") <= 0;
                count++;
            }

            assert count == 6;

            count = 0;

            // Test on Non-Clustering Key (Test 4 : String)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = "Student5";

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "name";
            arrSQLTerms[1]._strOperator = ">";
            arrSQLTerms[1]._objValue = "Student15";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name <
                                                                            // "Student5"(5) OR name > "Student15" (4)
                                                                            // --> 5+4= 9 records;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert ((String) tuple.getColumnValue("name")).compareTo("Student5") < 0
                        || ((String) tuple.getColumnValue("name")).compareTo("Student15") > 0;
                count++;
            }

            assert count == 9;

            count = 0;

            // Test on Non-Clustering Key (Test 5 : String)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "<=";
            arrSQLTerms[0]._objValue = "Student5";

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "name";
            arrSQLTerms[1]._strOperator = ">";
            arrSQLTerms[1]._objValue = "Student15";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name <=
                                                                            // "Student5"(6) OR name > "Student15" (4)
                                                                            // --> 6+4= 10 records;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert ((String) tuple.getColumnValue("name")).compareTo("Student5") <= 0
                        || ((String) tuple.getColumnValue("name")).compareTo("Student15") > 0;
                count++;
            }

            assert count == 10;

            count = 0;

            // Test on Non-Clustering Key (Test 6 : String)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = "Student5";

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "name";
            arrSQLTerms[1]._strOperator = ">=";
            arrSQLTerms[1]._objValue = "Student15";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name <
                                                                            // "Student5"(5) OR name >= "Student15" (5)
                                                                            // --> 5+5= 10 records;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert ((String) tuple.getColumnValue("name")).compareTo("Student5") < 0
                        || ((String) tuple.getColumnValue("name")).compareTo("Student15") >= 0;
                count++;
            }

            assert count == 10;

            count = 0;

            // Test on Non-Clustering Key (Test 7 : String) (Same as Test 6 but change order
            // of range)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = ">=";
            arrSQLTerms[0]._objValue = "Student15";

            strarrOperators[0] = "OR";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "name";
            arrSQLTerms[1]._strOperator = "<";
            arrSQLTerms[1]._objValue = "Student5";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name >=
                                                                            // "Student15"(5) OR name < "Student5" (5)
                                                                            // --> 5+5= 10 records;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert ((String) tuple.getColumnValue("name")).compareTo("Student5") < 0
                        || ((String) tuple.getColumnValue("name")).compareTo("Student15") >= 0;
                count++;
            }

            assert count == 10;

            count = 0;

        } finally {
            cleanUp();
        }
    }

    @Test
    public void NonExistantTuplesExactValues() throws DBAppException, ClassNotFoundException, IOException {
        // select tuples/values that dont exist in my table using exact values

        try {
            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20);

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            // Test on Clustering Key (Test 1)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 40;

            Iterator iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // select * from Student where id =
                                                                                     // 40;

            assert !iterator.hasNext();

            // Test on Clustering Key (Test 2)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = -1;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // select * from Student where id = -1;

            assert !iterator.hasNext();

            // Test on Non-Clustering Key (Test 1 : String)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = "Harridy";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // select * from Student where name =
                                                                            // "Harridy";

            assert !iterator.hasNext();

            // Test on Non-Clustering Key (Test 2 : String)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = "Student0";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // select * from Student where name <
                                                                            // "Student0";

            assert !iterator.hasNext();

            // Test on Non-Clustering Key (Test 3: String)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = ">";
            arrSQLTerms[0]._objValue = "Student19";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // select * from Student where name >
                                                                            // "Student19";

            assert !iterator.hasNext();

            // Test on Non-Clustering Key (Test 2 : Double)
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "gpa";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 1.0;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

            assert !iterator.hasNext();
        } finally {
            cleanUp();
        }
    }

    @Test
    public void NonExistantTuplesRangedValues() throws DBAppException, ClassNotFoundException, IOException {
        // Select values that dont exist using ranges
        try {

            DBApp dbApp = new DBApp();
            dbApp.init();

            initializeTestTable(dbApp, 20);

            int count = 0;

            SQLTerm[] arrSQLTerms = new SQLTerm[1];
            String[] strarrOperators = new String[0];

            // Test on Non-Clustering Key (Test 1 : String)
            arrSQLTerms = new SQLTerm[2];
            strarrOperators = new String[1];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "<";
            arrSQLTerms[0]._objValue = "Student5";

            strarrOperators[0] = "AND";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "name";
            arrSQLTerms[1]._strOperator = ">";
            arrSQLTerms[1]._objValue = "Student15";

            Iterator iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name
                                                                                     // < "Student5"(5) AND name >
                                                                                     // "Student15" (4) --> = 0 records;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert ((String) tuple.getColumnValue("name")).compareTo("Student5") < 0
                        && ((String) tuple.getColumnValue("name")).compareTo("Student15") > 0;
                count++;
            }

            assert count == 0;

            count = 0;
        } finally {
            cleanUp();
        }
    }

    @Test
    public void testPrecedence() throws DBAppException, IOException, ClassNotFoundException {
        try {
            DBApp dbApp = new DBApp();

            dbApp.init();

            initializeTestTable(dbApp, 20);

            SQLTerm[] arrSQLTerms = new SQLTerm[3];
            String[] strarrOperators = new String[2];

            // Testing OR + AND
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = "Student2";

            strarrOperators[0] = "or";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "name";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = "Student1";

            strarrOperators[1] = "and";

            arrSQLTerms[2] = new SQLTerm();
            arrSQLTerms[2]._strTableName = "Student";
            arrSQLTerms[2]._strColumnName = "name";
            arrSQLTerms[2]._strOperator = "=";
            arrSQLTerms[2]._objValue = "Student1";

            Iterator iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name
                                                                                     // = "Student2" OR name =
                                                                                     // "Student1"
                                                                                     // AND name = "Student1";
            int count = 0;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert tuple.getColumnValue("name").equals("Student1")
                        || tuple.getColumnValue("name").equals("Student2");
                count++;
            }

            assert count == 2;

            count = 0;

            // Testing XOR+AND
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = "Student1";

            strarrOperators[0] = "xor";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "id";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 2;

            strarrOperators[1] = "and";

            arrSQLTerms[2] = new SQLTerm();
            arrSQLTerms[2]._strTableName = "Student";
            arrSQLTerms[2]._strColumnName = "name";
            arrSQLTerms[2]._strOperator = "=";
            arrSQLTerms[2]._objValue = "Student2";

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name =
                                                                            // "Student1" XOR id = 2 AND name =
                                                                            // "Student2";

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert tuple.getColumnValue("name").equals("Student1") || (int) tuple.getColumnValue("id") == 2;
                count++;
            }

            assert count == 2;

            count = 0;

            // Testing XOR+OR
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "name";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = "Student2";

            strarrOperators[0] = "xor";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "id";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 2;

            strarrOperators[1] = "or";

            arrSQLTerms[2] = new SQLTerm();
            arrSQLTerms[2]._strTableName = "Student";
            arrSQLTerms[2]._strColumnName = "id";
            arrSQLTerms[2]._strOperator = "=";
            arrSQLTerms[2]._objValue = 3;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name =
                                                                            // "Student1" XOR id = 2 OR id = 3;

            while (iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert tuple.getColumnValue("name").equals("Student3") && (int) tuple.getColumnValue("id") == 3;
                count++;
            }

            assert count == 1;

            count = 0;

            // Testing XOR+OR+AND
            arrSQLTerms = new SQLTerm[4];
            strarrOperators = new String[3];
            arrSQLTerms[0] = new SQLTerm();
            arrSQLTerms[0]._strTableName = "Student";
            arrSQLTerms[0]._strColumnName = "id";
            arrSQLTerms[0]._strOperator = "=";
            arrSQLTerms[0]._objValue = 1;

            strarrOperators[0] = "xor";

            arrSQLTerms[1] = new SQLTerm();
            arrSQLTerms[1]._strTableName = "Student";
            arrSQLTerms[1]._strColumnName = "id";
            arrSQLTerms[1]._strOperator = "=";
            arrSQLTerms[1]._objValue = 2;

            strarrOperators[1] = "or";

            arrSQLTerms[2] = new SQLTerm();
            arrSQLTerms[2]._strTableName = "Student";
            arrSQLTerms[2]._strColumnName = "id";
            arrSQLTerms[2]._strOperator = "=";
            arrSQLTerms[2]._objValue = 3;

            strarrOperators[2] = "and";

            arrSQLTerms[3] = new SQLTerm();
            arrSQLTerms[3]._strTableName = "Student";
            arrSQLTerms[3]._strColumnName = "id";
            arrSQLTerms[3]._strOperator = "=";
            arrSQLTerms[3]._objValue = 4;

            iterator = dbApp.selectFromTable(arrSQLTerms, strarrOperators); // Select * from Student where name =
                                                                            // "Student1" XOR id = 2 OR id = 3 AND id =
                                                                            // 4;
            while (!iterator.hasNext()) {
                Tuple tuple = (Tuple) iterator.next();
                assert (int) tuple.getColumnValue("id") == 1
                        || (int) tuple.getColumnValue("id") == 2;
                count++;
            }

            assert count == 2;

        } finally {
            cleanUp();
        }
    }

    @Test // TODO
    public void testOperatorCapitilzation() throws IOException, DBAppException {
        try {
            DBApp dbApp = new DBApp();

            dbApp.init();

            initializeTestTable(dbApp, 20);

            SQLTerm[] arrSQLTerms = new SQLTerm[4];
            String[] strarrOperators = new String[3];
        } finally {
            cleanUp();
        }
    }

    private void cleanUp() throws IOException {
        try {
            // delete tables directory

            String tablesPath = "tables/";

            Path dir = Paths.get(tablesPath); // path to the directory
            Files
                    .walk(dir) // Traverse the file tree in depth-first order
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {

                            Files.delete(path); // delete each file or directory
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {

        }

        try {

            // delete Indicies directory

            String indiciesPath = "Indicies/";

            Path dir2 = Paths.get(indiciesPath); // path to the directory
            Files
                    .walk(dir2) // Traverse the file tree in depth-first order
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {

                            Files.delete(path); // delete each file or directory
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {

        }
        try {
            // delete metadata.csv

            File metadata = new File("metadata.csv");
            metadata.delete();
        } catch (Exception e) {

        }

    }

    private void initializeTestTable(DBApp dbApp, int n) {
        try {
            String strTableName = "Student";

            Hashtable<String, String> htblColNameType = new Hashtable<String, String>();

            htblColNameType.put("id", "java.lang.Integer");

            htblColNameType.put("name", "java.lang.String");

            htblColNameType.put("gpa", "java.lang.Double");

            dbApp.createTable(strTableName, "id", htblColNameType);

            // insert n rows

            for (int i = 0; i < n; i++) {
                Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>();
                htblColNameValue.put("id", i);
                htblColNameValue.put("name", "Student" + i);
                htblColNameValue.put("gpa", 3.0 + i);
                dbApp.insertIntoTable(strTableName, htblColNameValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}