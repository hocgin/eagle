package in.hocg.eagle.modules.com.controller;


import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.pojo.vo.district.DistrictComplexVo;
import in.hocg.eagle.modules.com.pojo.vo.district.DistrictTreeVo;
import in.hocg.eagle.modules.com.service.DistrictService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @ApiOperation("导入城市区域(高德) - 城市区域")
    @PostMapping("/import-amap")
    public Result<Void> importByAMap() {
        service.importByAMapUrl();
        return Result.success();
    }

    @ApiOperation("获取省/市/区(树型) - 城市区域")
    @GetMapping("/tree")
    public Result<List<DistrictTreeVo>> tree() {
        return Result.success(service.tree());
    }

    @ApiOperation("获取指定下一级区域列表 - 城市区域")
    @GetMapping
    public Result<List<DistrictComplexVo>> selectChildrenByAdcode(@RequestParam(value = "adcode", required = false, defaultValue = "100000") String adcode) {
        return Result.success(service.selectChildrenByAdcode(adcode));
    }

    @ApiOperation("获取省份列表 - 城市区域")
    @GetMapping("/province")
    public Result<List<DistrictComplexVo>> getProvince() {
        return Result.success(service.getProvince());
    }

    @ApiOperation("获取城市列表 - 城市区域")
    @GetMapping("/city")
    public Result<List<DistrictComplexVo>> getCity() {
        return Result.success(service.getCity());
    }

    @ApiOperation("获取国家列表 - 城市区域")
    @GetMapping("/county")
    public Result<List<DistrictComplexVo>> getCounty() {
        return Result.success(service.getCounty());
    }

    @ApiOperation("获取地区列表 - 城市区域")
    @GetMapping("/district")
    public Result<List<DistrictComplexVo>> getDistrict() {
        return Result.success(service.getDistrict());
    }
}

