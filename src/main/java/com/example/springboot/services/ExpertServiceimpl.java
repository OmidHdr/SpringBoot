package com.example.springboot.services;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.dto.ReguestJob;
import com.example.springboot.dto.RemoveExpertFromSubService;
import com.example.springboot.dto.SaveExpert;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.exeption.TasksException;
import com.example.springboot.repository.ExpertRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@Service
public class ExpertServiceimpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final TasksServiceimpl tasksService;
    private final SubTasksimpl subTasksService;

    public ExpertServiceimpl(ExpertRepository expertRepository, TasksServiceimpl tasksService, SubTasksimpl subTasksService) {
        this.expertRepository = expertRepository;
        this.tasksService = tasksService;
        this.subTasksService = subTasksService;
    }

    //section register
    @Override
    public Expert saveExpert(SaveExpert account) throws ExpertException, TasksException, SubTasksException {
        if (account.getFirstName() == null || account.getPassword() == null || !Validation.validString(account.getFirstName()) || !Validation.validString(account.getLastName()))
            throw new ExpertException("wrong firstname or lastname !!");
        if (account.getPassword() == null || !Validation.validPassword(account.getPassword()))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Validation.validateEmail(account.getEmail()))
            throw new ExpertException("Email not valid");
        if (account.getSubTaskName() == null )
            throw new ExpertException("You didn't set subTask");
        SubTasks subtask = subTasksService.findByName(account.getSubTaskName());
        if (subtask == null)
            throw new ExpertException("This Subtask dose not exist please tell admin add it first");
        Expert expert = Expert.builder().firstName(account.getFirstName()).lastName(account.getLastName())
                .email(account.getEmail()).date(LocalDate.now()).username(account.getUsername())
                .password(account.getPassword()).userRole(UserRole.EXPERT).inventory(0)
                .status(false).subTasks(Collections.singletonList(subtask)).build();
        try {
            return expertRepository.save(expert);
        } catch (Exception e) {
            throw new ExpertException("Failed to save expert ! ");
        }
    }

    //section login
    @Override
    public Expert findByUsernameAndPassword(String username, String password) throws ExpertException {
        final Expert expert = expertRepository.findByUsernameAndPassword(username,password);
        if (expert == null)
            throw new ExpertException("wrong username or password");
        if (!expert.getStatus())
            throw new ExpertException("this expert is deactivate first admin should confirm");
        return expert;
    }

    //section confirm
    @Override
    public Expert confirmExpert(Expert expert) throws ExpertException {
        final Expert byUsername = expertRepository.findByUsername(expert.getUsername());
        if (byUsername == null)
            throw new ExpertException("dear admin please give expert username");
        byUsername.setStatus(true);
        return expertRepository.save(byUsername);
    }

    //section reguest job
    @Override
    public Expert requestForNewJob(ReguestJob job) throws ExpertException, TasksException, SubTasksException {
        if (job.getTaskName() == null)
            throw new ExpertException("you didn't set task");
        if (job.getSubTaskName() == null)
            throw new ExpertException("you didn't set subtask");
        if (job.getUsername() == null || job.getPassword()== null)
            throw new ExpertException("you didn't set username or password");
        final Expert byUserPass = expertRepository.findByUsernameAndPassword(job.getUsername(), job.getPassword());
        if (byUserPass == null)
            throw new ExpertException("wrong username or password");
        if (!byUserPass.getStatus())
            throw new ExpertException("this expert is deactivate please tell admin to active it first");
        final Tasks task = tasksService.findByName(job.getTaskName());
        final SubTasks subTask = subTasksService.findByName(job.getSubTaskName());
        final List<SubTasks> subTasks = byUserPass.getSubTasks();
        subTasks.add(subTask);
        return expertRepository.save(byUserPass);
    }

    //    section password
    @Override
    public Expert changePassword(ChangePassword changePassword) throws ExpertException {
        if (changePassword.getPassword() == null || changePassword.getUsername() == null || changePassword.getNewPassword() == null)
            throw new ExpertException("you should fill all of the items");
        if (!Validation.validPassword(changePassword.getNewPassword()))
            throw new ExpertException("password should have at least a capital Letter and a minimal Letter and 8 character");
        Expert byUsername = findByUsernameAndPassword(changePassword.getUsername(), changePassword.getPassword());
        byUsername.setPassword(changePassword.getNewPassword());
        return expertRepository.save(byUsername);
    }

    //section showUnconfirmed
    public List<Expert> showUnconfirmExpert() throws ExpertException {
        final List<Expert> allByStatus = expertRepository.findAllByStatus(false);
        if (allByStatus.size() < 1)
            throw new ExpertException("there is no expert to confirm");
        return allByStatus;
    }

    //section removeSubService
    @Override
    public Expert removeExpertFromSubtask(RemoveExpertFromSubService remove) throws ExpertException, SubTasksException, TasksException {
        if (remove.getUsername() == null || remove.getSubtaskName() == null)
            throw new ExpertException("you should fill all of the items");
        Expert expert = expertRepository.findByUsername(remove.getUsername());
        SubTasks subtask = subTasksService.findByName(remove.getSubtaskName());
        if (expert == null)
            throw new ExpertException("this expert dose not exist");
        if (subtask == null)
            throw new ExpertException("this Subtask dose not exist");
        List<SubTasks> subTasks = expert.getSubTasks();
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getName().equals(subtask.getName())) {
                subTasks.remove(i);
            }
        }
        expert.setSubTasks(subTasks);
        return expertRepository.save(expert);
    }

}
