package com.carrentalbackend.service;

import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.model.enumeration.MsgRecipient;
import com.carrentalbackend.model.enumeration.MsgTitles;
import com.carrentalbackend.model.temporary.Msg;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class MsgService {
    private final OfficeRepository officeRepository;
    private final EmployeeRepository employeeRepository;
    public List<Msg> getAllByRecipient(MsgRecipient recipient) {
        return switch (recipient) {
            case ADMIN -> getMsgsForAdmin();
            default -> new ArrayList<>();
        };
    }
//TODO implement!
    private List<Msg> getMsgsForAdmin() {
        List<Msg> result = new ArrayList<>();
        result.addAll(checkNumberOfManagers());

        return result;
    }

    private List<Msg> checkNumberOfManagers() {
        List<Msg> result = new ArrayList<>();
        List<Long> officeIds = officeRepository.getAllOfficeIds();
        for (Long officeId : officeIds) {
            int managers = getNumberOfManagersInOffice(officeId);
            if (managers != 1){
                String content = createIncorrectNumberOfManagersContent(officeId, managers);
                result.add(new Msg(MsgTitles.WARNING_MANAGERS.title, content, MsgRecipient.ADMIN));
            }
        }
        return result;
    }

    private String createIncorrectNumberOfManagersContent(Long officeId, int managers) {
        return ("The number of managers in office with id = " + officeId + " equals " + managers);
    }

    private Integer getNumberOfManagersInOffice(Long officeId) {
        return employeeRepository.countAllByBranchOffice_IdAndJobPosition(officeId, JobPosition.MANAGER);
    }
}
