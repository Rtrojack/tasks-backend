package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController taskController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ValidationException.class)
    public void naoDeveSalvarTarefaSemDescricao() throws ValidationException {
        Task todo = new Task();
        //todo.setTask("Desc");
        todo.setDueDate(LocalDate.now());

        taskController.save(todo);
        Mockito.verify(taskRepo, Mockito.times(0)).save(todo);
    }

    @Test(expected = ValidationException.class)
    public void naoDeveSalvarTarefaSemData() throws ValidationException {
        Task todo = new Task();
        todo.setTask("Desc");
        //todo.setDueDate(LocalDate.now());

        taskController.save(todo);
        Mockito.verify(taskRepo, Mockito.times(0)).save(todo);
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setTask("Desc");
        todo.setDueDate(LocalDate.now());

        taskController.save(todo);

        Mockito.verify(taskRepo, Mockito.times(1)).save(todo);
    }
}
