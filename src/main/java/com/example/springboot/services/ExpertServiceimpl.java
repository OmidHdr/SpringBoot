package com.example.springboot.services;

import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.repository.ExpertRepository;
import com.example.springboot.repository.SubTasksRepository;
import com.example.springboot.repository.TasksRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpertServiceimpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final TasksRepository tasksRepository;
    private final SubTasksRepository subRepository;

    public ExpertServiceimpl(ExpertRepository expertRepository, TasksRepository tasksRepository, SubTasksRepository subRepository) {
        this.expertRepository = expertRepository;
        this.tasksRepository = tasksRepository;
        this.subRepository = subRepository;
    }

    //section register
    @Override
    public Expert saveExpert(Expert account) throws ExpertException {
        final String password = account.getPassword();
        final String email = account.getEmail();
        if (account.getFirstName() == null || account.getPassword() == null || !Validation.validString(account.getFirstName()) || !Validation.validString(account.getLastName()))
            throw new ExpertException("wrong firstname or lastname !!");
        if (account.getPassword() == null || !Validation.validPassword(password))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Validation.validateEmail(email))
            throw new ExpertException("Email not valid");
        if (account.getTasks().getName() == null)
            throw new ExpertException("You didn't set task");
        if (account.getSubTasks().getName() == null)
            throw new ExpertException("You didn't set subTask");
        final String taskName = account.getTasks().getName();
        final Tasks findTask = tasksRepository.findByName(taskName);
        if (findTask.getName() == null)
            throw new ExpertException("This task dose not exist please tell admin add it first");
        final String subTaskName = account.getSubTasks().getName();
        final SubTasks findSub = subRepository.findByName(subTaskName);
        if (findSub.getName() == null)
            throw new ExpertException("This Subtask dose not exist please tell admin add it first");
        account.setInventory(0);
        account.setStatus(false);
        account.setDate(LocalDate.now());
        account.setSubTasks(findSub);
        account.setTasks(findTask);
        try {
            return expertRepository.save(account);
        } catch (Exception e) {
            throw new ExpertException("Failed to save expert ! ");
        }
    }

    //section login
    @Override
    public Expert findByUsernameAndPassword(String username, String password) throws ExpertException {
        final Expert expert = expertRepository.findByUsernameAndPassword(username, password);
        if (expert == null)
            throw new ExpertException("Expert not found");
        if (!expert.getStatus())
            throw new ExpertException("this expert is deactivate first admin should confirm");
        return expert;
    }

    @Override
    public Expert confirmExpert(Expert expert) throws ExpertException {
        final Expert byUsername = expertRepository.findByUsername(expert.getUsername());
        if (byUsername==null)
            throw new ExpertException("dear admin please give expert username");
        byUsername.setStatus(true);
        return expertRepository.save(byUsername);
    }

    @Override
    public Expert requestForNewJob(Expert expert, SubTasks sub) {
        Expert newExpert = Expert.builder().firstName(expert.getFirstName()).lastName(expert.getLastName())
                .email(expert.getEmail()).password(expert.getPassword()).userRole(UserRole.EXPERT)
                .status(true).tasks(expert.getTasks()).inventory(expert.getInventory())
                .subTasks(sub).build();
        return expertRepository.save(newExpert);
    }

    @Override
    public Expert changePassword(Expert expert, String newPassword) {
        expert.setPassword(newPassword);
        return expertRepository.save(expert);
    }

    @Override
    public void removeSubServiceFromExpert(Expert expert, SubTasks sub) {
        Expert newExpert = Expert.builder().firstName(expert.getFirstName()).lastName(expert.getLastName())
                .email(expert.getEmail()).password(expert.getPassword()).userRole(UserRole.EXPERT)
                .status(true).tasks(expert.getTasks()).inventory(expert.getInventory())
                .subTasks(sub).build();
        expertRepository.delete(newExpert);
    }

    public List<Expert> showUnconfirmExpert() {
        final List<Expert> allByStatus = expertRepository.findAllByStatus(false);
        return allByStatus;
    }
}
