package tasks.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.provider.ValueSource;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskIOTest {

    private ArrayTaskList TaskIO;


    @BeforeEach
    void setUp() {
        TaskIO = new ArrayTaskList();
    }


    @Test
    @EnabledIfSystemProperty(named = "os.name", matches = "Windows")
    @DisplayName("Add Valid Task ECP")
    void testECP_1() {
        assertEquals(TaskIO.size(), 0);
        Task task = new Task("Doing things", new Date(2024, 4, 5, 10, 0));
        TaskIO.add(task);
        assertEquals(TaskIO.size(), 1);
    }

    @Test
    @Tag("slow")
    @Timeout(5)
    @RepeatedTest(5)
    @DisplayName("Add Invalid Task ECP")
    void testECP_2() {
        assertEquals(TaskIO.size(), 0);
        try {
            Task task = new Task("Doing things", new Date(2024, 4, 5, 10, 1) , new Date(2024, 4, 5, 10, 1), -1);
            TaskIO.add(task);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "interval should be > 1");
        }
        assertEquals(TaskIO.size(), 0);
    }

    @Test
    void testECP_3(){
        assertEquals(TaskIO.size(), 0);
        try {
            Task task = new Task("Doing things", new Date(2026, 4, 5, 10, 1) , new Date(2024, 4, 5, 15, 1), 100);
            TaskIO.add(task);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "interval should be > 1");
        }
        assertEquals(TaskIO.size(), 1);
    }


    @Test
    @DisplayName("Add Valid Task BVA")
    void testBVA_1() {
        assertEquals(TaskIO.size(), 0);
        Task task = new Task();
        TaskIO.add(task);
        assertEquals(TaskIO.size(), 1);
    }

    @Test
    @DisplayName("Add Invalid Task BVA")
    void testBVA_2() {
        assertEquals(TaskIO.size(), 0);
        try {
            Task task = new Task("", new Date(-1));
            TaskIO.add(task);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Time cannot be negative");
        }
        assertEquals(TaskIO.size(), 0);
    }

    @Test
    void testBVA_3(){
        try {
            for (int i = 0; i < 1000000; i++) {
                Task task = new Task();
                TaskIO.add(task);
            }
        }
        catch (OutOfMemoryError e ){
            assert (false);
        }
        assert (true);
    }

    @Test
    void testBVA_4(){
        try {
            for (int i = 0; i < 10000001; i++) {
                Task task = new Task();
                TaskIO.add(task);
            }
            assert (true);
        }
        catch (OutOfMemoryError e ){
            assert (true);
        }
    }

}