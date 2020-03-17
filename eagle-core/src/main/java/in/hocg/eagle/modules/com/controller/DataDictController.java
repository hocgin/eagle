package in.hocg.eagle.modules.com.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.KeyValue;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.pojo.qo.datadict.DataDictDeleteQo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.DataDictInsertQo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.DataDictUpdateQo;
import in.hocg.eagle.modules.com.pojo.qo.datadict.DataDictSearchQo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.DataDictComplexVo;
import in.hocg.eagle.modules.com.pojo.vo.datadict.DataDictSearchVo;
import in.hocg.eagle.modules.com.service.DataDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * [基础模块] 数据字典表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/data-dict")
public class DataDictController {
    private final DataDictService service;

    @UseLogger("查询数据字典项")
    @GetMapping("/{code:\\w+}")
    public Result<List<KeyValue>> selectListDataDictItem(@PathVariable("code") String code) {
        return Result.success(service.selectListDictItemByCode(code));
    }

    @UseLogger("查询数据字典详情")
    @GetMapping("/{id:\\d+}:complex")
    public Result<DataDictComplexVo> selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("删除数据字典")
    @DeleteMapping
    public Result<Void> batchDelete(@Validated @RequestBody DataDictDeleteQo qo) {
        service.batchDelete(qo);
        return Result.success();
    }

    @UseLogger("新增数据字典")
    @PostMapping
    public Result<Void> insertOne(@Validated @RequestBody DataDictInsertQo qo) {
        service.insertOne(qo);
        return Result.success();
    }

    @UseLogger("更新数据字典")
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable Long id,
                                  @Validated @RequestBody DataDictUpdateQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }

    @UseLogger("搜索数据字典列表")
    @PostMapping("/_search")
    public Result<IPage<DataDictSearchVo>> search(@Validated @RequestBody DataDictSearchQo qo) {
        return Result.success(service.search(qo));
    }

}

