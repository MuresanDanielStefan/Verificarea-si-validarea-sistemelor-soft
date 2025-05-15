package tasks.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.model.LinkedTaskList;
import tasks.model.Task;
import tasks.model.TaskList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static tasks.repository.TaskRepository.read;

class TaskRepositoryTest {

    @Test
    public void testReadSingleNonRepeatedTask() throws IOException {
        TaskList tasks = new LinkedTaskList();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(output);

        dataOutput.writeInt(1); // 1 task
        dataOutput.writeInt(5); // length of title
        dataOutput.writeUTF("Task1");
        dataOutput.writeBoolean(true); // active
        dataOutput.writeInt(0); // interval = 0 => non-repetitive
        dataOutput.writeLong(100000L); // start time

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        read(tasks, input);

        assertEquals(1, tasks.size());
        Task task = tasks.getTask(0);
        assertEquals("Task1", task.getTitle());
        assertTrue(task.isActive());
        assertEquals(new Date(100000L), task.getTime());
    }

    @Test
    public void testReadSingleRepeatedTask() throws IOException {
        TaskList tasks = new LinkedTaskList();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(output);

        dataOutput.writeInt(1); // 1 task
        dataOutput.writeInt(5); // title length
        dataOutput.writeUTF("Task2");
        dataOutput.writeBoolean(false); // inactive
        dataOutput.writeInt(3600); // interval
        dataOutput.writeLong(100000L); // start time
        dataOutput.writeLong(200000L); // end time

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        read(tasks, input);

        assertEquals(1, tasks.size());
        Task task = tasks.getTask(0);
        assertEquals("Task2", task.getTitle());
        assertFalse(task.isActive());
        assertEquals(3600, task.getRepeatInterval());
        assertEquals(new Date(100000L), task.getStartTime());
        assertEquals(new Date(200000L), task.getEndTime());
    }


    @Test
    public void testReadWithMockedTaskList() throws IOException {
        TaskList mockList = Mockito.mock(TaskList.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(output);

        dataOutput.writeInt(1);
        dataOutput.writeInt(4);
        dataOutput.writeUTF("Test");
        dataOutput.writeBoolean(true);
        dataOutput.writeInt(0);
        dataOutput.writeLong(50000L);

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        read(mockList, input);

        Mockito.verify(mockList, Mockito.times(1)).add(Mockito.any(Task.class));
    }


    @Test
    public void testReadMultipleTasksWithMock() throws IOException {
        TaskList mockList = Mockito.mock(TaskList.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(output);

        dataOutput.writeInt(2);

        // Task 1
        dataOutput.writeInt(3);
        dataOutput.writeUTF("A1");
        dataOutput.writeBoolean(true);
        dataOutput.writeInt(0);
        dataOutput.writeLong(123L);

        // Task 2
        dataOutput.writeInt(3);
        dataOutput.writeUTF("A2");
        dataOutput.writeBoolean(false);
        dataOutput.writeInt(10);
        dataOutput.writeLong(200L);
        dataOutput.writeLong(300L);

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        read(mockList, input);

        Mockito.verify(mockList, Mockito.times(2)).add(Mockito.any(Task.class));
    }


}