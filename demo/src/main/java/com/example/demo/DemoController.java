package com.example.demo;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController {


    private final DemoRepository demoRepository;

    public DemoController(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }
    @PostMapping
    public DemoEntity save(@RequestBody DemoEntity demoEntity) {
        return demoRepository.save(demoEntity);
    }
    @GetMapping("/{id}")
    public DemoEntity findById(@PathVariable Long id) {
        return demoRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<DemoEntity> findAll() {
        return demoRepository.findAll();
    }

}
