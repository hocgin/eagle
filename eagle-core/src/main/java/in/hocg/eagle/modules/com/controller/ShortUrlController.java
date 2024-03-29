package in.hocg.eagle.modules.com.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.pojo.ro.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlPagingQo;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.shorturl.ShortUrlComplexVo;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [基础模块] 短链接表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/short-url")
public class ShortUrlController {
    private final ShortUrlService service;

    @PostMapping({"/_paging"})
    @UseLogger("分页查询 - 短链接")
    public Result<IPage<ShortUrlComplexVo>> paging(@Validated @RequestBody ShortUrlPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @PostMapping
    @UseLogger("新增 - 短链接")
    public Result insertOne(@Validated({Insert.class}) @RequestBody ShortUrlSaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @PutMapping("/{id:\\d+}")
    @UseLogger("修改 - 短链接")
    public Result updateOne(@PathVariable Long id, @Validated({Update.class}) @RequestBody ShortUrlSaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }

}

