package com.example.openfeaturedemo.config;

import com.example.openfeaturedemo.service.GoodsService;
import dev.openfeature.sdk.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/*
    AOP切面方法，將根據Flag來決定使用 oldGoodsService 還是 newGoodsService，
    模擬API的新舊架構切換，並且透過FeatBit來控制使用新舊架構流量的比例，逐步轉移至新架構。
*/

@Aspect
@Component
public class ArchitectureSwitchAspect {

    private final GoodsService oldGoodsService;

    private final GoodsService newGoodsService;

    public ArchitectureSwitchAspect(@Qualifier("oldGoodsService") GoodsService oldGoodsService,
                                    @Qualifier("newGoodsService") GoodsService newGoodsService) {
        this.oldGoodsService = oldGoodsService;
        this.newGoodsService = newGoodsService;
    }

    // 主要的AOP方法，根據Flag來決定使用 oldGoodsService 還是 newGoodsService
    @Around("@annotation(com.example.openfeaturedemo.annotation.UseNewArchitecture)")
    public Object switchArchitecture(ProceedingJoinPoint joinPoint) throws Throwable {
        // 獲取flag的值
        boolean useNewArchitecture = getFeatureFlagValue();

        // 選擇Service
        GoodsService selectedService = selectService(useNewArchitecture);

        // 調用選擇的Service的方法並處理返回值
        return invokeServiceMethod(joinPoint, selectedService);
    }

    private boolean getFeatureFlagValue() {
        Client featbitClient = OpenFeatureAPI.getInstance().getClient("featbit");
        EvaluationContext evalCtx = new ImmutableContext("user-key", new HashMap<>() {{
            put("case", new Value("switchArchitecture"));
        }});
        return featbitClient.getBooleanValue("usenewarchitecture", false, evalCtx);
    }

    private GoodsService selectService(boolean useNewArchitecture) {
        return useNewArchitecture ? newGoodsService : oldGoodsService;
    }

    // 負責調用選擇的Service的方法並處理返回值
    private Object invokeServiceMethod(ProceedingJoinPoint joinPoint, GoodsService selectedService) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Object[] args = joinPoint.getArgs();

        Method method = selectedService.getClass().getMethod(methodName, parameterTypes);

        // 在AOP層面上處理返回值的包裝，好像不太優
        Object serviceResult;
        try {
            serviceResult = method.invoke(selectedService, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }

        if (signature.getReturnType().equals(ResponseEntity.class)) {
            return ResponseEntity.status(HttpStatus.OK).body(serviceResult);
        } else {
            return selectedService;
        }
    }
}
