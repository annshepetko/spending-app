package com.ann.spending.spending.controller;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.dto.request.AddSpendingRequest;
import com.ann.spending.spending.dto.CreateSpendingBody;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import com.ann.spending.spending.interfaces.SpendingPageService;
import com.ann.spending.spending.mapper.CreationSpendingMapper;
import com.ann.spending.spending.mapper.SpendingMapper;
import com.ann.spending.spending.service.SpendingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/spending")
public class SpendingController {

    private final CreationSpendingMapper creationMapping;
    private final SpendingService spendingService;
    private final SpendingMapper spendingMapper;
    private final SpendingPageService spendingPageService;

    public SpendingController(CreationSpendingMapper creationMapping, SpendingService spendingService, SpendingMapper spendingMapper, SpendingPageService spendingPageService) {
        this.creationMapping = creationMapping;
        this.spendingService = spendingService;
        this.spendingMapper = spendingMapper;
        this.spendingPageService = spendingPageService;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SpendingDTO>> retrieveSpendingPage(
            @RequestAttribute("user") User user,
            Pageable pageable
    ){
        return ResponseEntity.ok().body(spendingPageService.findSpendingPage(pageable, user.getId()));
    }

    @PutMapping
    public ResponseEntity<SpendingDTO> updateSpending(@RequestBody SpendingDTO spendingDTO){
        Spending spending = spendingService.update(spendingDTO);

        return ResponseEntity.ok(spendingMapper.mapToDTO(spending));
    }

    @PostMapping
    public void addSpending(@RequestBody AddSpendingRequest addSpendingRequest, @RequestAttribute("user") User user){
        CreateSpendingBody createSpendingBody = creationMapping.mapToSpendingBody(addSpendingRequest, user);
        spendingService.createSpending(createSpendingBody);
    }

    @DeleteMapping("/{id}")
    public void deleteSpending(@PathVariable("id") Long id){
        spendingService.deleteSpending(id);
    }

}
