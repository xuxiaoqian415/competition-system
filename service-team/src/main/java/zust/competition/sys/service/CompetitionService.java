package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-competition")
public interface CompetitionService {

//    @RequestMapping("/competition/insertStuComp")
//    void insertStuComp(StuComp stuComp);
}
