package in.hocg.eagle.modules.com.controller;


import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [基础模块] 城市规划表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/district")
public class DistrictController {
    private final DistrictService service;

    @PostMapping("/import-amap")
    public Result importByAMap() {
        service.importByAMapUrl();
        return Result.success();
    }

    @GetMapping("/tree")
    public Result tree() {
        return Result.success(service.tree());
    }

    @GetMapping
    public Result selectChildrenByAdcode(@RequestParam(value = "adcode", required = false, defaultValue = "100000") String adcode) {
        return Result.success(service.selectChildrenByAdcode(adcode));
    }

    @GetMapping("/province")
    public Result getProvince() {
        return Result.success(service.getProvince());
    }

    @GetMapping("/city")
    public Result getCity() {
        return Result.success(service.getCity());
    }

    @GetMapping("/county")
    public Result getCounty() {
        return Result.success(service.getCounty());
    }
}

