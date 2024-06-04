package com.example.openfeaturedemo.config;

import com.example.openfeaturedemo.entity.Goods;
import com.example.openfeaturedemo.service.GoodsService;
import dev.openfeature.sdk.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Aspect
@Component
public class ArchitectureSwitchAspect {

    @Autowired
    @Qualifier("oldGoodsService")
    private GoodsService oldGoodsService;

    @Autowired
    @Qualifier("newGoodsService")
    private GoodsService newGoodsService;

    @Around("@annotation(com.example.openfeaturedemo.annotation.UseNewArchitecture)")
    public Object switchArchitecture(ProceedingJoinPoint joinPoint) throws Throwable {

        Client featbitClient = OpenFeatureAPI.getInstance().getClient("featbit");
        EvaluationContext evalCtx = new ImmutableContext("user-key", new HashMap<String, Value>() {{
            put("case", new Value("switchArchitecture"));
        }});
        boolean useNewArchitecture = featbitClient.getBooleanValue("usenewarchitecture", false, evalCtx);

        Goods goods;
        if (useNewArchitecture) {
            goods = newGoodsService.getGoodsByProductCode((String) joinPoint.getArgs()[0]);
        } else {
            goods = oldGoodsService.getGoodsByProductCode((String) joinPoint.getArgs()[0]);
        }

        if (goods == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }
}
