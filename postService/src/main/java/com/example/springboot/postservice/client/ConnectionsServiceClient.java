package com.example.springboot.postservice.client;


import com.example.springboot.postservice.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service", path = "/api/v1/connections/core", url = "${CONNECTION_SERVICE_URI:}")
public interface ConnectionsServiceClient {

    @GetMapping(path = "/{userId}/second-degree")
    List<PersonDto> getSecondDegreeConnection(@PathVariable Long userId);


}
