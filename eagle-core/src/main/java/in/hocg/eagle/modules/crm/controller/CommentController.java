package in.hocg.eagle.modules.crm.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.crm.pojo.qo.comment.ChildCommentPagingQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPostQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.CommentPutQo;
import in.hocg.eagle.modules.crm.pojo.qo.comment.RootCommentPagingQo;
import in.hocg.eagle.modules.crm.pojo.vo.comment.CommentComplexVo;
import in.hocg.eagle.modules.crm.pojo.vo.comment.RootCommentComplexVo;
import in.hocg.eagle.modules.crm.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [评论模块] 评论表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService service;

    @PutMapping("/{id:\\d+}")
    @UseLogger("更新 - 评论")
    public Result<Void> updateOne(@PathVariable("id") Long id,
                                  @Validated @RequestBody CommentPutQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }

    @PostMapping
    @UseLogger("新增 - 评论")
    public Result<Void> insertOne(@Validated @RequestBody CommentPostQo qo) throws Throwable {
        service.insertOne(qo);
        return Result.success();
    }

    @PostMapping("/_paging")
    @UseLogger("分页查询根 - 根评论")
    public Result<IPage<RootCommentComplexVo>> pagingRootComment(@Validated @RequestBody RootCommentPagingQo qo) throws Throwable {
        return Result.success(service.pagingRootComment(qo));
    }

    @PostMapping("/{parentId:\\d+}/_paging")
    @UseLogger("分页查询 - 子评论")
    public Result<IPage<CommentComplexVo>> pagingChildComment(@PathVariable("parentId") Long parentId,
                                                              @RequestBody ChildCommentPagingQo qo) {
        qo.setParentId(parentId);
        return Result.success(service.pagingChildComment(qo));
    }

}

