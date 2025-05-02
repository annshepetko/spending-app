package com.ann.spending.spending;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.dto.AddSpendingRequest;
import com.ann.spending.spending.dto.CreateSpendingBody;
import com.ann.spending.spending.mapper.CreationSpendingMapper;
import com.ann.spending.spending.service.SpendingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/spending")
public class SpendingController {

    private final CreationSpendingMapper creationMapping;
    private final SpendingService spendingService;

    public SpendingController(CreationSpendingMapper creationMapping, SpendingService spendingService) {
        this.creationMapping = creationMapping;
        this.spendingService = spendingService;
    }


    @PostMapping
    public void addSpending(@RequestBody AddSpendingRequest addSpendingRequest, @RequestAttribute("user") User user){
        CreateSpendingBody createSpendingBody = creationMapping.mapToSpendingBody(addSpendingRequest, user);
        spendingService.createSpending(createSpendingBody);
    }

}
