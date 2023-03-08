package com.example.springboot.services;

import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.repository.SubTasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SubTasksimplTest {

    @Mock
    private SubTasksRepository repository;

    @InjectMocks
    private SubTasksimpl subService;

    /*
    @Test
    void saveSubTask() throws SubTasksException {
        Tasks task = Tasks.builder().name("house").build();
        SubTasks subTasks = SubTasks.builder().name("washing Dishes").task(task).description("cleaning hole house")
                .basePrice(120000L).build();
        BDDMockito.given(repository.save(subTasks)).willReturn(subTasks);
        final SubTasks result = subService.saveSubTask(subTasks);
        assertThat(result).isNotNull();
    }
     */
}